package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class JumpAction extends Action {
	

	public JumpAction(Sprite sprite, double value, KeyCode... keys) {
		super(sprite,value,keys);
	}

	@Override
	public void prepare() {}
	
    @Override
    public void execute () {
        VelocityComponent velocityComp = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
        velocityComp.setVelocityY(EngineMathFunctions.velocityValueFrame(value));
        System.out.println("We're Jumping!");
    }
    
    @Override
    public void stop () {}

}
