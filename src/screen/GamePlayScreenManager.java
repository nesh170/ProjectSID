package screen;

import screen.gamePlayScreen.GamePlayScreenController;
import screen.manager.Manager;


// Inner class for handling GamePlayScreenController methods
class GamePlayScreenManager extends Manager implements GamePlayScreenController {
	
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