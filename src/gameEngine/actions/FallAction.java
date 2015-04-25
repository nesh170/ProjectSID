package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class FallAction extends Action {

	private VelocityComponent myVelocityComponent;

	public FallAction(Sprite sprite, Double gravValue, KeyCode... keys) {
		super(sprite, keys);
		value = gravValue;
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");

	}

	@Override
	public void doAction() {
		myVelocityComponent.accelerate(0.0, EngineMathFunctions.accelerationValueFrame(value));

	}

	@Override
	public void stop() {
	}

}
