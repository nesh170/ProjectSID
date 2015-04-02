package player;

import java.util.List;

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
	 * Allows engine to get highscore. 
	 */
	int getHighScore();
	
	/**
	 * Calls GameLoader to select a game to play. 
	 */
	void loadNewGame();
	
	/**
	 * Set preferences for specific games. 
	 */
	void setPreferences();
	
	/**
	 * Enable showing possible games to be played. 
	 */
	List findGames();
	
	/**
	 * Saves current game being played. 
	 */
	void saveGame();
}
