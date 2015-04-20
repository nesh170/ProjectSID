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
	private double mySpeed;
	private double myTolerance;

	public MotionPathAction(Sprite sprite, double speed, Point2D[] points, KeyCode... keys) {
		super(sprite, keys);
		myPoints = points;
		mySpeed = speed;
		myTolerance = speed;
	}
	
	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
		//myLastVisititedIndex gives most recent point visited
		//use direction to determine next point
		Point2D nextPoint = myPoints[myLastVisitedIndex + myDirection];
		//use speed, and position of next point and sprite, to move the sprite to the next point appropriately
		Point2D curPos = mySprite.transform().getPositionPoint();
		double angle = Math.atan((nextPoint.getY() - curPos.getY())/(nextPoint.getX() - curPos.getX()));
		myVelocityComponent.setVelocityX(mySpeed*Math.cos(angle));
		myVelocityComponent.setVelocityY(mySpeed*Math.sin(angle));
		//Check to see if we're close enough to switch to next point. If so, switch
		if(closeEnough(curPos, nextPoint)){
			myLastVisitedIndex += myDirection;
		}
		//If at end or beginning, switch direction	
		if(myLastVisitedIndex + myDirection<0 || myLastVisitedIndex + myDirection >= myPoints.length){
			myDirection = myDirection*(-1);
		}
	}
	
	private boolean closeEnough(Point2D curPos, Point2D nextPoint){
		return(curPos.getX()>nextPoint.getX()-myTolerance
				&& curPos.getX()<nextPoint.getX() + myTolerance
				&& curPos.getY()>nextPoint.getY()-myTolerance
				&& curPos.getY()<nextPoint.getY()+myTolerance);
	}

	@Override
	public void stop() {
		myVelocityComponent.setVelocityX(0.0);
		myVelocityComponent.setVelocityY(0.0);
	}

}
