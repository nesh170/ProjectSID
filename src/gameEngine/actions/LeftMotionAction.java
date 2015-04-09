package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class LeftMotionAction extends Action {

	private Double velocity;
	private VelocityComponent myVelocityComponent;
	
	public LeftMotionAction(Sprite sprite, Double delta) {
		super(sprite);
		velocity = EngineMathFunctions.velocityValueFrame(delta);
	}

	public LeftMotionAction(Sprite sprite, Double delta, KeyCode... keys) {
		super(sprite, keys);
		velocity = EngineMathFunctions.velocityValueFrame(delta);
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");

	}

	@Override
	public void execute() {
	    System.out.println("left");
		myVelocityComponent.setVelocityX(-velocity);

	}

	@Override
	public void stop() {
		myVelocityComponent.setVelocityX(0.0);
	}

}
