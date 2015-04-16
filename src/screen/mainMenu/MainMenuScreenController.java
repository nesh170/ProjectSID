package screen.mainMenu;

import java.net.URI;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Popup;

public interface MainMenuScreenController {

	/**
	 * shows popup for user to input game name and description
	 * @param popup
	 */
	public void createNewGame(Popup popup);
	
	//public void createNewGame();
	
	/**
	 * Tells the ScreenController to load a FileChooser. If non-null, load the Game. 
	 * Important: Done in ScreenController because access to the stage is needed.
	 * 
	 * This calls loadGame(URI gameLocationOnDisk) internally in ScreenController.
	 */
	public void loadGame();
	
	public void closeApplication();
	
	/**
	 * Creates a new game and loads the game to the game editing screen
	 */

	void confirmToCreateGame(Popup popup, TextField gameName, TextArea des);

}
