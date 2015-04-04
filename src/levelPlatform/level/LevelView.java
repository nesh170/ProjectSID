package levelPlatform.level;

import gameEngine.Collision;
import gameEngine.Transform;
import sprite.Sprite;
import sprite.SpriteImage;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * 
 * This class represents the frontend of the levels. It also Handles the collisions
 *
 */
public class LevelView {
    
    private Level myLevel;
    private Collision myCollisionHandler;
    private SpriteImage mySpriteImageManager;
    
    public LevelView(Level level) {
    	
        myLevel=level;
        
    }
    
    public void setLevel(Level levelToSet) {
    	
        myLevel=levelToSet;
        //TODO clear the rest of the instance
        
    }
    
    /**
     * Loops through all the avaliable sprite in the level to render each one.
     * @return
     */
    public Group renderLevel() {
    	
        Group levelGroup = new Group();
        myLevel.sprites().stream().forEach(sprite -> levelGroup.getChildren().add(renderSprite(sprite)));
        return levelGroup;
        
    }
    
    private Group renderSprite(Sprite sprite) {
    	
    	Group spriteGroup = new Group();
    	Transform transform = sprite.transform();
        Rectangle spriteNode = new Rectangle(transform.getPosX(),transform.getPosY(),transform.getWidth(),transform.getHeight());
        Paint spriteColor;
        
        try {
            spriteColor = Color.web(sprite.path());
        } 
        
        catch(IllegalArgumentException e) {
        	
            //TODO add Ruslan's sprite image methodology
            //spriteColor = new ImagePattern(resourceManager.getImage(myColorPath));
            spriteColor = Color.BEIGE;
            
        }
        
        spriteNode.setFill(spriteColor);
        spriteGroup.getChildren().add(spriteNode);
        sprite.emissionList().stream().forEach(emission -> spriteGroup.getChildren().add(renderSprite(emission)));
        
        return spriteGroup;
        
    }
    
    /**
     * Checks for collision with each node
     */
    public void updateCollisions(){
    	
        for(Sprite sprite:myLevel.sprites()){
        	myLevel.sprites().stream().forEach(sprite2 -> handleCollisions(sprite,sprite2));
        }
        
    }
    
    /**
     * handles the collision initially using JavaFX implementation and if there is a collision, pass it to the collision handler 
     * to deal with the outcome of the collision.
     * @param sprite1
     * @param sprite2
     */
    private void handleCollisions(Sprite sprite1, Sprite sprite2){
    	
        if(sprite1.equals(sprite2)){
            return;
        }
        
        Bounds boundsSprite1 = renderSprite(sprite1).getChildren().get(0).getBoundsInParent();
        Bounds boundsSprite2 = (renderSprite(sprite2).getChildren().get(0).getBoundsInParent());
        
        if(boundsSprite1.intersects(boundsSprite2)) {
        	
            myCollisionHandler.handleCollide(sprite1, sprite2);
            //TODO should we just act on sprite 1 or both sprite1 and sprite 2
            
        }
        
    }
    
}
