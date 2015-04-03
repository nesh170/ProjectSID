package gameEngine.components;

import javafx.geometry.Point2D;
import resources.constants.DOUBLE;
import sprite.Sprite;

/**
 * Example subclass of MoveComponent.
 * Looks toward
 *
 */
public class TowardPlayerMoveComponent extends MoveComponent {

	private Sprite playerSprite;
	private Double speedFactor;
	
	public TowardPlayerMoveComponent(Sprite sprite){
		super(sprite);
		speedFactor = DOUBLE.DEFAULT_TOWARD_MULTIPLIER;
	}
	public TowardPlayerMoveComponent(Sprite sprite, Double speedMultiply) {
		super(sprite);
		speedFactor = speedMultiply;
		//GET PLAYER SPRITE (From..level??)
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void frameCalculateVelocity(){
		//calculate vector from this sprite to the player
		Point2D towards = playerSprite.transform().getPositionPoint().subtract(mySprite.transform().getPositionPoint());
		setVelocity(towards.multiply(speedFactor));
		mySprite.transform().setRotation(towards);
		
	}
}
