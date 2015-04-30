package player;

import java.util.List;

import javafx.stage.Stage;

public interface GamePlayerInterface {
	/**
	 * Starts or restarts the game animation.
	 */
	void start();
	
	/**
	 * Pauses the game. 
	 */
	void pause();
	
	/**
	 * Calls GameLoader to select a game to play. 
	 */
	void loadNewGame();
	
	/**
	 * Set preferences for specific games. 
	 */
	void setPreferences();
	
	/**
	 * Saves current game being played. 
	 */
	void saveGame();
}
