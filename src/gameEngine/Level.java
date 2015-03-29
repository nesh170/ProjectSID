package gameEngine;
import java.util.List;
import java.util.function.Consumer;

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
		
		activateAllSprites();
		
	}
	
	public void activateAllSprites(){
		
		Consumer<Sprite> initializeSpriteCon = spr -> spr.activateAllBehaviors();
		boundaries.stream().forEach(initializeSpriteCon);
		projectiles.stream().forEach(initializeSpriteCon);
		sprites.stream().forEach(initializeSpriteCon);
		
	}
	
	// All Other Instance Methods
	public void update(){
		//ordering an issue here
		
		//sprites updating
		Consumer<Sprite> updateSpriteCon = spr -> spr.updateAllBehaviors();
		boundaries.stream().forEach(updateSpriteCon);
		projectiles.stream().forEach(updateSpriteCon);
		sprites.stream().forEach(updateSpriteCon);
		
	}
	
	public Sprite[] getSpritesWithTag(String tag){
		Sprite[] tagSprites = (Sprite[]) sprites.stream().filter(sprite -> sprite.getTag() == tag).toArray();
		return tagSprites;
	}
	
	public Sprite[] getAllSprites(){
		return (Sprite[]) sprites.toArray();
	}
	
	
	
}	
