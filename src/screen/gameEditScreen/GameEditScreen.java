package screen.gameEditScreen;

import game.Game;
import java.awt.SplashScreen;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import resources.ScreenButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.ScreenController;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
//Question:GameEditScreen do not need to save anything? only trash level or splashscreens? saving done by LevelEdit, SpriteEdit, SplashEdit?
public class GameEditScreen extends Screen {
	
	// Instance variables
	private GameEditScreenController controller;
	private Game myGame;

	private ObservableList<Level>	myLevels;  	
	private ObservableList<SplashScreen> mySplashScreen; 
	
	// Getters & Setters
	/**
	 * add to current game level
	 * @param level
	 * @return
	 */
	public void setMyLevel(int index, Level level){
		myLevels.add(index, level);
	}	
	
	/**
	 * Set up GameEdit screen from previously created game to re-edit game.
	 * @param controller, width, height, game
	 */
	public GameEditScreen(Game game, ScreenController controller, double width, double height){
		super(null, width, height);
		myGame = game;
		initialize(controller);
	}
	/**
	 * Creates new GameEditScreen
	 */
	public GameEditScreen( ScreenController controller, double width, double height) {
			this(new Game() ,controller, width, height);
	}
	
	@Override
	protected void createAppropriateControllerForParent(ScreenController parent) {
		this.controller = new GameEditScreenManager(parent);
	}
	
	private void configureButtons() {	
		
		//this.setRight(makeBackButton());		
		//this.setBottom(makePlayButton());
		this.setCenter(DisplayLevels(myLevels));		
		this.setLeft(DisplaySplash());
		//this.setBottom(makeAddLevelButton());
		//set rest of buttons
		 System.out.println("my width is "+ this.getWidth() + "  " + this.getHeight());
		 
	}
	
	/**
	 * 
	 * @param controller
	 */
	private void initialize(ScreenController controller){
		createAppropriateControllerForParent(controller);
		configureButtons();
	}
	/**
	 * Displays a splash 
	 * @return
	 */
	private VBox DisplaySplash() {
		
		VBox displaySplash  = new VBox();
		displaySplash.setAlignment(Pos.CENTER);
		displaySplash.setStyle(STRING.FX_GAME_EDIT_BACKGROUND);
		
		StackPane sp = new StackPane();		
		sp.getChildren().addAll(makeText(STRING.SPLASH_SCREEN), makeRectangularShape(), makeAddSign());
			
		displaySplash.getChildren().add(sp);
		return displaySplash;
		
	}
	
	private Text makeText(String s) {
		
		Text text = new Text(s);
		Reflection r = new Reflection();
		r.setFraction(0.6f);		 
		text.setEffect(r);
		text.setFont(Font.font("SERIF", FontWeight.BOLD, 30));
		text.setTranslateY(-300);  //?? uncertain of how offset works but this works for now
		return text;
		
	}
	
	private Rectangle makeRectangularShape() {		
		Rectangle screen = new Rectangle(INT.DEFAULT_LEVEL_DISPLAY_WIDTH, INT.DEFAULT_LEVEL_DISPLAY_HEIGHT, Color.TRANSPARENT);
		screen.setStroke(Color.GRAY);
		return screen;
		
	}
	
	private  ImageView makeAddSign() {
		
		ImageView addsign = new ImageView(new Image(STRING.ADD_SIGN));
		addsign.setFitHeight(INT.GAMEEDIT_ADD_SIGN_DIM);
		addsign.setFitWidth(INT.GAMEEDIT_ADD_SIGN_DIM);
		addsign.setOnMouseClicked(e -> controller.loadSplashEditScreen());
		return addsign;		
	}
	
	/**
	 * display list of levels that are represented by images in parallel 
	 * @param ObservableList<Level>
	 */
	private ScrollPane DisplayLevels(List<Level> levels) { 
		
		//TODO: get ListView: List<LevelView>: a group to represent each level, in replace of ImageView below
		//TableView<ObservableList> levelTable = new TableView();	
		ScrollPane s1 = new ScrollPane();
		s1.setFitToHeight(true); //wont extend out of ScrollPane vertically 
		s1.setPannable(true);
		HBox levelView = displayLevelsInParallel();
		s1.setContent(levelView);
		return s1;
		
	}
	
	private HBox displayLevelsInParallel() {
		
		//can't add ObservableList to a HBox directly
		HBox levelView = new HBox(INT.GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE);
		levelView.setAlignment(Pos.CENTER);	
		ImageView level1 = makeTempLeveDisplayImage(STRING.LEVEL1IMAGE);  //temp string path
		level1.setOnMouseClicked(handleDouleRightClick(level1));		
		ImageView level2 = makeTempLeveDisplayImage(STRING.LEVEL2IMAGE);
		level2.setOnMouseClicked(handleDouleRightClick(level2));
		ImageView level3 = makeTempLeveDisplayImage(STRING.SPRITEIMAGE);
		levelView.getChildren().addAll(level1, level2, level3);
		level3.setOnMouseClicked(handleDouleRightClick(level3));
		return levelView;		
	}
	
