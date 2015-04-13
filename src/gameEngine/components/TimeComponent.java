package gameEngine.components;

import sprite.Sprite;
import gameEngine.Component;

public class TimeComponent extends Component {
	
	private double levelTime;
	private int displayTime;

	public TimeComponent(Sprite sprite, double levelTime) {
		super(sprite);
		this.levelTime = levelTime;
	}

	@Override
	public void prepare() { }

	@Override
	protected void update() {
		levelTime -= 1/60;
		displayTime = (int) Math.round(levelTime);
		if (levelTime == 0) {
			HealthComponent health = (HealthComponent) mySprite.getComponentOfType("HealthComponent");
			health.kill();
		}
	}
	
	public int getDisplayTime() {
		return displayTime;
	}

}
