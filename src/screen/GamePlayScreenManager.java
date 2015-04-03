package screen;

import screen.gamePlayScreen.GamePlayScreenController;


// Inner class for handling GamePlayScreenController methods
class GamePlayScreenManager extends UniversalManager implements GamePlayScreenController {

	@Override
	public void returnToMainMenuScreen() {
		throw new IllegalStateException("unimplemented returnToMainMenuScreen in GamePlayScreenController");
	}

	@Override
	public void loadSplashEditScreen() {
		throw new IllegalStateException("unimplemented loadSplashEditScreen in GamePlayScreenController");
	}

	@Override
	public void loadLevelEditScreen() {
		throw new IllegalStateException("unimplemented loadLevelEditScreen in GamePlayScreenController");
	}
	
}