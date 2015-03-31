package screen.mainMenu;

import java.net.URI;

import screen.ScreenControllerInterface;

public interface MainMenuScreenController extends ScreenControllerInterface {
	
	/**
	 * Creates a new game and loads the game to the game editing screen
	 */
	public void createNewGame();
	
	void loadGameEditScreen(String recentGameName);
	
	/**
	 * Takes the file path to a directory containing a game and loads it to
	 * the game editing screen
	 */
	public void loadGame(String gameFile);
	
	public void closeApplication();
	
}
