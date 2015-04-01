package sprite;

import gameEngine.Action;
import gameEngine.Component;
import gameEngine.Transform;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.*;

import resources.constants.DIMENSION2D;
import resources.constants.POINT2D;
import sprite.spriteImage.SpriteImage;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Represents single object within game
 * 
 * Contains data and methods for handling
 * functionality that is tied to each sprite
 *
 */
public class Sprite {
	
	// Static Variables
	
	
	// Instance Variables
	private SpriteImage spriteImage;
	
	private List<Action> actionList;
	private List<Component> componentList;
	
	private boolean isActive;
	private String tag;

	private Transform transform;
	private String colorPath;

	
	// Getters & Setters
	public SpriteImage spriteImage() {
		return this.spriteImage;
	}
	
	public int width() {
		return spriteImage.width();
	}
	
	public int height() {
		return spriteImage.height();
	}
	
	public List<Action> actionList() {
		return Collections.unmodifiableList(this.actionList);
	}
	
	public List<Component> componentList() {
	    return Collections.unmodifiableList(this.componentList);
	}
	
	/*
	 * Sprites that are active are located within the camera view
	 * 
	 * TODO: is this what is meant by active sprites? thanks
	 */
	public boolean isActive() {
		return this.isActive;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public String tag() {
		return this.tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public Transform transform() {
		return this.transform;
	}

	public Dimension2D dimensions() {
		return transform.getDimensions();
	}
	
	
	// Constructor & Helpers
	public Sprite() {
		this(POINT2D.DEFAULT_POSITION, POINT2D.DEFAULT_ROTATION, DIMENSION2D.DEFAULT_DIMENSIONS);
		// Just curious/confused: TODO: when would this be called? If we have dragging and dropping onto the level edit screen,
		// the second or third constructors would be called.
	
	}
	
	public Sprite(Point2D coordinate) {
		this(coordinate, POINT2D.DEFAULT_ROTATION, DIMENSION2D.DEFAULT_DIMENSIONS);
	}
	
	public Sprite(Point2D coordinate, Point2D rotate) {
		this(coordinate, rotate, DIMENSION2D.DEFAULT_DIMENSIONS);
	}
	
	public Sprite (Point2D coordinate, Point2D rotate, Dimension2D dimension){
		
		this.isActive = true;
		this.transform = new Transform(coordinate, rotate, dimension);
		this.spriteImage = new SpriteImage();
		
	}
	
	
	// All other instance methods
	/**
	 * Apply 'initialize' method of
	 * each behavior in list of components, actions
	 * that will be within every sprite
	 * (at beginning of scene)
	 */

	public void prepareAllComponents(){
		componentList.stream().forEach(com -> com.prepare());
	}
	
	public void prepareAllActions(){
		actionList.stream().forEach(action -> action.prepare());
	}
	
	/**
	 * Apply 'update' method of each behavior
	 * in list of behaviors
	 * every frame
	 * 
	 * Also contains logic for update-ordering
	 * (deciding what shoud happen first, etc
	 */
	public void updateAllComponents(){
		
		if(isActive) {
			
			Consumer<Component> updateCon = com -> com.updateIfEnabled();
			componentList.stream().forEach(updateCon);
			
		}
		
	}
	
	/**
	 * 
	 */
	public void addComponent(Component componentToAdd){
		
		componentList.add(componentToAdd);
		
	}
	
	/**
	 * gets Behavior attached to this sprite
	 * of a specific type (there should be one
	 * behavior of each type for each sprite)
	 * @throws ClassNotFoundException 
	 * 
	 */
	public Component getComponentOfType(String componentClassName) {
		
		for(Component component: componentList) {
			
			try {
				
				if(component.getClass() == Class.forName(componentClassName)) {
					return component;	
				}
				
			} catch (ClassNotFoundException e) {
				return null;	
			}
			
		}
		
		return null;
		
	}
	
	public Action getActionOfType(String actionClassName) {
		
		for(Action action: actionList) {
			
			try {
				if(action.getClass() == Class.forName(actionClassName)) {
					return action;	
				}
			} catch (ClassNotFoundException e) {
				return null;		
			}
			
		}
		
		return null;
		
	}
		
	public Group render() {
		
	    Shape spriteView = new Rectangle(transform.getPosX(),transform.getPosY(),transform.getWidth(),transform.getHeight());
	    Paint spriteColor;
	    
	    try {
	        spriteColor = Color.web(colorPath);
	    }
	    
	    catch(IllegalArgumentException e) {
	    	
	        //spriteColor = new ImagePattern(resourceManager.getImage(myColorPath));
	        spriteColor = Color.BEIGE;
	        
	    }
	    
	    spriteView.setFill(spriteColor);
	    return new Group(spriteView);
	    
	}
	
}
