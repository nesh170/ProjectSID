package application;

public class Platform {

	public double xPos;
	public double yPos;
	public double width;
	
	public Platform(double x, double y, double w){
		xPos = x;
		yPos = y;
		width = w;
	}
	
	public void move(){
		xPos ++;
	}
	
}
