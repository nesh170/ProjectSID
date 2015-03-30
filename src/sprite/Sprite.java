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
	private List<Component> myComponentsList;
	private List<Action> myActionsList;
	
	
	// Instance Variables

	private boolean isActive;
	private String myTag;

	private Transform myTransform;
	private String myColorPath;

	
	// Getters & Setters
	
	
	
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
		
		setActive(true);
		myTransform = new Transform(coordinate, rotate, dimension);
		
	}
	
	
	// All other instance methods
	
	/**
	 * Apply 'initialize' method of
	 * each behavior in list of components, actions
	 * that will be within every sprite
	 * (at beginning of scene)
	 */

	public void prepareAllComponents(){
		myComponentsList.stream().forEach(com -> com.prepare());
	}
	
	public void prepareAllActions(){
		myActionsList.stream().forEach(action -> action.prepare());
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
		if(isActive){
			Consumer<Component> updateCon = com -> com.updateIfEnabled();
			myComponentsList.stream().forEach(updateCon);
		}
	}
	
	/**
	 * 
	 */
	public void addComponent(Component componentToAdd){
		myComponentsList.add(componentToAdd);
		
	}
	
	/**
	 * gets Behavior attached to this sprite
	 * of a specific type (there should be one
	 * behavior of each type for each sprite)
	 * @throws ClassNotFoundException 
	 * 
	 */
	public Component getComponentOfType(String componentClassName){
		
		for(Component component: myComponentsList){
			
			try {
				if(component.getClass() == Class.forName(componentClassName)){
					return component;	
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				return null;		
			}
			
		}
		return null;
	}
	
	public Action getActionOfType(String actionClassName) {
		
		for(Action action: myActionsList){
			
			try {
				if(action.getClass() == Class.forName(actionClassName)){
					return action;	
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				return null;		
			}
			
		}
		return null;
		
	}
	
	
	public Transform getTransform(){
		return myTransform;
	}

	public void setActive(boolean set){
		isActive = set;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	public String getTag(){
		return myTag;
	}
	
	public void setTag(String tagString) {
		myTag = tagString;
	}
	

	public Dimension2D getDimensions(){
		return myTransform.getDimensions();
	}
	
	public Group render(){
	    Shape spriteView = new Rectangle(myTransform.getPosX(),myTransform.getPosY(),myTransform.getWidth(),myTransform.getHeight());
	    Paint spriteColor;
	    try{
	        spriteColor = Color.web(myColorPath);
	    }
	    catch(IllegalArgumentException e){
	        //spriteColor = new ImagePattern(resourceManager.getImage(myColorPath));
	        spriteColor = Color.BEIGE;
	    }
	    spriteView.setFill(spriteColor);
	    return new Group(spriteView);
	}
	
	public List<Component> getComponents(){
	    return Collections.unmodifiableList(myComponentsList);
	}

	public List<Action> getActions() {
		return Collections.unmodifiableList(myActionsList);
	}
}
