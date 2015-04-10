package screen.levelEditScreen;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import levelPlatform.level.LevelView;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.ScreenController;
import screen.gameEditScreen.GameEditScreen;
import sprite.Sprite;
import sprite.SpriteImage;

/**
 * 
 * @author Leo
 *
 */

public class LevelEditScreen extends Screen {
	
	// Static Variables
	
	
	// Instance Variables
	private LevelEditScreenController controller;
	
	private Level level;
	private LevelView levelView;
	private Tab currentGameScreen;
	private Sprite spriteToAdd;
	private Sprite selectedSprite;
				
	private final ObservableList<String> listOfPlatforms = FXCollections.observableArrayList();
	private final ObservableList<String> listOfEnemies = FXCollections.observableArrayList();
	private final ObservableList<String> listOfPlayers = FXCollections.observableArrayList();
	private final ObservableList<String> listOfPowerups = FXCollections.observableArrayList();
	
	private Map<String,ObservableList<String>> stringToListMap;
	private Map<String,Sprite> stringToSpriteMap;


	// Getters & Setters
	public Level getCurrentLevel() {
		return level;
	}
	
	
	// Constructor & Helpers
	/**
	 * 
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
	 * 
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
	public LevelEditScreen(LevelEditScreenController parent, double width, double height, Level level) {
		
		super(width,height);
		
		this.controller = parent;
		
		stringToSpriteMap = new HashMap<>();
		
		setUpLevelViewFromLevel(level);
		makeSpritesInLevelTab();
		makeButtonsTab();

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
				
	private Menu makeAddSpriteButton() {
		
		ImageView spritePic = new ImageView(new Image("images/sprite.jpg"));
		
		super.sizeMenuImageView(spritePic, DOUBLE.MENU_BAR_HEIGHT, DOUBLE.MENU_BAR_HEIGHT);
		
		Menu spriteButton = new Menu(STRING.ADD_SPRITE,spritePic);
		MenuItem addSprite = new MenuItem(STRING.ADD_SPRITE);
		spriteButton.getItems().add(addSprite);
		
		addSprite.setOnAction(e -> controller.loadSpriteEditScreen(new Sprite()));
		return spriteButton;
		
	}
	
	private void setUpLevelViewFromLevel(Level level) {
		
		this.level = level;
		
		Level levelToUse = level;
		
		if (levelToUse == null) {
			levelToUse = new Level(INT.DEFAULT_LEVEL_DISPLAY_WIDTH, INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		}
		
		this.levelView = new LevelView(levelToUse, EditMode.EDIT_MODE_ON);
		this.viewableArea().setCenter(levelView);
		this.levelView.setOnMouseReleased(e -> addSpriteToLocation(e));
		
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
					levelView.getImageForSprite(selectedSprite).setOpacity(1);
				}
				/*
				 * this next line could throw an exception possibly if
				 * the selection model is empty, catch statement is precautionary
				 */				
				String sprite = platformListView.getSelectionModel().getSelectedItem();
				selectedSprite = stringToSpriteMap.get(sprite);
				levelView.getImageForSprite(selectedSprite).setOpacity(0.4); //magic number? TODO move this number somewhere
			}
			catch (IndexOutOfBoundsException | NullPointerException ee) {
				//do not select any sprites, since no sprites are in the selection model
			}
		});
		return new TitledPane(title, platformListView);

	}
	
	private void makeButtonsTab() {
		
		VBox paneForButtons = new VBox();
		
		paneForButtons.setAlignment(Pos.BASELINE_CENTER);
		paneForButtons.setFillWidth(false);
		paneForButtons.setSpacing(DOUBLE.BUTTON_SPACING);
		paneForButtons.getStyleClass().add("pane");
		
		this.viewableArea().setRight(paneForButtons);
				
		Button addSpriteButton = makeButtonForPane("Add Sprite", e -> controller.loadSpriteEditScreen(new Sprite()));
		Button returnToGameEditButton = makeButtonForPane("Back", e -> controller.returnToGameEditScreen());
		
		paneForButtons.getChildren().addAll(addSpriteButton, returnToGameEditButton);

	}
	
	private void addSpriteToLocation(MouseEvent e) {
		
		if(spriteToAdd != null) {
			
			stringToSpriteMap.put(spriteToAdd.getName(), spriteToAdd);
			stringToListMap.get(spriteToAdd.tag()).add(spriteToAdd.getName());
			
			configureSpriteXYFromClick(e, spriteToAdd);
			
			level.sprites().add(spriteToAdd);
			setUpLevelViewFromLevel(level);
			levelView.renderLevel();

			//do this once sprite has been added
			spriteToAdd = null; 
			
		}
		
	}
	
	// TODO
	private void configureSpriteXYFromClick(MouseEvent e, Sprite sprite) {
		
//		System.out.println("LevelEditScreen uses e.getX and Y. those are JavaFX. use the"
//		+ " future util to first convert those to SID pixels");
		
		double xLocation = e.getX();
		double yLocation = e.getY();
		
		sprite.setX(xLocation);
		sprite.setY(yLocation);
				
	}
		
	private void save() {
		//TODO save this level to XML (and update game edit screen)?
	}
	
	// All other instance methods
	/**
	 * add a sprite to the level edit screen
	 */
	public void addSprite(Sprite sprite) {
		
		spriteToAdd = sprite;
		
	}
			
}
