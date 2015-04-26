package gameEngine.components;

import java.util.List;
import javafx.geometry.Point2D;
import resources.constants.DOUBLE;
import sprite.Sprite;
import gameEngine.components.VelocityComponent;

public class ProjectileMotionComponent extends VelocityComponent {

	private Sprite myShooter;
	private double myBulletSpeed;
	private double mySelfDestructDistance;
	
	public ProjectileMotionComponent(Sprite sprite, Double speed, Double sdd, Sprite shooter) {
		super(sprite, null);
		myShooter = shooter;
		myBulletSpeed = speed;
		mySelfDestructDistance = sdd;
	}

	
	protected void frameCalculateVelocity(){
		//override for any possible movement
		//algorithm here.
		if((myShooter.transform().getPosX() - mySprite.transform().getPosX()) > mySelfDestructDistance){
			mySprite.setIsActive(false);
		}
		setVelocityX(myBulletSpeed);

	}

}
