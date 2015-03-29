package gameEngine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;

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
		
		Consumer<Sprite> initializeSpriteCon = spr -> spr.initializeAllBehaviors();
		doOnEachSpriteList(initializeSpriteCon);
		
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
		Consumer<Sprite> updateSpriteCon = spr -> spr.updateAllBehaviors();
		doOnEachSpriteList(updateSpriteCon);
		
	}
	
	public Sprite[] getSpritesWithTag(String tag){
		Sprite[] tagSprites = (Sprite[]) sprites.stream().filter(sprite -> sprite.getTag() == tag).toArray();
		return tagSprites;
	}
	
	public Sprite[] getAllSprites(){
		return (Sprite[]) sprites.toArray();
	}
	
	/**
	 * 
	 * @return a controlMap which might change depending on the behaviours for each level
	 */
	public Map<KeyCode,Behavior> getControlMap(){
	    Map<KeyCode,Behavior> controlMap = new HashMap<>();
	    //TODO loop through each behavior to get the controlMap 
            return controlMap;
	}
	
	public Group render(){
	    Group levelView = new Group();
	    Consumer<Sprite> renderSprite = spr -> levelView.getChildren().addAll(spr.render());
	    doOnEachSpriteList(renderSprite);
	    return levelView;
	}
	
	
	
}	
