package gameEngine.components;

import javafx.geometry.Point2D;
import resources.constants.DOUBLE;
import sprite.Sprite;

/**
 * Example subclass of MoveComponent.
 * Looks toward
 *
 */
public class TowardPlayerMotionComponent extends MotionComponent {

	private Sprite playerSprite;
	private Double speedFactor;
	
	public TowardPlayerMotionComponent(Sprite sprite){
		super(sprite);
		speedFactor = DOUBLE.DEFAULT_TOWARD_MULTIPLIER;
	}
	
	public void setSpeedFactor(Double multiply){
		speedFactor = multiply;
	}
	
	@Override
	protected void frameCalculateVelocity(){
		//calculate vector from this sprite to the player
		Point2D towards = playerSprite.transform().getPositionPoint().subtract(mySprite.transform().getPositionPoint());
		setVelocity(towards.multiply(speedFactor));
		mySprite.transform().setRotation(towards);
		
	}
}
