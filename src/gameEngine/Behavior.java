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
	
	Sprite mySprite;
	/**
	 * Initialize aspects of specific
	 * behavior that need to happen at the
	 * onset of the level
	 * 
	 * 
	 */
	public abstract void initialize();
	
	/**
	 * Aspects of specific behavior that
	 * need to happen every frame
	 */
	public abstract void update();
	
}
