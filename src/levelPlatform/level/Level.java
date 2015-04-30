package levelPlatform.level;
import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gameEngine.actions.ActionName;
import gameEngine.actions.GroovyAction;
import gameEngine.components.GroovyComponent;
import gameEngine.components.HUDGetter;
import gameEngine.components.HUDInterface;
import resources.constants.INT;
import screen.screenmodels.CollisionMap;
import sprite.Sprite;
import util.DialogUtil;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import levelPlatform.LevelPlatform;

/**
 * 
 * Contains all information, methods for
 * each scene/level
 *
 * Subclass of LevelPlatform, which contains functionality such as:
 * 	- width
 * 	- height
 * 	- Sprites
 * 
 * 	Level adds functionality on top such as
 * 	- A "Player" Sprite
 * 	- Controls
 * 
 */
public class Level extends LevelPlatform {

	// Static Variables
	public static final int X = 0;
	public static final int Y = 1;

	
	// Instance Variables
	private List<Sprite> playerSpriteList;
	private CollisionTable collisionTable;
	private IntConsumer nextLevelMethod;
	private Map<Sprite, Integer> goalMap;
	private String myBackgroundPath;

	// Getters & Setters
	/**
	 * @return a controlMap which might change depending on the behaviours for each level
	 */
	public Map<KeyCode,Action> controlMap(Sprite playerSprite) {
		Map<KeyCode,Action> controlMap = new HashMap<>();
		playerSprite.actionList().forEach(action -> action.setUpKey(controlMap));
		
		return controlMap;

	}	
	
	public void addPlayerSprite(Sprite player){
	    playerSpriteList.add(0, player);
	}

	public String backgroundPath() {
		return myBackgroundPath;
	}

	public void setBackground(String backgroundPath) {
		myBackgroundPath = backgroundPath;
	}

	public void setSprites(List<Sprite> spriteList){
		sprites = spriteList;
	}

	public CollisionTable collisionTable() {
		return collisionTable;
	}
	
	public void setCollisionTable(CollisionTable collisionTable) {
		this.collisionTable = collisionTable;
	}

	public void setGoalMap(Map<Sprite,Integer> goalMap){
		this.goalMap = goalMap;
	}
	
	public List<Sprite> playerSpriteList(){
	    return Collections.unmodifiableList(playerSpriteList);
	}
	
	
	// Constructor & Helpers
	/**
	 * Calls the larger constructor and internally creates a new Sprite() for playerSprite
	 * 
	 * @param width
	 * @param height
	 */
	public Level(int width, int height) {
		this(width, height, new ArrayList<Sprite>());
	}
	
	public Level(int width, int height, List<Sprite> playerSpriteList) {
		super(width, height);
		collisionTable = new CollisionTable();
		this.playerSpriteList=new ArrayList<>();
	    goalMap = new HashMap<>();
		if (playerSpriteList != null){
			this.playerSpriteList=playerSpriteList;
			prepareAllSprites();
		}
	}

	/**
	 * Gets the method to initialize the next level from the GameEngine
	 * @param nextLevelConsumer
	 */

    public void passInitializeLevelMethod (IntConsumer nextLevelConsumer) {
        nextLevelMethod = nextLevelConsumer;
        
    }
    
    @Override
    public void update(){
        super.update();
        playerSpriteList.stream().forEach(player -> edgeCheckSprite(player));
        goalMap.keySet().forEach(sprite -> handleGoals(sprite));
    }

    private void edgeCheckSprite (Sprite sprite) {
        if(sprite.transform().getPosX()<0){
            sprite.transform().setPosition(new Point2D(0, sprite.transform().getPosY()));
        }
        if(sprite.transform().getPosX()>width()){
            sprite.transform().setPosition(new Point2D(width(), sprite.transform().getPosY()));
        }
        if(sprite.transform().getPosY()<0){
            sprite.transform().setPosition(new Point2D(sprite.transform().getPosX(), 0));
        }
        if(sprite.transform().getPosY()>height()){
            sprite.transform().setPosition(new Point2D(sprite.transform().getPosX(), height()));
        }
    }

    /**
     * Checks the goal and initializes the next level if the goal is acheived
     * @param sprite
     */
    private void handleGoals (Sprite sprite) {
        if(!sprite.isActive()){
        	System.out.println("running handleGoals");
            nextLevelMethod.accept(goalMap.get(sprite));
        }
    }
        
