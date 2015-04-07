package screen.mainMenu;

/**
 * Factory used to create MainMenuScreens. Allows for extensibility
 * and abstraction of Screens.
 * @author Michael
 *
 */
public class MainMenuScreenFactory {
	
	private double width;
	private double height;
	
	public MainMenuScreenFactory(double width, double height) {
		this.width = width;
		this.height = height;
	}

//	public MainMenuScreen createNewGameEditScreen(
//			MainMenuScreenController mainMenuScreenController) {
//		return new MainMenuScreen(mainMenuScreenController, width, height);
//	}
	
}