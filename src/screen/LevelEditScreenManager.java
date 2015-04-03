package screen;

import javafx.stage.Stage;
import screen.levelEditScreen.LevelEditScreenController;

// Inner class for handling LevelEditScreenController methods
class LevelEditScreenManager extends Manager implements LevelEditScreenController {

	public LevelEditScreenManager(Stage stage) {
		super(stage);
	}
	
	@Override
	public void returnToGameEditScreen() {
		throw new IllegalStateException("unimplemented returnToGameEditScreen in LevelEditScreenController");
	}

	@Override
	public void loadSpriteEditScreen() {
		throw new IllegalStateException("unimplemented loadSpriteEditScreen in LevelEditScreenController");
	}
	
}