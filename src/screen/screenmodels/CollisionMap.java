package screen.screenmodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import resources.constants.INT;
import resources.constants.STRING;

public class CollisionMap {

	Map<String, Map<String, List<List<String>>>> collisonMap;
	
	public CollisionMap(){
		collisonMap = new HashMap<String, Map<String, List<List<String>>>>();
		//HashMap<String, List<List<String>>> m = new HashMap<String, List<List<String>>>();
		
	}
	
	public List<String> getDirectionList(String attacker, String attacked, int direc){
		if( collisonMap!= null && collisonMap.containsKey(attacker)){
			HashMap<String, List<List<String>>>  attackedMap =
					(HashMap<String, List<List<String>>>) collisonMap.get(attacker);
			if(attackedMap!= null && attackedMap.containsKey(attacked)){
				List<List<String>> direcList = attackedMap.get(attacked);
					return direcList.get(direc);
			}
		}
		return null;
		
	}
	public void put(String spriteActing, String spriteActedUpon, int direction, List<String> actionParameters){

		Map<String, List<List<String>>> activeSpriteMap = this.getOrInstantiateActiveSpriteMap(spriteActing);
		
		List<List<String>> actionList = this.getOrInstantiateDirectionListOfActions(activeSpriteMap, spriteActedUpon);
		

		actionList.set(direction, actionParameters);
		
		activeSpriteMap.put(spriteActedUpon, actionList);
		this.collisonMap.put(spriteActing, activeSpriteMap);
	}
	
	
	private Map<String, List<List<String>>> getOrInstantiateActiveSpriteMap(String activeSpriteTag)
	{
		Map<String, List<List<String>>> activeSpriteMap;
		if (!(this.collisonMap.containsKey(activeSpriteTag)))
		{
			activeSpriteMap = new HashMap<String, List<List<String>>>();
			System.out.println("debug / test : here 1");
		}
		else
		{
			activeSpriteMap = collisonMap.get(activeSpriteTag);
			System.out.println("debug / test : here 2");

		}
		return activeSpriteMap;
	}
	
	private List<List<String>> getOrInstantiateDirectionListOfActions(Map<String, List<List<String>>> activeSpMap, String inactiveSprite)
	{
		List<List<String>> actionList;
		
		if (!(activeSpMap.containsKey(inactiveSprite)))
		{
			actionList = new ArrayList<List<String>>();
			for (int size = 0; size < INT.NUM_DIRECTIONS; size++)
			{
				actionList.add(new ArrayList<String>());
			}
			System.out.println("debug / test : here 3");

		}
		else
		{
			actionList = activeSpMap.get(inactiveSprite);
			System.out.println("debug / test : here 4");

		}
		
		return actionList;
	}
	
	
	public Set<String> keySet(){
		return collisonMap.keySet();
	}
	
	public boolean containsKey(String s){		
		return collisonMap.containsKey(s);
	}
	public Map<String, List<List<String>>> get(String s){
		if(containsKey(s))
			return collisonMap.get(s);	
		return null;
	}
	public int size(){
		return collisonMap.size();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
