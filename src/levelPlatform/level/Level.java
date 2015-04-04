package levelPlatform.level;
import gameEngine.Action;
import gameEngine.Collision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import sprite.Sprite;
import javafx.scene.input.KeyCode;
import levelPlatform.LevelPlatform;

/**
 * 
 * Contains all information, methods for
 * each scene/level.
 *
 * Subclass of LevelPlatform, which contains functionality such as:
 * 	- width
 * 	- height
 * 	- Sprites
 * 	- boundaries
 * 	- projecticles
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
	private Collision collisionDetector;
	
	
	// Getters & Setters
	/**
	 * @return a controlMap which might change depending on the behaviours for each level
	 */
	public Map<KeyCode,Action> controlMap(){
		
		Map<KeyCode,Action> controlMap = new HashMap<>();
		playerSprite.actionList().forEach(action -> action.setUpKey(controlMap));
		return controlMap;
            
	}
	
	
		
		
	}
	
		
		
	}
	
	// All Other Instance Methods
	public void update(){
		
		//ordering an issue here
		
		//sprites updating
		
		
	}
	
//	/**
//	 * Calls the render method from each sprite and puts it within a group
//	 * @return
//	 */
//	public Group render(){
//	    
//	}
	
	/**
	 * Checks for collision between the sprite and if there is any intersection between the shapes, 
	 * send it to the collision class to figure the collision happened from which side and decrement
	 * the correct behavior
	 * @param sprite1
	 * @param sprite2
	 */
	private void checkCollision(Sprite sprite1,Sprite sprite2){
	    //TODO figure out collisions
	}
	
}	
