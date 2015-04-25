package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class BounceAction extends Action {
	
	private VelocityComponent myVelocityComponent;

	public BounceAction(Sprite sprite, Double val, KeyCode... keys) {
		super(sprite, val, keys);
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
	}
	
	@Override
	public void execute() {
        myVelocityComponent.setVelocityY(EngineMathFunctions.velocityValueFrame(value));
        myVelocityComponent.setGrounded(false);
      
	}

	@Override
	public void stop() {
	}


}
