package gameEngine.physics;

import sprite.Sprite;
import gameEngine.EngineMathFunctions;
import gameEngine.Physics;
import gameEngine.components.VelocityComponent;

public class Gravity extends Physics {

	private double myReactionValue=0.0;
	
	public Gravity(Sprite sprite, double gravValue) {
		super(sprite, EngineMathFunctions.accelerationValueFrame(gravValue));
	}

	@Override
	public void updateByPhysics() {
		VelocityComponent motionComp = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
		motionComp.accelerate(0.0, myValue + myReactionValue);
	}

    @Override
    public double getValue () {
        return myValue;
    }

    @Override
    public void setReactionValue (double reaction) {
        myReactionValue=reaction;
    }

}
