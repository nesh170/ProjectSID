package gameEngine.actions;

import sprite.Sprite;
import gameEngine.Action;

/**
 * FrameActions are actions that occur every frame, regardless of a collision or a button pressed
 *
 */

public abstract class FrameAction extends Action {

	public FrameAction(Sprite sprite) {
		super(sprite);
	}

}
