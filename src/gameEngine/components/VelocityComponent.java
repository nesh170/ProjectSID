package gameEngine.components;

import javafx.geometry.Point2D;
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
	
	private static final double TERMINAL_VELOCITY = 5.0;
	
	private boolean isGrounded;
	
	public VelocityComponent(Sprite sprite) {
		super(sprite);
		myVelocity = Point2D.ZERO;
		isGrounded = true;
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
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
		if(myVelocity.getY()<TERMINAL_VELOCITY){
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