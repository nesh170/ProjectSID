package gameEngine;

import java.util.List;
import java.util.Set;
import java.util.function.*;

/**
 * Represents single object within game
 * 
 * Contains data and methods for handling
 * functionality that is tied to each sprite
 *
 */
public class Sprite {
	
	private List<Behavior> allBehaviors;
	private boolean isActive;
	private String myTag;
	
	/**
	 * Apply 'initialize' method of
	 * each behavior in list of behaviors
	 * that will be within every sprite
	 * (once every
	 */
	public void initializeAllBehaviors(){
		Consumer<Behavior> initializeCon = beh -> beh.update();
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
			Consumer<Behavior> updateCon = beh -> beh.update();
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
