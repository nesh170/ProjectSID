package gameEngine.components;

import java.util.List;

import sprite.Sprite;
import gameEngine.Component;

public class AmmoComponent extends Component {
	
	private int myAmmo;

	public AmmoComponent(Sprite sprite, Double value) {
		super(sprite, value);
		myAmmo = Math.toIntExact(Math.round(value));
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
