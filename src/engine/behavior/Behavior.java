package engine.behavior;

public interface Behavior {

	/**
	 * Initialize aspects of specific
	 * behavior that need to happen at the
	 * onset of the level
	 * 
	 * 
	 */
	public /*abstract*/ void initialize();
	
	/**
	 * Aspects of specific behavior that
	 * need to happen every frame
	 */
	public /*abstract*/ void update();
}
