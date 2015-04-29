package gameEngine.actions;

import java.util.List;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;

@ActionName(displayName = "Multiple Sprite Action")
public class MultiSpriteAction extends Action {
	
	protected int mySpriteIndex;
	protected Sprite[] mySprites;
	protected List<Sprite> myPlayerList;
	
	public MultiSpriteAction(Sprite[] sprites, List<Sprite> allPlayers, KeyCode ... keys) {
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
	protected void doAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
