package screen.mainMenu;

import java.net.URI;

public interface MainMenuScreenController {
	
	/**
	 * Creates a new game and loads the game to the game editing screen
	 */
	public void createNewGame();
	
	/**
	 * Tells the ScreenController to load a FileChooser. If non-null, load the Game. 
	 * Important: Done in ScreenController because access to the stage is needed.
	 * 
	 * This calls loadGame(URI gameLocationOnDisk) internally in ScreenController.
	 */
	public void loadGame();
	
	public void closeApplication();
	
}
