package gameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import screen.screenmodels.CollisionMap;
import sprite.Sprite;

public class CollisionTable {

	
	private Map<String, Map<String, List<Map<Sprite, Action>>>> myBigTable;
	private List<Action> availableActions;
	private List<String> tagList;

	
	private CollisionMap collisionMap;
	private static final int MAX_SIZE = 5;


	public CollisionTable(){
		myBigTable = new HashMap<>();
		tagList = new ArrayList<>();
		collisionMap = new CollisionMap();
	}
	
	
	public void clear(){
		myBigTable = new HashMap<>();
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
				while(newList.size() < MAX_SIZE) newList.add(new HashMap<>());
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
	

	public Action getActionForCollisionDirectionAndSprite(String type1, String type2, int direction, Sprite spr){
		//TODO: Add Try Catch
		if (tagList.contains(type1) && myBigTable.get(type1).containsKey(type2) && myBigTable.get(type1).get(type2).get(direction).containsKey(spr)) {
			return (myBigTable.get(type1).get(type2).get(direction).get(spr));
		}
		return null;
	}

	public CollisionMap getMap() {
		return collisionMap;
	}
}
