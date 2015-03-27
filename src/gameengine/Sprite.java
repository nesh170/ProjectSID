package gameengine;
/**
 * Represents single object within game
 * 
 * Contains data and methods for handling
 * functionality that is tied to each sprite
 *
 */
public interface Sprite {
	
	/**
	 * Apply 'initialize' method of
	 * each behavior in list of behaviors
	 * that will be within every sprite
	 * (once every
	 */
	public void initializeAllBehaviors();
	
	/**
	 * Apply 'update' method of each behavior
	 * in list of behaviors
	 * every frame
	 * 
	 * Also contains logic for update-ordering
	 * (deciding what shoud happen first, etc
	 */
	public void updateAllBehaviors();
	
	/**
	 * 
	 */
	public void addBehavior(BehaviorExample behaviorToAdd);
	
	/**
	 * gets Behavior attached to this sprite
	 * of a specific type (there should be one
	 * behavior of each type for each sprite)
	 * 
	 */
	public BehaviorExample getBehaviorOfType(Class behaviorClass);
	
	
	
	
	
	

	
	
}
