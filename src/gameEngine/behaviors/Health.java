package gameEngine.behaviors;

import gameEngine.Behavior;
import gameEngine.Sprite;

/**
 * Behavior containing health state,
 * and methods for increasing/decreasing
 * 
 */
public class Health extends Behavior {
	
	private Double myHP;
	
	public Health(Sprite sprite, Double startingHP){
		super(sprite);
		myHP = startingHP;
	}
	
	/** get health points */
	public Double getHealth(){
		return myHP;
	}
	/**	Subtract double from HP */
	public void decrease(Double decreaseAmount){
		myHP -= decreaseAmount;
	}
	
	/** Subtract int from HP */
	public void decrease(int decreaseAmount){
		myHP -= decreaseAmount;
	}
	
	/** Add double to HP */
	public void increase(Double increaseAmount){
		myHP += increaseAmount;
	}
	
	/** Add int to HP */
	public void increase(int increaseAmount){
		myHP += increaseAmount;
	}
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
