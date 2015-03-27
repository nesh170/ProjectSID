package engine.level;

import java.util.List;

import engine.sprite.Sprite;

/**
 * 
 * Contains all information, methods for
 * each scene/level.
 *
 */
public abstract class Level {
	
	private int width;
	private int height;
	
	Sprite playerSprite;
	
	List<Sprite> allSprites;
	
	public void initializeAllSprites(){
		
	}
	
	public abstract void update();
	
}	
