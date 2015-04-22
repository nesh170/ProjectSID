package util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


public class ScriptRunner {
    private static final String GROOVY = "groovy";

    @XStreamOmitField
    private transient ScriptEngine myScriptEngine;

    private Map<String, Object> myObjectsMap;

    public ScriptRunner (List<Object> objects) {
        myObjectsMap = new HashMap<>();
        objects.stream().forEach(parameter -> myObjectsMap.put(parameter.getClass().getSimpleName(), parameter));
    }

    public void prepareEngine () {
        ScriptEngineManager manager = new ScriptEngineManager();
        myScriptEngine = manager.getEngineByName(GROOVY);
        myObjectsMap.keySet().stream().forEach(key -> myScriptEngine.put(key, myObjectsMap.get(key)));
    }


    public void evaluateScript (String script) throws ScriptException {
        myScriptEngine.eval(script);
    }

    public Map<String, Object> getObjectMap () {
        return Collections.unmodifiableMap(myObjectsMap);
    }
}
