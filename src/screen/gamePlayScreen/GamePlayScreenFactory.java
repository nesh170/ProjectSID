package screen.gamePlayScreen;

import game.Game;

/**
 * Factory used to create GamePlayScreens. Allows for extensibility
 * and abstraction of Screens.
 * @author Michael
 *
 */
public class GamePlayScreenFactory {
	
	private double width;
	private double height;
	
	public GamePlayScreenFactory(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public GamePlayScreen createGameEditScreen() {
		return new GamePlayScreen(width, height);
	}
	
	public GamePlayScreen createNewGameEditScreen(GamePlayScreenController gameEditScreenController) {
		return new GamePlayScreen(gameEditScreenController, width, height);
	}
	
}