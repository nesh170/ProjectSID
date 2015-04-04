package levelPlatform;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import sprite.Sprite;

public class LevelPlatform {

	// Static Variables 
	
	
	// Instance variables
	private int width;
	private int height;
	
	private List<Sprite> sprites;
	private List<Sprite> boundaries;
	private List<Sprite> projectiles;
	
	
	// Getters & Setters
	public int width() {
		return this.width;
	}
	
	public int height() {
		return this.height;
	}
	
	/**
	 * @return a *modifiable* list (on purpose for being able to edit)
	 */
	public List<Sprite> sprites() {
		return this.sprites;
	}
	
	/**
	 * @param tag
	 * @return Sprite[] of Sprite(s) that match tag
	 */
	public Sprite[] getSpritesWithTag(String tag){
		
		Sprite[] tagSprites = (Sprite[]) sprites.stream().filter(sprite -> sprite.tag() == tag).toArray();
		return tagSprites;
		
	}
	
	/**
	 * @return a *modifiable* list (on purpose for being able to edit)
	 */
	public List<Sprite> boundaries() {
		return this.boundaries;
	}
	
	/**
	 * @return a *modifiable* list (on purpose for being able to edit)
	 */
	public List<Sprite> projectiles() {
		return this.projectiles;
	}
	
	
	// Constructor & Helpers
	// All other instance methods
	// Public
	
	// Private
	
	
}
