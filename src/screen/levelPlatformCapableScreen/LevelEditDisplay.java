package screen.levelPlatformCapableScreen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resources.constants.INT;
import sprite.Sprite;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import levelPlatform.level.Level;

/**
 * 
 * @author Leo
 *
 */


public class LevelEditDisplay extends ScrollPane {
	
	private Pane content;
	private int increaseLevelSize;
	private Map<Sprite,ImageView> spriteToImageMap;

	
	public LevelEditDisplay(int width, int height, List<Sprite> listOfSprites) {
		this();
		content.setMinWidth(width);
		content.setMinHeight(height);
		listOfSprites.forEach(sprite -> addSpriteToDisplay(sprite,sprite.spriteImage().getImageViewToDisplay()));
	}
	
	
	public LevelEditDisplay() {
		
		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
				
		content = new Pane();
		this.setContent(content);
		increaseLevelSize = INT.DEFAULT_INCREASE_LEVEL_SIZE;
		spriteToImageMap = new HashMap<>();
		
	}
	
	/*
	 * Sets the size of the content equal to the size of the scroll pane
	 * after resizing.
	 */
	public void setContentMinSize(Level level) {
		
		content.setMinSize(this.getWidth(), this.getHeight());
		level.configureWidthAndHeight((int) this.getWidth(), (int) this.getHeight());
		
	}
		
	/*
	 * Overrides default increaseLevelSize value
	 */
	public void setSizeToIncrease(int growth) {
		increaseLevelSize = growth;
	}
	
	/*
	 * Gets the number of pixels to increase the level by
	 */
	public int getSizeToIncrease() {
		return increaseLevelSize;
	}
	
	/*
	 * Adds width to content
	 */
	public void addWidthLeft() {
		content.getChildren().forEach(image -> moveRight(image));
		addWidthRight();
	}
	
	/*
	 * Adds width to content without moving all image representations
	 */
	public void addWidthRight() {
		content.setMinWidth(content.getMinWidth() + increaseLevelSize);
	}
	
	/*
	 * Adds height to content without moving all image representations
	 */
	public void addHeightDown() {
		content.setMinHeight(content.getMinHeight() + increaseLevelSize);
	}
	
	/*
	 * Adds height to content
	 */
	public void addHeightUp() {
		content.getChildren().forEach(image -> moveDown(image));
		addHeightDown();
	}
	
	/*
	 * Returns the ImageView for the parameter sprite
	 */
	public ImageView getImage(Sprite sprite) {
		return spriteToImageMap.get(sprite);
	}
	
	/*
	 * Sets a listener on mouse released to the content
	 */
	public void setListenerToContent(EventHandler<MouseEvent> m) {
		content.setOnMouseReleased(m);
	}
	
	/*
	 * Sets the cursor for the content
	 */
	public void setCursor(Image image) {
		content.setCursor(new ImageCursor(image));
	}
	
	private void moveRight(Node n) {
		n.setTranslateX(n.getTranslateX() + increaseLevelSize);
	}
	
	private void moveDown(Node n) {
		n.setTranslateY(n.getTranslateY() + increaseLevelSize);
	}
	
	/*
	 * Places an ImageView representation of the sprite in the content
	 */
	public void addSpriteToDisplay(Sprite sprite, ImageView image) {
		spriteToImageMap.put(sprite, image);
		image.setTranslateX(sprite.getX());
		image.setTranslateY(sprite.getY());
		content.getChildren().add(image);
	}
	
	public void removeSpriteFromDisplay(Sprite sprite, ImageView image) {
		spriteToImageMap.keySet().remove(sprite);
		content.getChildren().remove(image);
	}


}