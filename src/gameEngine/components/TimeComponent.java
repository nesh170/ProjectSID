package gameEngine.components;

import java.util.List;

import resources.constants.DOUBLE;
import sprite.Sprite;
import gameEngine.Component;

public class TimeComponent extends Component {
	
	private double levelTime;
	private int displayTime;

	public TimeComponent(Sprite sprite, List<Double> valueList) {
		super(sprite, valueList);
		try{
		    levelTime = myValueList.get(0);
		}
		catch(Exception e){
		    levelTime = DOUBLE.DEFAULT_TIME_LIMIT;
		}
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