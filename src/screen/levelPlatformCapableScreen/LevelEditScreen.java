package screen.levelPlatformCapableScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;
import javafx.scene.control.ScrollPane;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.controllers.LevelEditScreenController;
import screen.controllers.ScreenController;
import screen.util.VerticalButtonBox;
import sprite.Sprite;
import sprite.SpriteImage;

/**
 * @author Leo
 * @author Ruslan
 */

public class LevelEditScreen extends LevelPlatformCapableScreen {

	// Static Variables


	// Instance Variables
	private LevelEditScreenController controller;
	private Level level;
//	private ScrollPane scrollPane;
//	private Pane levelDisplay;
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
		makeSpritesInLevelTab();
		makeButtonsOnRight();

		configureLevelEditDisplay(level);		

		this.setOnMouseEntered(e -> onLevelScreenRender());

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

		TitledPane platforms = makeTitledPane(languageResources().getString("Platform"),listOfPlatforms);
		TitledPane enemies = makeTitledPane(languageResources().getString("Enemy"),listOfEnemies);
		TitledPane players = makeTitledPane(languageResources().getString("Player"),listOfPlayers);
		TitledPane powerups = makeTitledPane(languageResources().getString("Powerup"),listOfPowerups);

		stringToListMap = new HashMap<>();
		stringToListMap.put(tagResources().getString("Platform"), listOfPlatforms);
		stringToListMap.put(tagResources().getString("Enemy"), listOfEnemies);
		stringToListMap.put(tagResources().getString("Player"), listOfPlayers);
		stringToListMap.put(tagResources().getString("Powerup"), listOfPowerups);

		paneForSprites.getChildren().addAll(platforms,enemies,players,powerups);

	}

	private TitledPane makeTitledPane(String title, ObservableList<String> content) {

		ListView<String> platformListView = new ListView<>(content);

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

		return new TitledPane(title, platformListView);

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
				makeButtonForPane(languageResources().getString("AddWidthLeft"), e -> levelEditDisplay.addWidthLeft());
		
		Button addWidthButton = 
				makeButtonForPane(languageResources().getString("AddWidthRight"), e -> levelEditDisplay.addWidthRight());

		Button addHeightUpButton = 
				makeButtonForPane(languageResources().getString("AddHeightUp"), e -> levelEditDisplay.addHeightUp());
				
		Button addHeightButton = 
				makeButtonForPane(languageResources().getString("AddHeightDown"), e -> levelEditDisplay.addHeightDown());

		Button addCollTableButton = 
				makeButtonForPane("Edit collisions", e -> controller.loadCollisionTableScreen(this));

		rightButtonBox.getChildren().addAll(addSpriteButton, returnToGameEditButton, addWidthLeftButton, addWidthButton, addHeightUpButton, addHeightButton, addCollTableButton);

	}

	private void configureLevelEditDisplay(Level level) {

		this.levelEditDisplay = new LevelEditDisplay(level.width(),level.height(),level.sprites());
		viewableArea().setCenter(levelEditDisplay);
		levelEditDisplay.setOnMouseReleased(e -> addSpriteToLocation(e));

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

	/*
	 * Visually displays the sprite
	 */
	// TODO: Refactor - this belongs in LevelPlatformView, not here
	private void addSpriteToLevelDisplay(Sprite sprite) {

		ImageView imageView = sprite.spriteImage().getImageViewToDisplay();

		levelEditDisplay.addSpriteToDisplay(sprite,imageView);

		stringToSpriteMap.put(sprite.getName(), sprite);
		stringToListMap.get(sprite.tag()).add(sprite.getName());

	}
	
	private void onLevelScreenRender() {
		levelEditDisplay.setContentMinSize();
		this.setOnMouseEntered(null);
	}

	/**
	 * add a sprite to the level edit screen
	 */
	// TODO: Refactor
	public void addSprite(Sprite sprite) {

		spriteToAdd = sprite;
		imageToAdd = spriteToAdd.spriteImage().getImageViewToDisplay().getImage(); //TODO get rid of magic;
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
