package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class RightMotionAction extends Action {

	private Double velocity;
	private VelocityComponent myVelocityComponent;
	

	public RightMotionAction(Sprite sprite, Double delta, KeyCode... keys) {
		super(sprite, delta, keys);
		velocity = delta;
	}

	@Override
	public void prepare() {
		

	}

	@Override
	public void execute() {
	    myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
	    myVelocityComponent.setVelocityX(EngineMathFunctions.velocityValueFrame(velocity));
	    System.out.println("right!");
	}

	@Override
	public void stop() {
	    myVelocityComponent.setVelocityX(0.0);
	}

}
