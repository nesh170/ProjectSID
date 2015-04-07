package screen.spriteEditScreen;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import screen.Screen;
import screen.ScreenController;
import screen.levelEditScreen.LevelEditScreen;
import sprite.Sprite;

public class SpriteEditScreen extends Screen {
	
	private SpriteEditScreenController controller;
	private LevelEditScreen levelEditScreen;

	public SpriteEditScreen(SpriteEditScreenController parent, LevelEditScreen levelEditScreen, double width, double height) {

		this(parent, levelEditScreen, width, height, null);

	}

	public SpriteEditScreen(SpriteEditScreenController parent, LevelEditScreen levelEditScreen, double width, double height, Sprite spriteToEdit) {

		super(width, height);
		
		this.levelEditScreen = levelEditScreen;
		this.controller = parent;

		if (spriteToEdit != null) {
			drawSpriteOnScreen(spriteToEdit);
		}
		
	}
		
	
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		Menu fileMenu = makeFileMenu(e -> saveSprite(),
									e -> exit(),
									e -> saveAndExit()
									);
		
	}
	
	
	private void saveSprite() {
		//TODO
	}
	
	private void exit() {
		//TODO
	}
	
	private void saveAndExit() {
		saveSprite();
		exit();
	}

	// All other instance methods
	private void drawSpriteOnScreen(Sprite sprite) {
		//TODO implement
	}

}
