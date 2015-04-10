package screen;

import game.Game;
import javafx.scene.control.Tab;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;
import resources.constants.STRING;
import screen.gameEditScreen.GameEditScreen;
import screen.gameEditScreen.GameEditScreenController;
import screen.gamePlayScreen.GamePlayScreen;
import screen.gamePlayScreen.GamePlayScreenController;
import screen.levelEditScreen.LevelEditScreen;
import screen.levelEditScreen.LevelEditScreenController;
import screen.mainMenu.MainMenuScreen;
import screen.mainMenu.MainMenuScreenController;
import screen.splashEditScreen.SplashEditScreen;
import screen.splashEditScreen.SplashEditScreenController;
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
			return new GameEditScreen(gameEditScreenController, width, height);
		}
		
	}
	
	public Screen createSplashEditScreen(SplashScreen splashScreen, 
			SplashEditScreenController splashEditScreenManager) {

		return new SplashEditScreen(splashEditScreenManager, width, height, splashScreen);
		
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

	
	public Screen createGamePlayScreen(Level level,
			GamePlayScreenController gamePlayScreenController) {
		
		return new GamePlayScreen(gamePlayScreenController, width, height, level);
		
	}

}
