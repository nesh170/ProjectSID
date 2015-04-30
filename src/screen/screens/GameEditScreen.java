package screen.screens;

import game.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import data.DataHandler;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
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
import javafx.scene.control.Tooltip;
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
import screen.screenmodels.GameEditModel;
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
import javafx.stage.Stage;
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
//- could also add drag and drop functionality to rearrange ordering of list of levels.
//fix the contextMenu staying in absolute position in screen

public class GameEditScreen extends Screen {
	
	private GameEditScreenController controller;
	private GameEditModel gameEditModel;
	// JavaFX
	private StackPane levelDisplay;
	private VBox splashDisplay;
	private Popup popup;
	private HBox levelHB;
	private StackPane splashSP;
	private TextField musicPath;
	

	// Constructor & Helpers
	/**
	 * Set up GameEdit screen from previously created game to re-edit game.
	 * 
	 * @param controller
	 *            , width, height, game
	 */
	public GameEditScreen(Game game, GameEditScreenController controller, double width, double height) {
	
		super(width, height);
		this.gameEditModel = new GameEditModel(game);	
		this.setStyle(STRING.COLORS.FX_GAME_EDIT_BACKGROUND);			
		initialize(controller);

	}
	
	
	/***
	 * rerender the non-static region of screen.
	 */
	@Override
	public void rerender() {
		displayLevels();
	}

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
		
