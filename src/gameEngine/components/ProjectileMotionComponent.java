package gameEngine.components;

import sprite.Sprite;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class ProjectileMotionComponent extends VelocityComponent {

	private Sprite myShooter;
	private double myBulletSpeed;
	private static final double SELF_DESTRUCT_DISTANCE=500;
	
	public ProjectileMotionComponent(Sprite sprite, Double speed) {
		super(sprite, null);
		myBulletSpeed = speed;
	}
	
	public void setShooter(Sprite shooter) {
		myShooter = Sprite.makeCopy(shooter);
	}

	
	protected void frameCalculateVelocity(){
		//override for any possible movement
		//algorithm here.
		if((Math.abs(-myShooter.transform().getPosX() + mySprite.transform().getPosX())) > SELF_DESTRUCT_DISTANCE){
			mySprite.setIsActive(false);
		}
		if(myShooter.facesLeft()){
	        setVelocityX(-EngineMathFunctions.velocityValueFrame(myBulletSpeed));
	    }
	    else{
	        setVelocityX(EngineMathFunctions.velocityValueFrame(myBulletSpeed));
	    }
	        

	}

}
