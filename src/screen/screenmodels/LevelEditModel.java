package screen.screenmodels;

import gameEngine.Action;
import gameEngine.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import data.DataHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import screen.screens.LevelEditDisplay;
import sprite.Sprite;
import util.ImageToInt2DArray;
import util.UniqueString;
import levelPlatform.level.Level;

public class LevelEditModel {
	
	private Level level;
	private LevelEditDisplay levelEditDisplay;
	
	private Sprite spriteToAdd;
	private Sprite selectedSprite;
	
	private Image imageToAdd;
	
	private ResourceBundle languageResources;
	private ResourceBundle tagResources;
	
	private Map<String,ObservableList<String>> stringToListMap;
	private Map<String,Sprite> stringToSpriteMap;
	
	private Set<String> tags;
	
	private final static double SELECT = 0.4;
	private final static double UNSELECT = 1.0;


	public LevelEditModel(LevelEditDisplay levelEditDisplay, Level level, Set<String> tags, ResourceBundle languageResources, ResourceBundle tagResources) {
		this.levelEditDisplay = levelEditDisplay;
		this.level = level;
		this.tags = tags;
		this.languageResources = languageResources;
		this.tagResources = tagResources;
		
		instantiateMaps();
	}
	
	public Sprite selectedSprite() {
		return selectedSprite;
	}
	
	public Level level() {
		return level;
	}
	
	
	private void instantiateMaps() {
		this.stringToSpriteMap = new HashMap<>();
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
	
	public void setUpListMapping(Map<String,ObservableList<String>> map) {
		stringToListMap = map;
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

			addSpriteToLevelDisplay(spriteToAdd);

			level.sprites().add(spriteToAdd);
			
			//TODO Remove sprite from player sprite list as well
			if(spriteToAdd.tag().equals(tagResources.getString("Player"))) {
				level.addPlayerSprite(spriteToAdd);
			}
			levelEditDisplay.setCursor(Cursor.DEFAULT);

			spriteToAdd = null; 
			imageToAdd = null;

		}

	}

	private void addSpriteToLevelDisplay(Sprite sprite) {

		ImageView imageView = new ImageView(imageToAdd);

		levelEditDisplay.addSpriteToDisplay(sprite,imageView);

		String newSpriteName = UniqueString.makeUniqueKey(stringToSpriteMap.keySet(), sprite.getName());
		sprite.setName(newSpriteName);
		stringToSpriteMap.put(sprite.getName(), sprite);
		try {
			stringToListMap.get(sprite.tag()).add(sprite.getName());
		} catch (NullPointerException e) {
			stringToListMap.get(languageResources.getString("Other")).add(sprite.getName());
		}

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
	}



}
