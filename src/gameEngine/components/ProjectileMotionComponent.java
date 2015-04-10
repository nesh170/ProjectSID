package gameEngine.components;

import javafx.geometry.Point2D;
import sprite.Sprite;
import gameEngine.components.VelocityComponent;

public class ProjectileMotionComponent extends VelocityComponent {

	private Sprite myShooter;
	
	public ProjectileMotionComponent(Sprite sprite, Sprite shooter) {
		super(sprite);
		myShooter = shooter;
		// TODO Auto-generated constructor stub
	}

	
	protected void frameCalculateVelocity(){
		//override for any possible movement
		//algorithm here.
		//TODO: REMOVE HARDCODED SPEED
		if((mySprite.transform().getPosX() - myShooter.transform().getPosX()) > 1000){
			mySprite.setIsActive(false);
		}
		setVelocity(new Point2D(50.0, 0.0));

	}

}