    public double[] getNewCameraLocations () {
        double[] xyLocations = new double[2];
        xyLocations[X] = playerSpriteList.get(INT.LOCAL_PLAYER).transform().getPosX()+playerSpriteList.get(INT.LOCAL_PLAYER).transform().getWidth()/2;
        xyLocations[Y] = playerSpriteList.get(INT.LOCAL_PLAYER).transform().getPosY()-playerSpriteList.get(INT.LOCAL_PLAYER).transform().getHeight()/2;
        return xyLocations;
    }

    /**
     * Adds the groovy Action (user defined) into the level
     * @param spriteTag
     * @param groovyObject
     */
    public void addGroovyAction (String spriteTag, Action groovyAction) {
        List<Sprite> spriteWithTag = sprites().stream().filter(sprite -> sprite.tag().equals(spriteTag)).collect(Collectors.toList());
        spriteWithTag.stream().forEach(sprite -> handleGroovyAction(sprite,((GroovyAction) groovyAction)));
    }

    private void handleGroovyAction (Sprite sprite, GroovyAction groovyAction) {
        GroovyAction copy = groovyAction.deepCopy();
        copy.setSprite(sprite);
        sprite.addActionRuntime(copy);
    }

    public void addGroovyComponent (String spriteTag, Component groovyComponent) {
        List<Sprite> spriteWithTag = sprites().stream().filter(sprite -> sprite.tag().equals(spriteTag)).collect(Collectors.toList());
        spriteWithTag.stream().forEach(sprite -> handleGroovyComponent(sprite,(GroovyComponent) groovyComponent));
    }

    private void handleGroovyComponent (Sprite sprite, GroovyComponent groovyComponent) {
        GroovyComponent copy = groovyComponent.deepCopy();
        copy.setSprite(sprite);
        sprite.addComponentRuntime(copy);
    }

    public Map<String,Consumer<KeyCode>> getActionChangeKeyCodeMethod (int playerNumber) {
        Map<String, Consumer<KeyCode>> actionKeyCodeMethodMap = new HashMap<>();
        playerSpriteList
                .get(playerNumber)
                .actionList()
                .stream()
                .filter(act -> act.keycode() != null && act.keycode().size() > 0)
                .forEach(action -> actionKeyCodeMethodMap
                        .put(action.getClass().getAnnotation(ActionName.class)
                                .displayName(), (Keycode) -> action
                                .setKeyCode(Stream.of(Keycode)
                                        .collect(Collectors.toList()))));
        return actionKeyCodeMethodMap;
    }
    
    public Map<String,KeyCode> getActionKeyCodeMap (int playerNumber) {
        Map<String, KeyCode> actionKeyCodeMap = new HashMap<>();
        playerSpriteList
                .get(playerNumber)
                .actionList()
                .stream()
                .filter(act -> act.keycode() != null && act.keycode().size() > 0)
                .forEach(action -> actionKeyCodeMap
                        .put(action.getClass().getAnnotation(ActionName.class)
                                .displayName(), action.keycode().get(0)));
        return actionKeyCodeMap;
    }

    public Map<String, Double> getUnmodifiableHUDMap () {
        Map<String, Double> HUDMap = new HashMap<>();
        playerSpriteList
                .get(INT.LOCAL_PLAYER)
                .componentList()
                .stream()
                .filter(comp -> comp.getClass().getAnnotation(HUDInterface.class) != null)
                .forEach(component -> HUDMap.put((component.getClass()
                                                         .getAnnotation(HUDInterface.class).name()),
                                                 getRightValueFromComponent(component)));
        return Collections.unmodifiableMap(HUDMap);
    }

    private Double getRightValueFromComponent (Component component) {
        List<Method> methodList =
                Stream.of(component.getClass().getMethods())
                        .filter(methods -> methods.getAnnotation(HUDGetter.class) != null)
                        .collect(Collectors.toList());
        System.out.println(methodList.size());
        if (methodList.size() > 0) {
            try {
                System.out.println(methodList.get(0).getName());
                return (Double) methodList.get(0).invoke(component);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                DialogUtil.displayMessage("ERROR", component.getClass().getName());
            }
        }
        return 0.0;
    }

	public CollisionMap collisionMap() {
		return collisionTable.getMap();
	}





}	
