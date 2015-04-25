package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class UpMotionAction extends Action {
	
	private boolean climbing;

	public UpMotionAction(Sprite sprite, Double value, KeyCode... keys) {
		super(sprite,value,keys);
		climbing = false;
	}
	
	public void setClimbing(boolean climbing) {
		this.climbing = climbing;
	}

	@Override
	public void prepare() {}
	
    @Override
    public void execute () {
    	VelocityComponent velocityComp = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
    	if (climbing) {
    		velocityComp.setVelocityY(EngineMathFunctions.velocityValueFrame(value));
    		climbing = false;
    		return;
    	}
        if(velocityComp.isGrounded()){
        	velocityComp.setVelocityY(EngineMathFunctions.velocityValueFrame(value));
        	velocityComp.setGrounded(false);
        }
     
        
    }
    
    @Override
    public void stop () {
    	if (climbing) {
    		VelocityComponent velocityComp = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
    		velocityComp.setVelocityY(0.0);
    	}
    }

}
