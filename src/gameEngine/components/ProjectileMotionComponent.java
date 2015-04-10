package gameEngine.components;

import javafx.geometry.Point2D;
import resources.constants.DOUBLE;
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
		if((mySprite.transform().getPosX() - myShooter.transform().getPosX()) > DOUBLE.BULLET_SELF_DESTRUCT_DIST){
			mySprite.setIsActive(false);
		}
		setVelocityX(DOUBLE.BULLET_SPEED);

	}

}