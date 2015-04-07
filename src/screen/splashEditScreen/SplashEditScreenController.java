package screen.splashEditScreen;

import javafx.scene.control.Tab;

public interface SplashEditScreenController {
	
	/*
	 * Goes back to the previous game editing screen  
	 */
	void returnToGameEditScreen(Tab tab);

	void addStartButton();

	void addImage();

	void addText();

	void addAnimation();

	void saveSplashScreen();

	void trashSplashScreen();

	void backSplashScreen();
	
}
