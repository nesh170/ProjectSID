package gameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sprite.Sprite;

public class CollisionTable {

	//old collisions table (TODO: DEPRECATE)
	private Map<String, Map<String, Action[]>> myTable;
	
	private Map<String, Map<String, List<Action>[]>> myBigTable;
	private List<Action> availableActions;
	private List<String> tagList;
	
	public CollisionTable(){
		myTable = new HashMap<>();
		myBigTable = new HashMap<>();
		tagList = new ArrayList<>();
	}
	
	private void generateActionsList(){
		
	}
	
	public Map<String, Action[]> getSingleCollideMap(String collideType){
		return myTable.get(collideType);
	}
	
	public Action[] getActionsForCollision(String type1, String type2){
		if (tagList.contains(type1) && myTable.get(type1).containsKey(type2)) {
			return myTable.get(type1).get(type2);
		}
		return null;
	}
	
	public Action getActionForCollisionAndDirection(String type1, String type2, int direction){
		if (tagList.contains(type1) && myTable.get(type1).containsKey(type2)) {
			return (myTable.get(type1).get(type2)[direction]);
		}
		return null;
	}
	
	public void addActionToMap(String type1, String type2, int direction, Action toAdd){
		if(myTable.containsKey(type1)) {
			
			if(myTable.get(type1).containsKey(type2)){
				
				myTable.get(type1).get(type2)[direction] = toAdd;
				
			} else {
				
				Action[] newActionList = new Action[4];
				newActionList[direction] = toAdd;
				myTable.get(type1).put(type2, newActionList);
				tagList.add(type2);
				
			}
			
		} else {
			
			HashMap<String, Action[]> subMap = new HashMap<>();
			Action[] newActionList = new Action[4];
			newActionList[direction] = toAdd;
			subMap.put(type2, newActionList);
			myTable.put(type1, subMap);
			tagList.add(type1);
			tagList.add(type2);
			
		}
		
	}
	
	public void addActionToBigMap(String type1, String type2, int direction, Action toAdd) {
		if(myBigTable.containsKey(type1)) {
			if(myBigTable.get(type1).containsKey(type2)) {
				if(myBigTable.get(type1).get(type2)[direction] != null) {
					myBigTable.get(type1).get(type2)[direction].add(toAdd);
				}
				else {
					myBigTable.get(type1).get(type2)[direction] = new ArrayList<Action>();
					myBigTable.get(type1).get(type2)[direction].add(toAdd);
				}
			} else {
				//Using List<?> because can't make array of generic types
				List<?>[] newActionsLists = new List<?>[4];
				List<Action> currActionList = new ArrayList<Action>();
				currActionList.add(toAdd);
				newActionsLists[direction] = currActionList;
				myBigTable.get(type1).put(type2, (List<Action>[]) newActionsLists);
				tagList.add(type2);
				
			}
		} else {
			HashMap<String, List<Action>[]> subMap = new HashMap<>();
			List<?>[] newActionsLists = new List<?>[4];
			List<Action> currActionList = new ArrayList<Action>();
			currActionList.add(toAdd);
			newActionsLists[direction] = currActionList;
			subMap.put(type2, (List<Action>[]) newActionsLists);
			myBigTable.put(type1, subMap);
			tagList.add(type1);
			tagList.add(type2);
			
		}
	}
	
	public List<Action> getActionsForCollisionAndDirection(String type1, String type2, int direction){
		if (tagList.contains(type1) && myBigTable.get(type1).containsKey(type2)) {
			return (myBigTable.get(type1).get(type2)[direction]);
		}
		return null;
	}
}
