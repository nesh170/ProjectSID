package screen.screens;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.DataHandler;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
	private Background background;

	
	public LevelEditDisplay(int width, int height, List<Sprite> listOfSprites, String backgroundPath) {
		this();
		content.setMinWidth(width);
		content.setMinHeight(height);
		listOfSprites.forEach(sprite -> addSpriteToDisplay(sprite,new ImageView(DataHandler.fileToImage(new File(sprite.getImagePath())))));
		try {
			setBackground(DataHandler.fileToImage(new File(backgroundPath)));
		}
		catch (Exception e) {
			//Do Nothing
		}
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
		this.setHmin(0);
		this.setHmax(content.getWidth());
	}
	
	/*
	 * Adds height to content without moving all image representations
	 */
	public void addHeightDown() {
		content.setMinHeight(content.getMinHeight() + increaseLevelSize);
		this.setVmin(0);
		this.setVmax(content.getHeight());
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
	
	public void setBackground(Image image) {
		if (background != null) {
			content.getChildren().remove(background);
		}
		background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(0, 0, false, false, false, true)));
		content.setBackground(background);
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
		image.setTranslateX(sprite.getPosition().getX());
		image.setTranslateY(sprite.getPosition().getY());
		content.getChildren().add(image);
	}
	
	public void removeSpriteFromDisplay(Sprite sprite, ImageView image) {
		spriteToImageMap.keySet().remove(sprite);
		content.getChildren().remove(image);
	}


}
