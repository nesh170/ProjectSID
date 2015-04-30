package screen.screens;

import game.Game;
import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.DataHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PopupControl;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;
import javafx.scene.control.ScrollPane;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.PopupWindow.AnchorLocation;
import resources.ImageViewButton;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.controllers.LevelEditScreenController;
import screen.controllers.ScreenController;
import screen.screenmodels.CollisionMap;
import screen.screenmodels.LevelEditModel;
import screen.util.VerticalButtonBox;
import sprite.Sprite;
import sprite.SpriteImage;
import util.ImageToInt2DArray;
import util.UniqueString;

/**
 * @author Leo
 * @author Ruslan
 */

public class LevelEditScreen extends LevelPlatformCapableScreen {

	// Static Variables
	private static final String DEFAULT_PLAYER_PATH = "defaults/defaultPlayer.xml.xml";
	private static final String DEFAULT_ENEMY_PATH = "defaults/defaultEnemy.xml.xml";
	private static final String DEFAULT_PLATFORM_PATH = "defaults/defaultPlatform.xml.xml";
	private static final String HIDDEN_SPRITE_PATH = "images/hiddenSprite.png";

	// Instance Variables
	private LevelEditScreenController controller;
	private LevelEditModel model;
	private Game parentGame;
	private LevelEditDisplay levelEditDisplay;
	private VBox paneForWaitingSprites;
	private ListView<String> listViewOfWaitingSprites;
	private Tab currentGameScreen;
	// Layout
	private VerticalButtonBox rightButtonBox;
	
	private Map<String,ObservableList<String>> stringToListMap;
	
	private Set<Sprite> premadePlatforms;
	private Set<Sprite> premadeEnemies;
	private Set<Sprite> premadePlayers;
	private Set<Sprite> premadePowerups;
		
	//Set of tags
	private Set<String> tags;

	// Getters & Setters
	private void setController(LevelEditScreenController controller) {
		this.controller = controller;
	}
	
	public Set<String> getTags() {
		return tags;
	}
	
	/**
	 * used for Collision Edit Screen during switch-out action
	 * @author Anika
	 * @return map of sprite strings
	 */
	public Map<String, ObservableList<String>> getSpriteMap()
	{
		return stringToListMap;
	}


	/**
	 * Sets up the Level Edit Screen when the user
	 * selects a level already created.
	 * 
	 * Constructor for loading previously saved levels.
	 * 
	 * @param parent
	 * @param game
	 * @param width
	 * @param height
	 * @param level
	 */
	public LevelEditScreen(LevelEditScreenController controller, Game game, double width, double height, Level level) {

		super(width, height);
		
		makeTagSet();
		
		this.parentGame = game;

		setController(controller);

		makePremadeSpriteSets();
		instantiateMaps();
		makeSpritesInLevelTab();
		makeButtonsOnRight();

		configureLevelEditDisplay(level);
		
		this.setOnMouseEntered(e -> onLevelScreenRender(level));
		this.setOnKeyPressed(e -> checkForKeyPressed(e));
		
		makeModel(level);
		
	}
	
	private void makeModel(Level level) {
		model = new LevelEditModel(levelEditDisplay, paneForWaitingSprites.cursorProperty(), level, tags, stringToListMap, languageResources(), tagResources());
		listViewOfWaitingSprites.setItems(model.setWaitingSpritesList());
	}


	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

		Menu fileMenu = makeFileMenu(e -> save(),
				e -> exit(),
				e -> saveAndExit());
		Menu addNewSpriteButton = makeAddSpriteButton();

