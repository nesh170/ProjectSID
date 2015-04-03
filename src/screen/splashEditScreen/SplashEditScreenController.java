package screen.splashEditScreen;

public interface SplashEditScreenController {
	
	/*
	 * Goes back to the previous game editing screen  
	 */
	void returnToGameEditScreen();

	void addStartButton();

	void addImage();

	void addText();

	void addAnimation();

	void saveSplashScreen();

	void trashSplashScreen();

	void backSplashScreen();
	
}
