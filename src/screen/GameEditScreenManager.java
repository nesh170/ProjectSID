package screen;

import game.Game;
import level.Level;
import screen.gameEditScreen.GameEditScreenController;

class GameEditScreenManager extends UniversalManager implements GameEditScreenController {

	@Override
	public void returnToMainMenuScreen() {
		throw new IllegalStateException("unimplemented returnToMainMenuScreen in GameEditScreenController");
	}

	@Override
	public void loadSplashEditScreen(Game game) {
		throw new IllegalStateException("unimplemented loadSplashEditScreen in GameEditScreenController");
	}

	@Override
	public void loadLevelEditScreen(Level level) {
		throw new IllegalStateException("unimplemented loadLevelEditScreen in GameEditScreenController");
	}
	
}