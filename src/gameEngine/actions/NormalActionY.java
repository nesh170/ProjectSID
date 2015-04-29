package gameEngine.actions;

import sprite.Sprite;
import gameEngine.Action;
import gameEngine.components.VelocityComponent;

@ActionName(DisplayName = "NormalY")
public class NormalActionY extends Action {

	private VelocityComponent myVelocityComponent;
	
	public NormalActionY(Sprite sprite) {
		super(sprite);
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
	}

	@Override
	public void doAction() {
		myVelocityComponent.setVelocityY(0.0);
		myVelocityComponent.setGrounded(true);
	}

	@Override
	public void stop() {
	}

}
