package gameEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionTable {

	private Map<String, Map<String, Action[]>> myTable;
	private List<Action> availableActions;
	
	public CollisionTable(){
		myTable = new HashMap<String, Map<String, Action[]>>();
	}
	
	private void generateActionsList(){
		
	}
	
	public Map<String, Action[]> getSingleCollideMap(String collideType){
		return myTable.get(collideType);
	}
	
	public Action[] getActionsForCollision(String type1, String type2){
		return myTable.get(type1).get(type2);
	}
	
	public Action getActionForCollisionAndDirection(String type1, String type2, int direction){
		return myTable.get(type1).get(type2)[direction];
	}
	
	public void addActionToMap(String type1, String type2, int direction, Action toAdd){
		if(myTable.containsKey(type1)) {
			
			if(myTable.get(type1).containsKey(type2)){
				
				myTable.get(type1).get(type2)[direction] = toAdd;
				
			} else {
				
				Action[] newActionList = new Action[4];
				newActionList[direction] = toAdd;
				myTable.get(type1).put(type2, newActionList);
				
			}
			
		} else {
			
			HashMap<String, Action[]> subMap = new HashMap<String, Action[]>();
			Action[] newActionList = new Action[4];
			newActionList[direction] = toAdd;
			subMap.put(type2, newActionList);
			myTable.put(type1, subMap);
			
		}
		
	}
	
}
