package gameEngine.actions;

import sprite.Sprite;
import javafx.scene.input.KeyCode;
import gameEngine.Action;
import gameEngine.components.HealthComponent;

public class AlterHealthAction extends Action {

	private Double alterAmount;
	private HealthComponent myHealth;
	
	public AlterHealthAction(Sprite sprite, Double delta){
		super(sprite);
		alterAmount = delta;
	}
	
	public AlterHealthAction(Sprite sprite, Double delta, KeyCode... keys){
		super(sprite, keys);
		alterAmount = delta;
	}
	@Override
	public void prepare() {
		myHealth = (HealthComponent) mySprite.getComponentOfType("HealthComponent"); //raw string, change asap

	}

	@Override
	public void execute() {
		myHealth.increase(alterAmount);
	}

	@Override
	public void stop() {
	}

}
