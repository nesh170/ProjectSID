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

		configureLoggedInDisplay();
		this.setLeft(loggedInDisplay);
		
		createPopUp();
	}

	/**
	 * Displays a splash
	 * 
	 * @return
	 */
	private void configureLoggedInDisplay() {

		loggedInDisplay = new VBox();		
		loggedSP = new StackPane();
		loggedInDisplay.setAlignment(Pos.CENTER);
		loggedInDisplay.getChildren().add(loggedSP);

		loggedSP.getChildren().addAll(makeText(loggedInUser.getName()));
		
	    ImageView recImage = new ImageView(loggedInUser.getImagePath());
		loggedSP.getChildren().add(recImage);
		loggedInDisplay.setOnMouseClicked(e -> controller.loadUserScreen(loggedInUser, loggedInUser));
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


	private void configureLevelDisplay() {

		friendsDisplay = new StackPane();
		ScrollPane friendsSP = this.createScrollPane();
				
		ImageView addButton = makeButton(STRING.GAME_EDIT.PLUS_IMG, 
				STRING.GAME_EDIT.PLUSDOWN_IMG,
				e -> controller.nop());
		
		StackPane.setAlignment(addButton, Pos.TOP_RIGHT);
		

		friendsDisplay.getChildren().addAll(friendsSP, addButton);

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




	//TODO: change here for different level indexes
	private Button getLevelOrSplashButtons(ImageView img, int splashOrLevel, int index) {

		Button b = new Button();
		img.setFitHeight(INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		img.setFitWidth(INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
		b.setGraphic(img);
		return b; 
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
	

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

	}




}
