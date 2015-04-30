package gameEngine.components;

import sprite.Sprite;
import gameEngine.Component;

@HUDInterface(name = "Life")
public class LifeComponent extends Component {
	
	private int myLives;

	public LifeComponent(Sprite sprite, Double value) {
		super(sprite, value);
		myLives = Math.toIntExact(Math.round(value));
	}
	
	@HUDGetter
	public int getLives(){
		return myLives;
	}
	
	public void kill(){
		mySprite.setIsActive(false);
		myLives--;
	}

	@Override
	public void prepare() {}

	@Override
	protected void update() {
		if (myLives < 0) {
			//TODO implement gameover method
		}

	}

}
