package screen.splashEditScreen;

import levelPlatform.splashScreen.SplashScreen;

/**
 * Factory used to create SplashEditScreens. Allows for extensibility
 * and abstraction of Screens.
 * @author Michael
 *
 */
public class SplashEditScreenFactory {
	
	private double width;
	private double height;
	private SplashScreen splashScreen;
	
	public SplashEditScreenFactory(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public void add() {
		// TODO Auto-generated method stub
	}
	
//	public SplashEditScreen createGameEditScreen(SplashEditScreenController splashEditScreenController) {
//		return new SplashEditScreen(splashEditScreenController, width, height);
//	}
	
}