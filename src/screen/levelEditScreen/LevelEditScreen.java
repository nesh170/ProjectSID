package screen.levelEditScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
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
import level.Level;
import resources.constants.DOUBLE;
import screen.Screen;
import screen.gameEditScreen.GameEditScreen;
import sprites.Sprite;

/**
 * 
 * @author Leo
 *
 */

public class LevelEditScreen extends Screen {
	
	// Static Variables
	
	
	// Instance Variables
	private Level level;
	private Pane levelScene;
	private Tab currentGameScreen;
	private Sprite spriteToAdd;
	
	private LevelEditScreenController parent;
	
	private final ObservableList<Sprite> listOfPlatforms = FXCollections.observableArrayList();
	private final ObservableList<Sprite> listOfEnemies = FXCollections.observableArrayList();
	private final ObservableList<Sprite> listOfPlayers = FXCollections.observableArrayList();


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
	public LevelEditScreen(LevelEditScreenController parent, Tab gameScreen, double width, double height) {
		this(parent, gameScreen, width, height, null);
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
	public LevelEditScreen(LevelEditScreenController parent, Tab gameScreen, double width, double height, Level level) {
		
		super(width,height);
		setUpLevelSceneFromLevel(level);
		initialize(parent, gameScreen);

	}
	
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		Menu fileMenu = makeFileMenu();
		Menu addNewSpriteButton = makeAddSpriteButton();
		
		menuBar.getMenus().addAll(fileMenu,addNewSpriteButton);
		
	}
	
	private Menu makeFileMenu() {	
		
		//TODO more menu items for file
		MenuItem exit = new MenuItem("Exit");
		MenuItem saveAndExit = new MenuItem("Save and Exit");
		
		//TODO possibly change the listeners below
		exit.setOnAction(e -> parent.returnToGameEditScreen(currentGameScreen));
		saveAndExit.setOnAction(e -> parent.returnToGameEditScreen(currentGameScreen));
		
		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(exit,saveAndExit);

		return fileMenu;
		
	}
	
	private Menu makeAddSpriteButton() {
		
		ImageView spritePic = new ImageView(new Image("images/sprite.jpg"));
		spritePic.setFitHeight(this.getHeight() * DOUBLE.percentHeightMenuBar);
		spritePic.setFitWidth(this.getHeight() * DOUBLE.percentHeightMenuBar);
		Menu spriteButton = new Menu("Add New Sprite",spritePic);
		
		spriteButton.setOnAction(e -> parent.loadSpriteEditScreen());
		return spriteButton;
		
	}
	
	private void setUpLevelSceneFromLevel(Level level) {
		this.level = level;
		//TODO populate the levelScene with sprite objects
	}
	
	private void initialize(LevelEditScreenController parent, Tab gameScreen) {
		
		this.parent = parent;
		this.currentGameScreen = gameScreen;
		this.levelScene.setStyle("-fx-background-color: lightgrey");
		this.setCenter(levelScene);
		makeLevelScene();
		makeSpritesInLevelTab();
		makeButtonsTab();
		
	}
	
	private void makeLevelScene() {
		this.levelScene = new Pane();
		this.levelScene.setOnMouseReleased(e -> addSpriteToLocation(e));
	}
	
	private void makeSpritesInLevelTab() {
		
		VBox paneForSprites = new VBox();
		this.setLeft(paneForSprites);
		
		TitledPane platforms = makeTitledPane("Platforms",listOfPlatforms);
		TitledPane enemies = makeTitledPane("Enemies",listOfEnemies);
		TitledPane players = makeTitledPane("Players",listOfPlayers); 
		
		paneForSprites.getChildren().addAll(platforms,enemies,players);
		
	}

	private TitledPane makeTitledPane(String title, ObservableList<Sprite> content) {

		ListView<Sprite> platformListView = new ListView<>(content);
		return new TitledPane(title, platformListView);

	}
	
	private void makeButtonsTab() {
		
		VBox paneForButtons = new VBox();
		
		paneForButtons.setAlignment(Pos.BASELINE_CENTER);
		paneForButtons.setFillWidth(false);
		paneForButtons.setSpacing(DOUBLE.BUTTON_SPACING);
		
		this.setRight(paneForButtons);
				
		Button addSpriteButton = makeButtonForPane("Add Sprite", e -> parent.loadSpriteEditScreen());
		Button returnToGameEditButton = makeButtonForPane("Back", e -> parent.returnToGameEditScreen(currentGameScreen));
		
		paneForButtons.getChildren().addAll(addSpriteButton, returnToGameEditButton);

	}
	
	private void addSpriteToLocation(MouseEvent e) {
		
		if(spriteToAdd != null) {
			double xLocation = e.getX();
			double yLocation = e.getY();
			//TODO add sprite to levelScene and add it to the level as well
			spriteToAdd = null; //do this once sprite has been added
		}
		
	}
	
	// All other instance methods
	/**
	 * add a sprite to the level edit screen
	 */
	public void addSprite(Sprite sprite) {
		
		spriteToAdd = sprite;
		
	}
			
}
