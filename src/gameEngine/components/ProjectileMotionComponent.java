package gameEngine.components;

import sprite.Sprite;
import gameEngine.components.VelocityComponent;

public class ProjectileMotionComponent extends VelocityComponent {

	private Sprite myShooter;
	private double myBulletSpeed;
	private double mySelfDestructDistance;
	
	public ProjectileMotionComponent(Sprite sprite, Double speed, Double selfDestructDistance, Sprite shooter) {
		super(sprite, null);
		myShooter = shooter;
		myBulletSpeed = speed;
		mySelfDestructDistance = selfDestructDistance;
	}

	
	protected void frameCalculateVelocity(){
		//override for any possible movement
		//algorithm here.
		if((-myShooter.transform().getPosX() + mySprite.transform().getPosX()) > mySelfDestructDistance){
			mySprite.setIsActive(false);
		}
		setVelocityX(myBulletSpeed);

	}

}
