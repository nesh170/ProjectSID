package gameEngine.actions;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.components.HealthComponent;

public class KillAction extends Action {

	public KillAction(Sprite sprite, Double val, KeyCode... keys) {
		super(sprite, val, keys);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//mySprite.setIsActive(false);
		mySprite.transform().setPosition(Point2D.ZERO);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
