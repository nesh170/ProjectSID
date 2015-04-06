package screen;

import javafx.scene.control.Tab;
import screen.spriteEditScreen.SpriteEditScreenController;
import sprite.Sprite;

// Inner class for handling SpriteEditScreenController methods
public class SpriteEditScreenManager extends UniversalManager implements SpriteEditScreenController {
	
	private ScreenController parent;
	
	public SpriteEditScreenManager(ScreenController screenController) {
		this.parent = screenController;
	}

	@Override
	public void returnToSelectedLevel(Tab tab, Sprite sprite) {
		// TODO Auto-generated method stub
	}
	
}