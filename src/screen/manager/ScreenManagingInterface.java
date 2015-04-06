package screen.manager;

import java.io.File;
import javafx.stage.Stage;


public interface ScreenManagingInterface {
	
	public void handleError(String message);
	
	public void handleSound(File soundFile);
	
	/**
	 * Close the application entirely
	 * 
	 * @param stage from (ScreenController)parent.stage()
	 */
	public void closeApplication(Stage stage);
	
}
