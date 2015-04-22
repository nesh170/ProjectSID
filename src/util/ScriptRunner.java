package util;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.codehaus.groovy.jsr223.ScriptStaticExtensions;

public class ScriptRunner {
	private transient ScriptEngine myEngine;

	private static final String GROOVY = "groovy";

	private Map<String, Object> myObjects;
	
	public ScriptRunner(){
		myObjects = new HashMap<String, Object>();
	}
	
	public void prepareEngine(){
	    ScriptEngineManager manager = new ScriptEngineManager();
	    myEngine = manager.getEngineByName(GROOVY);
	}
	
	public void giveVariables(List<Object> objects){
		//TODO
		//deal with multiple objects of same type
		for(Object parameter: objects){
		        System.out.println(parameter.getClass().getSimpleName());
			myObjects.put(parameter.getClass().getSimpleName(), parameter);
			myEngine.put(parameter.getClass().getSimpleName(), parameter);
		}
			
	}
	public void evaluateScript(String script) throws ScriptException{
		myEngine.eval(script);
		updateObjects();
	}
	
	private void updateObjects(){
		for(String key: myObjects.keySet()){
			myObjects.put(key, myEngine.get(key));
		}
	}
	
	public Map<String, Object> getMap(){
	    return Collections.unmodifiableMap(myObjects);
	}
}
