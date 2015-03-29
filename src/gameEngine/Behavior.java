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
public abstract class Behavior {
	
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
	public Behavior(Sprite sprite){
		mySprite = sprite;
	}
	
	/** Constructor for Behaviors that are executed by the user pressing keys
	 * @param sprite
	 * @param keys
	 */
	public Behavior(Sprite sprite, KeyCode... keys){
		this(sprite);
		myKeyCode = Arrays.asList(keys);
	}
	
	/**
	 * Initialize aspects of specific
	 * behavior that need to happen at the
	 * onset of the level
	 * 
	 * 
	 */
	public abstract void activate();
	
	/**
	 * Check if behavior is active.
	 * if so, execute update
	 */
	public void updateIfEnabled(){
		if(isActive){
			update();
		}
	}
	/**
	 * Aspects of specific behavior that
	 * need to happen every frame
	 */
	protected abstract void update();
	
	/** Set behavior to active */
	public void setActive(boolean set){
		isActive = set;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	/**
	 * Executes the behavior based on a keypressed
	 */
	public abstract void execute();
	
	/**
	 * puts in an Behaviour into a methodMap. This allows the method to be executed based on the press
	 * @param methodMap
	 */
	public void setUpKey(Map<KeyCode,Behavior> methodMap){
	    myKeyCode.forEach((KeyCode key)-> methodMap.put(key, this));
	}
	
}
