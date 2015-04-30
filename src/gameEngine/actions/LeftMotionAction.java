package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

@ActionName(displayName = "Left")
public class LeftMotionAction extends DoubleAction {

	private VelocityComponent myVelocityComponent;

	public LeftMotionAction(Sprite sprite, Double delta, KeyCode... keys) {
		super(sprite, delta, keys);
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType(VEL_COMP);

	}

	@Override
	public void doAction() {
		myVelocityComponent.setVelocityX(-EngineMathFunctions.velocityValueFrame(value));
		mySprite.setFacesLeft(true);
	}

	@Override
	public void stop() {
		myVelocityComponent.setVelocityX(0.0);
	}

}
