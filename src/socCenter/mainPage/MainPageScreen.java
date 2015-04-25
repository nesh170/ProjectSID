package socCenter.mainPage;

import game.Game;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import socCenter.User;


public class MainPageScreen extends Screen {


	// Static Variables
	private static boolean TESTING = false;		// change this to true to debug, but only push "false"
	
	// Instance variables
	private MainPageScreenController controller;
	private User loggedInUser;
	
	private int selectedIndex;
	// JavaFX
	private StackPane friendsDisplay;
	private VBox loggedInDisplay;
	private Popup popup;
	private HBox levelHB;
	private StackPane loggedSP;

	// Getters & Setters

	
	// Constructor & Helpers
	/**
	 * Set up Main Page screen given user that's logged in
	 * 
	 * @param controller
	 *            , width, height, user
	 */
	public MainPageScreen(MainPageScreenController controller, double width, double height, User user) {
	
		super(width, height);
		this.loggedInUser = user;
		this.setStyle(STRING.COLORS.FX_GAME_EDIT_BACKGROUND);
		
		initialize(controller);

		
	}

	/**
	 * @param controller
	 */
	private void initialize(MainPageScreenController controller) {

		this.controller = controller;
		configureButtons();
		
	}

	private void configureButtons() {

		configureLevelDisplay();
		this.setCenter(friendsDisplay);

		configureSplashDisplay();
		this.setLeft(loggedInDisplay);
		
		createPopUp();
	}

	/**
	 * Displays a splash
	 * 
	 * @return
	 */
	private void configureSplashDisplay() {

		loggedInDisplay = new VBox();		
		loggedSP = new StackPane();
		loggedInDisplay.setAlignment(Pos.CENTER);
		loggedInDisplay.getChildren().add(loggedSP);

		loggedSP.getChildren().addAll(makeText(loggedInUser.getName()));
		
		Rectangle rec = new Rectangle(INT.DEFAULT_LEVEL_DISPLAY_WIDTH + 5 ,INT.DEFAULT_LEVEL_DISPLAY_HEIGHT + 5);	 
	    rec.setFill(Color.TRANSPARENT);
		rec.setStyle("-fx-stroke-dash-array: 12 12 12 12; -fx-stroke-width: 3;-fx-stroke: gray;"); 
		loggedSP.getChildren().addAll(rec);  
		displayApproporiateSplashButton();	
	}
	
	/**
	 * updates the node for splash screen display area depending
	 * on weather current game has splash screen
	 * @return
	 */
	public void displayApproporiateSplashButton(){
		Button b = new Button();
		/*
		if (!game.hasSplash()) {
			
			b = makeAddSignWhenEmpty("Add New Splash Screen",
					e -> controller.loadSplashEditScreen(game, this));	
			splashSP.getChildren().add(b);
		}
			
		else {
			b = displayMySplash();
			splashSP.getChildren().set(splashSP.getChildren().size() -1, b);
		}*/
	}
	
	private Button displayMySplash() {
		
		Button b = new Button();
		
		return b;
		
	}

	private Text makeText(String s) {
		
		Text text = new Text(s);
		Reflection r = new Reflection();
		
		r.setFraction(0.6f);
		
		text.setEffect(r);
		text.setFont(Font.font("SERIF", FontWeight.BOLD, 30));
		text.setTranslateY(-300); // ?? uncertain of how offset works but this
									// works for now
		
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

		friendsDisplay = new StackPane();
		ScrollPane levelSP = this.createScrollPane();

		/*
		 * using ImageViewButton class to make a blink of image when button is pressed down
		 * @author Anika, Yongjiao
		 */
				
		ImageView addButton = makeButton(STRING.GAME_EDIT.PLUS_IMG, 
				STRING.GAME_EDIT.PLUSDOWN_IMG,
				e -> controller.nop());
		
		StackPane.setAlignment(addButton, Pos.TOP_RIGHT);
		

		/*ImageView play = makeButton(STRING.GAME_EDIT.PLAY_IMG, 
				STRING.GAME_EDIT.PLAYDOWN_IMG, 
				e -> controller.nop());
		
		StackPane.setAlignment(play, Pos.TOP_CENTER);*/

		/*ImageView back = makeButton(STRING.GAME_EDIT.BACK_IMG,
				STRING.GAME_EDIT.BACKDOWN_IMG, 
				e -> controller.nop());
		
		StackPane.setAlignment(back, Pos.TOP_LEFT);*/

		friendsDisplay.getChildren().addAll(levelSP, addButton,
				displayNote());

	}

