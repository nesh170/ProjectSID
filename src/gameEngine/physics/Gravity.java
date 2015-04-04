package gameEngine.physics;

import sprite.Sprite;
import gameEngine.EngineMathFunctions;
import gameEngine.Physics;
import gameEngine.components.MotionComponent;

public class Gravity extends Physics {

	private double gravityValue;
	
	public Gravity(Sprite sprite, double gravValue) {
		super(sprite);
		gravityValue = EngineMathFunctions.gravityValueFrame(gravValue);
	}

	@Override
	public void updateByPhysics() {
		MotionComponent motionComp = (MotionComponent) mySprite.getComponentOfType("MotionComponent");
		motionComp.accelerate(0.0, gravityValue);

	}

}
