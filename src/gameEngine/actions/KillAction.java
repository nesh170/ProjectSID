package gameEngine.actions;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.components.HealthComponent;

@ActionName(displayName = "Kill")
public class KillAction extends DoubleAction {

	public KillAction(Sprite sprite, Double val, KeyCode... keys) {
		super(sprite, val, keys);
	}

	@Override
	public void prepare() {
	}

	@Override
	public void doAction() {
		mySprite.setIsActive(false);
		//TODO:why does it reset transform
		//mySprite.transform().setPosition(Point2D.ZERO);
	}

	@Override
	public void stop() {
	}

}
