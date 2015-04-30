package gameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sprite.Sprite;

public class CollisionTable {

	//old collisions table (TODO: DEPRECATE)
	private Map<String, Map<String, Action[]>> myTable;
	
	private Map<String, Map<String, List<Action>[]>> myBigBigTable;
	private Map<String, Map<String, Map<Sprite, Action>[]>> myBigTabe;
	private Map<String, Map<String, List<Map<Sprite, Action>>>> myBigTable;
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
			
			Map<String, Action[]> subMap = new HashMap<>();
			Action[] newActionList = new Action[4];
			newActionList[direction] = toAdd;
			subMap.put(type2, newActionList);
			myTable.put(type1, subMap);
			tagList.add(type1);
			tagList.add(type2);
			
		}
		
	}
	
	//im disgusted w myself
	public void addActionToBigMap(String type1, String type2, int direction, Action toAdd, Sprite spr) {
		if(myBigTable.containsKey(type1)) {
			if(myBigTable.get(type1).containsKey(type2)) {
				if(myBigTable.get(type1).get(type2).get(direction).isEmpty()) {
					
					myBigTable.get(type1).get(type2).get(direction).put(spr, toAdd);
				
				} 
				
			} else {
				
				List<Map<Sprite,Action>> newList = new ArrayList<Map<Sprite,Action>>();
				while(newList.size() < 5) newList.add(new HashMap<>());
				myBigTable.get(type1).put(type2, newList);
				Map<Sprite,Action> newMap = new HashMap<Sprite, Action>();
				myBigTable.get(type1).get(type2).add(direction, newMap);
				myBigTable.get(type1).get(type2).get(direction).put(spr, toAdd);

				
			}
		} else {
			
			HashMap<String, List<Map<Sprite,Action>>> subMap = new HashMap<>();
			
			List<Map<Sprite,Action>> newActionLists = new ArrayList<>();
			while(newActionLists.size() < 5) newActionLists.add(new HashMap<>());

			Map<Sprite, Action> currActionMap = new HashMap<>();
			currActionMap.put(spr, toAdd);
			
			newActionLists.add(direction, currActionMap);
		
			subMap.put(type2, newActionLists);
			myBigTable.put(type1, subMap);
			tagList.add(type1);
			tagList.add(type2);
			
		}
	}
	
	/*public List<Action> getActionsForCollisionAndDirection(String type1, String type2, int direction){
		if (tagList.contains(type1) && myBigTable.get(type1).containsKey(type2)) {
			return (myBigTable.get(type1).get(type2)[direction]);
		}
		return null;
	}*/
	
	public Action getActionsForCollisionAndDirectionAndSprite(String type1, String type2, int direction, Sprite spr){
		if (tagList.contains(type1) && myBigTable.get(type1).containsKey(type2)) {
			return (myBigTable.get(type1).get(type2).get(direction).get(spr));
		}
		return null;
	}
}
