package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class LeftMotionAction extends Action {

	private VelocityComponent myVelocityComponent;

	public LeftMotionAction(Sprite sprite, Double delta, KeyCode... keys) {
		super(sprite, delta, keys);
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");

	}

	@Override
	public void execute() {
		myVelocityComponent.setVelocityX(-EngineMathFunctions.velocityValueFrame(value));

	}

	@Override
	public void stop() {
		myVelocityComponent.setVelocityX(0.0);
	}

}
