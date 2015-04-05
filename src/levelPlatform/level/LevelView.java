package levelPlatform.level;

import gameEngine.Collision;
import gameEngine.Transform;
import resources.constants.DOUBLE;
import sprite.Sprite;
import utils.IntArray2DToImageConverter;
import utils.SIDPixelsToFXpixels;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * 
 * This class represents the frontend of the levels. It also Handles the collisions
 *
 */
public class LevelView {
    
	// Static Variables

	
	// Instance Variables
    private Level level;
    private Collision collisionHandler;
    private double lengthSidePixel;
    
    
    // Getters & Setters
    public Level level() {
    	return this.level;
    }
    
    public void setLevel(Level level) {
    	this.level = level;
    }
    
    public void setLengthSidePixel(double lengthSidePixel) {
    	this.lengthSidePixel = lengthSidePixel;
    }
    
    
    // Constructor & Helpers
    /**
     * Infers lengthSidePixel from Default in DOUBLE
     * 
     * @param level
     */
    public LevelView(Level level) {
    	
    	this(level, DOUBLE.DEFAULT_LENGTH_SIDE_PIXEL);
        
    }
    
    /**
     * 
     * @param (Level) level
     * @param (double) lengthSidePixel - size of each of our pixels in real java pixels
     */
    public LevelView(Level level, double lengthSidePixel) {
    	
    	setLengthSidePixel(lengthSidePixel);
    	setLevel(level);
        renderLevel();
    	
    }
    
    
    // All other instance variables
    /**
     * Loops through all the avaliable sprite in the level to render each one.
     * @return
     */
    public Group renderLevel() {
    	
        Group levelGroup = new Group();
        level.sprites().stream().forEach(sprite -> levelGroup.getChildren().add(renderSprite(sprite)));
        return levelGroup;
        
    }
    
    /**
     * Renders the sprite based on it's current sprite image. It also renders each of the children sprite
     * @param sprite
     * @return
     */
    private Group renderSprite(Sprite sprite) {
    	
    	Group spriteGroup = new Group();
    	
    	Image spriteImage = sprite.spriteImage().getImageToDisplay(lengthSidePixel);
    	ImageView spriteImageView = new ImageView(spriteImage);
    	SIDPixelsToFXpixels.translate(spriteImageView, sprite.transform().getPosX(), sprite.transform().getPosY());
        spriteGroup.getChildren().add(spriteImageView);
        sprite.emissionList().stream().forEach(emission -> spriteGroup.getChildren().add(renderSprite(emission)));
        
        return spriteGroup;
        
    }
    
    /**
     * Checks for collision with each node
     */
    public void updateCollisions() {
    	
        for(Sprite sprite : level.sprites()) {
        	level.sprites().stream().forEach(sprite2 -> handleCollisions(sprite, sprite2));
        }
        
    }
    
    /**
     * Checks for collision between the sprite and if there is any intersection between the shapes, 
     * send it to the collision class to figure the collision happened from which side and decrement
     * the correct behavior
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
            collisionHandler.handleCollide(sprite1, sprite2);
            //TODO should we just act on sprite 1 or both sprite1 and sprite 2
        }
        
    }
	
}
