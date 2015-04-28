package gameEngine.components;

import resources.constants.DOUBLE;
import sprite.Sprite;
import gameEngine.Component;

@HUDInterface(name = "Time")
public class TimeComponent extends Component {
	
	private double levelTime;
	private int displayTime;

	public TimeComponent(Sprite sprite, Double value) {
		super(sprite, value);
		try{
		    levelTime = value;
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
			LifeComponent life = (LifeComponent) mySprite.getComponentOfType("LifeComponent");
			life.kill();
		}
	}
	
	@HUDGetter
	public Double getDisplayTime() {
		return displayTime+0.0;
	}

}
