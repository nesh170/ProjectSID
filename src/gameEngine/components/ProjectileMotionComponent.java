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
	
	public ProjectileMotionComponent(Sprite sprite, List<Double> valueList ,Sprite shooter) {
		super(sprite,valueList);
		myShooter = shooter;
		myBulletSpeed = valueList.get(0);
		mySelfDestructDistance = valueList.get(1);
	}

	
	protected void frameCalculateVelocity(){
		//override for any possible movement
		//algorithm here.
		//TODO: REMOVE HARDCODED SPEED
		if((mySprite.transform().getPosX() - myShooter.transform().getPosX()) > mySelfDestructDistance){
			mySprite.setIsActive(false);
		}
		setVelocityX(myBulletSpeed);

	}

}
