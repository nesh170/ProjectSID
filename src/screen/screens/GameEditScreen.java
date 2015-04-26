package screen.screens;

import game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import resources.ImageViewButton;
import resources.ScreenButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.controllers.GameEditScreenController;
import screen.controllers.ScreenController;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.util.Duration;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;

/**
 * The screen where users edit a game allows users to edit a level or edit a
 * sprite.
 * 
 * @author Yongjiao
 * @author Anika
 */
//TODO list: - disable rest of screen when popup shows
//- could add only once a draft levelImagerepresnetation when user returns from LevelEditScreen without modifying everything since level is automatically saved
//- could also add drag and drop functionality to rearrange ordering of list of levels.
//fix the bug with contextMenu staying in absolute positioni in screen
public class GameEditScreen extends Screen {

	// Static Variables
	private static boolean TESTING = false;		// change this to true to debug, but only push "false"
	
	
	// Instance variables
	private GameEditScreenController controller;
	
	private Game game;
	private Level selectedLevel;
	private int selectedIndex;
	// JavaFX
	private StackPane levelDisplay;
	private VBox splashDisplay;
	private Popup popup;
	private HBox levelHB;
	private StackPane splashSP;

	// Getters & Setters
	/**
	 * add a Level to a Game
	 * 
	 * @return int index of where the inserted Level is
	 */
/*	public int addLevel(Level level) {
		
		levels.add(level);
		return levels.indexOf(level);
		
	}
	*/
	/**
	 * add to current game level
	 * 
	 * @param level
	 * @return
	 */
	public void setLevel(int index, Level level) {
		game.setLevel(index, level);
	}
	
	// Constructor & Helpers
	/**
	 * Set up GameEdit screen from previously created game to re-edit game.
	 * 
	 * @param controller
	 *            , width, height, game
	 */
	public GameEditScreen(Game game, GameEditScreenController controller, double width, double height) {
	
		super(width, height);
		this.game = game;
		
		this.setStyle(STRING.COLORS.FX_GAME_EDIT_BACKGROUND);
			
		//System.out.println(this.game);
			
//		configureLevels();
		
		initialize(controller);

		
	}
/*
	private void configureLevels() {
		levels = FXCollections.observableArrayList();
		levels.addListener(new ListChangeListener<Level>() {

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends Level> listener) {
				displayLevelsInParallel(game.levels());
			}
		});
	}
*/
	/**
	 * @param controller
	 */
	private void initialize(GameEditScreenController controller) {

		this.controller = controller;
		configureButtons();
		
	}

	private void configureButtons() {

		configureLevelDisplay();
		this.setCenter(levelDisplay);

		configureSplashDisplay();
		this.setLeft(splashDisplay);
		
		createPopUp();
	}

	/**
	 * Displays a splash
	 * 
	 * @return
	 */
	private void configureSplashDisplay() {

		splashDisplay = new VBox();		
		splashSP = new StackPane();
		splashDisplay.setAlignment(Pos.CENTER);
		splashDisplay.getChildren().add(splashSP);
		splashSP.getChildren().addAll(makeText(STRING.GAME_EDIT.SPLASH_SCREEN));
		
		ImageView hide = makeHideShowArrow(	STRING.GAME_EDIT.HIDE_ARROW , e -> hideSplashRegion());
		hide.setTranslateX(240);
		hide.setTranslateY(-350);
		
		Rectangle rec = new Rectangle(INT.DEFAULT_LEVEL_DISPLAY_WIDTH + 5 ,INT.DEFAULT_LEVEL_DISPLAY_HEIGHT + 5);	 
	    rec.setFill(Color.TRANSPARENT);
		rec.setStyle("-fx-stroke-dash-array: 12 12 12 12; -fx-stroke-width: 3;-fx-stroke: gray;"); 
		splashSP.getChildren().addAll(rec, hide);  
		displayApproporiateSplashButton();
		
	}
	
