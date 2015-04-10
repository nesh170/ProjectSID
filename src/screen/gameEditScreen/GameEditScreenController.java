package screen.gameEditScreen;

import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;
import game.Game;

/**
 * 
 * @author Yongjiao
 * @author Michael
 *
 */
public interface GameEditScreenController {
	
	public void returnToMainMenuScreen();
	
	/**
	 * Loads a new splash edit screen that edits the splash screen
	 * of the game parameter?
	 * @param game
	 */
	public void loadSplashEditScreen(Game game);
	/**
	 * Loads a new level edit screen and adds it to a game
	 * @param game
	 */
	public void loadLevelEditScreen(Game game);
	/**
	 * Loads a new level edit screen with an existing level
	 * @param level
	 */
	public void loadLevelEditScreen(Level level);
	/**
	 * allow users to test/demo while editing a game
	 * @param game
	 */
	public void playGame(Game game);
	/**
	 * remove the level from list of levels created 
	 * @param level
	 */
	public void trashLevel(Game game, int levelIndex);
	/**
	 * removes the splash screen from list of splash screen created
	 * pass in SplashScreen or Game?
	 */
	public void trashSplash(Game game);	

}