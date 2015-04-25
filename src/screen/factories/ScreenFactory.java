package screen.factories;

import java.util.List;
import java.util.Set;

import game.Game;
import javafx.scene.control.Tab;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;
import resources.constants.STRING;
import screen.Screen;
import screen.controllers.CollisionTableScreenController;
import screen.controllers.GameEditScreenController;
import screen.controllers.GamePlayScreenController;
import screen.controllers.LevelEditScreenController;
import screen.controllers.MainMenuScreenController;
import screen.controllers.SplashEditScreenController;
import screen.controllers.SpriteEditScreenController;
import screen.screens.CollisionTableScreen;
import screen.screens.GameEditScreen;
import screen.screens.GamePlayScreen;
import screen.screens.LevelEditScreen;
import screen.screens.MainMenuScreen;
import screen.screens.SplashEditScreen;
import screen.screens.SpriteEditScreen;
import sprite.Sprite;

/**
 * Factory used to create all types of screens. Allows for extensibility
 * and abstraction of Screens.
 * @author Michael
 *
 */
public class ScreenFactory {
	
	double width;
	double height;
	
	public ScreenFactory(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public Screen createMainMenuScreen(MainMenuScreenController mainMenuScreenController) {

		return new MainMenuScreen(mainMenuScreenController, width, height);

	}
	
	public Screen createGameEditScreen(Game game, 
			GameEditScreenController gameEditScreenController) {
		if (game != null) {
			return new GameEditScreen(game, gameEditScreenController, width, height);
		}
		else {
			Game newGame = new Game(STRING.DEFAULT_GAME_NAME);
			return new GameEditScreen(newGame, gameEditScreenController, width, height);
		}
		
	}
	
	public Screen createSplashEditScreen(SplashScreen splashScreen, 
			SplashEditScreenController splashEditScreenManager) {

		return new SplashEditScreen(splashEditScreenManager, width, height, splashScreen);
		
	}
	
	/**
	 * @author Anika
	 * @param splashScreen
	 * @param splashEditScreenManager
	 * @return Screen
	 */
	public Screen createCollisionTableScreen(Set<String> spriteTags, 
			CollisionTableScreenController collisionTableScreenController) {

		return new CollisionTableScreen(collisionTableScreenController, width, height, spriteTags);
		
	}
	
	
	public Screen createLevelEditScreen(Level level, 
			LevelEditScreenController levelEditScreenController) {

		return new LevelEditScreen(levelEditScreenController, width, height, level);
	
	}
	
	public Screen createSpriteEditScreen(Tab levelEditTab, Sprite sprite,
			SpriteEditScreenController spriteEditScreenController) {
		
		return new SpriteEditScreen(spriteEditScreenController,levelEditTab,
				width, height, sprite);
		
	}

	
	public Screen createGamePlayScreen(Game game,
			GamePlayScreenController gamePlayScreenController) {
		
		return new GamePlayScreen(gamePlayScreenController, game, width, height);
		
	}

	public Screen createGamePlayScreen(GamePlayScreenController gamePlayScreenController) {
		return new GamePlayScreen(gamePlayScreenController, width, height);
	}

}
