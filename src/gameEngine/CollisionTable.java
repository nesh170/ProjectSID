package gameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionTable {

	private Map<String, Map<String, Action[]>> myTable;
	private List<Action> availableActions;
	private List<String> tagList;
	
	public CollisionTable(){
		myTable = new HashMap<>();
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
	
}
