package gameEngine;

import javafx.geometry.Point2D;

public class Transform {
	
	private Point2D myPosition;
	private Point2D myRotation;
	
	public Transform(Point2D position, Point2D rotation){
		myPosition = position;
		myRotation = rotation;
	}
	public Point2D getPositionPoint(){
		return myPosition;
	}
	
	public Double getPosX() {
		return myPosition.getX();
	}
	
	public Double getPosY() {
		return myPosition.getY();
	}
	
	public Double getRotX() {
		return myRotation.getX();
	}
	
	public Double getRotY() {
		return myRotation.getY();
	}
	
	public void horizontalMove(Double moveAmount){
		myPosition = new Point2D(myPosition.getX() + moveAmount, myPosition.getY());
	}
	
	public void verticalMove(Double moveAmount){
		myPosition = new Point2D(myPosition.getX(), myPosition.getY() + moveAmount);
	}
	
	public void setRotation(Point2D rotate) {
		myRotation = rotate;
	}
	
	public void setPosition(Point2D point) {
		myPosition = point;
	}
}
