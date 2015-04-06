package screen;

import game.Game;
import screen.manager.ScreenManager;
import sprite.Sprite;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;

/**
 * 
 * @author Ruslan
 * 
 */

public interface ScreenDisplayingInterface {

	// Overall width, height
	public double width();
	public double height();
	
	// For instantiating new Screen instances
	public double newScreenWidth();
	public double newScreenHeight();
	public Tab addTabWithScreenWithStringIdentifier(Screen screen, String identifier);
	
	// New Screen
	/**
	 * Check inside the Screen constructors if their special objects are null, 
	 * NOT in ScreenController or in these methods
	 * 
	 * @return (optional) Tab to which the ScreenController added them to
	 */
	public Tab createMainMenuScreen();
	public Tab createGameEditScreen(Game game);
	public Tab createSplashEditScreen(SplashScreen splashScreen);
	public Tab createLevelEditScreen(Level level);
	public Tab createSpriteEditScreen(Sprite sprite);
	public Tab createGamePlayScreen(Level level);
	
	
	// JavaFX
	public Stage stage();
	public Scene scene();
	// Cursor
	public void setCursor(ImageCursor imageCursor);
	
	// Display invalid commands message
	public void displayError(String error);
	
}
