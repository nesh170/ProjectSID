package gameEngine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.scene.input.KeyCode;


/**
 * In general, any logic/functionality/reactions 
 * pertaining to a Sprite will be held within the 
 * sprite as a list of Behavior classes. Subclasses
 * of behavior will be made for specific
 * facets of  a sprite's function within the game
 *  
 */
public abstract class Action {
	
	/** A behavior is attached to a single sprite,
	 *  and contains a reference to its sprite
	 */
	protected Sprite mySprite;
	private boolean isActive;
	private List<KeyCode> myKeyCode;
	
	/** At construction, behavior knows the
	 * sprite it is attached to
	 * @param sprite
	 */
	public Action(Sprite sprite){
		mySprite = sprite;
	}
	
	/** Constructor for Behaviors that are executed by the user pressing keys
	 * @param sprite
	 * @param keys
	 */
	public Action(Sprite sprite, KeyCode... keys){
		this(sprite);
		myKeyCode = Arrays.asList(keys);
	}
	
	/** Set behavior to active */
	public void setActive(boolean set){
		isActive = set;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	/**
	 * Initialize aspects of specific
	 * behavior that need to happen at the
	 * onset of the level
	 * 
	 * 
	 */
	public abstract void prepare();
	/**
	 * Executes the behavior based on a keypressed
	 */
	public abstract void execute();
	
	/**
	 * Stops the execution for movements if needed.
	 */
	public abstract void stop();
	
	/**
	 * puts in an Behaviour into a methodMap. This allows the method to be executed based on the press
	 * @param methodMap
	 */
	public void setUpKey(Map<KeyCode, Action> controlMap){
	    myKeyCode.forEach((KeyCode key)-> controlMap.put(key, this));
	}
	
}