	private ScrollPane createScrollPane() {

		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true); // wont extend out of ScrollPane vertically
		sp.setPannable(true);
		this.levelHB = configureHBox();
		sp.setContent(levelHB);
		//displayLevels(game.levels());
		return sp;
		
	}
	
	/**
	 * dynamically display most updated list of levels in game or add sign when empty
	 * @param game.levels()
	 */
	public void displayLevels() {
		/*
		// TODO:  in replace of ImageView below
		if (game.hasLevel()) {
			displayLevelsAndReassignPossition();
		}

		else {
			displayLevelsWhenEmpty();
		}*/
		
	}


	private void displayLevelsWhenEmpty() {

		//levelHB.setAlignment(Pos.CENTER);
		levelHB.getChildren().clear();
		levelHB.getChildren().addAll(
				this.makeAddSignWhenEmpty("Add A New Level",
						e -> controller.nop()));

	}


	//TODO: change here for different level indexes
	private Button getLevelOrSplashButtons(ImageView img, int splashOrLevel, int index) {

		Button b = new Button();
		img.setFitHeight(INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		img.setFitWidth(INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
		b.setGraphic(img);
		return b; 
	}

	/**
	 * handles mouse event: double left mouse click and right mouse click
	 * 
	 * @param node
	 * @return EventHandler<MouseEvent>
	 */
	//private EventHandler<MouseEvent> handleDoubleRightClick(Node node, int splashOrLevel, 
			//*GameEditScreen g,*/ int index) {
		/*
		return new EventHandler<MouseEvent>() { // double Click to edit a screen

			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					
					if (mouseEvent.getClickCount() == 2) {
						
						if (splashOrLevel == INT.LEVEL) {
							configureSelection(index);
							controller.loadLevelEditScreen(selectedLevel);
						}
							
						else {
							controller.loadSplashEditScreen(game, g);
						}
							
					}
					
				}
				
				else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
					
					if (splashOrLevel == INT.LEVEL) {
						configureSelection(index);
						makeRightClickMenu(
								e -> controller
										.loadLevelEditScreen(selectedLevel),
								e -> controller.trashLevel(game,
										selectedIndex, g)).show(node,
								mouseEvent.getScreenX(),
								mouseEvent.getScreenY());
					}
						
					else {
						makeRightClickMenu(
								e -> controller.loadSplashEditScreen(game, g),
								e -> controller.trashSplash(game, g)).show(node,
								mouseEvent.getSceneX(), mouseEvent.getSceneY());
					}
						
				}
				
			}
			
		};*/
		
	//}	
	
	private void configureSelection(int index){
		selectedIndex = index;
		/*selectedLevel = game.levels().get(selectedIndex);*/
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
		  save.setOnMouseClicked(e -> controller.nop());
		  back.setOnMouseClicked(e -> controller.nop());
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
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(toEdit);
		MenuItem remove = new MenuItem("remove");
		remove.setOnAction(toRemove);
		rMenu.getItems().addAll(edit, remove);
		
		return rMenu;
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		/*
		Menu fileMenu = makeFileMenu(o -> controller.saveGame(game), //change
				o -> controller.returnToMainMenuScreen(popup),
				o -> controller.returnToMainMenuScreen(popup));

		menuBar.getMenus().addAll(fileMenu, makeLevelMenu(),
				makeGameMenu(), makeTrashMenu());
		*/
	}

	private Menu makeUserMenu() {
		
		Menu levelMenu = new Menu("Level");
		MenuItem addLevel = new MenuItem("Add new Level");
		addLevel.setOnAction(o -> controller.nop());
		MenuItem editLevel = new MenuItem("Edit Level");
		editLevel.setOnAction(o -> controller.nop()); // references to the specific
												// level within a game
		levelMenu.getItems().addAll(addLevel, editLevel);
		return levelMenu;

	}
	
	private Menu makeGameMenu() {

		Menu gameMenu = new Menu("Game");
		MenuItem addPlay = new MenuItem("Play Game");
		addPlay.setOnAction( o -> controller.nop());
		gameMenu.getItems().addAll(addPlay);
		return gameMenu;
		
	}

	private Menu makeTrashMenu() {
		
		ImageView trashImage = new ImageView(new Image(STRING.GAME_EDIT.TRASH_ICON));

		super.sizeMenuImageView(trashImage, DOUBLE.MENU_BAR_HEIGHT, DOUBLE.MENU_BAR_HEIGHT);
		
		Menu trashButton = new Menu("", trashImage);
		MenuItem delete = new MenuItem("Add new Level");
		delete.setOnAction(o -> controller.nop());
		
		return trashButton; 

	}


}
