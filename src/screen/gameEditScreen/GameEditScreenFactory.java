package screen.gameEditScreen;

import game.Game;

/**
 * Factory used to create GameEditScreens. Allows for extensibility
 * and abstraction of Screens.
 * @author Michael
 *
 */
public class GameEditScreenFactory {
	
	private double width;
	private double height;
	
	public GameEditScreenFactory(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public GameEditScreen createGameEditScreen(Game game, GameEditScreenController gameEditScreenController) {
		return new GameEditScreen(game, gameEditScreenController, width, height);
	}
}