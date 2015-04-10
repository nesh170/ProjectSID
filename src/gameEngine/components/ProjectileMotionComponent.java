package gameEngine.components;

import javafx.geometry.Point2D;
import sprite.Sprite;
import gameEngine.components.VelocityComponent;

public class ProjectileMotionComponent extends VelocityComponent {

	public ProjectileMotionComponent(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	
	protected void frameCalculateVelocity(){
		//override for any possible movement
		//algorithm here.
		//TODO: REMOVE HARDCODED SPEED
		setVelocity(new Point2D(50.0, 0.0));

	}

}
