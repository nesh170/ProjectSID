package screen.gameEditScreen;

import game.Game;

import java.awt.SplashScreen;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import levelPlatform.level.Level;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
/**
 * The screen where users edit a game
 * allows users to edit a level or edit a sprite.
 * @author Yongjiao
 * @author Anika
 * 
 * TODO ANIKA: loop creation of buttons - static array in resources of string names
 */
//Question:GameEditScreen do not need to save anything? only trash level or splashscreens? saving done by LevelEdit, SpriteEdit, SplashEdit?
public class GameEditScreen extends Screen {
	// Instance variables
	private GameEditScreenController parent;
	private Game myGame;
	private ArrayList<Level>	myLevels;  //may not need or change type
	private ArrayList<SplashScreen> mySplashScreen; //may not need
	private static final String BUTTON_STYLE = 
			"-fx-font: 14 georgia; -fx-text-fill: black;  "
			+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); "
			+ "-fx-border-width: 2 2 2 2; -fx-border-color: #006652; -fx-background-color: white;";
	
	private String[] myScreenButtonsNames;
	private Button[] myScreenButtons;
	/**
	 * Set up GameEdit screen from previously created game to re-edit game.
	 * @param controller, width, height, game
	 */
	public GameEditScreen(Game game, GameEditScreenController controller, double width, double height){
		super(width, height);
		this.myGame = game;
		//this.myGame.getLevels() = game.getLevels();
		//this.myGame.getSplash() = game.getSplash();
		configureParent(controller);
		//other configurations
	}
	/**
	 * Creates new GameEditScreen
	 */
	public GameEditScreen( GameEditScreenController controller, double width, double height) {
		super(width, height);
		myGame = new Game(); //inatilize a game 
		configureParent(controller);
		configureButtons();
	}
	// Getters & Setters
	/**
	 * add to current game level
	 * @param level
	 * @return
	 */
	public void setMyLevel(int index, Level level){
		myLevels.add(index, level);
	}
	
	private void configureParent(GameEditScreenController controller) {
		this.parent = controller;	
	}	
	private void configureButtons() {		
		this.setRight(makeBackButton());		
		this.setBottom(makePlayButton());
		//set rest of buttons
	}
	private Button makeAddLevelButton(){
		Button addSplash = new Button(STRING.ADD_SPLASH);
		return addSplash;
	}
	private Button makeEditLevelButton(){
		Button editLevel = new Button(STRING.EDIT_LEVEL);
		return editLevel;
	}
	private Button makeAddSplashButton(){
		Button addSplash = new Button(STRING.ADD_SPLASH);
		return addSplash;
	}
	private Button makeEditSplashButton(){
		Button editSplash = new Button(STRING.EDIT_SPLASH);
		return editSplash;
	}
	private Button makeTrashButton(){
		Button trash = new Button(STRING.TRASH);
		return trash;
	}
	private Button makePlayButton(){
		Button play = new Button(STRING.PLAY);
		play.setMinSize(INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_WIDTH, INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_HEIGHT);
		play.setOnMouseClicked(e -> parent.playGame(myGame));
		return play;
	}
	private Button makeBackButton(){
		Button back = new Button(STRING.BACK);
		back.setMinSize(INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_WIDTH, INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_HEIGHT); 
		back.setOnMouseClicked(e -> parent.returnToMainMenuScreen());		
		return back;
	}
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
			
			Menu back = new Menu("Go Back");
			//MenuItem back = new MenuItem("back");
			//step.setOnAction(o -> loadMainMenuScreen());
			//back.getItems().addAll(back);
			menuBar.getMenus().addAll(makeFileMenu(), makeLevelMenu(), makeSplashMenu(), back);				
	}	
	
	/**
	 * This method initializes making buttons from the properties files labels.
	 * Initializes event handlers for buttons on the screen
	 * @author Anika
	 */	
/*	
	private void createScreenButtons() {
		// Creates buttons that are put on the screen
		for (int i = 0; i < NUM_BUTTONS; i++){
			ScreenButton myB = new ScreenButton(myButtonLabels.getString(myScreenButtonsNames[i]), BUTTON_STYLE);
			myScreenButtons[i] = myB;
		//	hbox.getChildren().add(myB); TODO: add to screen
		}	
		myScreenButtons[SAVE_BUTTON].setOnMouseClicked(e -> saveLevel());
		myScreenButtons[PLAY_BUTTON].setOnMouseClicked(e -> playGame());
		myScreenButtons[REMOVE_SPLASH_BUTTON].setOnMouseClicked(e -> trashSplashScreen());
		myScreenButtons[REMOVE_LEVEL_BUTTON].setOnMouseClicked(e -> removeLevel());

		myScreenButtons[ADD_LEVEL_BUTTON].setOnMouseClicked(e -> addLevel());
		myScreenButtons[ADD_SPLASH_BUTTON].setOnMouseClicked(e -> addSplash());			
	}
**/
	private Menu makeFileMenu(){
		Menu fileMenu = new Menu("File");
		MenuItem save = new MenuItem("Save");
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(o -> System.exit(0));
		fileMenu.getItems().addAll(save, exit);
		return fileMenu;
	}
	private Menu makeLevelMenu(){
		Menu levelMenu = new Menu("Level");
		MenuItem addLevel = new MenuItem("Add new Level");
		addLevel.setOnAction(o -> parent.loadLevelEditScreen());
		MenuItem editLevel = new MenuItem("Edit Level");
//		addLevel.setOnAction(o -> parent.loadLevelEditScreen(selectedLevel));
		levelMenu.getItems().addAll(addLevel, editLevel);
		return levelMenu;
	}
	private Menu makeSplashMenu(){
		Menu splashMenu = new Menu("Splash Screen");
		MenuItem addSplash = new MenuItem("Add new Splash Screen");
		addSplash.setOnAction(o -> parent.loadSplashEditScreen());
		MenuItem editSplash = new MenuItem("Edit Splash Screen");
		//	addSplash.setOnAction(o -> parent.loadSplashEditScreen(selectedSplash));
		splashMenu.getItems().addAll(addSplash, editSplash);
		return splashMenu;
	}

	
	//MenuBar
	//TODO: Back, returns to main menu
	//TODO: Add, adds a level
	//TODO: Remove, removes a level
	//TODO: Play, starts the game
	//TODO: Add Splash Screen
	//TODO: Save, saves level and possible splash screen
	//TODO: Trash, trashes the level and possible splash screen

}