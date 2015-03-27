package gameEngine;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Contains all information, methods for
 * each scene/level.
 *
 */
public class Level {
	
	private int width;
	private int height;
	
	SpriteExample playerSprite;
	
	List<SpriteExample> sprites;
	List<SpriteExample> boundaries;
	List<SpriteExample> projectiles;
	
	// Getters & Setters
	public List<SpriteExample> boundaries() {
		return this.boundaries;
	}
	
	// Constructor & Helpers
	public Level() {
		
		initializeAllSprites();
		
	}
	
	public void initializeAllSprites(){
		
		sprites = new ArrayList<SpriteExample>();
		
	}
	
	// All Other Instance Methods
	public void update(){
		
	}
	
	
	
}	
