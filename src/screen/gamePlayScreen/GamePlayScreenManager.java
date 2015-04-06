package screen.gamePlayScreen;

import screen.ScreenController;
import screen.manager.ScreenManager;


// Inner class for handling GamePlayScreenController methods
public class GamePlayScreenManager extends ScreenManager implements GamePlayScreenController {
	
	// Static Variables
	
	
	// Instance Variables
	
	
	// Getters & Setters
	
	
	// Static Methods
	
	
	// Constructor & Helpers
	public GamePlayScreenManager(ScreenController parent) {
		
		super(parent);
		
	}

	
	// All other instance methods
	// Public
	@Override
	public void returnToMainMenuScreen() {
		throw new IllegalStateException("unimplemented returnToMainMenuScreen in GamePlayScreenController");
	}

	@Override
	public void loadSplashEditScreen() {
		throw new IllegalStateException("unimplemented loadSplashEditScreen in GamePlayScreenController");
	}

	@Override
	public void loadLevelEditScreen() {
		throw new IllegalStateException("unimplemented loadLevelEditScreen in GamePlayScreenController");
	}
	
	
	// Private
	
}