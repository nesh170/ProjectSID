package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class JumpAction extends Action {
	

	public JumpAction(Sprite sprite, Double value, KeyCode... keys) {
		super(sprite,value,keys);
	}

	@Override
	public void prepare() {}
	
    @Override
    public void execute () {
        VelocityComponent velocityComp = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
        if(velocityComp.isGrounded()){
        	velocityComp.setVelocityY(EngineMathFunctions.velocityValueFrame(value));
        	System.out.println("We're Jumping!");
        	velocityComp.setGrounded(false);
        }
     
        
    }
    
    @Override
    public void stop () {}

}
