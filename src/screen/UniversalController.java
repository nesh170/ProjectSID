package screen;

import java.io.File;


public interface UniversalController {
	
	public void handleError(String message);
	
	public void handleSound(File soundFile);
	
}
