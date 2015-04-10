package screen.mainMenu;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import media.MediaManager;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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
	
	
	// Getters & Setters
	
	
	// Constructor & Helpers
	public MainMenuScreen(MainMenuScreenController controller, double width, double height) {
		
		super(width, height);
		
		this.controller = controller;

		configureButtons(width, height);
		this.setStyle(STRING.FX_GAME_EDIT_BACKGROUND);
		
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
		menu.getChildren().addAll(makeBlueDevil(), makeMenuButtons(), makeText("Welcome Blue Devils"));
		this.viewableArea().setCenter(menu);
	}
	
	private ImageView makeBlueDevil(){
		ImageView img = new ImageView(new Image("images/Blue_Devil.png"));
		img.setFitHeight(300);
		img.setFitWidth(540);
		return img;
	}
	
	private VBox makeMenuButtons(){
		Button newGame = makeButton("New Game");
		Button loadGame = makeButton("Load Game");
		Button exit = makeButton("Exit Application ");
		newGame.setOnMouseClicked(e -> controller.createNewGame());
		loadGame.setOnMouseClicked(e -> controller.loadGame());
		exit.setOnMouseClicked(e -> System.exit(0));
		VBox vbox = new VBox(INT.DEFAULT_BUTTON_SPREAD);
		vbox.getChildren().addAll(newGame, loadGame, exit);
		vbox.setAlignment(Pos.CENTER);
		return vbox;
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
		text.setTranslateY(-250);  //?? uncertain of how offset works but this works for now
		return text;
		
	}
	private HBox DisplayMembersEmojis(){
		HBox memebers = new HBox(10);
		ImageView a = new ImageView(new Image("images/member_emoji/Ruslan.png"));
		
		return memebers;
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
			newFile.setOnAction(e -> controller.createNewGame());
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
