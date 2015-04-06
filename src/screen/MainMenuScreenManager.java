package screen;

import game.Game;

import java.io.File;
import java.net.URI;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import screen.mainMenu.MainMenuScreenController;
import screen.manager.Manager;

// Nested Classes
// Inner class for handling MainMenuScreenController methods
public class MainMenuScreenManager extends Manager implements MainMenuScreenController {
	
	// Static Variables
	
	
	// Instance Variables
	
	
	// Getters & Setters
	
	
	// Static Methods
	
	
	// Constructor & Helpers
	public MainMenuScreenManager(ScreenController parent) {
		
		super(parent);
		
	}
	
	@Override
	public void createNewGame() {
		throw new IllegalStateException("unimplemented createNewGame in MainMenuScreenController");
	}

	@Override
	public void loadGameEditScreen(String recentGameName) {
		throw new IllegalStateException("unimplemented loadGameEditScreen in MainMenuScreenController");
	}

	@Override
	public void loadGame() {
		
		URI gameLocation;

		if ((gameLocation = getGameFileURI()) != null) {
			loadGame(gameLocation);
		}

	}
	
	/**
	 * @author Ruslan
	 * 
	 * Opens a FileChooser, select an XML that stores our Game
	 * @return URI of exact Game XML location
	 */
	private URI getGameFileURI() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Game File");
		
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Game File", "*.xml*"));
		
		File file = parent().getFileUsingFileChooser(fileChooser);
				
		if (file != null) {
			return file.toURI();
		} else {
			return null;
		}
		
	}

	/**
	 * Called by loadGame() in the interface. Guaranteed that gameLocationOnDisk != null
	 * 
	 * @param gameLocationOnDisk
	 */
	private void loadGame(URI gameLocationOnDisk) {
		
		Game game = null;
		
		// TODO:
			/*
			 * Instantiate a DataManager or call DataManager.sharedInstance()
			 * Read off a Game from XML
			 */
		
		// Here, Game can potentiall still be null if the user selected a non-Game XML
		if (game != null) {
			
			
			// TODO
			/*
			 * Instantiate a GameEditScreen
			 * Set main menu tab to unclickable and unremovable
			 */
		} 
		
		else {
			handleError("Invalid Game file selected.");
		}
		
	}
	
	@Override
	public void closeApplication() {
		parent().closeApplication();
	}
	
}