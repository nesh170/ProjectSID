package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;

public class BlankAction extends Action {

	public BlankAction(Sprite sprite, KeyCode[] keys) {
		super(sprite, keys);
	}

	@Override
	public void prepare() {
	}

	@Override
	protected void doAction() {
	}

	@Override
	public void stop() {
	}

}
