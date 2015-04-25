package levelPlatform.level;
import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import gameEngine.actions.GroovyAction;
import gameEngine.components.GroovyComponent;
import resources.constants.INT;
import sprite.Sprite;
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
	    playerSpriteList.add(player);
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
		if (this.playerSpriteList.isEmpty()) {
			this.playerSpriteList.add(new Sprite());
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
        goalMap.keySet().forEach(sprite -> handleGoals(sprite));
    }

    /**
     * Checks the goal and initializes the next level if the goal is acheived
     * @param sprite
     */
    private void handleGoals (Sprite sprite) {
        if(!sprite.isActive()){
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

    public List<String> getActionListInStrings (int playerNumber) {
        List<String> actionName = new ArrayList<>();
        playerSpriteList().get(playerNumber).actionList().stream().forEach(action -> actionName.add(action.getClass().getSimpleName()));
        return actionName;
    }
    
    public void setKeyCodeToPlayer(int playerNumber, String actionName, KeyCode key){
        playerSpriteList().get(playerNumber).getActionOfType(actionName).setKeyCode(Stream.of(key).collect(Collectors.toList()));
    }

}	