		ImageView hide = makeHideShowArrow(	STRING.GAME_EDIT.HIDE_ARROW, e -> hideSplashRegion());
		hide.setTranslateX(240);
		hide.setTranslateY(-350);
		hide.managedProperty().bind(hide.visibleProperty());
		Rectangle rec = new Rectangle(INT.DEFAULT_LEVEL_DISPLAY_WIDTH + 5 ,INT.DEFAULT_LEVEL_DISPLAY_HEIGHT + 5);	 
	    rec.setFill(Color.TRANSPARENT);
		rec.setStyle(STRING.COLORS.RECTANGLE_COLOR); 
		HBox musicBox = makeMusicBox();
		Text text = makeText(STRING.GAME_EDIT.SPLASH_SCREEN);
		splashSP.getChildren().addAll( rec, text, hide, musicBox);
		displayApproporiateSplashButton();
	}
	
	private HBox makeMusicBox(){
		HBox musicBox = new HBox(30);
		musicBox.setTranslateY(500);
		musicBox.setTranslateX(45);
		Button select = new Button(STRING.GAME_EDIT.SELECTMUSIC);
		musicPath = new TextField();
		musicPath.setPrefWidth(300);
		musicPath.setEditable(false);
		select.setOnMouseClicked(e -> setSoundPathTextAndSave());		
		musicBox.getChildren().addAll(select, musicPath);
		return musicBox;
	}	
	
	
	private void setSoundPathTextAndSave() {
		musicPath.getStyleClass().remove(STRING.CSS.ERROR);
		try {
			File file = DataHandler.chooseFile(new Stage());
			if(file.getName().endsWith(".mp3") || file.getName().endsWith(".m4a")) {
				musicPath.setText(file.getPath());
				gameEditModel.getGame().setSoundPath(file.getPath());
			}
			else {
				musicPath.getStyleClass().add(STRING.CSS.ERROR);
			}
		} catch (NullPointerException e) {
			//IDC, do nothing
		}
		
	}
	/**
	 * updates the node for splash screen display area depending
	 * on weather current game has splash screen
	 * @return
	 */
	public void displayApproporiateSplashButton(){
		Button b = new Button();
		b.managedProperty().bind(b.visibleProperty());

		if (!gameEditModel.hasSplash()) {
			
			b = makeAddSignWhenEmpty(STRING.GAME_EDIT.ADD_NEW_SPLASH,
					e -> controller.loadSplashEditScreen(gameEditModel.getGame(), this));	
			if(splashSP.getChildren().size() == INT.INITIAL_SETUP) 
					splashSP.getChildren().add(b);
			else
			splashSP.getChildren().set(splashSP.getChildren().size() - 1, b);}
			
		else {
			b = displayMySplash();
			splashSP.getChildren().set(splashSP.getChildren().size() -1, b);
		}
	}
	
	private Button displayMySplash() {

		ImageView img = gameEditModel.getLevelPlatformImageView();
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
		text.managedProperty().bind(text.visibleProperty());
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
				e -> controller.loadLevelEditScreen(gameEditModel.getGame(), this));
		
		StackPane.setAlignment(addButton, Pos.TOP_RIGHT);
		

		ImageView play = makeButton(STRING.GAME_EDIT.PLAY_IMG, 
				STRING.GAME_EDIT.PLAYDOWN_IMG, 
				e -> controller.playGame(gameEditModel.getGame()));
		
		StackPane.setAlignment(play, Pos.TOP_CENTER);

		ImageView back = makeButton(STRING.GAME_EDIT.BACK_IMG,
				STRING.GAME_EDIT.BACKDOWN_IMG, 
				e -> controller.showConfirmPopUpWithGame(gameEditModel.getGame(), popup));
		
		StackPane.setAlignment(back, Pos.TOP_LEFT);
		
		
		ImageView img = makeHideShowArrow(STRING.GAME_EDIT.SHOW_ARROW, e -> this.showSplashRegion());
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
		displayLevels();
		return sp;
		
	}
	
	/**
	 * dynamically display most updated list of levels in game or add sign when empty
	 * @param game.levels()
	 */
	public void displayLevels() {
		
		// TODO:  in replace of ImageView below
		if (gameEditModel.hasLevel()) {
			displayLevelsAndReassignPossition();
		}

		else {
			displayLevelsWhenEmpty();
		}		
	}


	private void displayLevelsWhenEmpty() {

		levelHB.getChildren().clear();
		levelHB.getChildren().addAll(
				this.makeAddSignWhenEmpty(STRING.GAME_EDIT.ADD_NEW_LEVEL,
						e -> controller.loadLevelEditScreen(gameEditModel.getGame(), this)));

	}
	/**
	 * display levels 
	 * reassign position info when there is change of position in levels
	 */
	private void displayLevelsAndReassignPossition() {
		levelHB.getChildren().clear();
		int[] index = {0}; 
		Consumer<Level> addLevelButtons = e -> {
			addLevelButtons(e, index[0]); 
			index[0]++;
			};
		gameEditModel.forEachLevel(addLevelButtons);
	}
	
	private void addLevelButtons(Level l, int index) {
		Button level = getLevelOrSplashButtons(
				l.getLevelPlatformImageView(), INT.LEVEL, index); //pass in index and set levelIndex
		levelHB.getChildren().add(level);
		index++;
	}
	

	//TODO: change here for different level indexes
	private Button getLevelOrSplashButtons(ImageView img, int splashOrLevel, int index) {

		Button b = new Button();
		img.setFitHeight(INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		img.setFitWidth(INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
		b.setStyle(STRING.COLORS.FX_GAME_EDIT_BUTTON_RADIUS);
		b.setGraphic(img);
		b.setFocusTraversable(false);
		b.setOnMouseEntered( e -> setNodeScale(b, DOUBLE.EXPANSION_FACTOR));
		b.setOnMouseExited(e -> setNodeScale(b, DOUBLE.ORIGINAL_SIZE));
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
							gameEditModel.configureSelection(index);
							controller.loadLevelEditScreen(gameEditModel.getGame(), gameEditModel.getSelectedLevel());
						}
							
						else {
							controller.loadSplashEditScreen(gameEditModel.getGame(), g);
						}						
					
				}
				
				else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
					
					if (splashOrLevel == INT.LEVEL) {
						gameEditModel.configureSelection(index);
						makeRightClickMenu(
								e -> controller
										.loadLevelEditScreen(gameEditModel.getGame(), gameEditModel.getSelectedLevel()),
								e -> controller.trashLevel(gameEditModel.getGame(), gameEditModel.getSelectedIndex(), g)).show(node,
										mouseEvent.getScreenX(), mouseEvent.getScreenY());
					}
						
					else {
						makeRightClickMenu(
								e -> controller.loadSplashEditScreen(gameEditModel.getGame(), g),
								e -> controller.trashSplash(gameEditModel.getGame(), g)).show(node,
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
		levelHB.getChildren().get(gameEditModel.getSelectedIndex()).setVisible(false);		
		ArrayList<TranslateTransition>	list = new ArrayList<>();		
		for(int i = gameEditModel.getSelectedIndex() + 1; i < levelHB.getChildren().size(); i++){
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
		SequentialTransition st = new SequentialTransition(
				animatesTrashPaper(INT.LEVEL,
						e ->levelHB.getChildren().get(gameEditModel.getSelectedIndex())
						.setVisible(false)),pt);		
		st.setOnFinished(onfinished);		
		return st;		
	}

	public Transition animatesTrashPaper(int splashOrLevel, EventHandler<ActionEvent> onfinished){	
		ScaleTransition st =  new ScaleTransition(Duration.millis(2000), 
				changeToTrashPaper(splashOrLevel));		
	    st.setDuration(Duration.seconds(0.19f));
	    st.setFromX(DOUBLE.START_SIZE);
	    st.setFromY(DOUBLE.START_SIZE);
	    st.setToX(DOUBLE.ORIGINAL_SIZE);
	    st.setToY(DOUBLE.ORIGINAL_SIZE);
	    st.setOnFinished(onfinished);
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
	
	public EventHandler<ActionEvent> trashLevelAnimationFinishedEvent(){
		return new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				levelHB.getChildren().remove(gameEditModel.getSelectedIndex());
				displayLevels();
			}
		};
	}
	
	public EventHandler<ActionEvent> trashSplashAnimationFinishedEvent(){
		return new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event) {
				displayApproporiateSplashButton();
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
	/**
	 * animates a level/splash image becomes to trash paper
	 * @param splashOrLevel
	 * @return
	 */
	private ImageView changeToTrashPaper(int splashOrLevel){

		ImageView img = makeTrashGas();
		if(splashOrLevel == INT.LEVEL){
		int selectedIndex = gameEditModel.getSelectedIndex();
			levelHB.getChildren().add(selectedIndex, 
				new StackPane(levelHB.getChildren().get(selectedIndex), img));	
		}
		else{
			int lastChildIndex = splashSP.getChildren().size()-1;
			Node toBeRemoved = splashSP.getChildren().get(lastChildIndex);
			toBeRemoved.setVisible(false);
			splashSP.getChildren().add(lastChildIndex, new StackPane(toBeRemoved,img));
		}
		return img;

	}
	
	private ImageView makeTrashGas(){
		ImageView img = new ImageView(new Image(STRING.GAME_EDIT.TRASH_GAS));
		img.setFitHeight(80);
		img.setFitWidth(80);	
		img.setFocusTraversable(false);
		return img;
	}
	
	private void hideSplashRegion(){
		splashDisplay.setVisible(false);
		Timeline slideIn = new Timeline();				
		KeyFrame k = new KeyFrame(Duration.millis(5), e -> updateSlidInFrame(slideIn));

		for(int i=1; i < splashSP.getChildren().size(); i++){
			Node splashSPContent = splashSP.getChildren().get(i);
			splashSPContent.managedProperty().bind(splashSPContent.visibleProperty());
			splashSPContent.setVisible(false);
		}
		
        slideIn.setCycleCount(Animation.INDEFINITE);
		slideIn.getKeyFrames().add(k);
		slideIn.play();
	}
	
	private void updateSlidInFrame(Timeline t){
	
		Rectangle rec = (Rectangle) splashSP.getChildren().get(INT.TRANSLATE_INDEX);		
		double width = rec.getWidth();
		if(width == DOUBLE.START_SIZE){
			t.stop();
			int levelButtonIndex = levelDisplay.getChildren().size() - 1;
			levelDisplay.getChildren().get(levelButtonIndex).setVisible(true); 
		}
		rec.setWidth(width-5);		

	}
	
	
	private void showSplashRegion(){
		//restore splashDisplay
		splashSP.getChildren().get(INT.TRANSLATE_INDEX).setVisible(false);
		int levelButtonIndex = levelDisplay.getChildren().size() - 1;
		levelDisplay.getChildren().get(levelButtonIndex).setVisible(false);
		Timeline slideOut = new Timeline();
		KeyFrame k = new KeyFrame(Duration.millis(5), e -> updateSlideOutFrame(slideOut));
		slideOut.getKeyFrames().add(k);
		slideOut.setCycleCount(Animation.INDEFINITE);
		slideOut.play();		
	}
	
	private void updateSlideOutFrame(Timeline t){
		
		Rectangle rec = (Rectangle) splashSP.getChildren().get(0);		
		double width = rec.getWidth();
		if(width == INT.DEFAULT_LEVEL_DISPLAY_WIDTH){
			t.stop();
			for(int i=1; i < splashSP.getChildren().size(); i++){
				splashSP.getChildren().get(i).setVisible(true);
			}
			splashDisplay.setVisible(true);
		}
		int offset = 5;
		rec.setWidth(width+ offset);			
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
		  Button save = new Button(STRING.COLLISION_EDIT.SAVE_BUTTON_TEXT);
		  Button back = new Button(STRING.SPLASH_EDIT_SCREEN.BACK);
		  buttons.setAlignment(Pos.CENTER);
		  buttons.getChildren().addAll( back, save);	   
		  save.setOnMouseClicked(e -> controller.saveAndExit(gameEditModel.getGame(), popup));
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
		MenuItem edit = new MenuItem(STRING.GAME_EDIT.EDIT);
		edit.setOnAction(toEdit);
		MenuItem remove = new MenuItem(STRING.GAME_EDIT.REMOVE);
		remove.setOnAction(toRemove);
		rMenu.getItems().addAll(edit, remove);
		
		return rMenu;
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

		Menu fileMenu = makeFileMenu(o -> controller.saveGame(gameEditModel.getGame()), //change
				o -> controller.returnToMainMenuScreen(popup),
				o -> controller.saveAndExit(gameEditModel.getGame(), popup));

		menuBar.getMenus().addAll(fileMenu, makeLevelMenu(), makeTools(),
				makeGameMenu(), makeTrashMenu());

	}

	private Menu makeLevelMenu() {
		
		Menu levelMenu = new Menu(STRING.GAME_EDIT.LEVEL);
		MenuItem addLevel = new MenuItem(STRING.GAME_EDIT.ADD_LEVEL);
		addLevel.setOnAction(o -> controller.loadLevelEditScreen(gameEditModel.getGame(), this));
		MenuItem editLevel = new MenuItem(STRING.GAME_EDIT.EDIT_LEVEL);
		editLevel.setOnAction(o -> controller.loadLevelEditScreen(gameEditModel.getGame(), gameEditModel.getGame()
				.levels().get(gameEditModel.getSelectedIndex()))); // references to the specific
												// level within a game
		levelMenu.getItems().addAll(addLevel, editLevel);
		return levelMenu;

	}

	private Menu makeTools(){
		Menu tools = new Menu(STRING.GAME_EDIT.TOOLS);
		MenuItem levelOnly = new MenuItem(STRING.GAME_EDIT.HIDE_SPLASH);
		levelOnly.setOnAction(e -> hideSplashRegion());
		MenuItem seeAll = new MenuItem(STRING.GAME_EDIT.DISPLAY_ALL);
		seeAll.setOnAction(e -> showSplashRegion());
		tools.getItems().addAll(levelOnly, seeAll);
		return tools;
	}
	
	private Menu makeGameMenu() {

		Menu gameMenu = new Menu(STRING.GAME_EDIT.GAME);
		MenuItem addPlay = new MenuItem(STRING.GAME_EDIT.PLAY);
		addPlay.setOnAction( o -> controller.playGame(gameEditModel.getGame()));
		gameMenu.getItems().addAll(addPlay);
		return gameMenu;
		
	}
	
	private Menu makeTrashMenu() {

		ImageView trashImage = new ImageView(new Image(STRING.GAME_EDIT.TRASH_ICON));

		super.sizeMenuImageView(trashImage, DOUBLE.MENU_BAR_HEIGHT, DOUBLE.MENU_BAR_HEIGHT);
		
		Menu trashButton = new Menu("", trashImage);
		MenuItem delete = new MenuItem(STRING.GAME_EDIT.ADD_LEVEL);
		delete.setOnAction(o -> controller.trashLevel(gameEditModel.getGame(), gameEditModel.getSelectedIndex(), this));
		
		return trashButton;

	}

}
