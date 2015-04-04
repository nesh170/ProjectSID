package levelPlatform;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import sprite.Sprite;

/**
 * Superclass of Level and SplashScreen, which contains functionality such as:
 * 	- width
 * 	- height
 * 	- Sprites
 * 	- boundaries
 * 	- projecticles
 * 
 * @author Ruslan
 *
 */
public class LevelPlatform {

	// Static Variables 
	
	
	// Instance variables
	private int width;
	private int height;
	
	private List<Sprite> sprites;
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
	public List<Sprite> projectiles() {
		return this.projectiles;
	}
	
	
	// Constructor & Helpers
	public LevelPlatform(int width, int height) {
		configureWidthAndHeight(width, height);
		instantiateLists();
		prepareAllSprites();
	}
	
	private void configureWidthAndHeight(int width, int height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	private void instantiateLists() {
		this.sprites = new ArrayList<Sprite>();
		this.projectiles = new ArrayList<Sprite>();
	}
	
	private void doOnEachSpriteList (Consumer<Sprite> spriteConsumer) {
	        projectiles.stream().forEach(spriteConsumer);
                sprites.stream().forEach(spriteConsumer);
        
        }
	
	
	// All other instance methods
	// Public
	public void prepareAllSprites() {
		doOnEachSpriteList(sprite -> sprite.prepareAllActions());
		doOnEachSpriteList(sprite -> sprite.prepareAllComponents());
	}
	
	public void update(){
	        doOnEachSpriteList(sprite -> sprite.updateSprite());
		
	}

	
}
