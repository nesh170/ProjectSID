package gameEngine.components;

import java.util.List;
import javafx.geometry.Point2D;
import resources.constants.DOUBLE;
import sprite.Sprite;
import gameEngine.components.VelocityComponent;

public class ProjectileMotionComponent extends VelocityComponent {

	private Sprite myShooter;
	
	public ProjectileMotionComponent(Sprite sprite, List<Double> valueList ,Sprite shooter) {
		super(sprite,valueList);
		myShooter = shooter;
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
