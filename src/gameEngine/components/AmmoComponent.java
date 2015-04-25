package gameEngine.components;

import java.util.List;

import sprite.Sprite;
import gameEngine.Component;

public class AmmoComponent extends Component {
	
	private int myAmmo;

	public AmmoComponent(Sprite sprite, List<Double> valueList) {
		super(sprite, valueList);
		myAmmo = Math.toIntExact(Math.round(valueList.get(0)));
	}
	
	public int getAmmoCount() {
		return myAmmo;
	}

	@Override
	public void prepare() {
		
	}

	@Override
	protected void update() {

	}

}
