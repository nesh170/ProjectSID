package levelPlatform.levelPlatformView;

import gameEngine.Collision;
import resources.constants.DOUBLE;
import sprite.Sprite;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.TilePane;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;

/**
 * 
 * This class represents the frontend of the levels. It also Handles the collisions
 * 
 * @author Ruslan
 * @author AuthEnvTeam (please add yourselves) :)
 *
 */
@Deprecated
public class LevelPlatformView extends ScrollPane {

	// Instance Variables
	// Containing TilePane of SIDPixel
	private TilePane sidPixelsTilePane;
	// Edit
	private EditMode editMode;
	// Level
	private Level level;
	// Layouts
	private double lengthSidePixel;
	// Playing
	private Collision collisionHandler;    


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

	public void setLengthSidePixel(double lengthSidePixel) {
		this.lengthSidePixel = lengthSidePixel;
	}

	public double getLengthSidePixel() {
		return this.lengthSidePixel;
	}

	public void setCollisionHandler(){
		this.collisionHandler = new Collision(level.collisionTable());
	}

	
	// Constructor & Helpers
	/**
	 * Infers lengthSidePixel from Default in DOUBLE
	 * 
	 * @param level
	 */
	public LevelPlatformView(Level level, EditMode editMode, double realPixelWidth, double realPixelHeight) {

		this(level, editMode, DOUBLE.DEFAULT_LENGTH_SIDE_PIXEL, realPixelWidth, realPixelHeight);

	}

	/**
	 * 
	 * @param (Level) level
	 * @param (double) lengthSidePixel - size of each of our pixels in real java pixels
	 */
	public LevelPlatformView(Level level, EditMode editMode, double lengthSidePixel, double realPixelWidth, double realPixelHeight) {

		setLevel(level);
		setEditMode(editMode);
		setLengthSidePixel(lengthSidePixel);
		setCollisionHandler();
		
		configureTilePane(realPixelWidth, realPixelHeight);

		if (level != null) {
			renderLevel();
		}

	}
	
	/**
	 * instantiate and add the tile pane to this.
	 *  
	 * get the desired width & height, make that many SID pixels.
	 * get the size of each tile's side via lengthSidePixel
	 *
	 * **Important** - Orientation.Vertical
	 * A horizontal tilepane (the default) will tile nodes in rows, wrapping at the tilepane's width. 
	 * A vertical tilepane will tile nodes in columns, wrapping at the tilepane's height.
	 *
	 * also set tile alignment while at it
	 * 
	 * @author Ruslan
	 */
	private void configureTilePane(double realPixelWidthOfLevelPlatformView, double realPixelHeightOfLevelPlatformView) {
		
		sidPixelsTilePane = new TilePane(Orientation.VERTICAL);
		sidPixelsTilePane.setTileAlignment(Pos.CENTER);
		
		sidPixelsTilePane.setMaxHeight(300.0);
//		sidPixelsTilePane.setMaxHeight(realPixelHeightOfLevelPlatformView);
		
//		int levelWidth = level.width(), levelHeight = level.height();
		int levelWidth = 20, levelHeight = 10;

		
		
		sidPixelsTilePane.setVgap(1);
		sidPixelsTilePane.setHgap(1);
		
		this.sidPixelsTilePane.setPrefRows(levelHeight);
		
		for (int row = 0; row < levelHeight; row++) {
			
			for (int column = 0; column  < levelWidth; column++) {
//				this.sidPixelsTilePane.getChildren().add(new SIDPixel(column, row, lengthSidePixel));
				this.sidPixelsTilePane.getChildren().add(new SIDPixel(column, row, 30.0));
			}
			
		}
		
		this.setContent(sidPixelsTilePane);
		
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
		Image spriteImage;
		ImageView spriteImageView;
		//TODO: delete rectangle-making, restore SpriteImage part
		/*if(sprite.isActive()) {
    		Rectangle r = new Rectangle(sprite.transform().getPosX(), sprite.transform().getPosY(), 
    				sprite.transform().getWidth(), sprite.transform().getHeight());
        	spriteGroup.getChildren().add(r);
    	}*/
    			
        if (sprite.isActive()) {
            // TestCode
//            Rectangle player = new Rectangle(sprite.transform().getPosX(),sprite.transform().getPosY(),sprite.transform().getWidth(),sprite.transform().getHeight());
//            spriteGroup.getChildren().add(player);
        	
            spriteImage = sprite.spriteImage().getImageToDisplay(lengthSidePixel);
            spriteImageView = new ImageView(spriteImage);
                        
            //SIDPixelsToFXpixels.translate(spriteImageView, sprite.transform().getPosX(), sprite
                   // .transform().getPosY());
            spriteImageView.setX(sprite.transform().getPosX());
            spriteImageView.setY(sprite.transform().getPosY());
            if(spriteImage !=null){
            	spriteImageView.setFitWidth(sprite.transform().getWidth());
            	spriteImageView.setFitHeight(sprite.transform().getHeight());
            	spriteGroup.getChildren().add(spriteImageView);
            }
            else{
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