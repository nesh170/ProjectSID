package screen.mainMenu;

import java.io.File;
import java.util.ArrayList;

import media.MediaManager;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import resources.constants.STRING;
import screen.Screen;

/**
 * The scene that contains pops up when the Authoring Environment 
 * application is opened.
 * Allows user to select whether they want to create a new game
 * or edit an existing one.
 * 
 * @author Michael
 * @author Ruslan
 * @author Leo
 *
 */

public class MainMenuScreen extends Screen {

	// Static variables
	
	
	// Instance variables
	MainMenuScreenController parent;
	
	
	
	// Getters & Setters
	
	
	// Constructor & Helpers
	public MainMenuScreen(MainMenuScreenController parent, double width, double height) {
		
		super(width, height);
		
		configureParent(parent);
		configureButtons();
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		MainMenuScreen.MainMenuMenuBarFactory mainMenuMenuBarFactory = new MainMenuScreen.MainMenuMenuBarFactory(menuBar);
		mainMenuMenuBarFactory.fill();
		
	}
		
	private String getGameFileURI() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Game File");
		return fileChooser.getInitialFileName();
		
	}
	
	private void configureParent(MainMenuScreenController parent) {
		this.parent = parent;
	}
	
	/**
	 * needs to be changed to a MenuItem but otherwise on point!
	 * please see methods above:
	 * 	0. addMenuItemsToMenuBar(MenuBar menuBar)
	 * 	1. instantiateAnotherMenu(MenuBar menuBar)
	 *  2. instantiateMusicMenu()
	 */
	private void configureButtons() {
		
		makeNewGameButton();
		loadGameButton();
		
	}
	
	/**
	 * needs to be changed to a MenuItem but otherwise on point!
	 * please see methods above:
	 * 	0. addMenuItemsToMenuBar(MenuBar menuBar)
	 * 	1. instantiateAnotherMenu(MenuBar menuBar)
	 *  2. instantiateMusicMenu()
	 */
	private void makeNewGameButton() {
		
		Control newGameButton = new Button();
		newGameButton.setOnMouseClicked(e -> parent.createNewGame());
		//TODO placing in the pane
		//TODO style
		this.getChildren().add(newGameButton);
		
	}
	
	/**
	 * needs to be changed to a MenuItem but otherwise on point!
	 * please see methods above:
	 * 	0. addMenuItemsToMenuBar(MenuBar menuBar)
	 * 	1. instantiateAnotherMenu(MenuBar menuBar)
	 *  2. instantiateMusicMenu()
	 */
	private void loadGameButton() {
		
		Control loadGameButton = new Button();
		ChoiceBox<String> loadGameChoice = new ChoiceBox<String>();
		//TODO placing in the pane
		//TODO style
		loadGameButton.setOnMouseClicked(e -> parent.loadGame(loadGameChoice.getSelectionModel().getSelectedItem()));
		
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
			
			ArrayList<Menu> menusToAdd = new ArrayList<>();
			
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

			Menu fileMenu = new Menu("File");

			MenuItem newFile = new MenuItem("New");
			MenuItem openFile = new MenuItem("Open");
			MenuItem closeFile = new MenuItem("Close");

			// These methods use "parent". The beauty of nested classes is that they actually access MainMenuScreen's parent, not the factory's
			newFile.setOnAction(e -> parent.createNewGame());
			openFile.setOnAction(e -> parent.loadGame(getGameFileURI()));
			closeFile.setOnAction(e -> parent.closeApplication());
			
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
