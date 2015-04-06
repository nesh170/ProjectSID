package screen.levelEditScreen;

import javafx.scene.control.Tab;

/**
 * Factory used to create LevelEditScreens. Allows for extensibility
 * and abstraction of Screens.
 * @author Michael
 *
 */
public class LevelEditScreenFactory {
	
	private double width;
	private double height;
	
	public LevelEditScreenFactory(double width, double height) {
		this.width = width;
		this.height = height;
	}

	
	public LevelEditScreen createNewGameEditScreen(
			LevelEditScreenController LevelEditScreenController,Tab tab) {
		return new LevelEditScreen(LevelEditScreenController, tab, width, height);
	}
	
}