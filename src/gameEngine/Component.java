package gameEngine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import sprites.Sprite;
import javafx.scene.input.KeyCode;


/**
 * In general, Components will hold some state
 * associated with a sprite, along with various utility methods
 * that act on that state, and finally an update() method that
 * is called every frame by the engine (and acts on the state in this
 * component or others on the sprite).
 *  
 */
public abstract class Component {
	
	/** A component is attached to a single sprite,
	 *  and contains a reference to its sprite
	 */
	protected Sprite mySprite;
	private boolean isActive;
	
	/** At construction, component knows the
	 * sprite it is attached to
	 * @param sprite
	 */
	public Component(Sprite sprite){
		mySprite = sprite;
	}
	
	/**
	 * Initialize aspects of specific
	 * component that need to happen at the
	 * onset of the level
	 * 
	 * 
	 */
	public abstract void prepare();
	
	/**
	 * Check if component is active.
	 * if so, execute update
	 */
	public void updateIfEnabled(){
		if(isActive){
			update();
		}
	}
	/**
	 * Aspects of specific component that
	 * need to happen every frame
	 */
	protected abstract void update();
	
	/** Set component to active */
	public void setActive(boolean set){
		isActive = set;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
}
