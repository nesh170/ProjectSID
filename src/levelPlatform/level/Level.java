package levelPlatform.level;
import gameEngine.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sprite.Sprite;
import javafx.scene.input.KeyCode;
import levelPlatform.LevelPlatform;

/**
 * 
 * Contains all information, methods for
 * each scene/level
 *
 * Subclass of LevelPlatform, which contains functionality such as:
 * 	- width
 * 	- height
 * 	- Sprites
 * 
 * 	Level adds functionality on top such as
 * 	- A "Player" Sprite
 * 	- Controls
 * 
 */
public class Level extends LevelPlatform {
	
	// Static Variables
	
	
	// Instance Variables
	private Sprite playerSprite;
	
	
	// Getters & Setters
	/**
	 * @return a controlMap which might change depending on the behaviours for each level
	 */
	public Map<KeyCode,Action> controlMap() {
		
		Map<KeyCode,Action> controlMap = new HashMap<>();
		playerSprite.actionList().forEach(action -> action.setUpKey(controlMap));
		return controlMap;
            
	}
	
	public void setPlayerSprite(Sprite player) {
	    playerSprite = player;
	}
	
	public void setSprites(List<Sprite> spriteList){
		sprites = spriteList;
	}
	
	
	// Constructor & Helpers
	public Level(int width, int height) {
		this(width, height, null);
	}
	
	public Level(int width, int height, Sprite playerSprite) {
		super(width, height);
		
		if (playerSprite != null) {
			
			this.playerSprite = playerSprite;
			
			// Call this in Level in addition to its super -- prepare playerSprite as well
			prepareAllSprites();
			
		}
		
	}
	
}	
