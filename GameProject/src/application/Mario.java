package application;

import javafx.scene.image.*;

public class Mario extends ImageView {
	
	public double velY = 0;
	private double mySpeedX = 1.0;
	private double mySpeedY = 0.0;
	
	public Mario(double xPos, double yPos, Image img){
		setX(xPos);
		setY(yPos);
		setImage(img);
		setFitWidth(40.0);
		setFitHeight(40.0);
	}
	
	public void moveLeft(){
		setX(getX() - mySpeedX);
	}
	public void moveRight(){
		setX(getX() + mySpeedX);
	}

}
