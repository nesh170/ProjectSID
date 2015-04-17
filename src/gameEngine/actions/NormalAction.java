package gameEngine.actions;

import sprite.Sprite;
import gameEngine.Action;
import gameEngine.components.VelocityComponent;

public class NormalAction extends Action {

	private VelocityComponent myVelocityComponent;
	
	public NormalAction(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
	}

	@Override
	public void execute() {
		myVelocityComponent.setVelocityY(0.0);
		myVelocityComponent.setGrounded(true);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
