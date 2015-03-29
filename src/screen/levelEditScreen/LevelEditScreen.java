package screen.levelEditScreen;

import gameEngine.Level;
import gameEngine.Sprite;
import javafx.geometry.Point2D;
import screen.Screen;

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
