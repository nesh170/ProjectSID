package screen;

import javafx.scene.control.Tab;
import screen.levelEditScreen.LevelEditScreenController;

// Inner class for handling LevelEditScreenController methods
public class LevelEditScreenManager extends UniversalManager implements LevelEditScreenController {

	@Override
	public void returnToGameEditScreen(Tab tab) {
		throw new IllegalStateException("unimplemented returnToGameEditScreen in LevelEditScreenController");
	}

	@Override
	public void loadSpriteEditScreen() {
		throw new IllegalStateException("unimplemented loadSpriteEditScreen in LevelEditScreenController");
	}

}