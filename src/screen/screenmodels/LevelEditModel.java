package screen.screenmodels;

import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.Component;
import gameEngine.actions.MultiSpriteAction;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

//import javax.media.jai.IntegerSequence;




import data.DataHandler;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import resources.constants.INT;
import resources.constants.STRING;
import screen.screens.LevelEditDisplay;
import sprite.Sprite;
import util.ImageToInt2DArray;
import util.UniqueString;
import levelPlatform.level.Level;

public class LevelEditModel {
	
	// Static Variables
	private final static double SELECT = 0.4;
	private final static double UNSELECT = 1.0;
	
	
	// Instance Variables
	private Level level;
	private LevelEditDisplay levelEditDisplay;
	
	private Sprite spriteToAdd;
	private Sprite selectedSprite;
	private ObjectProperty<Cursor> waitingSpriteCursor;
	
	private ObservableList<String> waitingSpriteSet;
	
	private Image imageToAdd;
	
	private ResourceBundle languageResources;
	private ResourceBundle tagResources;
	
	private Map<String, String> classPathMap;
	
	private Map<String,ObservableList<String>> stringToListMap;
	private Map<String,Sprite> stringToSpriteMap;
	private Map<Sprite, Integer> goalMap;
	
	private Set<String> tags;
	
	
	// Getters & Setters
	// public
	public CollisionMap getCollisionMap() {
		return level.collisionMap();
	}
	
	public ObservableList<String> setWaitingSpritesList() {
		waitingSpriteSet = FXCollections.observableArrayList();
		return waitingSpriteSet;
	}
	
	public Sprite selectedSprite() {
		return selectedSprite;
	}
	
	public Level level() {
		return level;
	}
	
	// private 
	
	
	// Constructor & Helpers
	public LevelEditModel(LevelEditDisplay levelEditDisplay, ObjectProperty<Cursor> waitingSpriteCursor, Level level, Set<String> tags, Map<String,ObservableList<String>> map, ResourceBundle languageResources, ResourceBundle tagResources) {
		
		// private setter calls plz
		this.levelEditDisplay = levelEditDisplay;
		this.level = level;
		this.tags = tags;
		this.languageResources = languageResources;
		this.tagResources = tagResources;
		this.waitingSpriteCursor = waitingSpriteCursor;
		this.stringToListMap = map;
		
		instantiateMaps();
		
		level.sprites().forEach(sprite -> {
			
			ImageView image = new ImageView(DataHandler.fileToImage(new File(sprite.getImagePath())));
			image.setFitWidth(sprite.transform().getWidth());
			image.setFitHeight(sprite.transform().getHeight());
			addSpriteToLevelDisplay(sprite, image);
			
		});
	}
	
	private void instantiateMaps() {
		this.stringToSpriteMap = new HashMap<>();
		this.goalMap = new HashMap<>();
		level.setGoalMap(goalMap);
		initializeClassPathMap();
	}
		
	// All other instance methods
	// public
	// private
	private void initializeClassPathMap() {

		classPathMap = new HashMap<>();

		ResourceBundle actionResources = ResourceBundle
				.getBundle("resources.spritePartProperties.action");
		actionResources.keySet().forEach(
				e -> classPathMap.put(
						languageResources.getString(e),
						actionResources.getString(e)));;
	}
	
	public void changeSelection(String newSelect) {
		try {
			if(stringToSpriteMap.containsKey(selectedSprite.getName())) {
				levelEditDisplay.getImage(stringToSpriteMap.get(selectedSprite.getName())).setOpacity(UNSELECT);
			}
		} catch (NullPointerException e) {
			//Nothing
		}
		selectSprite(stringToSpriteMap.get(newSelect));
	}
			
//	private void makeSpriteForPremadeSet(String imagePath, String customName, String tag, List<Action> actions, List<Component> components, Set<ImageView> setForSprite) {
//		Image image = new Image(imagePath);
//		ImageView imageView = new ImageView(image);
//		Sprite sprite = new Sprite();
//		sprite.spriteImage().addImage(ImageToInt2DArray.convertImageTo2DIntArray(image, (int) image.getWidth(), (int) image.getHeight()));
//		actions.forEach(action -> sprite.addAction(action));
//		components.forEach(component -> sprite.addComponent(component));
//		sprite.setName(customName);
//		sprite.setTag(tag);
//		
//		imageView.setOnMouseClicked(e -> addSprite(Sprite.makeCopy(sprite)));
//		
//		setForSprite.add(imageView);
//	}
	
