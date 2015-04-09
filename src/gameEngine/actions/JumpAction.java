package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class JumpAction extends Action {
	
	private Double initialVelocity;
	
	public JumpAction(Sprite sprite, Double initVelocity, KeyCode... keys){
		super(sprite , keys);
		initialVelocity = EngineMathFunctions.velocityValueFrame(initVelocity);
		
	}
	@Override
	public void prepare() {}
	
    @Override
    public void execute () {
        VelocityComponent velocityComp = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
        velocityComp.setVelocityY(initialVelocity);
    }
    
    @Override
    public void stop () {}

}
