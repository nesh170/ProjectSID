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
	
	Sprite playerSprite;
	
	List<Sprite> sprites;
	List<Sprite> boundaries;
	List<Sprite> projectiles;
	
	// Getters & Setters
	public List<Sprite> boundaries() {
		return this.boundaries;
	}
	
	// Constructor & Helpers
	public Level() {
		
		initializeAllSprites();
		
	}
	
	public void initializeAllSprites(){
		
		sprites = new ArrayList<Sprite>();
		
	}
	
	// All Other Instance Methods
	public void update(){
		
	}
	
	
	
}	
