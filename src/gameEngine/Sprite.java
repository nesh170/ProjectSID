package gameEngine;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.*;
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
public abstract class Sprite {
	
	private List<Behavior> myBehaviorsList;
	private static final Point2D DEFAULT_POSITION = new Point2D(0.0, 0.0);
	private static final Point2D DEFAULT_ROTATION = new Point2D(0.0 ,0.0);
	private boolean isActive;
	private String myTag;

	private Transform myTransform;
	private Point2D startCoordinate;
	private Dimension2D myDimensions; //width,height 
	private String myColorPath;

	
	public Sprite() {
		setActive(true);
		myTransform = new Transform(DEFAULT_POSITION, DEFAULT_ROTATION);
		startCoordinate = DEFAULT_POSITION;
	}
	
	public Sprite(Point2D coordinate) {
		setActive(true);
		myTransform = new Transform(coordinate, DEFAULT_ROTATION);
		startCoordinate = coordinate;
	}
	
	public Sprite(Point2D coordinate, Point2D rotate) {
		setActive(true);
		myTransform = new Transform(coordinate, rotate);
	}
	
	/**
	 * Apply 'initialize' method of
	 * each behavior in list of behaviors
	 * that will be within every sprite
	 * (at beginning of scene)
	 */

	public void activateAllBehaviors(){
		myBehaviorsList.stream().forEach(beh -> beh.activate());
	}
	
	/**
	 * Apply 'update' method of each behavior
	 * in list of behaviors
	 * every frame
	 * 
	 * Also contains logic for update-ordering
	 * (deciding what shoud happen first, etc
	 */
	public void updateAllBehaviors(){
		if(isActive){
			Consumer<Behavior> updateCon = beh -> beh.updateIfEnabled();
			myBehaviorsList.stream().forEach(updateCon);
		}
	}
	
	/**
	 * 
	 */
	public void addBehavior(Behavior behaviorToAdd){
		myBehaviorsList.add(behaviorToAdd);
		
	}
	
	/**
	 * gets Behavior attached to this sprite
	 * of a specific type (there should be one
	 * behavior of each type for each sprite)
	 * @throws ClassNotFoundException 
	 * 
	 */
	public Behavior getBehaviorOfType(String behaviorClassName){
		
		for(Behavior behavior: myBehaviorsList){
			
			try {
				if(behavior.getClass() == Class.forName(behaviorClassName)){
					return behavior;	
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
		return myDimensions;
	}
	
	public Group render(){
	    Shape spriteView = new Rectangle(myTransform.getPosX(),myTransform.getPosY(),myDimensions.getWidth(),myDimensions.getHeight());
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
	
	public List<Behavior> getBehaviors(){
	    return Collections.unmodifiableList(myBehaviorsList);
	}
}
