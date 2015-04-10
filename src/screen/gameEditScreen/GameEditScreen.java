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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
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
	public GameEditScreen(Game game, GameEditScreenController controller, double width, double height){
		super(width, height);
		myGame = game;
		initialize(controller);
		this.setStyle(STRING.FX_GAME_EDIT_BACKGROUND);
	}
	
	private void configureButtons() {	
		
		this.setCenter(DisplayLevelRegion());		
		this.setLeft(DisplaySplash());		 
	}
	
	/**
	 * @param controller
	 */
	private void initialize(GameEditScreenController controller){
		this.controller = controller;
		configureButtons();
	}
	/**
	 * Displays a splash 
	 * @return
	 */
	private VBox DisplaySplash() {
		
		VBox displaySplash  = new VBox();
		displaySplash.setAlignment(Pos.CENTER);
		
		StackPane sp = new StackPane();		
		sp.getChildren().addAll(makeText(STRING.SPLASH_SCREEN), makeAddSplashSign());
			
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
	private  Button makeAddSplashSign() {
		ImageView addsign = new ImageView(new Image(STRING.ADD_IMG));
		addsign.setFitHeight(INT.GAMEEDIT_ADD_SIGN_DIM);
		addsign.setFitWidth(INT.GAMEEDIT_ADD_SIGN_DIM);
		Button b = new Button("Add New Splash Screen", addsign);
		b.setContentDisplay(ContentDisplay.TOP);
		b.setOnMouseClicked(e -> controller.loadSplashEditScreen(myGame)); //？change to doubleclicked
		b.setMinSize(INT.DEFAULT_LEVEL_DISPLAY_WIDTH, INT.DEFAULT_LEVEL_DISPLAY_HEIGHT); 
		b.setGraphic(addsign);			
		return b;		
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

	private StackPane DisplayLevelRegion(){
		StackPane levelRegion = new StackPane();	//, DisplayLevels(myLevels)
		
		ImageView add = makeButton(STRING.PLUS_IMG);
		add.setOnMouseClicked(e -> controller.loadLevelEditScreen(myGame));
		levelRegion.setAlignment(add, Pos.TOP_RIGHT);		
		ImageView play = makeButton(STRING.PLAY_IMG);
		play.setOnMouseClicked(e -> controller.playGame(myGame));
		levelRegion.setAlignment(play, Pos.TOP_CENTER);
		ImageView back = makeButton(STRING.BACK_IMG);
		levelRegion.setAlignment(back, Pos.TOP_LEFT);
		back.setOnMouseClicked(e -> controller.returnToMainMenuScreen());
		ImageView trash = makeButton(STRING.TRASH_IMG);
		//trash.setOnMouseClicked(e -> controller.trashLevel(myGame, levelIndex));
		levelRegion.setAlignment(trash, Pos.BOTTOM_RIGHT);
		levelRegion.getChildren().addAll(DisplayLevels(myLevels), back, add, play, trash);			
		return levelRegion;
	}
	
	private HBox displayLevelsInParallel() {
	
		//can't add ObservableList to a HBox directly
		HBox levelView = new HBox(INT.GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE);
		levelView.setAlignment(Pos.CENTER);	
		Button level1 = makeTempLeveDisplayImage(STRING.LEVEL1IMAGE);  //tmp string path
		level1.setOnMouseClicked(handleDouleRightClick(level1));		
		Button level2 = makeTempLeveDisplayImage(STRING.LEVEL2IMAGE);
		level2.setOnMouseClicked(handleDouleRightClick(level2));
		Button level3 = makeTempLeveDisplayImage(STRING.SPRITEIMAGE);
		levelView.getChildren().addAll(level1, level2, level3);
		level3.setOnMouseClicked(handleDouleRightClick(level3));
		return levelView;		
	}
	/**
	 * handles mouse event: double left mouse click and right mouse click 
	 * @param node
	 * @return EventHandler<MouseEvent>
	 */
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
		        	makeRightClickeMenu().show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());		        	
		        }
		    }
		};
	}
	/**
	 * make a right click menu for editing and removing a level
	 */
	private ContextMenu makeRightClickeMenu(){ //pass in Level
		final ContextMenu rMenu = new ContextMenu();
		MenuItem edit = new MenuItem("Edit");
		//edit.setOnAction(controller.loadLevelEditScreen();
		MenuItem remove = new MenuItem("remove");
		//remove.setOnAction(controller.trashLevel(level);
		rMenu.getItems().addAll(edit, remove);
		return rMenu;
	}
	//temporary methods to display level Image
	private Button makeTempLeveDisplayImage(String path) {
		Button b = new Button();
		ImageView level1 = new ImageView(new Image(path));
		level1.setFitHeight(INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		level1.setFitWidth(INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
		b.setGraphic(level1);
		return b;		
	}
	
	/**
	 * This method initializes making buttons from STRING constants class for adding and editing
	 * levels and splash screens.
	 * Initializes event handlers for buttons on the screen.
	 * @author Anika modified by Yongjiao
	 */
	private List<ScreenButton> createSplashAndLevelButtons() {		
		// get <String buttonID, String buttonName> map for buttons from STRING constants class
		Map<String, String> buttonMap = STRING.LEVELS_SPLASH_MAP;
		// get values of map and put into an array -> buttonNames = array of button names
		String[] buttonNames = (String[]) buttonMap.values().toArray();
		// create a map of <String buttonID, ScreenButton button>
		Map<String, ScreenButton> myButtons = new HashMap<String, ScreenButton>();	
		// for each name in buttonNames, create a new ScreenButton and put it in the map
		for (int i = 0; i < buttonNames.length; i++){
			ScreenButton myB = new ScreenButton(buttonNames[i], STRING.BUTTON_STYLE);
			System.out.println(buttonNames[i]); // testing
			myButtons.put(buttonNames[i], myB);
		}	
		/*
		 * initialize event handlers for buttons
		 * modified by Yongjiao: changed to use methods in manager class.
		 * modified by Anika: changed to use methods in controller class
		 */
//		myButtons.get("ADD_LEVEL").setOnMouseClicked( e -> controller.loadLevelEditScreen()); 
//		myButtons.get("ADD_SPLASH").setOnMouseClicked(e -> controller.loadSplashEditScreen());	
//		myButtons.get("EDIT_LEVEL").setOnMouseClicked(e -> parent.loadLevelEditScreen(level));
//		myButtons.get("EDIT_SPLASH").setOnMouseClicked(e -> parent.loadSplashEditScreen(game));
//		myButtons.get("REMOVE_LEVEL").setOnMouseClicked(e -> parent.trashLevel(level));		
		return new ArrayList(myButtons.values());	
	}
	
	private ImageView makeButton(String location){
		ImageView b = new ImageView(new Image(location));
		b.setFitHeight(80);
		b.setFitWidth(80);
		return b;
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
		save.setOnAction(o -> controller.saveGame(myGame));
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(o -> controller.loadLevelEditScreen(myGame));
		fileMenu.getItems().addAll(save, exit);
		return fileMenu;
		
	}
	
	private Menu makeLevelMenu() {
		
		Menu levelMenu = new Menu("Level");
		MenuItem addLevel = new MenuItem("Add new Level");
		addLevel.setOnAction(o -> controller.loadLevelEditScreen(myGame));
		MenuItem editLevel = new MenuItem("Edit Level");
//		editLevel.setOnAction(o -> controller.loadLevelEditScreen(myGame.getLevel(index))); //references to the specific level within a game
		levelMenu.getItems().addAll(addLevel, editLevel);
		return levelMenu;
		
	}
	
	private Menu makeSplashMenu() {
		
		Menu splashMenu = new Menu("Splash Screen");
		MenuItem addSplash = new MenuItem("Add new Splash Screen");
		addSplash.setOnAction(o -> controller.loadSplashEditScreen(myGame));
		MenuItem editSplash = new MenuItem("Edit Splash Screen");
		//	addSplash.setOnAction(o -> controller.loadSplashEditScreen(selectedSplash));
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