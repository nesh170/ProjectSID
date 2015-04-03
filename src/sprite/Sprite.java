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
	private List<Action> myActionList;
	private List<Component> myComponentList;
	
	private boolean isActive;
	private String myTag;

	private Transform myTransform;
	private String myImagePath;

	
	// Getters & Setters
	public List<Action> actionList() {
		return Collections.unmodifiableList(this.myActionList);
	}
	
	public List<Component> componentList() {
	    return Collections.unmodifiableList(this.myComponentList);
	}
	
	/*
	 * Sprites that are active are located within the camera view
	 */
	public boolean isActive() {
		return this.isActive;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public String tag() {
		return this.myTag;
	}
	
	public void setTag(String tag) {
		this.myTag = tag;
	}
	
	public Transform transform() {
		return this.myTransform;
	}

	public Dimension2D dimensions() {
		return myTransform.getDimensions();
	}
	
	public String path() {
	        return myImagePath;
	}
	
	
	// Constructor & Helpers
	public Sprite() {
		this(POINT2D.DEFAULT_POSITION, POINT2D.DEFAULT_ROTATION, DIMENSION2D.DEFAULT_DIMENSIONS);
	}
	
	public Sprite(Point2D coordinate) {
		this(coordinate, POINT2D.DEFAULT_ROTATION, DIMENSION2D.DEFAULT_DIMENSIONS);
	}
	
	public Sprite(Point2D coordinate, Point2D rotate) {
		this(coordinate, rotate, DIMENSION2D.DEFAULT_DIMENSIONS);
	}
	
	public Sprite (Point2D coordinate, Point2D rotate, Dimension2D dimension){
		this.isActive = true;
		this.myTransform = new Transform(coordinate, rotate, dimension);
	}
	
	
	// All other instance methods
	/**
	 * Apply 'initialize' method of
	 * each behavior in list of components, actions
	 * that will be within every sprite
	 * (at beginning of scene)
	 */

	public void prepareAllComponents(){
		myComponentList.stream().forEach(com -> com.prepare());
	}
	
	public void prepareAllActions(){
		myActionList.stream().forEach(action -> action.prepare());
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
			myComponentList.stream().forEach(updateCon);
			
		}
		
	}
	
	/**
	 * 
	 */
	public void addComponent(Component componentToAdd){
		
		myComponentList.add(componentToAdd);
		
	}
	
	/**
	 * gets Behavior attached to this sprite
	 * of a specific type (there should be one
	 * behavior of each type for each sprite)
	 * @throws ClassNotFoundException 
	 * 
	 */
	public Component getComponentOfType(String componentClassName) {
		
		for(Component component: myComponentList) {
			
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
		
		for(Action action: myActionList) {
			
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
	
	
}
