package screen.controllers;

import java.util.List;
import java.util.Map;

import game.Game;
import screen.screenmodels.CollisionMap;
import screen.screens.LevelEditScreen;
import sprite.Sprite;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import levelPlatform.level.Level;

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
	public void loadCollisionTableScreen(LevelEditScreen levelEditScreen, CollisionMap collisionTableMap, Map<String,ObservableList<String>> stringToListMap);

	/**
	 * Saves the level created in the levelEditScreen
	 */
	public void saveLevel(Game game, Level level);
	

}
