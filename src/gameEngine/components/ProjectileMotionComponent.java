package gameEngine.components;

import javafx.geometry.Point2D;
import sprite.Sprite;
import gameEngine.components.VelocityComponent;

public class ProjectileMotionComponent extends VelocityComponent {

	public ProjectileMotionComponent(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}
	
	protected void frameCalculateVelocity(){
		//override for any possible movement
		//algorithm here.
		setVelocity(new Point2D(100.0, 0.0));
	}

}
