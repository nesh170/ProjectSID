package gameEngine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 * In general, any logic/functionality/reactions 
 * pertaining to a Sprite will be held within the 
 * sprite as a list of Behavior classes. Subclasses
 * of behavior will be made for specific
 * facets of  a sprite�s function within the game
 *  
 */
public abstract class Behavior {
	
	/** A behavior is attached to a single sprite,
	 *  and contains a reference to its sprite
	 */
	protected Sprite mySprite;
	private boolean isActive;
	private Set<KeyCode> myKeys = new HashSet<>();
	
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
		myKeys.addAll(Arrays.asList(keys));
	}
	
	/**
	 * Initialize aspects of specific
	 * behavior that need to happen at the
	 * onset of the level
	 * 
	 * 
	 */
	public abstract void initialize();
	
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
	public abstract void update();
	
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
	protected abstract void execute();
	
	/** Takes in a keyCode and sets up lambda expression to call this Behavior's execute method
	 * @param key
	 */
	public void setUpKey(Scene scene){
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyPressed) {
				if(myKeys.contains(keyPressed)){
					execute();
				}
			}
		});
	}
	
}
