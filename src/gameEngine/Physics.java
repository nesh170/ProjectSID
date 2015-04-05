package gameEngine;

import sprite.Sprite;

/**
 * 
 * Physics class handles the physics for each object. 
 *
 */
public abstract class Physics {
	
	protected Sprite mySprite;
	protected double myValue;
	
	public Physics(Sprite sprite, double value) {
		mySprite = sprite;
		myValue = value;
	}
	
	/**
	 * This method updates the physics at everyframe
	 */
	public abstract void updateByPhysics();
	
	/**
	 * This method gets the value represented by the physics. For example, gravity is defined by it's value
	 * @return
	 */
	public double getValue(){
	    return myValue;
	}
	
	/**
	 * This method get the value represented by the reaction to that physics, gravity's reaction is the normal acceleration
	 * @param reaction is in pixels/frame^2
	 */
	public abstract void setReactionValue(double reaction);
}
