package screen.gameEditScreen;

import level.Level;
import game.Game;

public interface GameEditScreenController {
	
	public void returnToMainMenuScreen();
	
	/**
	 * Loads a new splash edit screen that edits the splash screen
	 * of the game parameter? 
	 */
	public void loadSplashEditScreen(Game game);
	public void loadLevelEditScreen(Level level);
	/**
	 * load a new edit screen when users press add button;
	 */
	public void loadLevelEditScreen();
	public void loadSplashEditScreen();
	/**
	 * allow users to test/demo while editing a game
	 */
	public void playGame(Game game);
	/**
	 * remove the level from list of levels created 
	 */
	public void trashLevel(Level level);
	/**
	 * removes the splash screen from list of splash screen created
	 * pass in SplashScreen or Game?
	 */
	//public void trashSplash(SplashScreen splash){}		
	/**
	 * 
	 */
	
}