	private void configureSpriteXYFromClick(MouseEvent e, Sprite sprite) {

		double xLocation = e.getX();
		double yLocation = e.getY();
		
		sprite.setPosition(new Point2D(xLocation,yLocation));

	}

	public void addSpriteToLocation(MouseEvent e) {

		if(spriteToAdd != null && imageToAdd!=null) {

			configureSpriteXYFromClick(e, spriteToAdd);

			addSpriteToLevelDisplay(spriteToAdd, new ImageView(imageToAdd));

			level.sprites().add(spriteToAdd);
			addToGoalMap(spriteToAdd);			
			//TODO Remove sprite from player sprite list as well
			if(spriteToAdd.tag().equals(tagResources.getString("Player"))) {
				level.addPlayerSprite(spriteToAdd);
			}
			clearCursors();

			clearSpriteToAdd();
		}

	}
	
	public void addSpriteToWaitingList() {
		
		if(spriteToAdd != null && imageToAdd!=null) {
			
			waitingSpriteSet.add(spriteToAdd.getName());
			makeSpriteNameUnique(spriteToAdd, stringToSpriteMap.keySet());
			stringToSpriteMap.put(spriteToAdd.getName(), spriteToAdd);
			addToGoalMap(spriteToAdd);
			
			clearCursors();
			clearSpriteToAdd();
		}
		
	}
	
	private void clearSpriteToAdd() {
		
		spriteToAdd = null; 
		imageToAdd = null;

	}
	
	private void clearCursors() {
		
		waitingSpriteCursor.set(Cursor.DEFAULT);
		levelEditDisplay.setCursor(Cursor.DEFAULT);
		
	}
	
	private void addToGoalMap(Sprite sprite) {
		
		int toLevel = sprite.getGoalToLevel();
		if(toLevel >= 0) {
			goalMap.put(sprite, toLevel);
		}
		
	}

	private void addSpriteToLevelDisplay(Sprite sprite, ImageView imageView) {

//		ImageView imageView = new ImageView(imageToAdd);

		levelEditDisplay.addSpriteToDisplay(sprite,imageView);
		
		makeSpriteNameUnique(sprite, stringToSpriteMap.keySet());
		stringToSpriteMap.put(sprite.getName(), sprite);
		addSpriteToAppropriateList(sprite);
	}
	
	private void addSpriteToAppropriateList(Sprite sprite) {
		try {
			stringToListMap.get(sprite.tag()).add(sprite.getName());
		} catch (NullPointerException e) {
			stringToListMap.get(languageResources.getString("Other")).add(sprite.getName());
		}
	}
	
	private void makeSpriteNameUnique(Sprite sprite, Collection<String> collection) {
		String newSpriteName = UniqueString.makeUniqueKey(collection, sprite.getName());
		sprite.setName(newSpriteName);
	}
	
	public void addWidthLeft() {
		levelEditDisplay.addWidthLeft();
		level.sprites().forEach(sprite -> {
			Point2D curPosition = sprite.getPosition();
			sprite.setPosition(new Point2D(curPosition.getX() + levelEditDisplay.getSizeToIncrease(), curPosition.getY()));
		});
		addLevelWidth();
	}
	
	public void addWidthRight() {
		levelEditDisplay.addWidthRight();
		addLevelWidth();
	}
	
	private void addLevelWidth() {
		level.configureWidthAndHeight(levelEditDisplay.getSizeToIncrease() + level.width(), level.height());
	}
	
	public void addHeightUp() {
		levelEditDisplay.addHeightUp();
		level.sprites().forEach(sprite -> {
			Point2D curPosition = sprite.getPosition();
			sprite.setPosition(new Point2D(curPosition.getX(), curPosition.getY() + levelEditDisplay.getSizeToIncrease()));
		});
		addLevelHeight();
	}
	
	public void addHeightDown() {
		levelEditDisplay.addHeightDown();
		addLevelHeight();
	}
	
	private void addLevelHeight() {
		level.configureWidthAndHeight(level.width(),levelEditDisplay.getSizeToIncrease() +  level.height());
	}
	
	public void addTagTypeToSet(String tag, Popup popup) {
		tags.add(tag);
		popup.hide();
	}
	
	private void selectSprite(Sprite sprite) {
		selectedSprite = sprite;
		levelEditDisplay.getImage(selectedSprite).setOpacity(SELECT);
		levelEditDisplay.setVvalue(selectedSprite.getPosition().getY()-levelEditDisplay.getHeight()/2);
		levelEditDisplay.setHvalue(selectedSprite.getPosition().getX()-levelEditDisplay.getWidth()/2);
	}
	
