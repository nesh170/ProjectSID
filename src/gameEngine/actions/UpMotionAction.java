package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

@ActionName(displayName = "Jump")
public class UpMotionAction extends DoubleAction {
	
	private boolean climbing;
	private VelocityComponent myVelocityComponent;

	public UpMotionAction(Sprite sprite, Double value, KeyCode... keys) {
		super(sprite,-value,keys);
		climbing = false;
	}
	
	public void setClimbing(boolean climbing) {
		this.climbing = climbing;
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
	}
	
    @Override
    public void doAction () {
    	if (climbing) {
    		myVelocityComponent.setVelocityY(EngineMathFunctions.velocityValueFrame(value));
    		climbing = false;
    		return;
    	}
        if(myVelocityComponent.isGrounded()){
        	myVelocityComponent.setVelocityY(EngineMathFunctions.velocityValueFrame(value));
        	myVelocityComponent.setGrounded(false);
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
