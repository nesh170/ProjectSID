package gameEngine;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

public class Transform {
	
	private Point2D myPosition;
	private Point2D myRotation;
	private Dimension2D myDimensions; //width,height 
	private Point2D startPosition;
	private Point2D startRotation;
	private Dimension2D startDimensions;
	
	public Transform(Point2D position, Point2D rotation, Dimension2D dimension){
		myPosition = startPosition = position;
		myRotation = startRotation = rotation;
		myDimensions = startDimensions = dimension;
	}
	
	public void reset(){
		myPosition = startPosition;
		myRotation = startRotation;
		myDimensions = startDimensions;
	}
	public Point2D getPositionPoint(){
		return myPosition;
	}
	
	public Dimension2D getDimensions(){
		return myDimensions;
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
	
	public Double getRotAngle(){
		return Math.atan(getRotY() / getRotX());
	}
	
	public Double getWidth(){
		return myDimensions.getWidth();
	}
	
	public Double getHeight(){
		return myDimensions.getHeight();
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
	
	public void setDimensions(Dimension2D dimension) {
		myDimensions = dimension;
	}
}
