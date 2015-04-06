package screen.spriteEditScreen;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import screen.Screen;
import screen.ScreenController;
import sprite.Sprite;

public class SpriteEditScreen extends Screen {
	
	private SpriteEditScreenController controller;

	public SpriteEditScreen(ScreenController parent, double width, double height) {

		this(parent, width, height, null);

	}

	public SpriteEditScreen(ScreenController parent, double width, double height, Sprite spriteToEdit) {

		super(parent, width, height);

		if (spriteToEdit != null) {
			drawSpriteOnScreen(spriteToEdit);
		}
		
	}
	
	@Override
	protected void createAppropriateControllerForParent(ScreenController parent) {
		this.controller = new SpriteEditScreenManager(parent);
	}
	
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		Menu fileMenu = makeFileMenu();
		
	}
	

	// All other instance methods
	private void drawSpriteOnScreen(Sprite sprite) {
		//TODO implement
	}

}
