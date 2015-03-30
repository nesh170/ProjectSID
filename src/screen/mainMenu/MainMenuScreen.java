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
		
		ArrayList<Menu> menusToAdd = new ArrayList<>();
		
		// TODO: add more menu instances below here
		menusToAdd.add(instantiateMusicMenu());
		System.out.println("TODO: extend addMenuItemsToMenuBar in MainMenuScreen");
		// TODO: add more menu instances above here
		
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
	
	/**
	 * stub for easy copying
	 * 
	 * @param menuBar
	 * @return Menu to add to Collection<Menu>
	 */
	private Menu instantiateAnotherMenu(MenuBar menuBar) {
		
		Menu anotherMenu = new Menu("Another Menu");
		
		// add MenuItems here
		
		return anotherMenu;
		
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

	private void configureParent(MainMenuScreenController parent) {
		this.parent = parent;
	}
	
	private void configureButtons() {
		
		makeNewGameButton();
		loadGameButton();
		
	}
	
	private void makeNewGameButton() {
		
		Control newGameButton = new Button();
		newGameButton.setOnMouseClicked(e -> parent.createNewGame());
		//TODO placing in the pane
		//TODO style
		this.getChildren().add(newGameButton);
		
	}
	
	private void loadGameButton() {
		
		Control loadGameButton = new Button();
		ChoiceBox<String> loadGameChoice = new ChoiceBox<String>();
		//TODO placing in the pane
		//TODO style
		loadGameButton.setOnMouseClicked(e -> parent.loadGame(loadGameChoice.getSelectionModel().getSelectedItem()));
		
	}
		
	// All other instance methods
	

}
