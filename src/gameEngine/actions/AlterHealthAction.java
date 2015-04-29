package gameEngine.actions;

import sprite.Sprite;
import javafx.scene.input.KeyCode;
import gameEngine.Action;
import gameEngine.components.HealthComponent;

@ActionName(DisplayName = "AlterHealth")
public class AlterHealthAction extends DoubleAction {

	private HealthComponent myHealth;
	
	
	public AlterHealthAction(Sprite sprite, Double delta, KeyCode... keys){
		super(sprite, delta, keys);
	}
	@Override
	public void prepare() {
		myHealth = (HealthComponent) mySprite.getComponentOfType("HealthComponent"); //raw string, change asap

	}

	@Override
	public void doAction() {
		myHealth.increase(value);
	}

	@Override
	public void stop() {
	}

}
