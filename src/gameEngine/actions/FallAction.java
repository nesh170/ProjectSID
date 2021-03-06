package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

@ActionName(displayName = "Gravity")
public class FallAction extends DoubleAction {

	private VelocityComponent myVelocityComponent;

	public FallAction(Sprite sprite, Double gravValue, KeyCode... keys) {
		super(sprite, gravValue, keys);
		value = gravValue;
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType(VEL_COMP);

	}

	@Override
	public void doAction() {
		myVelocityComponent.accelerate(0.0, EngineMathFunctions.accelerationValueFrame(value));

	}

	@Override
	public void stop() {
	}

}
