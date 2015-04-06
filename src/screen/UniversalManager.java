package screen;

import java.io.File;


public abstract class UniversalManager {
	
	// Static Variables
	
	
	// Instance Variables
	
	
	// Getters & Setters
	
	
	
	// Constructor & Helpers
	
	/**
	 * Yes, this is a setter. It's only supposed to be used once, however.
	 * @param parent
	 */
	
	
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
	
	// Private
	
}
