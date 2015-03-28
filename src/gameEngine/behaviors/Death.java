package gameEngine.behaviors;

import gameEngine.Behavior;
import gameEngine.Sprite;

public class Death extends Behavior {
	
	private Health myHealth;
	
	public Death(Sprite sprite){
		super(sprite);
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		myHealth = (Health) mySprite.getBehaviorOfType("Health"); //RAW STRING - CHANGE TO VARIABLE ASAP

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(mySprite.isActive() && (myHealth.getHealth() <= 0.0))	{
			mySprite.setActive(false);
		}
	}

}
