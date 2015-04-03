package screen;

import javafx.stage.Stage;
import screen.splashEditScreen.SplashEditScreenController;

// Inner class for handling SplashEditScreenController methods
class SplashEditScreenManager extends Manager implements SplashEditScreenController {

	public SplashEditScreenManager(Stage stage) {
		super(stage);
	}
	
	@Override
	public void returnToGameEditScreen() {
		throw new IllegalStateException("unimplemented returnToGameEditScreen in SplashEditScreenController");
	}
	
}