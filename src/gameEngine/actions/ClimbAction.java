package gameEngine.actions;

import sprite.Sprite;
import gameEngine.Action;

@ActionName(displayName = "Climb")
public class ClimbAction extends Action {

	public ClimbAction(Sprite sprite) {
		super(sprite);
	}

	@Override
	public void prepare() {}

	@Override
	public void doAction() {
		UpMotionAction up = (UpMotionAction) mySprite.getActionOfType("UpMotionAction");
		up.setClimbing(true);
	}

	@Override
	public void stop() {}

}
