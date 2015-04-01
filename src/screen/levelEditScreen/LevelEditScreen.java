package screen.levelEditScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import level.Level;
import resources.constants.DOUBLE;
import screen.Screen;
import sprite.Sprite;

/**
 * 
 * @author Leo
 *
 */

public class LevelEditScreen extends Screen {
	
	private Level level;
	private Pane levelScene;
	
	private LevelEditScreenController parent;
	
	final ObservableList<Sprite> listOfPlatforms = FXCollections.observableArrayList();
	final ObservableList<Sprite> listOfEnemies = FXCollections.observableArrayList();
	final ObservableList<Sprite> listOfPlayers = FXCollections.observableArrayList();


	/**
	 * 
	 * Sets up the Level Edit Screen when the user
	 * selects a level already created.
	 * 
	 * Constructor for loading previously saved levels.
	 * 
	 * @param parent
	 * @param width
	 * @param height
	 * @param level
	 */
	public LevelEditScreen(LevelEditScreenController parent, double width, double height, Level level) {
		super(width,height);
		setUpLevelSceneFromLevel(level);
		makeSpritesInLevelTab();

	}
	
	/**
	 * 
	 * Constructor for creating new levels.
	 * 
	 * @param parent
	 * @param width
	 * @param height
	 */
	public LevelEditScreen(LevelEditScreenController parent, double width, double height) {
		
		super(width, height);
		makeSpritesInLevelTab();
		
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
	
	private void setUpLevelSceneFromLevel(Level level) {
		this.level = level;
		//TODO populate the levelScene with sprite objects
	}
	
	private Menu makeFileMenu() {		
		//TODO more menu items for file
		MenuItem exit = new MenuItem("Exit");
		MenuItem saveAndExit = new MenuItem("Save and Exit");
		
		//TODO possibly change the listeners below
		exit.setOnAction(e -> parent.returnToGameEditScreen());
		saveAndExit.setOnAction(e -> parent.returnToGameEditScreen());
		
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
		
	private TitledPane makeTitledPane(String title, ObservableList<Sprite> content) {
		ListView<Sprite> platformListView = new ListView<>(content);
		return new TitledPane(title, platformListView);
	}

}
