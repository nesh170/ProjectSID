package gameEngine.components;

import java.util.List;
import javafx.geometry.Point2D;
import resources.constants.DOUBLE;
import sprite.Sprite;
import gameEngine.Component;

/**
 * 
 * Component that holds a sprite's velocity
 * and moves the sprite frame-by-frame by
 * said velocity. Subclassed for different movement
 * behavior; variation in subclasses will lie mainly in
 * the (here unimplemented) frameCalculateVelocity()
 * method, which will contain code describing how sprite
 * determines where to head/how fast to head there.
 * 
 */
public class VelocityComponent extends Component{

	private Point2D myVelocity;
	private double myTerminalVelocity;
	private boolean isGrounded;
	
	public VelocityComponent(Sprite sprite, Double value) {
		super(sprite, value);
		myVelocity = Point2D.ZERO;
		try{
		myTerminalVelocity=value;
		}
		catch(Exception e){
		    myTerminalVelocity=DOUBLE.TERMINAL_VELOCITY;
		}
		isGrounded = true;
	}

	@Override
	public void prepare() {
		
	}

	@Override
	protected void update() {
		frameCalculateVelocity();
		frameMove();
	}
	
	private void frameMove(){
		mySprite.transform().horizontalMove(myVelocity.getX());
		mySprite.transform().verticalMove(myVelocity.getY());
	}
	
	public void setVelocity(Double x, Double y){
		myVelocity = Point2D.ZERO.add(x, y);
	}
	
	public void setGrounded(boolean ground){
		isGrounded = ground;
	}
	
	public boolean isGrounded(){
		return isGrounded;
	}
	public void setVelocityX(Double x) {
		myVelocity = Point2D.ZERO.add(x, myVelocity.getY());
	}
	
	public void setVelocityY(Double y) {
		myVelocity = Point2D.ZERO.add(myVelocity.getX(), y);
	}
	
	public void accelerate(Double x, Double y) {
		if(myVelocity.getY()<myTerminalVelocity){
		myVelocity = myVelocity.add(new Point2D(x, y));
		}
	}
	
	public void setVelocity(Point2D vel) {
		myVelocity = vel;
	}
	
	public Point2D getVelocity(){
		return myVelocity;
	}
	protected void frameCalculateVelocity(){
		//override for any possible movement
		//algorithm here.
	}
	
}
