package util;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptRunner {
	private ScriptEngine myEngine;
	private ScriptEngineManager myManager;


	private Map<String, Object> myObjects;
	
	public ScriptRunner(){
		myManager = new ScriptEngineManager();
		myEngine = myManager.getEngineByName("groovy");
	}
	
	public void giveVariables(List<Object> objects){
		//TODO
		//deal with multiple objects of same type
		for(Object parameter: objects){
			myObjects.put(parameter.getClass().getSimpleName(), parameter);
			myEngine.put(parameter.getClass().getSimpleName(), parameter);
		}
			
	}
	public void returnEval(String script) throws ScriptException{
		myEngine.eval(script);
		updateObjects();
	}
	
	private void updateObjects(){
		for(String key: myObjects.keySet()){
			myObjects.put(key, myEngine.get(key));
		}
	}
	
}
