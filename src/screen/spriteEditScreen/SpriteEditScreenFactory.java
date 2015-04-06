package screen.spriteEditScreen;

import javafx.scene.control.Tab;

/**
 * Factory used to create SpriteEditScreens. Allows for extensibility
 * and abstraction of Screens.
 * @author Michael
 *
 */
public class SpriteEditScreenFactory {
	
	private double width;
	private double height;
	
	public SpriteEditScreenFactory(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public SpriteEditScreen createGameEditScreen(SpriteEditScreenController spriteEditScreenController, Tab tab) {
		return new SpriteEditScreen(spriteEditScreenController, tab, width, height);
	}
	
}