	public void delete() {
		if(selectedSprite!=null) {
			level.sprites().remove(selectedSprite);
			stringToSpriteMap.remove(selectedSprite.getName());
			levelEditDisplay.removeSpriteFromDisplay(selectedSprite, levelEditDisplay.getImage(selectedSprite));
			Sprite tempSprite = selectedSprite;
			selectedSprite = null;
			stringToListMap.get(tempSprite.tag()).remove(tempSprite.getName());
		}
	}
	
	public void copy() {
		if(selectedSprite!=null) {
			addSprite(Sprite.makeCopy(selectedSprite));
		}
	}
	
	public void addSprite(Sprite sprite) {
		spriteToAdd = sprite;
		Dimension2D spriteSize = spriteToAdd.dimensions();
		imageToAdd = DataHandler.fileToImage(new File(spriteToAdd.getImagePath()),spriteSize.getWidth(),spriteSize.getHeight(),false);
		levelEditDisplay.setCursor(new ImageCursor(imageToAdd));
		waitingSpriteCursor.set(new ImageCursor(imageToAdd));
	}

	public void setBackgroundImage(String path) {
		level.setBackground(path);
	}

	/**
	 * from the collision table screen -> map of sprite 1, map of sprite 2 and action components
	 * pair programming - april 27 - michael, anika
	 * @param collisionMap
	 */
	public void updateCollisions(CollisionMap collisionMap) {
		level.collisionTable().clear();
	//	collisionMap.keySet().forEach(outerTag ->);
		
		
		// essential idea
		for (String sprite1Tag : collisionMap.keySet())
		{
			for (String sprite2Tag : collisionMap.get(sprite1Tag).keySet())
			{
				constructActionForEachSprite(sprite1Tag, sprite2Tag, collisionMap.get(sprite1Tag).get(sprite2Tag));
			}
		}
		
		
	}
	
	private List<Sprite> getSpritesFromTag(String tag)
	{
		List<Sprite> sprites = new ArrayList<>();
		stringToListMap.get(tag).stream().forEach(name -> sprites.add(stringToSpriteMap.get(name)));
		return sprites;
	}
	
	 
	
	private void constructActionForEachSprite(String sprite1Tag,
			String sprite2Tag, List<List<String>> directionList) {
		
		for (Sprite sprite1: getSpritesFromTag(sprite1Tag)) {
			for (int whichDir = 0; whichDir < INT.NUM_DIRECTIONS; whichDir++)
			{
				List<String> specificActionList = directionList.get(whichDir);
				level.collisionTable().addActionToBigMap(
						sprite1Tag, sprite2Tag, whichDir, createAction(sprite1, specificActionList), sprite1);			
			}
			
		}
	}

	
	private Action createAction(Sprite sprite1, List<String> paramList) {
		String actionName = paramList.get(INT.ACTION_INDEX);
		double value = parseDouble(paramList.get(INT.VALUE_INDEX));
		Sprite switchOut = stringToSpriteMap.get(paramList.get(INT.SWITCH_OPTION_INDEX));
		try {
			Class<Action> actionClass = (Class<Action>) Class.forName(classPathMap.get(actionName));
			Constructor<Action> constructor;
			// switch out action
			if ((constructor = actionClass.getConstructor(Sprite[].class, List.class, KeyCode[].class)) != null) {
				// switchOptionSpriteName = sprites to switch to
				// level.getSpritesWithTag("player") = list of sprites 
				// null = no keycode needed (from collision table so doesn't trigger on keycode)
				Sprite[] sprites = {switchOut};
				return constructor.newInstance(sprites, level.getSpritesWithTag("player"), null);
			}
			else if ((constructor = actionClass.getConstructor(Sprite.class, Double.class, KeyCode[].class)) != null) {
				// parameters: active sprite, double value
				// example: alter health, bounce, fall
				return constructor.newInstance(stringToSpriteMap.get(sprite1), value, null);
			}
			else if ((constructor = actionClass.getConstructor(Sprite.class, KeyCode[].class)) != null) {
				// parameter: sprite to kill or active sprite; example: kill action; compound action
				return constructor.newInstance(stringToSpriteMap.get(sprite1), value, null);
			}
		}
		catch (Exception e) {
			
		}
		return null;
		
	}

	private double parseDouble(String string) {
		try {
			return Double.parseDouble(string);
		} catch(Exception e) {
			return 0;
		}
	}

}
