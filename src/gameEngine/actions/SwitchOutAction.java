package gameEngine.actions;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.components.VelocityComponent;

/**
 * This is an action that switches out one sprite for another
 * used for powerups, changing character during gameplay, etc.
 *
 */
@ActionName(displayName = "Switch Out")
public class SwitchOutAction extends MultiSpriteAction{
	
	/*@Sprite[]
	@List<Sprites>
	@KeyCode...*/
	public SwitchOutAction(Sprite[] sprites, List<Sprite> allPlayers, KeyCode ... keys) {
		super(sprites, allPlayers, keys);
		
	}

	@Override
	public void prepare() {
	}

	@Override
	public void doAction() { // TODO: need to change name to "execute()" in order for CollisionTable to run Action
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
