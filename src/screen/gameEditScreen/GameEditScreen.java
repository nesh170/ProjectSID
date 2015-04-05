package screen.gameEditScreen;

import game.Game;

import java.awt.SplashScreen;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import levelPlatform.level.Level;
/**
 * The screen where users edit a game
 * allows users to edit a level or edit a sprite.
 * @author Yongjiao
 * @author Anika
 * TODO by Yongjiao: add the rest of buttons to its appropriate place
 * 
 * TODO by ANIKA: loop creation of buttons - static array in resources of string names
 */
//always working on the "stuff" in parent's parent: work on BoarderPane directly (ex: setLeft, setRight)
//Question:GameEditScreen do not need to save anything? only trash level or splashscreens? saving done by LevelEdit, SpriteEdit, SplashEdit?
public class GameEditScreen extends Screen {
	// Instance variables
	private GameEditScreenController parent;
	private Game myGame;

	private ObservableList<Level>	myLevels;  	
	private ObservableList<SplashScreen> mySplashScreen; 
	
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
		myGame = new Game(); //initialize a game 
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
		//this.setRight(makeBackButton());		
		//this.setBottom(makePlayButton());
		this.setCenter(makeLevelsDisplay(myLevels));		
		this.setLeft(makeSplashScreen(mySplashScreen));
		//this.setBottom(makeAddLevelButton());
		//set rest of buttons
	}
	/**
	 * display list of levels that are represented by images in parallel 
	 * @param ObservableList<Level>
	 */
	private ScrollPane makeLevelsDisplay(List<Level> levels){ 
		//TODO: get ListView: List<LevelView>: a group to represent each level, in replace of ImageView below
		//TableView<ObservableList> levelTable = new TableView();	
		ScrollPane s1 = new ScrollPane();
		s1.setFitToHeight(true); //wont extend out of ScrollPane vertically 
		s1.setPannable(true);
		HBox levelView = displayLevelsInParallel();
		s1.setContent(levelView);
		System.out.println("The bounds of the box is" + s1.getViewportBounds());
		return s1;
	}
	private HBox displayLevelsInParallel(){
		HBox levelView = new HBox(INT.GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE);
		levelView.setAlignment(Pos.CENTER);	
		ImageView level1 = makeTempLeveDisplayImage("images/level1_tmp.PNG");
		ImageView level2 = makeTempLeveDisplayImage("images/level2_tmp.PNG");
		ImageView level3 = makeTempLeveDisplayImage("images/sprite.jpg");
		levelView.getChildren().addAll(level1, level2, level3);
		return levelView;
	}
	//temporary methods to display level Image
	private ImageView makeTempLeveDisplayImage(String path){
		ImageView level1 = new ImageView(new Image(path));
		level1.setFitHeight(400);
		level1.setFitWidth(500);
		return level1;
	}
	private ListView makeSplashScreen(List<SplashScreen> screen){
		ObservableList<String> mySplash = FXCollections.observableArrayList(
		          "Splash Screen1", "Splash Screen 2", "Splash Screen 3", "Splash Screen 4"); //change to track game splash
		 ListView<String> splashList = new ListView<String>(mySplash);
		 return splashList;
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
		/**
		 * Consider using makeFileMenu(EventHandler<ActionEvent>... fileMenuActions)
		 * located in the abstract class screen in order to reduce duplicated code
		 * in different screens (all screens have a file menu)
		 * 
		 * -Leo
		 */
		menuBar.getMenus().addAll(makeFileMenu(), makeLevelMenu(), makeSplashMenu(), back);				
	}		
	/*
	 * This method initializes making buttons from the properties files labels.
	 * Initializes event handlers for buttons on the screen
	 * @author Anika
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
}