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
	
	// JavaFX
	private StackPane friendsDisplay;
	private VBox loggedInDisplay;
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
		

		friendsDisplay.getChildren().addAll(friendsSP);

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








	
	private HBox configureHBox() {

		HBox hb = new HBox(INT.GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE);
		hb.setAlignment(Pos.CENTER);
		return hb;

	}
	

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

	}




}
