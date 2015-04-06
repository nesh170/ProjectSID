package screen.spriteEditScreen;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import screen.Screen;
import sprite.Sprite;

public class SpriteEditScreen extends Screen {
	
	private SpriteEditScreenController parent;

	public SpriteEditScreen(SpriteEditScreenController parent, double width, double height) {

		this(parent, width, height, null);

	}

	public SpriteEditScreen(SpriteEditScreenController parent, double width, double height, Sprite spriteToEdit) {

		super(width, height);

		configureParentScreenController(parent);
		
		if (spriteToEdit != null) {
			drawSpriteOnScreen(spriteToEdit);
		}
		
	}
	
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		Menu fileMenu = makeFileMenu();
		
	}
	
	private void configureParentScreenController(SpriteEditScreenController parent) {
		this.parent = parent;
	}
	
	// All other instance methods
	private void drawSpriteOnScreen(Sprite sprite) {
		//TODO implement
	}

}
