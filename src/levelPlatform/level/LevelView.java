package levelPlatform.level;

import java.util.List;
import java.util.stream.Collectors;

import gameEngine.Collision;
import resources.constants.DOUBLE;
import sprite.ImageManager;
import sprite.Sprite;
import util.DialogUtil;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * 
 * This class represents the frontend of the levels. It also Handles the collisions
 *
 */
public class LevelView extends ScrollPane {

	// Static Variables


	// Instance Variables
	// Edit
	private EditMode editMode;
	// Level
	private Level level;
	// Playing
	private Collision collisionHandler;    

	private ImageManager myImageManager = new ImageManager();

	// Getters & Setters
	public Level level() {
		return this.level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void setEditMode(EditMode editMode) {
		this.editMode = editMode;
	}

	public void setCollisionHandler(){
	        if(level==null){
	            return;
	        }
		this.collisionHandler = new Collision(level.collisionTable());
	}

	// Constructor & Helpers
	/**
	 * Infers lengthSidePixel from Default in DOUBLE
	 * 
	 * @param level
	 */
	public LevelView(Level level, EditMode editMode) {

		this(level, editMode, DOUBLE.DEFAULT_LENGTH_SIDE_PIXEL);

	}

	/**
	 * 
	 * @param (Level) level
	 * @param (double) lengthSidePixel - size of each of our pixels in real java pixels
	 */
	public LevelView(Level level, EditMode editMode, double lengthSidePixel) {

		setLevel(level);
		setEditMode(editMode);
		setCollisionHandler();

		if (level != null) {
			renderLevel();
		}
	}


	// All other instance variables
	/**
	 * Loops through all the avaliable sprite in the level to render each one.
	 * @return
	 */
	public Group renderLevel() {
		Group levelGroup = new Group();
		Node background = renderBackground(level);
		levelGroup.getChildren().add(background);
		List<Sprite> textSpriteList = level.sprites().stream().filter(sprite -> sprite.isText()).collect(Collectors.toList());
		textSpriteList.stream().forEach(textSprite -> levelGroup.getChildren().add(renderText(textSprite)));
		List<Sprite> imageSpriteList = level.sprites().stream().filter((sprite -> !sprite.isText())).collect(Collectors.toList());
		imageSpriteList.stream().filter(sprite -> sprite.isActive()).forEach(sprite -> levelGroup.getChildren().add(renderSprite(sprite)));
		return levelGroup;
	}

	private Node renderText (Sprite textSprite) {
	    Text text = new Text(textSprite.getName());
	    text.setTranslateX(textSprite.transform().getPosX());
	    text.setTranslateY(textSprite.transform().getPosY());
	    return text;
	}

    private Node renderBackground(Level aLevel) {
		Node background = null;
		try {
			ImageView backgroundImageView = new ImageView(myImageManager.getImageForString(aLevel.backgroundPath()));
			backgroundImageView.setFitWidth(aLevel.width());
			backgroundImageView.setFitHeight(aLevel.height());
			backgroundImageView.setX(0);
			backgroundImageView.setY(0);
			background = backgroundImageView;
		} catch (Exception e){
			 DialogUtil.displayMessage("Error Image Rendering", "Image Rendering");
		}
		return background;
	}

	/**
	 * Renders the sprite based on it's current sprite image. It also renders each of the children sprite
	 * @param sprite
	 * @return
	 */
	private Group renderSprite(Sprite sprite) {
//		System.out.println("Rendering a sprite at" + sprite.transform().getPosX() + ", height " + sprite.transform().getHeight());
		Group spriteGroup = new Group();	
			try{
				Image spriteImg = myImageManager.getImageForString(sprite.getImagePath());
				ImageView spriteImageView = null;
				if(spriteImg !=null){
					spriteImageView = new ImageView(spriteImg);
					if(sprite.facesLeft()) spriteImageView.setScaleX(-1.0);
					spriteImageView.setX(sprite.transform().getPosX());
					spriteImageView.setY(sprite.transform().getPosY());
					spriteImageView.setFitWidth(sprite.transform().getWidth());
					spriteImageView.setFitHeight(sprite.transform().getHeight());
					spriteGroup.getChildren().add(spriteImageView);
				}
				else {
					Rectangle r = new Rectangle(sprite.transform().getPosX(), sprite.transform().getPosY(), 
							sprite.transform().getWidth(), sprite.transform().getHeight());
					spriteGroup.getChildren().add(r);
				}   
				sprite.emissionList().stream()
				.forEach(emission -> spriteGroup.getChildren().add(renderSprite(emission)));
				if (editMode == EditMode.EDIT_MODE_ON) {
					configureMouseHandlersOnSpriteImageView(spriteImageView);
				}

			}
			catch (Exception e){
				 DialogUtil.displayMessage("Error Sprite Rendering", "Sprite Rendering");
			}
		return spriteGroup;

	}

	private void configureMouseHandlersOnSpriteImageView(ImageView spriteImageView) {

		spriteImageView.setOnMouseEntered(ee -> createDisplayEditOverlay());
		spriteImageView.setOnMouseExited(ee -> destroyDisplayEditOverlay());

	}

	private void createDisplayEditOverlay() {
		// TODO Auto-generated method stub
	}

	private void destroyDisplayEditOverlay() {
		// TODO Auto-generated method stub
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
		if(!sprite1.isActive() || !sprite2.isActive()) return;

		if(sprite1.equals(sprite2)){
			return;
		}

		Bounds boundsSprite1 = renderSprite(sprite1).getChildren().get(0).getBoundsInParent();
		Bounds boundsSprite2 = (renderSprite(sprite2).getChildren().get(0).getBoundsInParent());

		if(boundsSprite1.intersects(boundsSprite2)) {
			collisionHandler.handleCollide(sprite1, sprite2);
		}

	}
}
