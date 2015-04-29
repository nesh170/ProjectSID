package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;

@ActionName(DisplayName = "Two Sprite")
public class TwoSpriteAction extends Action {

	protected Sprite mySecondSprite;
	
	public TwoSpriteAction(Sprite sprite,  Sprite second, KeyCode... keys) {
		super(sprite, keys);
		mySecondSprite = second;
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
