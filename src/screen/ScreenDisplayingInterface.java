package screen;

import screen.manager.Manager;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
