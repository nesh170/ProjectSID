package screen;

import java.io.File;
import javafx.stage.Stage;


public interface UniversalController {
	
	public void handleError(String message);
	
	public void handleSound(File soundFile);
	
	/**
	 * Close the application entirely
	 * 
	 * @param stage from (ScreenController)parent.stage()
	 */
	public void closeApplication(Stage stage);
	
}
