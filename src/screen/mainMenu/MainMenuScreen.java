package screen.mainMenu;

import java.io.File;

import media.MediaManager;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
		configureMusic();
		configureButtons();
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		System.out.println("Implement addMenuItemsToMenuBar in MainMenuScreen");
		
	}
	
	private void configureParent(MainMenuScreenController parent) {
		this.parent = parent;
	}
	
	private void configureMusic() {
		
		MediaManager.sharedInstance().loadNewMedia("RollingInTheDeep.mp3");
		MediaManager.sharedInstance().play();
		
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
