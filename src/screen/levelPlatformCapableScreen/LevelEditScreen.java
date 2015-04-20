package screen.levelPlatformCapableScreen;

import gameEngine.Action;
import gameEngine.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;
import javafx.scene.control.ScrollPane;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.PopupWindow.AnchorLocation;
import resources.ImageViewButton;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.controllers.LevelEditScreenController;
import screen.controllers.ScreenController;
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


	// Instance Variables
	private LevelEditScreenController controller;
	private Level level;
	private LevelEditDisplay levelEditDisplay;
	private Tab currentGameScreen;
	// Layout
	private VerticalButtonBox rightButtonBox;
	// Sprite(s)
	private Sprite spriteToAdd;
	private Sprite selectedSprite;
	// Image
	private Image imageToAdd;
	// Lists
	private final ObservableList<String> listOfPlatforms = FXCollections.observableArrayList();
	private final ObservableList<String> listOfEnemies = FXCollections.observableArrayList();
	private final ObservableList<String> listOfPlayers = FXCollections.observableArrayList();
	private final ObservableList<String> listOfPowerups = FXCollections.observableArrayList();
	// Maps
	private Map<String,ObservableList<String>> stringToListMap;
	private Map<String,Sprite> stringToSpriteMap;
	//Sets of premade sprites
	private Set<ImageView> premadePlatforms;
	private Set<ImageView> premadeEnemies;
	private Set<ImageView> premadePlayers;
	private Set<ImageView> premadePowerups;


	// Getters & Setters
	private void setController(LevelEditScreenController controller) {
		this.controller = controller;
	}

	private void setLevel(Level level) {
		this.level = level;
	}

	public Level currentLevel() {
		return level;
	}


	// Constructor & Helpers
	/**
	 * Constructor for creating new levels.
	 * 
	 * @param parent
	 * @param gameScreen
	 * @param width
	 * @param height
	 */
	public LevelEditScreen(LevelEditScreenController parent, double width, double height) {

		this(parent, width, height, new Level(INT.DEFAULT_LEVEL_WIDTH, INT.DEFAULT_LEVEL_HEIGHT));

	}

	/**
	 * Sets up the Level Edit Screen when the user
	 * selects a level already created.
	 * 
	 * Constructor for loading previously saved levels.
	 * 
	 * @param parent
	 * @param gameScreen
	 * @param width
	 * @param height
	 * @param level
	 */
	public LevelEditScreen(LevelEditScreenController controller, double width, double height, Level level) {

		super(width, height);

		setController(controller);
		setLevel(level);

		instantiateMaps();
		makePremadeSpriteSets();
		makeSpritesInLevelTab();
		makeButtonsOnRight();

		configureLevelEditDisplay(level);		

		this.setOnMouseEntered(e -> onLevelScreenRender());
		this.setOnKeyPressed(e -> checkForDelete(e));

	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

		Menu fileMenu = makeFileMenu(e -> save(),
				e -> controller.returnToGameEditScreen(),
				e -> controller.returnToGameEditScreen());
		//TODO for file menu = save and exit (third parameter) might need a different lambda
		Menu addNewSpriteButton = makeAddSpriteButton();

		menuBar.getMenus().addAll(fileMenu,addNewSpriteButton);

	}

	private void instantiateMaps() {

		this.stringToSpriteMap = new HashMap<>();

	}

	private Menu makeAddSpriteButton() {

		ImageView spritePic = new ImageView(new Image("images/sprite.jpg"));

		super.sizeMenuImageView(spritePic, DOUBLE.MENU_BAR_HEIGHT, DOUBLE.MENU_BAR_HEIGHT);

		Menu spriteButton = new Menu(STRING.LEVEL_EDIT.ADD_SPRITE,spritePic);
		MenuItem addSprite = new MenuItem(STRING.LEVEL_EDIT.ADD_SPRITE);
		spriteButton.getItems().add(addSprite);

		addSprite.setOnAction(e -> controller.loadSpriteEditScreen(this, new Sprite()));
		return spriteButton;

	}

	private void makeSpritesInLevelTab() {

		VBox paneForSprites = new VBox();
		this.viewableArea().setLeft(paneForSprites);

		TitledPane platforms = makeTitledPane(languageResources().getString("Platform"),languageResources().getString("AddPlatform"), listOfPlatforms, premadePlatforms);
		TitledPane enemies = makeTitledPane(languageResources().getString("Enemy"),languageResources().getString("AddEnemy"), listOfEnemies, premadeEnemies);
		TitledPane players = makeTitledPane(languageResources().getString("Player"),languageResources().getString("AddPlayer"), listOfPlayers, premadePlayers);
		TitledPane powerups = makeTitledPane(languageResources().getString("Powerup"),languageResources().getString("AddPowerup"), listOfPowerups, premadePowerups);

		stringToListMap = new HashMap<>();
		stringToListMap.put(tagResources().getString("Platform"), listOfPlatforms);
		stringToListMap.put(tagResources().getString("Enemy"), listOfEnemies);
		stringToListMap.put(tagResources().getString("Player"), listOfPlayers);
		stringToListMap.put(tagResources().getString("Powerup"), listOfPowerups);

		paneForSprites.getChildren().addAll(platforms,enemies,players,powerups);

	}

	private TitledPane makeTitledPane(String title, String addSpriteTitle, ObservableList<String> content, Set<ImageView> premade) {

		VBox titledPaneBox = new VBox();
		titledPaneBox.setAlignment(Pos.CENTER);
		ListView<String> platformListView = new ListView<>(content);
		Button addButton = new Button(addSpriteTitle);
		addButton.setOnMouseClicked(e -> makeAddSpritePopup(addButton,premade));
		titledPaneBox.getChildren().addAll(platformListView, addButton);

		/*
		 * Unsure if I want to use setOnMouseReleased or setOnMouseClicked
		 */
		platformListView.setOnMouseReleased(e -> {

			try {

				if(selectedSprite!=null) { //Deselect the old selected sprite by setting opacity to 1
					levelEditDisplay.getImage(selectedSprite).setOpacity(1);
				}

				/*
				 * this next line could throw an exception possibly if
				 * the selection model is empty, catch statement is precautionary
				 */				
				String sprite = platformListView.getSelectionModel().getSelectedItem();
				selectedSprite = stringToSpriteMap.get(sprite);
				levelEditDisplay.getImage(selectedSprite).setOpacity(0.4); //magic number? TODO move this number somewhere

			}

			catch (IndexOutOfBoundsException | NullPointerException ee) {
				//do not select any sprites, since no sprites are in the selection model
			}

		});

		return new TitledPane(title, titledPaneBox);

	}

	private void makeButtonsOnRight() {

		this.rightButtonBox = new VerticalButtonBox();

		rightButtonBox.setMinWidth(sideBarWidth());

		this.viewableArea().setRight(rightButtonBox);

		//added by Anika
		// TODO: fix hardcoded string

		Button addSpriteButton = 
				makeButtonForPane(languageResources().getString("AddSprite"), e -> controller.loadSpriteEditScreen(this));

		Button returnToGameEditButton = 
				makeButtonForPane(languageResources().getString("Back"), e -> controller.returnToGameEditScreen());

		Button addWidthLeftButton = 
				makeButtonForPane(languageResources().getString("AddWidthLeft"), e -> addWidthLeft());
		
		Button addWidthButton = 
				makeButtonForPane(languageResources().getString("AddWidthRight"), e -> addWidthRight());

		Button addHeightUpButton = 
				makeButtonForPane(languageResources().getString("AddHeightUp"), e -> addHeightUp());
				
		Button addHeightButton = 
				makeButtonForPane(languageResources().getString("AddHeightDown"), e -> addHeightDown());

		Button addCollTableButton = 
				makeButtonForPane("Edit collisions", e -> controller.loadCollisionTableScreen(this));

		rightButtonBox.getChildren().addAll(addSpriteButton, returnToGameEditButton, addWidthLeftButton, addWidthButton, addHeightUpButton, addHeightButton, addCollTableButton);

	}

	private void configureLevelEditDisplay(Level level) {

		this.levelEditDisplay = new LevelEditDisplay(level.width(),level.height(),level.sprites());
		viewableArea().setCenter(levelEditDisplay);
		levelEditDisplay.getContent().setOnMouseReleased(e -> addSpriteToLocation(e));

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

	private void makePremadeEnemySet() {
		//TODO add extra premades

		premadeEnemies = new HashSet<>();
		
	}

	private void makePremadePlayerSet() {
		//TODO add extra premades

		premadePlayers = new HashSet<>();
		
	}

	private void makePremadePlatformSet() {
		//TODO add extra premades

		premadePlatforms = new HashSet<>();
		makeSpriteForPremadeSet("platform.jpeg",languageResources().getString("Platform1"),tagResources().getString("Platform"),new ArrayList<Action>(), new ArrayList<Component>(), premadePlatforms);
		makeSpriteForPremadeSet("spike.png",languageResources().getString("Platform2"),tagResources().getString("Platform"),new ArrayList<Action>(), new ArrayList<Component>(), premadePlatforms);
	}
	
	
	/**
	 * 
	 * @param imagePath - Path of the image that represents the sprite in game
	 * @param customName - Name displayed for the sprite in the Level Edit Screen
	 * @param tag - Type of sprite, might need to change from string to ENUM later
	 * @param actions - List of actions attatched to the sprite
	 * @param components - List of components attached to the sprite
	 * @param setForSprite - Set of ImageViews to add to in order to create the popup menu later on
	 */
	private void makeSpriteForPremadeSet(String imagePath, String customName, String tag, List<Action> actions, List<Component> components, Set<ImageView> setForSprite) {
		Image image = new Image(imagePath);
		ImageView imageView = new ImageView(image);
		Sprite sprite = new Sprite();
		sprite.spriteImage().addImage(ImageToInt2DArray.convertImageTo2DIntArray(image, (int) image.getWidth(), (int) image.getHeight()));
		actions.forEach(action -> sprite.addAction(action));
		components.forEach(component -> sprite.addComponent(component));
		sprite.setName(customName);
		sprite.setTag(tag);
		
		imageView.setOnMouseClicked(e -> addSprite(new Sprite(sprite)));
		
		setForSprite.add(imageView);
	}

	private void save() {
		//TODO save this level to XML (and update game edit screen)?
	}



	// TODO
	private void configureSpriteXYFromClick(MouseEvent e, Sprite sprite) {

		double xLocation = e.getX();
		double yLocation = e.getY();

		sprite.setX(xLocation);
		sprite.setY(yLocation);

	}

	private void addSpriteToLocation(MouseEvent e) {

		if(spriteToAdd != null && imageToAdd!=null) {

			configureSpriteXYFromClick(e, spriteToAdd);

			addSpriteToLevelDisplay(spriteToAdd);

			level.sprites().add(spriteToAdd);
			levelEditDisplay.setCursor(Cursor.DEFAULT);

			spriteToAdd = null; 
			imageToAdd = null;

		}

	}

	private void addSpriteToLevelDisplay(Sprite sprite) {

		ImageView imageView = sprite.spriteImage().getImageViewToDisplay();

		levelEditDisplay.addSpriteToDisplay(sprite,imageView);

		String newSpriteName = UniqueString.makeUniqueKey(stringToSpriteMap.keySet(), sprite.getName());
		sprite.setName(newSpriteName);
		stringToSpriteMap.put(sprite.getName(), sprite);
		stringToListMap.get(sprite.tag()).add(sprite.getName());

	}
	
	private void onLevelScreenRender() {
		levelEditDisplay.setContentMinSize(level);
		this.setOnMouseEntered(null);
	}
	
	private void addWidthLeft() {
		levelEditDisplay.addWidthLeft();
		level.sprites().forEach(sprite -> {
			sprite.setX(sprite.getX() + levelEditDisplay.getSizeToIncrease());
		});
		addLevelWidth();
	}
	
	private void addWidthRight() {
		levelEditDisplay.addWidthRight();
		addLevelWidth();
	}
	
	private void addLevelWidth() {
		level.configureWidthAndHeight(levelEditDisplay.getSizeToIncrease() + level.width(), level.height());
	}
	
	private void addHeightUp() {
		levelEditDisplay.addHeightUp();
		level.sprites().forEach(sprite -> {
			sprite.setY(sprite.getY() + levelEditDisplay.getSizeToIncrease());
		});
		addLevelHeight();
	}
	
	private void addHeightDown() {
		levelEditDisplay.addHeightDown();
		addLevelHeight();
	}
	
	private void addLevelHeight() {
		level.configureWidthAndHeight(level.width(),levelEditDisplay.getSizeToIncrease() +  level.height());
	}
	
	
	/**
	 * Very tentative method here:
	 * Need some kind of popup, contextmenu, tooltip, etc. to appear when clicking on add sprite
	 * buttons on the left.  Hard coded in a rectangle for now just to see how things are working
	 * -Leo
	 * 
	 */
	private void makeAddSpritePopup(Button button, Set<ImageView> premade) {
		Popup newSpriteDisplay = new Popup();
		
		VBox display = new VBox();
		display.getStyleClass().add("pane");
		display.setSpacing(DOUBLE.BUTTON_SPACING);
		premade.forEach(image -> display.getChildren().add(image));
		
		newSpriteDisplay.getContent().add(display);
		
		newSpriteDisplay.show(button, levelEditDisplay.getLayoutX(),levelEditDisplay.getLayoutY());
	}
	
	private void checkForDelete(KeyEvent e) {
		
		if((e.getCode().equals(KeyCode.DELETE) || e.getCode().equals(KeyCode.BACK_SPACE))
				&& selectedSprite!=null) {
			level.sprites().remove(selectedSprite);
			stringToListMap.get(selectedSprite.tag()).remove(selectedSprite.getName());
			stringToSpriteMap.remove(selectedSprite.getName());
			levelEditDisplay.removeSpriteFromDisplay(selectedSprite, levelEditDisplay.getImage(selectedSprite));
			selectedSprite = null;
		}
		
	}

	/**
	 * add a sprite to the level edit screen
	 */
	public void addSprite(Sprite sprite) {

		spriteToAdd = sprite;
		imageToAdd = spriteToAdd.spriteImage().getImageViewToDisplay().getImage();
		levelEditDisplay.setCursor(new ImageCursor(imageToAdd));

	}

	/**
	 * used for collision table
	 * Note: can't simply cast keyset as list!! (4/13/15)
	 * @author Anika
	 * @return sprite tags as Strings
	 */
	public List<String> getSpriteTags()
	{
		return new ArrayList<String>(stringToSpriteMap.keySet());

	}


}
