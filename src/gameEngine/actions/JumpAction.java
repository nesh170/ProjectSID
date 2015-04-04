package gameEngine.actions;

import sprite.Sprite;
import gameEngine.Action;
import gameEngine.EngineMathFunctions;
import gameEngine.components.MotionComponent;

public class JumpAction extends Action {
	
	private Double initialVelocity;
	
	public JumpAction(Sprite sprite, Double initVelocity){
		super(sprite);
		initialVelocity = EngineMathFunctions.velocityValueFrame(initVelocity);
	}
	@Override
	public void prepare() {}
	
    @Override
    public void execute () {
        MotionComponent mc = (MotionComponent) mySprite.getComponentOfType("MotionComponent");
        mc.setVelocityY(initialVelocity);
            
    }
    @Override
    public void stop () {}

}
