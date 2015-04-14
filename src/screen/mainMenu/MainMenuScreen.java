package screen.mainMenu;

import game.Game;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import media.MediaManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.ScreenController;

/**
 * The scene that contains pops up when the Authoring Environment 
 * application is opened.
 * Allows user to select whether they want to create a new game
 * or edit an existing one.
 * 
 * @author Michael
 * @author Ruslan
 * @author Leo
 * @author Kyle
 * @author Yongjiao
 */

public class MainMenuScreen extends Screen {

	// Static variables
	
	
	// Instance variables
	MainMenuScreenController controller;
	Popup myPopUp;
	
	// Getters & Setters
	
	
	// Constructor & Helpers
	public MainMenuScreen(MainMenuScreenController controller, double width, double height) {
		
		super(width, height);
		
		this.controller = controller;
		configureButtons(width, height);
		this.setStyle(STRING.COLORS.FX_GAME_EDIT_BACKGROUND);
		this.getStyleClass().add("pane");
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		MainMenuScreen.MainMenuMenuBarFactory mainMenuMenuBarFactory = new MainMenuScreen.MainMenuMenuBarFactory(menuBar);
		mainMenuMenuBarFactory.fill();
		
	}
	
	/**
	 * needs to be changed to a MenuItem but otherwise on point!
	 * please see methods above:
	 * 	0. addMenuItemsToMenuBar(MenuBar menuBar)
	 * 	1. instantiateAnotherMenu(MenuBar menuBar)
	 *  2. instantiateMusicMenu()
	 */
	private void configureButtons(double width, double height) {
		
		StackPane menu = new StackPane();	
		menu.getChildren().addAll(makeImageView("images/Blue_Devil.png", 300, 540), makeMenuButtons(), makeText("Welcome Blue Devils"));
		this.viewableArea().setCenter(menu);
		
	}
	
	private ImageView makeImageView(String s, int height, int width) {
		
		ImageView img = new ImageView(s);
		img.setFitHeight(height);
		img.setFitWidth(width);
		return img;
		
	}
	
	private VBox makeMenuButtons() {
		
		Button newGame = makeButton("New Game");
		Button loadGame = makeButton("Load Game");
		Button exit = makeButton("Exit Application ");
		configurePopUp();
		newGame.setOnMouseClicked(e -> controller.createNewGame(myPopUp));
		loadGame.setOnMouseClicked(e -> controller.loadGame());
		exit.setOnMouseClicked(e -> System.exit(0));
		VBox vbox = new VBox(INT.DEFAULT_BUTTON_SPREAD);
		vbox.getChildren().addAll(newGame, loadGame, exit);
		vbox.setAlignment(Pos.CENTER);
		return vbox;
		
	}
	
