package gameEngine;


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
	
	/** At construction, behavior knows the
	 * sprite it is attached to
	 * @param sprite
	 */
	public Behavior(Sprite sprite){
		mySprite = sprite;
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
	
}
