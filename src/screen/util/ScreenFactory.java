package screen.util;

import java.util.List;

import game.Game;
import javafx.scene.control.Tab;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;
import resources.constants.STRING;
import screen.Screen;
import screen.collisionTableScreen.CollisionTableScreen;
import screen.collisionTableScreen.CollisionTableScreenController;
import screen.gameEditScreen.GameEditScreen;
import screen.gameEditScreen.GameEditScreenController;
import screen.levelPlatformCapableScreen.gamePlayScreen.GamePlayScreen;
import screen.levelPlatformCapableScreen.gamePlayScreen.GamePlayScreenController;
import screen.levelPlatformCapableScreen.levelEditScreen.LevelEditScreen;
import screen.levelPlatformCapableScreen.levelEditScreen.LevelEditScreenController;
import screen.levelPlatformCapableScreen.splashEditScreen.SplashEditScreen;
import screen.levelPlatformCapableScreen.splashEditScreen.SplashEditScreenController;
import screen.mainMenu.MainMenuScreen;
import screen.mainMenu.MainMenuScreenController;
import screen.spriteEditScreen.SpriteEditScreen;
import screen.spriteEditScreen.SpriteEditScreenController;
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
	public Screen createCollisionTableScreen(List<String> sprites, 
			CollisionTableScreenController collisionTableScreenController) {

		return new CollisionTableScreen(collisionTableScreenController, width, height, sprites);
		
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
