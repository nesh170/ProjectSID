package screen.gameEditScreen;

import gameEngine.Level;

public interface GameEditScreenController {
	
	void returnToMainMenuScreen();
	
	/*
	 * 
	 */
	void loadSplashEditScreen();
	
	void loadLevelEditScreen(Level level);
	
}
