package screen.spriteEditScreen;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import screen.Screen;
import sprite.Sprite;

public class SpriteEditScreen extends Screen {
	
	private Tab currentLevelScreen;
	
	private SpriteEditScreenController parent;

	
	public SpriteEditScreen(SpriteEditScreenController parent, Sprite spriteToEdit, Tab levelScreen, double width, double height) {
		super(width, height);
		drawSpriteOnScreen(spriteToEdit);
		initialize(parent, levelScreen);
	}
	
	
	public SpriteEditScreen(SpriteEditScreenController parent, Tab levelScreen, double width, double height) {
		
		super(width, height);
		initialize(parent, levelScreen);
		
	}
	
	private void initialize(SpriteEditScreenController parent, Tab levelScreen) {
		this.parent = parent;
		this.currentLevelScreen = levelScreen;
	}
	
	private void drawSpriteOnScreen(Sprite sprite) {
		//TODO implement
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		Menu fileMenu = makeFileMenu();
	}

}
