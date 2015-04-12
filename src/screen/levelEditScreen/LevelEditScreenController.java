package screen.levelEditScreen;

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

}