	/**
	 * updates the node for splash screen display area depending
	 * on weather current game has splash screen
	 * @return
	 */
	public void displayApproporiateSplashButton(){
		Button b = new Button();
		// Testing
		if (GameEditScreen.TESTING) {
			System.out.println(game.hasSplash());
		}
		if (!game.hasSplash()) {
			
			b = makeAddSignWhenEmpty("Add New Splash Screen",
					e -> controller.loadSplashEditScreen(game, this));	
			if(splashSP.getChildren().size() == INT.INITIAL_SETUP) 
					splashSP.getChildren().add(b);
			else splashSP.getChildren().set(splashSP.getChildren().size() - 1, b);
		}
			
		else {
			b = displayMySplash();
			splashSP.getChildren().set(splashSP.getChildren().size() -1, b);
		}
	}
	
	private Button displayMySplash() {

		ImageView img = game.splashScreen().getLevelPlatformImageView();
		Button b = getLevelOrSplashButtons(img, INT.SPLASH, 0);
		
		return b;
		
	}

	private Text makeText(String s) {

		Text text = new Text(s);
		Reflection r = new Reflection();
		
		r.setFraction(0.6f);
		
		text.setEffect(r);
		text.setFont(Font.font("SERIF", FontWeight.BOLD, 30));
		text.setTranslateY(-300); 
		
		return text;
		
	}

	private Button makeAddSignWhenEmpty(String s, EventHandler<MouseEvent> lamda) {

		ImageView addsign = new ImageView(new Image(STRING.GAME_EDIT.ADD_IMG));
		
		addsign.setFitHeight(INT.GAMEEDIT_ADD_SIGN_DIM);
		addsign.setFitWidth(INT.GAMEEDIT_ADD_SIGN_DIM);
		Button b = new Button(s, addsign);
		b.setContentDisplay(ContentDisplay.TOP);
		b.setOnMouseClicked(lamda);
		b.setMinSize(INT.DEFAULT_LEVEL_DISPLAY_WIDTH,
				INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		
		return b;

	}

	/**
	 * display a note on editing and removing a level
	 */
	private Text displayNote() {

		Text t = new Text(STRING.GAME_EDIT.NOTE);
		t.setTranslateY(210);
		t.setTranslateX(250);
		return t;

	}

	private void configureLevelDisplay() {

		levelDisplay = new StackPane();
		ScrollPane levelSP = this.createScrollPane();

		/*
		 * using ImageViewButton class to make a blink of image when button is pressed down
		 * @author Anika, Yongjiao
		 */
				
		ImageView addButton = makeButton(STRING.GAME_EDIT.PLUS_IMG, 
				STRING.GAME_EDIT.PLUSDOWN_IMG,
				e -> controller.loadLevelEditScreen(game, this));
		
		StackPane.setAlignment(addButton, Pos.TOP_RIGHT);
		

		ImageView play = makeButton(STRING.GAME_EDIT.PLAY_IMG, 
				STRING.GAME_EDIT.PLAYDOWN_IMG, 
				e -> controller.playGame(game));
		
		StackPane.setAlignment(play, Pos.TOP_CENTER);

		ImageView back = makeButton(STRING.GAME_EDIT.BACK_IMG,
				STRING.GAME_EDIT.BACKDOWN_IMG, 
				e -> controller.showConfirmPopUpWithGame(game, popup));
		
		StackPane.setAlignment(back, Pos.TOP_LEFT);
		ImageView img = makeHideShowArrow("images/GameEdit_images/show.png", e -> this.showSplashRegion());
		img.setTranslateX(-700);
		img.setTranslateY(-280);
		img.setVisible(false);
		levelDisplay.getChildren().addAll(levelSP, back, addButton, play,
				displayNote(),img);
		
	}
	/**
	 * makes the arrow button for hiding and showing splash display area: the left region
	 */
	private ImageView makeHideShowArrow(String path, EventHandler<MouseEvent> event){
		
		ImageView hide = new ImageView(new Image(path));
		hide.setFitHeight(30);
		hide.setFitWidth(30);
		hide.setOnMouseClicked(event);
		return hide;
		
	}
	
	private ScrollPane createScrollPane() {

		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true); // wont extend out of ScrollPane vertically
		sp.setPannable(true);
		this.levelHB = configureHBox();
		sp.setContent(levelHB);
		displayLevels(game.levels());
		return sp;
		
	}
	
