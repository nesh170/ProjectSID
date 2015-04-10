package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class BounceAction extends Action {

	public BounceAction(Sprite sprite, Double val, KeyCode... keys) {
		super(sprite, val, keys);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
        VelocityComponent velocityComp = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
        velocityComp.setVelocityY(EngineMathFunctions.velocityValueFrame(value));
        System.out.println("We're Bouncing!");
        velocityComp.setGrounded(false);
      
	}


	@Override
	public void prepare() {
	}


	@Override
	public void stop() {
	}


}
