package gameEngine.actions;

import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;
import sprite.Sprite;

public class GravityAction extends FrameAction {

	private double myGravityConstant;
	
	public GravityAction(Sprite sprite, double gravityConstant) {
		super(sprite);
		myGravityConstant = EngineMathFunctions.accelerationValueFrame(gravityConstant);
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		VelocityComponent velocityComp = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
		velocityComp.accelerate(0.0, myGravityConstant);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
