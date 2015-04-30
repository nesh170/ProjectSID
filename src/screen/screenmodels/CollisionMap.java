package screen.screenmodels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import resources.constants.INT;

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
	public void put(String attacker, String attacked, int direc, List<String> list){
	
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
