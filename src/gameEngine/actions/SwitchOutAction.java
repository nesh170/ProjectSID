package gameEngine.actions;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.components.VelocityComponent;

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
	}

	@Override
	public void execute() {
		Sprite sprite1 = mySprites[mySpriteIndex];
		mySpriteIndex = (mySpriteIndex+1)%mySprites.length;
		Sprite sprite2 = mySprites[mySpriteIndex];
		sprite1.setIsActive(false);
		sprite2.setIsActive(true);
		transferPositionAndDirection(sprite1, sprite2);
		transferVelocity(sprite1, sprite2);
		switchPlayers(sprite1, sprite2);
	}

	private void transferPositionAndDirection(Sprite sprite1, Sprite sprite2) {
		sprite2.transform().setPosition(new Point2D(sprite1.transform().getPosX(), sprite1.transform().getPosY()));
		sprite2.setFacesLeft(sprite1.facesLeft());
	}
	
	private void transferVelocity(Sprite sprite1, Sprite sprite2){
		VelocityComponent velComponent1 = (VelocityComponent) sprite1.getComponentOfType("VelocityComponent");
		VelocityComponent velComponent2 = (VelocityComponent) sprite2.getComponentOfType("VelocityComponent");
		if(velComponent1 != null && velComponent2 != null){
			velComponent2.setVelocity(velComponent1.getVelocity());
		}
	}

	private void switchPlayers(Sprite sprite1, Sprite sprite2) {
		int sprite1Index = myPlayerList.indexOf(sprite1);
		int sprite2Index = myPlayerList.indexOf(sprite2);
		myPlayerList.set(sprite1Index, sprite2);
		myPlayerList.set(sprite2Index, sprite1);
	}

	@Override
	public void stop() {
		
	}

}