	private EventHandler<MouseEvent> handleDouleRightClick(Node node){
		return new EventHandler<MouseEvent>() { //double Click to edit a screen
		    public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		            	System.out.println("Double Clicked");
		            	//throw new IllegalStateException("unimplemented loadEditLevelScreen: need to pass in a level");
		            }
		        }
		        else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
		        	makeEditAndRemoveMenu().show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());		        	
		        }
		    }
		};

	}
	
	private ContextMenu makeEditAndRemoveMenu(){ //pass in Level
		final ContextMenu rMenu = new ContextMenu();
		MenuItem edit = new MenuItem("Edit");
		//edit.setOnAction(controller.loadLevelEditScreen(level);
		MenuItem remove = new MenuItem("remove");
		//remove.setOnAction(controller.trashLevel(level);
		rMenu.getItems().addAll(edit, remove);
		return rMenu;
	}
	//temporary methods to display level Image
	private ImageView makeTempLeveDisplayImage(String path) {
		
		ImageView level1 = new ImageView(new Image(path));
		level1.setFitHeight(INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		level1.setFitWidth(INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
		return level1;
		
	}
	
	/**
	 * This method initializes making buttons from STRING constants class for adding and editing
	 * levels and splash screens.
	 * Initializes event handlers for buttons on the screen.
	 * @author Anika modified by Yongjiao
	 */
	private List<ScreenButton> createSplashAndLevelButtons() {		
		Map<String, String> buttonMap = STRING.LEVELS_SPLASH_MAP;
		String[] buttonNames = (String[]) buttonMap.values().toArray();
		Map<String, ScreenButton> myButtons = new HashMap<String, ScreenButton>();		
		for (int i = 0; i < buttonNames.length; i++){
			ScreenButton myB = new ScreenButton(buttonNames[i], STRING.BUTTON_STYLE);
			System.out.println(buttonNames[i]);
			myButtons.put(buttonNames[i], myB);
		}	
//modified by Yongjiao: changed to use methods in manager class.
		myButtons.get("ADD_LEVEL").setOnMouseClicked( e -> controller.loadLevelEditScreen()); 
		myButtons.get("ADD_SPLASH").setOnMouseClicked(e -> controller.loadSplashEditScreen());	
//		myButtons.get("EDIT_LEVEL").setOnMouseClicked(e -> parent.loadLevelEditScreen(level));
//		myButtons.get("EDIT_SPLASH").setOnMouseClicked(e -> parent.loadSplashEditScreen(game));
//		myButtons.get("REMOVE_LEVEL").setOnMouseClicked(e -> parent.trashLevel(level));		
		return new ArrayList(myButtons.values());
		
	}

	private Button makeTrashButton(){		
		Button trash = new Button(STRING.TRASH);
		ImageView trashButton = new ImageView(new Image(STRING.TRASH_BUTTON)); 
		return trash;		
	}
	
	private Button makePlayButton() {	
		Button play = new Button(STRING.PLAY);
		play.setMinSize(INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_WIDTH, INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_HEIGHT);
		play.setOnMouseClicked(e -> controller.playGame(myGame));
		return play;
		
	}
	
	private Button makeBackButton() {		
		Button back = new Button(STRING.BACK);
		back.setMinSize(INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_WIDTH, INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_HEIGHT); 
		back.setOnMouseClicked(e -> controller.returnToMainMenuScreen());		
		return back;
		
	}
	
	/**
	 * Consider using makeFileMenu(EventHandler<ActionEvent>... fileMenuActions)
	 * located in the abstract class screen in order to reduce duplicated code
	 * in different screens (all screens have a file menu)
	 * 
	 * -Leo
	 */
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {					
		Menu back = new Menu("Go Back");
		back.setOnAction(o -> controller.returnToMainMenuScreen());
		menuBar.getMenus().addAll(makeFileMenu(), makeLevelMenu(), makeSplashMenu(), back, makeTrashMenu());	

	}		

	private Menu makeFileMenu() {
		
		Menu fileMenu = new Menu("File");
		MenuItem save = new MenuItem("Save");
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(o -> System.exit(0));
		fileMenu.getItems().addAll(save, exit);
		return fileMenu;
		
	}
	
	private Menu makeLevelMenu() {
		
		Menu levelMenu = new Menu("Level");
		MenuItem addLevel = new MenuItem("Add new Level");
		addLevel.setOnAction(o -> controller.loadLevelEditScreen());
		MenuItem editLevel = new MenuItem("Edit Level");
//		addLevel.setOnAction(o -> parent.loadLevelEditScreen(selectedLevel));
		levelMenu.getItems().addAll(addLevel, editLevel);
		return levelMenu;
		
	}
	
	private Menu makeSplashMenu() {
		
		Menu splashMenu = new Menu("Splash Screen");
		MenuItem addSplash = new MenuItem("Add new Splash Screen");
		addSplash.setOnAction(o -> controller.loadSplashEditScreen());
		MenuItem editSplash = new MenuItem("Edit Splash Screen");
		//	addSplash.setOnAction(o -> parent.loadSplashEditScreen(selectedSplash));
		splashMenu.getItems().addAll(addSplash, editSplash);
		return splashMenu;
		
	}
	
	private Menu makeTrashMenu() {
		
		ImageView trashImage = new ImageView(new Image(STRING.TRASH_ICON));
		
		super.sizeMenuImageView(trashImage, DOUBLE.MENU_BAR_HEIGHT, DOUBLE.MENU_BAR_HEIGHT);
		
		Menu trashButton = new Menu("", trashImage);
		return trashButton;
		
	}

}
