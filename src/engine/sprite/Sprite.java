package engine.sprite;

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
	 */
	public void updateAllBehaviors();
	

	
	
}
