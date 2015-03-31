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
	 * Tells the ScreenController to load a FileChooser. If non-null, load the Game. 
	 * Important: Done in ScreenController because access to the stage is needed.
	 * 
	 * This calls loadGame(URI gameLocationOnDisk) internally in ScreenController.
	 */
	public void loadGame();
	
	public void closeApplication();
	
}
