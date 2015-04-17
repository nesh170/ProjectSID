package gameEngine.actions;

import sprite.Sprite;
import gameEngine.Action;

public class ClimbAction extends Action {

	public ClimbAction(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		UpMotionAction up = (UpMotionAction) mySprite.getActionOfType("UpMotionAction");
		up.setClimbing(true);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
