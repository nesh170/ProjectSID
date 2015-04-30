package gameEngine.components;

import sprite.Sprite;
import gameEngine.EngineMathFunctions;
import gameEngine.components.VelocityComponent;

public class ProjectileMotionComponent extends VelocityComponent {

	private Sprite myShooter;
	private double myBulletSpeed;
	private double mySelfDestructDistance;
	
	public ProjectileMotionComponent(Sprite sprite, Double speed, Double selfDestructDistance) {
		super(sprite, null);
		myBulletSpeed = speed;
		mySelfDestructDistance = selfDestructDistance;
	}
	
	public void setShooter(Sprite shooter) {
		myShooter = Sprite.makeCopy(shooter);
	}

	
	protected void frameCalculateVelocity(){
		//override for any possible movement
		//algorithm here.
		if((Math.abs(-myShooter.transform().getPosX() + mySprite.transform().getPosX())) > mySelfDestructDistance){
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
