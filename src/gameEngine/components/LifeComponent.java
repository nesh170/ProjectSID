package gameEngine.components;

import java.util.List;

import sprite.Sprite;
import gameEngine.Component;

public class LifeComponent extends Component {
	
	private int myLives;

	public LifeComponent(Sprite sprite, List<Double> valueList) {
		super(sprite, valueList);
		myLives = Math.toIntExact(Math.round(valueList.get(0)));
	}
	
	public int getLives(){
		return myLives;
	}
	
	public void kill(){
		mySprite.setIsActive(false);
		myLives--;
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void update() {
		if (myLives < 0) {
			//TODO implement gameover method
		}

	}

}