		menuBar.getMenus().addAll(fileMenu,addNewSpriteButton);

	}
	
	private void instantiateMaps() {
		stringToListMap = new HashMap<>();
	}

	
	private void makeTagSet() {
		this.tags = new HashSet<>();
		tagResources().keySet().forEach(key -> tags.add(tagResources().getString(key)));
	}

	private Menu makeAddSpriteButton() {

		ImageView spritePic = new ImageView(new Image("images/sprite.jpg"));

		super.sizeMenuImageView(spritePic, DOUBLE.MENU_BAR_HEIGHT, DOUBLE.MENU_BAR_HEIGHT);

		Menu spriteButton = new Menu(STRING.LEVEL_EDIT.ADD_SPRITE,spritePic);
		MenuItem addSprite = new MenuItem(STRING.LEVEL_EDIT.ADD_SPRITE);
		MenuItem editSprite = new MenuItem(STRING.LEVEL_EDIT.EDIT_SELECTED_SPRITE);
		spriteButton.getItems().addAll(addSprite,editSprite);

		addSprite.setOnAction(e -> controller.loadSpriteEditScreen(this, null));
		editSprite.setOnAction(e -> controller.loadSpriteEditScreen(this, model.selectedSprite()));
		return spriteButton;

	}

	private void makeSpritesInLevelTab() {

		VBox paneForSprites = new VBox();
		this.viewableArea().setLeft(paneForSprites);
		
		final ObservableList<String> listOfPlatforms = FXCollections.observableArrayList();
		final ObservableList<String> listOfEnemies = FXCollections.observableArrayList();
		final ObservableList<String> listOfPlayers = FXCollections.observableArrayList();
		final ObservableList<String> listOfPowerups = FXCollections.observableArrayList();
		final ObservableList<String> listOfOther = FXCollections.observableArrayList();

		TitledPane platforms = makeTitledPane(languageResources().getString("Platform"),languageResources().getString("AddPlatform"), listOfPlatforms, premadePlatforms);
		TitledPane enemies = makeTitledPane(languageResources().getString("Enemy"),languageResources().getString("AddEnemy"), listOfEnemies, premadeEnemies);
		TitledPane players = makeTitledPane(languageResources().getString("Player"),languageResources().getString("AddPlayer"), listOfPlayers, premadePlayers);
		TitledPane powerups = makeTitledPane(languageResources().getString("Powerup"),languageResources().getString("AddPowerup"), listOfPowerups, premadePowerups);
		TitledPane other = makeTitledPane(languageResources().getString("Other"),languageResources().getString("AddOther"), listOfOther, new HashSet<Sprite>());
		TitledPane waitingSprites = makePaneForWaitingSprites(other.widthProperty());
		
		stringToListMap = new HashMap<>();
		stringToListMap.put(tagResources().getString("Platform"), listOfPlatforms);
		stringToListMap.put(tagResources().getString("Enemy"), listOfEnemies);
		stringToListMap.put(tagResources().getString("Player"), listOfPlayers);
		stringToListMap.put(tagResources().getString("Powerup"), listOfPowerups);
		stringToListMap.put(languageResources().getString("Other"), listOfOther);

		paneForSprites.getChildren().addAll(platforms,enemies,players,powerups,other,waitingSprites);

	}

	private TitledPane makeTitledPane(String title, String addSpriteTitle, ObservableList<String> content, Set<Sprite> premade) {

		VBox titledPaneBox = new VBox();
		titledPaneBox.setAlignment(Pos.CENTER);
		ListView<String> platformListView = new ListView<>(content);
		Button addButton = new Button(addSpriteTitle);
		addButton.setOnMouseClicked(e -> makeAddSpritePopup(addButton,premade));
		titledPaneBox.getChildren().addAll(platformListView, addButton);

		/*
		 * Unsure if I want to use setOnMouseReleased or setOnMouseClicked
		 */
		platformListView.getSelectionModel().selectedItemProperty().addListener((observable,oldSelect,newSelect) -> model.changeSelection(newSelect));
		platformListView.setOnMouseReleased(e -> model.changeSelection(platformListView.getSelectionModel().getSelectedItem()));
		
		return new TitledPane(title, titledPaneBox);

	}
	
	private void makeButtonsOnRight() {

		this.rightButtonBox = new VerticalButtonBox();

		rightButtonBox.setMinWidth(sideBarWidth());

		this.viewableArea().setRight(rightButtonBox);

		Button addSpriteButton = 
				makeButtonForPane(languageResources().getString("AddSprite"), e -> controller.loadSpriteEditScreen(this, null));
				
		Button editSpriteButton =
				makeButtonForPane(languageResources().getString("EditSprite"), e -> controller.loadSpriteEditScreen(this, model.selectedSprite()));

		Button addBackgroundButton =
				makeButtonForPane(languageResources().getString("AddBackground"), e -> selectBackgroundImage());
		
		Button returnToGameEditButton = 
				makeButtonForPane(languageResources().getString("Back"), e -> controller.returnToGameEditScreen());

		Button addWidthLeftButton = 
				makeButtonForPane(languageResources().getString("AddWidthLeft"), e -> model.addWidthLeft());
		
		Button addWidthButton = 
				makeButtonForPane(languageResources().getString("AddWidthRight"), e -> model.addWidthRight());

		Button addHeightUpButton = 
				makeButtonForPane(languageResources().getString("AddHeightUp"), e -> model.addHeightUp());
				
		Button addHeightButton = 
				makeButtonForPane(languageResources().getString("AddHeightDown"), e -> model.addHeightDown());

		Button addCollTableButton = 
				makeButtonForPane(languageResources().getString("EditCols"), e -> controller.loadCollisionTableScreen(this, model.getCollisionMap(), stringToListMap));
		
		Button addTagType = 
				makeButtonForPane(languageResources().getString("AddTagType"), e -> addTagType(e));
				
		rightButtonBox.getChildren().addAll(addSpriteButton, editSpriteButton, addBackgroundButton, 
				returnToGameEditButton, addWidthLeftButton, addWidthButton, addHeightUpButton, addHeightButton, addCollTableButton, addTagType);

	}
	
	private TitledPane makePaneForWaitingSprites(ReadOnlyDoubleProperty widthProperty) {
		
		paneForWaitingSprites = new VBox();
		paneForWaitingSprites.setOnMouseEntered(e -> makeTooltip(paneForWaitingSprites, e.getSceneX(), e.getSceneY(), languageResources().getString("OnWaitingSpriteHover")));
		paneForWaitingSprites.setAlignment(Pos.CENTER);
		
		listViewOfWaitingSprites = new ListView<String>();	
		listViewOfWaitingSprites.setOnMouseClicked(e -> model.addSpriteToWaitingList());
		
		paneForWaitingSprites.getChildren().addAll(listViewOfWaitingSprites);
		
		return new TitledPane(languageResources().getString("WaitingSprites"), paneForWaitingSprites);
	}
	
	private void makeTooltip(Node onThisNode, double x, double y, String text) {
		Tooltip tooltip = new Tooltip(text);
		tooltip.show(onThisNode, x, y);	
		onThisNode.setOnMouseExited(e -> tooltip.hide());
	}

	private void selectBackgroundImage() {
		File file = DataHandler.chooseFile(new Stage());
		Image image = DataHandler.fileToImage(file);
		model.setBackgroundImage(file.getPath());
		levelEditDisplay.setBackground(image);
		
	}

	private void configureLevelEditDisplay(Level level) {

		this.levelEditDisplay = new LevelEditDisplay(level.width(),level.height(),level.sprites(),
				level.backgroundPath());
		viewableArea().setCenter(levelEditDisplay);
		levelEditDisplay.getContent().setOnMouseReleased(e -> model.addSpriteToLocation(e));

	}
	
	private void save() {
		controller.saveLevel(parentGame, model.level());
	}
	
	private void exit() {
		controller.returnToGameEditScreen();
	}
	
	private void saveAndExit() {
		save();
		exit();
	}
	
	private void onLevelScreenRender(Level level) {
		levelEditDisplay.setContentMinSize(level);
		this.setOnMouseEntered(null);
	}
	
	//TODO possible duplicated code here and makeAddSpritePopup
	private void addTagType(MouseEvent e) {
		Popup tagTypePopup = new Popup();
		tagTypePopup.setHideOnEscape(true);
		tagTypePopup.setAutoHide(true);
		
		VBox display = new VBox();		
		display.alignmentProperty().set(Pos.CENTER);
		display.getStyleClass().add("pane");
		TextField addTagHere = new TextField();
		
		Button confirmTag = makeButtonForPane(languageResources().getString("Confirm"), ee -> model.addTagTypeToSet(addTagHere.getText(),tagTypePopup));
		display.getChildren().addAll(addTagHere,confirmTag);		
		tagTypePopup.getContent().add(display);
		tagTypePopup.show(rightButtonBox, e.getSceneX(), e.getSceneY());

	}
		
	
	/**
	 * Very tentative method here:
	 * Need some kind of popup, contextmenu, tooltip, etc. to appear when clicking on add sprite
	 * buttons on the left.  Hard coded in a rectangle for now just to see how things are working
	 * -Leo
	 * 
	 */
	private void makeAddSpritePopup(Button button, Set<Sprite> premade) {
		Popup newSpriteDisplay = new Popup();
		newSpriteDisplay.setHideOnEscape(true);
		newSpriteDisplay.setAutoHide(true);
		
		VBox display = new VBox();
		display.getStyleClass().add("pane");
		display.setSpacing(DOUBLE.BUTTON_SPACING);
		premade.forEach(sprite -> {
			ImageView image = new ImageView(DataHandler.fileToImage(new File(sprite.getImagePath()), sprite.dimensions().getWidth(), sprite.dimensions().getHeight(), false));
			image.setOnMouseClicked(e -> addSprite(Sprite.makeCopy(sprite)));
			display.getChildren().add(image);
		});
		
		newSpriteDisplay.getContent().add(display);
		
		newSpriteDisplay.show(button, levelEditDisplay.getLayoutX(),levelEditDisplay.getLayoutY());
	}
	
	private void checkForKeyPressed(KeyEvent e) {
		
		if((e.getCode().equals(KeyCode.DELETE) || e.getCode().equals(KeyCode.BACK_SPACE))
				) {
			model.delete();
		}
		if(e.getCode().equals(KeyCode.C)) {
			model.copy();
		}

		
	}
	
	private void makePremadeSpriteSets() {
		makePremadePlatformSet();
		makePremadePlayerSet();
		makePremadeEnemySet();
		makePremadePowerupSet();
	}

	private void makePremadePowerupSet() {
		//TODO add extra premades
		premadePowerups = new HashSet<>();
		
	}

	@SuppressWarnings("unchecked")
	private void makePremadeEnemySet() {
		premadeEnemies = (Set<Sprite>) DataHandler.fromXMLFile(new File(DEFAULT_ENEMY_PATH));
	}

	@SuppressWarnings("unchecked")
	private void makePremadePlayerSet() {
		premadePlayers = (Set<Sprite>) DataHandler.fromXMLFile(new File(DEFAULT_PLAYER_PATH));
	}

	@SuppressWarnings("unchecked")
	private void makePremadePlatformSet() {
		premadePlatforms = (Set<Sprite>) DataHandler.fromXMLFile(new File(DEFAULT_PLATFORM_PATH));
	}
		
	/**
	 * add a sprite to the level edit screen
	 */
	public void addSprite(Sprite sprite) {
		model.addSprite(sprite);
	}

	/**
	 * used for collision table
	 * Note: can't simply cast keyset as list!! (4/13/15)
	 * @author Anika
	 * @return sprite tags as Strings
	 */
	public List<String> getSpriteTags()
	{
		return new ArrayList<String>(tags);

	}

	public void updateCollisions(CollisionMap collisionMap) {
		model.updateCollisions(collisionMap);
	}


}
