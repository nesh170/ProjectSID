package gameEngine.actions;

import java.util.List;

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
	private List<Sprite> myPlayerList;

	public SwitchOutAction(Sprite[] sprites, List<Sprite> allPlayers, KeyCode ... keys) {
		super(sprites[0], keys);
		mySprites = sprites;
		mySpriteIndex = 0;
		for(int i = 1; i<sprites.length; i ++){
			sprites[i].setIsActive(false);
		}
		myPlayerList = allPlayers;
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		Transform transform1 = mySprites[mySpriteIndex].transform();
		mySprites[mySpriteIndex].setIsActive(false);
		int sprite1Index = myPlayerList.indexOf(mySprites[mySpriteIndex]);
		System.out.println(sprite1Index);
		mySpriteIndex = (mySpriteIndex+1)%mySprites.length;
		int sprite2Index = myPlayerList.indexOf(mySprites[mySpriteIndex]);
		System.out.println(sprite2Index);
		mySprites[mySpriteIndex].setIsActive(true);
		mySprites[mySpriteIndex].transform().setPosition(new Point2D(transform1.getPosX(), transform1.getPosY()));
		switchPlayers(sprite1Index, sprite2Index);
	}

	private void switchPlayers(int sprite1Index, int sprite2Index) {
		Sprite player1 = myPlayerList.get(sprite1Index);
		Sprite player2 = myPlayerList.get(sprite2Index);
		myPlayerList.set(sprite1Index, player2);
		myPlayerList.set(sprite2Index, player1);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
