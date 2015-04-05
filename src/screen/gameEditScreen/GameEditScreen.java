package screen.gameEditScreen;

import game.Game;

import java.awt.SplashScreen;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import resources.ScreenButton;
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
	
	/**
	 * This method initializes making buttons from STRING constants class for adding and editing
	 * levels and splash screens.
	 * Initializes event handlers for buttons on the screen.
	 * @author Anika
	 */
	private List<ScreenButton> createSplashAndLevelButtons() {

		System.out.println("in method");
		Map<String, String> buttonMap = STRING.LEVELS_SPLASH_MAP;
		String[] buttonNames = (String[]) buttonMap.values().toArray();
		Map<String, ScreenButton> myButtons = new HashMap<String, ScreenButton>();
		
		for (int i = 0; i < buttonNames.length; i++){
			ScreenButton myB = new ScreenButton(buttonNames[i], STRING.BUTTON_STYLE);
			System.out.println(buttonNames[i]);
			myButtons.put(buttonNames[i], myB);
		}	
		
		if (myButtons.containsKey("ADD_LEVEL")) {System.out.println("hi");}
		
		myButtons.get("ADD_LEVEL").setOnMouseClicked(e -> addLevel());
		myButtons.get("ADD_SPLASH").setOnMouseClicked(e -> addSplash());	
		myButtons.get("EDIT_LEVEL").setOnMouseClicked(e -> editLevel());
		myButtons.get("EDIT_SPLASH").setOnMouseClicked(e -> editSplash());
		myButtons.get("REMOVE_LEVEL").setOnMouseClicked(e -> removeLevel());
		
		return new ArrayList(myButtons.values());
	}
	
	
	private Object removeLevel() {
		return null;
	}
	private Object editSplash() {
		// TODO Auto-generated method stub
		return null;
	}
	private Object editLevel() {
		// TODO Auto-generated method stub
		return null;
	}
	private Object addSplash() {
		// TODO Auto-generated method stub
		return null;
	}
	private Object addLevel() {
		// TODO Auto-generated method stub
		return null;
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