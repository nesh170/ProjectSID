package tester.engine;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.components.VelocityComponent;

public class MotionPathAction extends Action {
	
	private Point2D[] myPoints;
	private VelocityComponent myVelocityComponent;
	private int myDirection = 1;
	private int myLastVisitedIndex = 0;

	public MotionPathAction(Sprite sprite, Point2D[] points, KeyCode... keys) {
		super(sprite, keys);
		myPoints = points;
	}
	
	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
		
	}

	@Override
	public void stop() {
		myVelocityComponent.setVelocityX(0.0);
		myVelocityComponent.setVelocityY(0.0);
	}

}
