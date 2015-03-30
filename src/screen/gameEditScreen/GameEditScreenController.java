package screen.gameEditScreen;

import screen.ScreenControllerInterface;
import gameEngine.Game;
import gameEngine.Level;

public interface GameEditScreenController extends ScreenControllerInterface {
	
	void returnToMainMenuScreen();
	
	/*
	 * Loads a new splash edit screen that edits the splash screen
	 * of the game parameter
	 */
	void loadSplashEditScreen(Game game);
	
	void loadLevelEditScreen(Level level);
	
}
