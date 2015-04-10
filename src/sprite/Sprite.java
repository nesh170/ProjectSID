package sprite;

import gameEngine.Action;
import gameEngine.Component;
import gameEngine.Transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.*;

import resources.constants.DIMENSION2D;
import resources.constants.POINT2D;
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
	private List<Action> actionList = new ArrayList<Action>();
	private List<Component> componentList = new ArrayList<Component>();
	//TODO what is the x and y for?
	private double x, y;

	private List<Sprite> emissionList;
	
	private boolean isActive;
	private String name;
	private String tag;
	private String collisionTag;

	private Transform transform;
	private SpriteImage spriteImage;

	
	// Getters & Setters
	/**
	 * Use SID pixels here
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Use SID pixels here
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	public List<Action> actionList() {
		return Collections.unmodifiableList(this.actionList);
	}
	
	public List<Component> componentList() {
	    return Collections.unmodifiableList(this.componentList);
	}
	
	public List<Sprite> emissionList() {
		return Collections.unmodifiableList(this.emissionList); 
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String tag() {
		return this.tag;
	}
	
	public String collisonTag(){
		return this.collisionTag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setCollisionTag(String collisionTag){
		this.collisionTag = collisionTag;
	}
	
	public Transform transform() {
		return this.transform;
	}
	
	public void addToEmissionList(Sprite sprite){
		emissionList.add(sprite);
	}
	
	public SpriteImage spriteImage() {
		
	    //TODO talk to Ruslan about death
		
		if (spriteImage == null) {
			this.spriteImage = new SpriteImage();
		}
		
		return this.spriteImage;
	}
	
	public void setSpriteImage(SpriteImage spriteImage) {
		this.spriteImage = spriteImage;
	}
	
	public Dimension2D dimensions() {
		return transform.getDimensions();
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	// Constructor & Helpers
	public Sprite() {
		this(POINT2D.DEFAULT_POSITION, POINT2D.DEFAULT_ROTATION, DIMENSION2D.DEFAULT_DIMENSIONS);
	}
	
	public Sprite(Point2D coordinate) {
		this(coordinate, POINT2D.DEFAULT_ROTATION, DIMENSION2D.DEFAULT_DIMENSIONS);
	}
	
	public Sprite (Point2D coordinate, Point2D rotate, Dimension2D dimension){
		this.isActive = true;
		this.transform = new Transform(coordinate, rotate, dimension);
		emissionList = new ArrayList<>();
	}
	
	
	public Sprite (Sprite toCopy){
		this(toCopy.transform().getPositionPoint(), toCopy.transform().getRot(), toCopy.transform().getDimensions());
		this.addComponent(toCopy.getComponentOfType("VelocityComponent"));
		
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
	 * in list of physics and components
	 * every frame
	 * 
	 * Also contains logic for update-ordering
	 * (deciding what should happen first, etc
	 */
	public void updateSprite(){
		if(isActive) {
			componentList.stream().forEach(com -> com.updateIfEnabled());
			actionList.stream().forEach(action -> action.update());
			emissionList.stream().forEach(proj -> proj.updateSprite());
		}
	}
	
	/**
	 * 
	 */
	public void addComponent(Component componentToAdd){
		
		componentList.add(componentToAdd);
		
	}
	
	public void addAction(Action actionToAdd){
		actionList.add(actionToAdd);
	}
	
	/**
	 * gets Component attached to this sprite
	 * of a specific type (there should be one
	 * behavior of each type for each sprite)
	 * @throws ClassNotFoundException 
	 * 
	 */
	public Component getComponentOfType(String componentClassName) {
		
		for(Component component: componentList) {
			
			try {
				if(Class.forName("gameEngine.components." + componentClassName).isInstance(component)) {
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
				if(Class.forName("gameEngine.actions." + actionClassName).isInstance(action)) {
					return action;	
				}
			} catch (ClassNotFoundException e) {
				return null;		
			}
			
		}
		
		return null;
		
	}
	
	
}
