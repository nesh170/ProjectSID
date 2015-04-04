package levelPlatform.level;
import gameEngine.Action;
import gameEngine.Collision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import sprite.Sprite;
import javafx.scene.input.KeyCode;
import levelPlatform.LevelPlatform;

/**
 * 
 * Contains all information, methods for
 * each scene/level.
 *
 */
public class Level {
	
	private int width;
	private int height;
	
	Sprite myPlayerSprite;
	Collision myCollisionDetector;
	
	List<Sprite> sprites;
	List<Sprite> boundaries = new ArrayList<Sprite>();
	List<Sprite> projectiles = new ArrayList<Sprite>();
	
	// Getters & Setters
	public List<Sprite> boundaries() {
		return this.boundaries;
	}
	
	// Constructor & Helpers
	public Level() {
		// TODO
	}
	
	public Level(List<Sprite> spriteList, Sprite playerSprite) {
	        myPlayerSprite = playerSprite;
		sprites = spriteList;
		prepareAllSprites();
	}

	
	public void prepareAllSprites(){
		doOnEachSpriteList(sprite -> sprite.prepareAllActions());
		doOnEachSpriteList(sprite -> sprite.prepareAllComponents());
	}

    private void doOnEachSpriteList (Consumer<Sprite> spriteConsumer) {
        boundaries.stream().forEach(spriteConsumer);
        projectiles.stream().forEach(spriteConsumer);
        sprites.stream().forEach(spriteConsumer);
    }
	
	// All Other Instance Methods
	public void update(){
		//ordering an issue here
		
		//sprites updating
		sprites.stream().forEach(spr -> checkCollision(myPlayerSprite,spr));
		doOnEachSpriteList(sprite -> sprite.updateSprite());
		
	}
	
	public Sprite[] getSpritesWithTag(String tag){
		Sprite[] tagSprites = (Sprite[]) sprites.stream().filter(sprite -> sprite.tag() == tag).toArray();
		return tagSprites;
	}
	
	public List<Sprite> getAllSprites(){
		return Collections.unmodifiableList(sprites);
	}
	
	/**
	 * 
	 * @return a controlMap which might change depending on the behaviours for each level
	 */
	public Map<KeyCode,Action> getControlMap(){
	    Map<KeyCode,Action> controlMap = new HashMap<>();
	    myPlayerSprite.actionList().forEach(action -> action.setUpKey(controlMap));
            return controlMap;
	}
	
//	/**
//	 * Calls the render method from each sprite and puts it within a group
//	 * @return
//	 */
//	public Group render(){
//	    
//	}
	
	/**
	 * Checks for collision between the sprite and if there is any intersection between the shapes, 
	 * send it to the collision class to figure the collision happened from which side and decrement
	 * the correct behavior
	 * @param sprite1
	 * @param sprite2
	 */
	private void checkCollision(Sprite sprite1,Sprite sprite2){
	    //TODO figure out collisions
	}
	
}	
