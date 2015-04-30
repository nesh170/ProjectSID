package gameEngine.actions;

import gameEngine.Action;
import gameEngine.components.VelocityComponent;
import sprite.Sprite;

@ActionName(displayName = "NormalX")
public class NormalActionX extends Action{

	private VelocityComponent myVelocityComponent;
	
	public NormalActionX(Sprite sprite) {
		super(sprite);
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType(VEL_COMP);
	}

	@Override
	public void doAction() {
		myVelocityComponent.setVelocityX(0.0);
	}

	@Override
	public void stop() {
	}
	
}
