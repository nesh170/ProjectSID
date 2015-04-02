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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import level.Level;
import resources.constants.DOUBLE;
import screen.Screen;
import screen.gameEditScreen.GameEditScreen;
import sprite.Sprite;

/**
 * 
 * @author Leo
 *
 */

public class LevelEditScreen extends Screen {
	
	private Level level;
	private Pane levelScene;
	private Tab currentGameScreen;
	
	private LevelEditScreenController parent;
	
	private final ObservableList<Sprite> listOfPlatforms = FXCollections.observableArrayList();
	private final ObservableList<Sprite> listOfEnemies = FXCollections.observableArrayList();
	private final ObservableList<Sprite> listOfPlayers = FXCollections.observableArrayList();


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
		
		super(width, height);
		initialize(parent, gameScreen);
		
	}
	
	public Level getCurrentLevel() {
		return level;
	}
	
	/*
	 * add a sprite to the level edit screen
	 */
	public void addSprite(Sprite sprite, Point2D location) {
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		Menu fileMenu = makeFileMenu();
		Menu addNewSpriteButton = makeAddSpriteButton();
		
		menuBar.getMenus().addAll(fileMenu,addNewSpriteButton);
		
	}
	
	private void initialize(LevelEditScreenController parent, Tab gameScreen) {
		this.parent = parent;
		currentGameScreen = gameScreen;
		levelScene = new Pane();
		levelScene.setStyle("-fx-background-color: lightgrey");
		this.setCenter(levelScene);
		makeSpritesInLevelTab();
		makeButtonsTab();
	}
	
	private void setUpLevelSceneFromLevel(Level level) {
		this.level = level;
		//TODO populate the levelScene with sprite objects
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
	
	private void makeSpritesInLevelTab() {
		VBox paneForSprites = new VBox();
		this.setLeft(paneForSprites);
		
		TitledPane platforms = makeTitledPane("Platforms",listOfPlatforms);
		TitledPane enemies = makeTitledPane("Enemies",listOfEnemies);
		TitledPane players = makeTitledPane("Players",listOfPlayers); 
		
		paneForSprites.getChildren().addAll(platforms,enemies,players);
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
		
	private TitledPane makeTitledPane(String title, ObservableList<Sprite> content) {
		ListView<Sprite> platformListView = new ListView<>(content);
		return new TitledPane(title, platformListView);

	}
	
	private Button makeButtonForPane(String text, EventHandler<ActionEvent> lambda) {
		Button button = new Button(text);
		button.setOnAction(lambda);
		VBox.setVgrow(button, Priority.NEVER);
		return button;
		
	}
	
}
