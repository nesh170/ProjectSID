package screen.controllers;

import game.Game;
import screen.levelPlatformCapableScreen.LevelEditScreen;
import sprite.Sprite;
import javafx.scene.control.Tab;

/**
 * 
 * @author Michael
 *
 */

public interface LevelEditScreenController {
	
	public void returnToGameEditScreen();
	
	/**
	 * Loads a new sprite edit screen with an existing sprite
	 * @param levelEditScreen TODO
	 * @param sprite
	 */
	public void loadSpriteEditScreen(LevelEditScreen levelEditScreen, Sprite sprite);
	
	/**
	 * Loads a new sprite edit screen with a new sprite
	 * @param levelEditScreen
	 */
	public void loadSpriteEditScreen(LevelEditScreen levelEditScreen);
	
	/**
	 * Loads the collision table screen for the user to edit actions
	 * that occur during interactions between sprites
	 * @author Anika
	 * @param level
	 */
	public void loadCollisionTableScreen(LevelEditScreen levelEditScreen);
	

}
