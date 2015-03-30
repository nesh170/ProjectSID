package screen.levelEditScreen;

import javafx.geometry.Point2D;
import javafx.scene.control.MenuBar;
import level.Level;
import screen.Screen;
import sprite.Sprite;

public class LevelEditScreen extends Screen {
	
	private Level level;

	public LevelEditScreen(double width, double height) {
		
		super(width, height);
		
	}
	
	public Level getCurrentLevel() {
		return level;
	}
	
	/*
	 * add a sprite to the level edit screen
	 */
	public void addSprite(Sprite sprite, Point2D location) {
		
	}

}
