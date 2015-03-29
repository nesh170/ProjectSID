package gameEngine;

import java.util.List;
import java.util.Set;
import java.util.function.*;
import javafx.geometry.Point2D;

/**
 * Represents single object within game
 * 
 * Contains data and methods for handling
 * functionality that is tied to each sprite
 *
 */
public class Sprite {
	
	private static final Point2D DEFAULT_POSITION = new Point2D(0.0, 0.0);
	private static final Point2D DEFAULT_ROTATION = new Point2D(0.0 ,0.0);
	
	private List<Behavior> allBehaviors;
	private boolean isActive;
	private String myTag;
	private Transform myTransform;
	private Point2D startCoordinate;
	private Point2D dimensions;
	
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
		Consumer<Behavior> initializeCon = beh -> beh.activate();
		allBehaviors.stream().forEach(initializeCon);
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
			allBehaviors.stream().forEach(updateCon);
		}
	}
	
	/**
	 * 
	 */
	public void addBehavior(Behavior behaviorToAdd){
		allBehaviors.add(behaviorToAdd);
		
	}
	
	/**
	 * gets Behavior attached to this sprite
	 * of a specific type (there should be one
	 * behavior of each type for each sprite)
	 * @throws ClassNotFoundException 
	 * 
	 */
	public Behavior getBehaviorOfType(String behaviorClassName){
		
		for(Behavior behavior: allBehaviors){
			
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
	
	
	

	
	
}
