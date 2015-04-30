package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

@ActionName(displayName = "Right")
public class RightMotionAction extends DoubleAction {

	private Double velocity;
	private VelocityComponent myVelocityComponent;
	

	public RightMotionAction(Sprite sprite, Double delta, KeyCode... keys) {
		super(sprite, delta, keys);
		velocity = delta;
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType(VEL_COMP);

	}

	@Override
	public void doAction() {
	    myVelocityComponent.setVelocityX(EngineMathFunctions.velocityValueFrame(velocity));
	    mySprite.setFacesLeft(false);
	}

	@Override
	public void stop() {
	    myVelocityComponent.setVelocityX(0.0);
	}

}
