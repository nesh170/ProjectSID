package levelPlatform.level;
import gameEngine.Action;
import gameEngine.CollisionTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;

import gameEngine.EngineMathFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resources.constants.INT;
import resources.constants.STRING;
import sprite.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private Sprite playerSprite;
	private CollisionTable collisionTable;
	private IntConsumer nextLevelMethod;
	private Map<Sprite, Integer> goalMap;


	// Getters & Setters
	/**
	 * @return a controlMap which might change depending on the behaviours for each level
	 */
	public Map<KeyCode,Action> controlMap() {

		Map<KeyCode,Action> controlMap = new HashMap<>();
		playerSprite.actionList().forEach(action -> action.setUpKey(controlMap));
		
		return controlMap;

	}

	public void setPlayerSprite(Sprite player) {
		playerSprite = player;
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


	// Constructor & Helpers
	/**
	 * Calls the larger constructor and internally creates a new Sprite() for playerSprite
	 * 
	 * @param width
	 * @param height
	 */
	public Level(int width, int height) {
		
		this(width, height, null);
		
	}

	/**
	 * Handles a null playerSprite by creating one
	 * 
	 * @param width
	 * @param height
	 * @param playerSprite
	 */
	public Level(int width, int height, Sprite playerSprite) {
		
		super(width, height);

		instantiateMaps();

		if (playerSprite == null) {
			setPlayerSprite(new Sprite());
		} else {
			setPlayerSprite(playerSprite);
		}

		// Call this in Level in addition to its super -- prepare playerSprite as well
		prepareAllSprites();

	}
	
	private void instantiateMaps() {
		
		this.collisionTable = new CollisionTable();
	    this.goalMap = new HashMap<>();
		
	}

	/**
	 * Gets the method to initialize the next level from the GameEngine
	 * @param nextLevelConsumer
	 */
	public void passInitializeLevelMethod (IntConsumer nextLevelConsumer) {
		nextLevelMethod = nextLevelConsumer;
	}

	@Override
	public void update() {
		
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
	/**
	 * get an ImageView representation for each level to display on GameEditScreen.
	 * returns a image of draft Level if 
	 * @return ImageView or Image
	 */
	public ImageView getLevelImageView(){
		//TODO: implement this method
		//temporary image to reprsent each level
		ImageView img = new ImageView(new Image(STRING.GAME_EDIT.LEVEL2IMAGE));
		return new ImageView(new Image(STRING.GAME_EDIT.LEVEL2IMAGE));
	}
	
	public double[] getNewCameraLocations () {
		
		double[] xyLocations = new double[2];
		
		xyLocations[X] = playerSprite.transform().getPosX()+playerSprite.transform().getWidth()/2;
		xyLocations[Y] = playerSprite.transform().getPosY()-playerSprite.transform().getHeight()/2;
		
		return xyLocations;
		
	}

}	
