package screen;

import java.io.File;

import javafx.scene.control.Dialog;

public abstract class UniversalManager {
	
	public void handleError(String message) {
//		Dialog<String> dialog = new Dialog<>();
//		dialog.setContentText(message);
//		dialog.show();
		
	}
	
	public void handleSound(File soundFile) {
		// ToDo:
		throw new IllegalStateException("Unimplemented handleSound method");
		
	}
	
}
