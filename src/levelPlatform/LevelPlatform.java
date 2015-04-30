package levelPlatform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.constants.STRING;
import sprite.Sprite;

/**
 * Superclass of Level and SplashScreen, which contains functionality such as:
 * 	- width
 * 	- height
 * 	- Sprites
 * 
 * @author Ruslan
 *
 */
public class LevelPlatform {

	// Static Variables 
	
	
	// Instance variables
	private int width;
	private int height;
	
	protected List<Sprite> sprites;
	protected List<Sprite> waitingSprites;
	
	
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
	
	public List<Sprite> waitingSprites() {
		return this.waitingSprites;
	}
	
	/**
	 * @param tag
	 * @return Sprite[] of Sprite(s) that match tag
	 */
	public Sprite[] getSpritesWithTag(String tag){
		Sprite[] tagSprites = (Sprite[]) sprites.stream().filter(sprite -> sprite.tag() == tag).toArray();
		return tagSprites;
	}
	
	
	// Constructor & Helpers
	public LevelPlatform(int width, int height) {
		configureWidthAndHeight(width, height);
		instantiateLists();
	}
	
	public void configureWidthAndHeight(int width, int height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	private void instantiateLists() {
		this.sprites = new ArrayList<Sprite>();
		this.waitingSprites = new ArrayList<Sprite>();
	}
 	/**
	 * get an ImageView representation for each level/splash to display on GameEditScreen.
	 * @return ImageView or Image
	 */
	public ImageView getLevelPlatformImageView(){
		//TODO: implement this method
		//temporary image to reprsent each level
		ImageView img = new ImageView(new Image(STRING.GAME_EDIT.LEVEL2IMAGE));
		return img;
	}
	/**
	 * Calls the prepare method in each sprite.
	 */
	public void prepareAllSprites() {
	        sprites.stream().forEach(sprite -> sprite.prepareAllActions());
	        sprites.stream().forEach(sprite -> sprite.prepareAllComponents());
	}
	
	public void update(){
	        sprites.stream().forEach(sprite -> sprite.updateSprite());
	        addEmissionListToSpriteList();
	}

    private void addEmissionListToSpriteList () {
        Set<Sprite> tempSpriteSet = new HashSet<>();
        sprites.stream().forEach(sprite-> sprite.emissionList().stream().forEach(emissionSprite -> tempSpriteSet.add(emissionSprite)));
        tempSpriteSet.stream().forEach(emissionSprite -> sprites.add(emissionSprite));
    }

	
}
