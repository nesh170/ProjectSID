package screen.manager;

import java.io.File;

import screen.ScreenController;
import screen.ScreenManagingInterface;
import javafx.stage.Stage;


public abstract class Manager implements ScreenManagingInterface {
	
	// Static Variables
	
	
	// Instance Variables
	private ScreenController parent;
	
	
	// Getters & Setters
	protected ScreenController parent() {
		return this.parent;
	}
	
	
	
	// Constructor & Helpers
	public Manager(ScreenController parent) {
		
		configureParent(parent);
		
	}
	
	/**
	 * Yes, this is a setter. It's only supposed to be used once, however.
	 * @param parent
	 */
	private void configureParent(ScreenController parent) {
		this.parent = parent;
	}
	
	
	// All other instance methods
	// Public
	public void handleError(String message) {
//		Dialog<String> dialog = new Dialog<>();
//		dialog.setContentText(message);
//		dialog.show();
		
	}
	
	public void handleSound(File soundFile) {
		// ToDo:
		throw new IllegalStateException("Unimplemented handleSound method");
		
	}

	@Override
	public void closeApplication(Stage stage) {
		stage.close();
	}
	
	// Private
	
}
