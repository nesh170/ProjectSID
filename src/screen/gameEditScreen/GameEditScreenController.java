package screen.gameEditScreen;

import screen.UniversalController;
import level.Level;
import game.Game;

public interface GameEditScreenController extends UniversalController {

	void returnToMainMenuScreen();
	
	/*
	 * Loads a new splash edit screen that edits the splash screen
	 * of the game parameter
	 */
	void loadSplashEditScreen(Game game);
	
	void loadLevelEditScreen(Level level);
	
}
