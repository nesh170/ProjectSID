package gameEngine.actions;

import javafx.scene.input.KeyCode;
import gameEngine.Action;
import gameEngine.components.VelocityComponent;
import sprite.Sprite;

@ActionName(displayName = "NormalX")
public class NormalActionX extends Action{

	private VelocityComponent myVelocityComponent;
	
	public NormalActionX(Sprite sprite, Double value, KeyCode... keyCodes) {
		super(sprite);
	}

	@Override
	public void prepare() {
		myVelocityComponent = (VelocityComponent) mySprite.getComponentOfType("VelocityComponent");
	}

	@Override
	public void doAction() {
		myVelocityComponent.setVelocityX(0.0);
	}

	@Override
	public void stop() {
	}
	
}
