package screen;

import javafx.stage.Stage;
import screen.spriteEditScreen.SpriteEditScreenController;

// Inner class for handling SpriteEditScreenController methods
class SpriteEditScreenManager extends Manager implements SpriteEditScreenController {
	
	public SpriteEditScreenManager(Stage stage) {
		super(stage);
	}
	
	@Override
	public void returnToSelectedLevel() {
		throw new IllegalStateException("unimplemented returnToSelectedLevel in SpriteEditScreenController");
	}
	
}