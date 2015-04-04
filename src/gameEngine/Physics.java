package gameEngine;

import sprite.Sprite;

public abstract class Physics {
	
	protected Sprite mySprite;
	
	public Physics(Sprite sprite) {
		mySprite = sprite;
	}
	
	public abstract void updateByPhysics();
	
}
