package gameEngine.actions;

import javafx.geometry.Point2D;
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
		for(int i = 1; i<sprites.length; i ++){
			sprites[i].setIsActive(false);
		}
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		Transform transform1 = mySprites[mySpriteIndex].transform();
		mySprites[mySpriteIndex].setIsActive(false);
		mySpriteIndex = (mySpriteIndex+1)%mySprites.length;
		mySprites[mySpriteIndex].setIsActive(true);
		mySprites[mySpriteIndex].transform().setPosition(new Point2D(transform1.getPosX(), transform1.getPosY()));
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
