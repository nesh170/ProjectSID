package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.Transform;

/**
 * This is an action that switches out one sprite for another
 * used for powerups, changing character during gameplay, etc.
 *
 */

public class SwitchOutAction extends Action{
	
	private int mySpriteIndex;
	private Sprite[] mySprites;

	public SwitchOutAction(Sprite[] sprites, KeyCode ... keys) {
		super(sprites[0], keys);
		mySprites = sprites;
		mySpriteIndex = 0;
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		Transform transform = mySprite.transform();
		mySpriteIndex ++;
		mySprite = mySprites[mySpriteIndex];
		mySprite.transform().setPosition(transform.getPositionPoint());
		mySprite.transform().setRotation(transform.getPositionPoint());
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
