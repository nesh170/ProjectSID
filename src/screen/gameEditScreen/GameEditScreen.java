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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import levelPlatform.level.Level;

/**
 * The screen where users edit a game
 * allows users to edit a level or edit a sprite.
 * @author Yongjiao
 * @author Anika 
 */
//Splash double click and right click
public class GameEditScreen extends Screen {
	
	// Instance variables
	private GameEditScreenController controller;
	private Game myGame;
	private ObservableList<Level>	myLevels;  	
	private SplashScreen mySplashScreen; 
	
	//initalized for testing purpose
	private Level selectedLevel = new Level(INT.DEFAULT_LEVEL_WIDTH, INT.DEFAULT_LEVEL_HEIGHT); 
	private int selectedIndex = 0;
	
	private StackPane levelDisplay;
	private VBox splashDisplay;
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
		System.out.println(myGame);
		initialize(controller);
		this.setStyle(STRING.FX_GAME_EDIT_BACKGROUND);
	}

	/**
	 * @param controller
	 */
	private void initialize(GameEditScreenController controller){
		this.controller = controller;
		configureButtons();
	}
	private void configureButtons() {	
		configureLevelDisplay();
		this.setCenter(levelDisplay);
		
		ConfigureSplashDisplay();
		this.setLeft(splashDisplay);	
	}
	

	/**
	 * Displays a splash 
	 * @return
	 */
	private void ConfigureSplashDisplay() {
		
		splashDisplay  = new VBox();
		splashDisplay.setAlignment(Pos.CENTER);
		StackPane sp = new StackPane();	
		splashDisplay.getChildren().add(sp);
		
		sp.getChildren().addAll(makeText(STRING.SPLASH_SCREEN));
		Button s;
		if(!myGame.hasSplash())
			s = makeAddSignWhenEmpty("Add New Splash Screen");
		else
			s = displayMySplash();
		sp.getChildren().add(s);
	}
	
	private Button displayMySplash(){
		Button b =  makeTempLevelSplashDisplayImage(STRING.SPLASH_TMP);
		//b.setOnMouseClicked(e -> this.handleDouleRightClick(b)); //parameter: myGame.getSplash().ImageRepresentation();
		return b;
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
	private  Button makeAddSignWhenEmpty( String s) {
		ImageView addsign = new ImageView(new Image(STRING.ADD_IMG));
		addsign.setFitHeight(INT.GAMEEDIT_ADD_SIGN_DIM);
		addsign.setFitWidth(INT.GAMEEDIT_ADD_SIGN_DIM);
		Button b = new Button(s, addsign);
		b.setContentDisplay(ContentDisplay.TOP);
		b.setOnMouseClicked(e -> controller.loadSplashEditScreen(myGame)); //？change to doubleclicked
		b.setMinSize(INT.DEFAULT_LEVEL_DISPLAY_WIDTH, INT.DEFAULT_LEVEL_DISPLAY_HEIGHT); 
		b.setGraphic(addsign);	
		return b;
	}
	
	/**
	 * display a note on editing and removing a level
	 */
	private Text displayNote() {
		Text t = new Text(STRING.NOTE);
		t.setTranslateY(210);
		t.setTranslateX(250);
		return t;
	}	
	
	private void configureLevelDisplay(){
			//, DisplayLevels(myLevels)
		levelDisplay = new StackPane();
		ScrollPane levelSP = this.displayLevels(myLevels);

		ImageView add = makeButton(STRING.PLUS_IMG, e -> controller.loadLevelEditScreen(myGame));
		levelDisplay.setAlignment(add, Pos.TOP_RIGHT);		
		
		ImageView play = makeButton(STRING.PLAY_IMG, e -> controller.playGame(myGame));
		levelDisplay.setAlignment(play, Pos.TOP_CENTER);
		
		ImageView back = makeButton(STRING.BACK_IMG, e -> controller.returnToMainMenuScreen());
		levelDisplay.setAlignment(back, Pos.TOP_LEFT);
		
//		ImageView trash = makeButton(STRING.TRASH_IMG, e -> controller.trashLevel(myGame, selectedIndex));
//		levelDisplay.setAlignment(trash, Pos.BOTTOM_RIGHT);
		
		levelDisplay.getChildren().addAll(levelSP, back, add, play, displayNote());
	}
	
	/**
	 * display list of levels that are represented by images in parallel 
	 * @param ObservableList<Level>
	 */
	private ScrollPane displayLevels(List<Level> levels) { 		
		//TODO: get ListView: List<LevelView>: a group to represent each level, in replace of ImageView below
		//TableView<ObservableList> levelTable = new TableView();	
		ScrollPane sp = configureScrollPane();
		HBox levelHB = configureHBox();
		sp.setContent(levelHB);
		if(myGame.hasLevel())
			 displayLevelsInParallel(levelHB);
		else			
			displayLevelsWhenEmpty(levelHB);		
		return sp;
		
	}
	private HBox configureHBox(){
		HBox hb = new HBox(INT.GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}
	private ScrollPane configureScrollPane(){
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true); //wont extend out of ScrollPane vertically 
		sp.setPannable(true);
		return sp;
	}
	private void displayLevelsWhenEmpty(HBox hb) {
		
		//can't add ObservableList to a HBox directly
		hb.setAlignment(Pos.CENTER);	
		
		hb.getChildren().addAll(this.makeAddSignWhenEmpty("Add New Level Here"));	
	}
	
	private void displayLevelsInParallel(HBox hb) {
	
		//can't add ObservableList to a HBox directly
		Button level1 = makeTempLevelSplashDisplayImage(STRING.LEVEL1IMAGE);  //tmp string path	
		Button level2 = makeTempLevelSplashDisplayImage(STRING.LEVEL2IMAGE);
		Button level3 = makeTempLevelSplashDisplayImage(STRING.SPRITEIMAGE);
		hb.getChildren().addAll(level1, level2, level3);	
	}
	
	//temporary methods to display level/Splash Image
	private Button makeTempLevelSplashDisplayImage(String path) {
		Button b = new Button();
		ImageView img = new ImageView(new Image(path));
		img.setFitHeight(INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		img.setFitWidth(INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
		b.setGraphic(img);
		b.setOnMouseClicked(handleDouleRightClick(b));
		return b;		
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
		            	controller.loadLevelEditScreen(selectedLevel);
		            }
		        }
		        else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
		        	makeRightClickeMenu().show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());		
		        }
		    }
		};
	}
	
	
	private ImageView makeButton(String location, EventHandler<MouseEvent> lamda){
		ImageView b = new ImageView(new Image(location));
		b.setFitHeight(80);
		b.setFitWidth(80);
		b.setOnMouseClicked(lamda);
		return b;
	}	
	/**
	 * make a right click menu for editing and removing a level
	 */
	private ContextMenu makeRightClickeMenu(){ //pass in Level
		final ContextMenu rMenu = new ContextMenu();
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(e -> controller.loadLevelEditScreen(selectedLevel));
		MenuItem remove = new MenuItem("remove");
		remove.setOnAction(e -> controller.trashLevel(myGame, selectedIndex));
		rMenu.getItems().addAll(edit, remove);
		return rMenu;
	}
	
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {		
		Menu fileMenu = makeFileMenu(o -> controller.saveGame(myGame),
				o -> controller.returnToMainMenuScreen(),
				o -> controller.returnToMainMenuScreen());
		menuBar.getMenus().addAll(fileMenu, makeLevelMenu(), makeSplashMenu(), makeGameMenu(), makeTrashMenu());	

	}		
	
	private Menu makeLevelMenu() {
		
		Menu levelMenu = new Menu("Level");
		MenuItem addLevel = new MenuItem("Add new Level");
		addLevel.setOnAction(o -> controller.loadLevelEditScreen(myGame));
		MenuItem editLevel = new MenuItem("Edit Level");
		editLevel.setOnAction(o -> controller.loadLevelEditScreen(myGame.levels().get(selectedIndex))); //references to the specific level within a game
		levelMenu.getItems().addAll(addLevel, editLevel);
		return levelMenu;
		
	}
	
	private Menu makeSplashMenu() {
		
		Menu splashMenu = new Menu("Splash Screen");
		MenuItem addSplash = new MenuItem("Add new Splash Screen");
		addSplash.setOnAction(o -> controller.loadSplashEditScreen(myGame));
		MenuItem editSplash = new MenuItem("Edit Splash Screen");
		editSplash.setOnAction(o -> controller.loadSplashEditScreen(myGame));
		splashMenu.getItems().addAll(addSplash, editSplash);
		return splashMenu;
		
	}
	
	private Menu makeGameMenu() {
		Menu gameMenu = new Menu("Game");
		MenuItem addPlay = new MenuItem("Play Game");
		addPlay.setOnAction(o -> controller.playGame(myGame));
		gameMenu.getItems().addAll(addPlay);
		return gameMenu;
	}
	
	private Menu makeTrashMenu() {
		
		ImageView trashImage = new ImageView(new Image(STRING.TRASH_ICON));
		
		super.sizeMenuImageView(trashImage, DOUBLE.MENU_BAR_HEIGHT, DOUBLE.MENU_BAR_HEIGHT);		
		Menu trashButton = new Menu("", trashImage);
		MenuItem delete = new MenuItem("Add new Level");
		delete.setOnAction(o -> controller.trashLevel(myGame, selectedIndex));
		return trashButton;
		
	}

}