	private void configurePopUp() {
		
		//popup menu for game name		
		 makeMyPopUp();
		 GridPane grid = configureGridPane();
	     ImageView img = makeImageView("images/GameEdit_Images/popup.png", 350, INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
	     myPopUp.getContent().addAll(img, grid);
	     
	}
	private void makeMyPopUp() {   
		
	     myPopUp = new Popup();
	     myPopUp.setAutoFix(false);
	     myPopUp.setHideOnEscape(true);
	     myPopUp.setX(500);
	     myPopUp.setY(250);
	     
	}

	private GridPane configureGridPane() {
		
		 GridPane grid = makeGridPane();
	     TextField gameName = new TextField("Please name your game here");
	     grid.add(gameName, 1, 1);
	     TextField des = new TextField("Please add your game description here");
	     des.setPrefHeight(100);
	     grid.add(des, 1, 2);
	     HBox popUpHBox = new HBox(100);
	     grid.add(popUpHBox, 1, 8);
	     Button ok = new Button("confirm");
	     Button cancel = new Button("cancel");
	     popUpHBox.getChildren().addAll(cancel, ok);
	     ok.setOnAction(e -> controller.confirmToCreateGame(myPopUp, gameName, des));
	     cancel.setOnAction(e -> myPopUp.hide());
	     return grid;
	     
	}
	
	private GridPane makeGridPane() {
		
		 GridPane grid = new GridPane();
	     grid.setAlignment(Pos.CENTER);
	     grid.setHgap(15);
	     grid.setVgap(15);	
	     Label name = new Label("Game Name:");
	     grid.add(name, 0, 1);
	     Label description = new Label("Game Description:");
	     grid.add(description, 0, 2);
	     return grid;
	     
	}
	
	private Text makeText(String s) {
		
		Text text = new Text(s);
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		text.setEffect(ds);
		text.setCache(true);
		text.setX(10.0f);
		text.setY(270.0f);
		text.setFill(Color.BLACK);
		text.setFont(Font.font("SERIF", FontWeight.BOLD, 48));
		text.setTranslateY(-250);  
		return text;
		
	}

	/**
	 * needs to be changed to a MenuItem but otherwise on point!
	 * please see methods above:
	 * 	0. addMenuItemsToMenuBar(MenuBar menuBar)
	 * 	1. instantiateAnotherMenu(MenuBar menuBar)
	 *  2. instantiateMusicMenu()
	 */
	private Button makeButton(String s) {	
		
		Button b = new Button(s);
		b.setStyle("-fx-background-color: lightgray;");
		b.setMinSize(INT.DEFAULT_BUTTON_WIDTH, INT.DEFAULT_BUTTON_HEIGHT);
		return b;
		
	}
		
	// All other instance methods
	
	/**
	 * 
	 * @author Ruslan
	 *
	 * A nested class within MainMenuScreen that populates the menuBar for the mainMenu
	 *
	 */
	class MainMenuMenuBarFactory {
		
		private MenuBar menuBar;
		
		private MainMenuMenuBarFactory(MenuBar menuBar) {
			this.menuBar = menuBar;
		}
		
		private void fill() {
			
			List<Menu> menusToAdd = new ArrayList<>();
			
			menusToAdd.add(instantiateFileMenu());
			menusToAdd.add(instantiateMusicMenu());

			// TODO: add more menu instances above here
			//TODO: set background and possibly other preferences
			
			menuBar.getMenus().addAll(menusToAdd);
			
		}

		private Menu instantiateMusicMenu() {

			Menu musicMenu = new Menu(STRING.MUSIC_OPTIONS);

			MenuItem playButton = new MenuItem(STRING.PLAY);
			MenuItem pauseButton = new MenuItem(STRING.PAUSE);
			MenuItem stopButton = new MenuItem(STRING.STOP);

			playButton.setOnAction(e -> handlePlayPressed());
			pauseButton.setOnAction(e -> handlePausePressed());
			stopButton.setOnAction(e -> handleStopPressed());

			musicMenu.getItems().addAll(playButton, pauseButton, stopButton);

			return musicMenu;

		}

		private Menu instantiateFileMenu() {

			Menu fileMenu = new Menu(STRING.FILE);

			MenuItem newFile = new MenuItem(STRING.NEW);
			MenuItem openFile = new MenuItem(STRING.OPEN);
			MenuItem closeFile = new MenuItem(STRING.CLOSE);

			// These methods use "parent". The beauty of nested classes is that they actually access MainMenuScreen's parent, not the factory's
			newFile.setOnAction(e -> controller.createNewGame(myPopUp));
			openFile.setOnAction(e -> controller.loadGame());
			closeFile.setOnAction(e -> controller.closeApplication());
			
			fileMenu.getItems().addAll(newFile, openFile, closeFile);
			
			return fileMenu;
			
		}
		
		private void handlePlayPressed() {
			
			MediaManager.sharedInstance().loadNewMedia("RollingInTheDeep.mp3");
			MediaManager.sharedInstance().play();
			
		}
		
		private void handlePausePressed() {
			MediaManager.sharedInstance().pause();
		}

		private void handleStopPressed() {
			MediaManager.sharedInstance().stop();
		}
		
	}

}