	/**
	 * dynamically display most updated list of levels in game or add sign when empty
	 * @param game.levels()
	 */
	public void displayLevels(List<Level> levels) {
		
		// TODO:  in replace of ImageView below
		if (game.hasLevel()) {
			displayLevelsAndReassignPossition();
		}

		else {
			displayLevelsWhenEmpty();
		}
		
	}


	private void displayLevelsWhenEmpty() {

		levelHB.getChildren().clear();
		levelHB.getChildren().addAll(
				this.makeAddSignWhenEmpty("Add A New Level",
						e -> controller.loadLevelEditScreen(game, this)));

	}
	/**
	 * display levels 
	 * reassign position info when there is change of position in levels
	 */
	private void displayLevelsAndReassignPossition() {
		levelHB.getChildren().clear();
		int index = 0; 
		for (Level l: game.levels()) {
			Button level = getLevelOrSplashButtons(
					l.getLevelPlatformImageView(), INT.LEVEL, index); //pass in index and set levelIndex
			levelHB.getChildren().add(level);
			index++;
		}
	}

	//TODO: change here for different level indexes
	private Button getLevelOrSplashButtons(ImageView img, int splashOrLevel, int index) {

		Button b = new Button();
		img.setFitHeight(INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		img.setFitWidth(INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
		b.setStyle(STRING.COLORS.FX_GAME_EDIT_BUTTON_RADIUS);
		b.setGraphic(img);
		b.setFocusTraversable(false);
		b.setOnMouseEntered( e -> setNodeScale(b, 1.12));
		b.setOnMouseExited(e -> setNodeScale(b,1.0));
		b.setOnMouseClicked(handleDoubleRightClick(b, splashOrLevel, this, index));
		return b;
	
	}

	/**
	 * handles mouse event: double left mouse click and right mouse click
	 * 
	 * @param node
	 * @return EventHandler<MouseEvent>
	 */
	private EventHandler<MouseEvent> handleDoubleRightClick(Node node, int splashOrLevel, 
			GameEditScreen g, int index) {
		
		return new EventHandler<MouseEvent>() { // double Click to edit a screen

			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && 
						mouseEvent.getClickCount() == 2)  {					
						
						if (splashOrLevel == INT.LEVEL) {
							configureSelection(index);
							controller.loadLevelEditScreen(selectedLevel);
						}
							
						else {
							controller.loadSplashEditScreen(game, g);
						}						
					
				}
				
				else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
					
					if (splashOrLevel == INT.LEVEL) {
						configureSelection(index);
						makeRightClickMenu(
								e -> controller
										.loadLevelEditScreen(selectedLevel),
								e -> controller.trashLevel(game,selectedIndex, g)).show(node,
										mouseEvent.getScreenX(), mouseEvent.getScreenY());
					}
						
					else {
						makeRightClickMenu(
								e -> controller.loadSplashEditScreen(game, g),
								e -> controller.trashSplash(game, g)).show(node,
										mouseEvent.getSceneX(), mouseEvent.getSceneY());
					}
						
				}
				
			}
			
		};
		
	}
	/**
	 * assign translate animation to each level button after the one removed by user 
	 * @return 
	 */
	public Transition[] assignLevelButtonsAnimation(){
		levelHB.getChildren().get(selectedIndex).setVisible(false);
		ArrayList<TranslateTransition>	list = new ArrayList();		
		for(int i = selectedIndex + 1; i < levelHB.getChildren().size(); i++){
			Node n = levelHB.getChildren().get(i);
			list.add(assignTranslateTransToNode(n));
		}		
		return list.toArray(new TranslateTransition[0]);

	}
	/**
	 * animates the entire level removal process
	 * @param onfinished
	 */
	public SequentialTransition animatesTrashLevel(EventHandler<ActionEvent> onfinished){
		Transition pt = runAnimationsInParallel(assignLevelButtonsAnimation());
		SequentialTransition st = new SequentialTransition(animatesTrashPaper(), pt);		
		st.setOnFinished(onfinished);		
		return st;		
	}
	
	private Transition animatesTrashPaper(){	
		//may need to change to Timeline
		ScaleTransition st =  new ScaleTransition(Duration.millis(2000), 
				changeToTrashPaper());		
	    st.setDuration(Duration.seconds(0.19f));
	    //st.setInterpolator(Interpolator.DISCRETE);
	    st.setFromX(0);
	    st.setFromY(0);
	  //  st.setByX(1.2);
	   // st.setByY(1.2);
	    st.setToX(1);
	    st.setToY(1);
	    st.setOnFinished(new EventHandler<ActionEvent>(){
	    	
			@Override
			public void handle(ActionEvent event) {
				levelHB.getChildren().get(selectedIndex).setVisible(false);
			}
	    });
	    return st;
	}
	/**
	 * runs multiple animations in parallel
	 * @param onfinished: action event triggered when the parallel animation is finished
	 * @return parallelTransition
	 */
	public ParallelTransition runAnimationsInParallel(Transition... transitions){
		
		ParallelTransition pt = new ParallelTransition(transitions);
		return pt;
		
	}
	
	private TranslateTransition assignTranslateTransToNode(Node n){
		 
	     TranslateTransition tt = new TranslateTransition(Duration.millis(2000), n);
	     tt.setByX(-INT.GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE -
	    		 INT.DEFAULT_LEVEL_DISPLAY_WIDTH );
	     tt.setCycleCount((int)1f);
	     tt.setDuration(Duration.seconds(0.5d));
	     return tt;
	}
	
	private void configureSelection(int index){
		selectedIndex = index;
		selectedLevel = game.levels().get(selectedIndex);
	}
	/**
	 * Event to happen at end of the entire level removal animation
	 * @return
	 */
	public EventHandler<ActionEvent> trashLevelAnimationFinishedEvent(){
		return new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				levelHB.getChildren().remove(selectedIndex);
				displayLevels(game.levels());
			}
		};
	}
	
	
	private ImageView makeButton(String locUp, String locDown, EventHandler<MouseEvent> lamda) {

		ImageView b = new ImageView(new Image(locUp));
		b.setFitHeight(80);
		b.setFitWidth(80);
		b.setOnMouseClicked(lamda);
		
		// added by anika
		b.setOnMousePressed(e -> b.setImage(new Image(locDown)));
		b.setOnMouseReleased(e -> b.setImage(new Image(locUp)));

		return b;

	}

	private ImageView changeToTrashPaper(){
		// not part of animation
		ImageView img = new ImageView(new Image("images/trash_gas.png"));
		img.setFitHeight(80);
		img.setFitWidth(80);	
		img.setFocusTraversable(false);
		levelHB.getChildren().add(selectedIndex, 
				new StackPane(levelHB.getChildren().get(selectedIndex), img));	
		return img;

	}
	
	private void createPopUp() {   
		
	     popup = new Popup();
	     popup.setAutoFix(false);
	     popup.setHideOnEscape(true);
	     popup.setX(500);
	     popup.setY(250);
	     ImageView img = makeImageView(STRING.MAIN_MENU_SCREEN.POPUP,
	    		 300, INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
	     GridPane layout = configurePopUpLayout();
	     popup.getContent().addAll(img, layout);
	     
	}
	
	private GridPane configurePopUpLayout(){
		
		  GridPane layout = new GridPane();
		  layout.setAlignment(Pos.CENTER);
		  layout.setHgap(10);
		  layout.setVgap(20);
		  Text doesSave = new Text(STRING.GAME_EDIT.POPUP_SAVE);
		  doesSave.setStyle(STRING.GAME_EDIT.FONT_POPUP);
		  HBox buttons= new HBox(150);
		  Button save = new Button("save");
		  Button back = new Button("back");
		  buttons.setAlignment(Pos.CENTER);
		  buttons.getChildren().addAll( back, save);	   
		  save.setOnMouseClicked(e -> controller.saveAndExit(game, popup));
		  back.setOnMouseClicked(e -> controller.returnToMainMenuScreen(popup));
		  layout.add(new Label(""), 1, 1, 1,4);
		  layout.add(doesSave, 1, 5);
		  layout.add(buttons, 1, 9);
		 return layout;
		 
	}
	
	private HBox configureHBox() {

		HBox hb = new HBox(INT.GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE);
		hb.setAlignment(Pos.CENTER);
		return hb;

	}
	
	/**
	 * make a right click menu for editing and removing a level
	 */
	private ContextMenu makeRightClickMenu(EventHandler<ActionEvent> toEdit, EventHandler<ActionEvent> toRemove) { // pass in Level

		final ContextMenu rMenu = new ContextMenu();
		MenuItem edit = new MenuItem("edit");
		edit.setOnAction(toEdit);
		MenuItem remove = new MenuItem("remove");
		remove.setOnAction(toRemove);
		rMenu.getItems().addAll(edit, remove);
		
		return rMenu;
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

		Menu fileMenu = makeFileMenu(o -> controller.saveGame(game), //change
				o -> controller.returnToMainMenuScreen(popup),
				o -> controller.returnToMainMenuScreen(popup));

		menuBar.getMenus().addAll(fileMenu, makeLevelMenu(), makeTools(),
				makeGameMenu(), makeTrashMenu());

	}
	//borderpane's left and right can overlap with each other!
	private void hideSplashRegion(){
		splashDisplay.managedProperty().bind(splashDisplay.visibleProperty());
		splashDisplay.setVisible(false);
	     TranslateTransition tt = new TranslateTransition(Duration.millis(2000), levelDisplay);
	     
	     tt.setByX(-500f);
	     tt.setCycleCount(1);
	     tt.setOnFinished( new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				
			}

	    	 
	     }
	    );
	     levelDisplay.getChildren().get(5).setVisible(true);
	    // tt.play();	 
	     
	}
	
	private void showSplashRegion(){
		splashDisplay.setVisible(true);
		levelDisplay.getChildren().get(5).setVisible(false);
	}
	
	private Menu makeLevelMenu() {

		Menu levelMenu = new Menu("Level");
		MenuItem addLevel = new MenuItem("Add new Level");
		addLevel.setOnAction(o -> controller.loadLevelEditScreen(game, this));
		MenuItem editLevel = new MenuItem("Edit Level");
		editLevel.setOnAction(o -> controller.loadLevelEditScreen(game
				.levels().get(selectedIndex))); // references to the specific
												// level within a game
		levelMenu.getItems().addAll(addLevel, editLevel);
		return levelMenu;

	}

	private Menu makeTools(){
		Menu tools = new Menu("Tools");
		MenuItem levelOnly = new MenuItem("hide splash");
		levelOnly.setOnAction(e -> hideSplashRegion());
		MenuItem seeAll = new MenuItem("display all");
		seeAll.setOnAction(e -> showSplashRegion());
		tools.getItems().addAll(levelOnly, seeAll);
		return tools;
	}
	
	private Menu makeGameMenu() {

		Menu gameMenu = new Menu("Game");
		MenuItem addPlay = new MenuItem("Play Game");
		addPlay.setOnAction( o -> controller.playGame(game));
		gameMenu.getItems().addAll(addPlay);
		return gameMenu;
		
	}
	
	private Menu makeTrashMenu() {

		ImageView trashImage = new ImageView(new Image(STRING.GAME_EDIT.TRASH_ICON));

		super.sizeMenuImageView(trashImage, DOUBLE.MENU_BAR_HEIGHT, DOUBLE.MENU_BAR_HEIGHT);
		
		Menu trashButton = new Menu("", trashImage);
		MenuItem delete = new MenuItem("Add new Level");
		delete.setOnAction(o -> controller.trashLevel(game, selectedIndex, this));
		
		return trashButton;

	}

}