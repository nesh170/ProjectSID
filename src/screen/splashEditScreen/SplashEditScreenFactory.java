package screen.splashEditScreen;

/**
 * Factory used to create SplashEditScreens. Allows for extensibility
 * and abstraction of Screens.
 * @author Michael
 *
 */
public class SplashEditScreenFactory {
	
	private double width;
	private double height;
	
	public SplashEditScreenFactory(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
//	public SplashEditScreen createGameEditScreen(SplashEditScreenController splashEditScreenController) {
//		return new SplashEditScreen(splashEditScreenController, width, height);
//	}
	
}