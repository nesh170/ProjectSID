package gameEngine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sprite.Sprite;
import javafx.scene.input.KeyCode;


/**
 * Action will hold a specific event (execute method) that will be triggered
 * at various points in game. Can be attached to a key for control,
 * added to a collision, or even called in the update() method of
 * Components
 */
public abstract class Action {
	
	/** An action is attached to a single sprite,
	 *  and contains a reference to its sprite
	 */
	protected Sprite mySprite;
	private boolean isActive;
	private List<KeyCode> myKeyCode;
	private boolean runsEveryFrame = false;
	protected double value;
	
	/** At construction, action knows the
	 * sprite it is attached to
	 * @param sprite
	 */
	public Action(Sprite sprite){
		mySprite = sprite;
	}
	
	/** Constructor for Actions that are executed by the user pressing keys
	 * @param sprite
	 * @param keys
	 */
	public Action(Sprite sprite, KeyCode... keys){
		this(sprite);
		myKeyCode = Arrays.asList(keys);
	}
	
	/**
	 * Constructor for actions that use double values
	 * @param sprite
	 * @param value
	 * @param keys
	 */
	public Action(Sprite sprite, Double val, KeyCode... keys){
		this(sprite, keys);
		value = val;
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
	
	public void update(){
		if(runsEveryFrame){
			execute();
		}
	}
	
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
		//TODO: discuss best way to only do this method for certain actions
		if(myKeyCode != null){
	    myKeyCode.forEach((KeyCode key)-> controlMap.put(key, this));
		}
	}
	/**
	 * Call this method for every action that needs to execute every frame. (usually things like gravity)
	 */
	public void runEveryFrame(){
		runsEveryFrame = true;
	}
	
}