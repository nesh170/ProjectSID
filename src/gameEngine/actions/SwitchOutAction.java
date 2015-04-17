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
		Sprite curSprite = mySprite;
		mySpriteIndex ++;
		Sprite nextSprite = mySprites[mySpriteIndex];
		nextSprite.transform().setPosition(curSprite.transform().getPositionPoint());
		nextSprite.transform().setRotation(curSprite.transform().getRot());
		curSprite.transform().setPosition(nextSprite.transform().getPositionPoint());
		curSprite.transform().setRotation(nextSprite.transform().getRot());
		mySprite = nextSprite;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
