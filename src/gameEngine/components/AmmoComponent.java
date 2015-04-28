package gameEngine.components;

import sprite.Sprite;
import gameEngine.Component;

@HUDInterface(name = "Ammo")
public class AmmoComponent extends Component {
	
	private int myAmmo;

	public AmmoComponent(Sprite sprite, Double value) {
		super(sprite, value);
		myAmmo = Math.toIntExact(Math.round(value));
	}
	
	@HUDGetter
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
