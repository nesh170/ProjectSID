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
	public void addTabWithScreenWithStringIndentifier(Screen screen, String identifier);
	
	// JavaFX
	public Stage stage();
	public Scene scene();
	// Cursor
	public void setCursor(ImageCursor imageCursor);
	
	// Display invalid commands message
	public void displayError(String error);
	
}
