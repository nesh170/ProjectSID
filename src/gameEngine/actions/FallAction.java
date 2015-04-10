package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class FallAction extends Action {

	private VelocityComponent myVelocityComponent;
	private double gravityValue;
	
	public FallAction(Sprite sprite, Double gravValue) {
		super(sprite);
		gravityValue = EngineMathFunctions.accelerationValueFrame(gravValue);
	}

	public FallAction(Sprite sprite, Double gravValue, KeyCode... keys) {
		super(sprite, keys);
		gravityValue = EngineMathFunctions.accelerationValueFrame(gravValue);
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");

	}

	@Override
	public void execute() {
		myVelocityComponent.accelerate(0.0, gravityValue);

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
