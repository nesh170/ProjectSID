package gameEngine.actions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.script.ScriptException;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import util.ScriptRunner;
import gameEngine.Action;

public class CustomAction extends Action {
    
    private ScriptRunner scriptEngine = new ScriptRunner();
    private String myExecuteCode;
    private String myStopCode;
    private String myPrepareCode;

    public CustomAction (Sprite sprite) {
        super(sprite);
        scriptEngine.giveVariables(Stream.of(sprite).collect(Collectors.toList()));
    }

    public CustomAction (Sprite sprite, KeyCode ... keys) {
        super(sprite, keys);
        scriptEngine.giveVariables(Stream.of(sprite,keys).collect(Collectors.toList()));
    }

    public CustomAction (Sprite sprite, Double val, KeyCode ... keys) {
        super(sprite, val, keys);
        scriptEngine.giveVariables(Stream.of(sprite,val,keys).collect(Collectors.toList()));
    }

    @Override
    public void prepare () {
        try {
            scriptEngine.evaluateScript(myPrepareCode);
            updateVariables();
        }
        catch (ScriptException e) {
          // e.printStackTrace();
        }

    }

    @Override
    public void execute () {
        try {
            scriptEngine.evaluateScript(myExecuteCode);
            updateVariables();
        }
        catch (ScriptException e) {
          //  e.printStackTrace();
        }
        
    }
    
    @Override
    public void stop () {
        try {
            scriptEngine.evaluateScript(myStopCode);
            updateVariables();
        }
        catch (ScriptException e) {
         //   e.printStackTrace();
        }
    }
    
    private void updateVariables () {
        Map<String, Object> objectMap = scriptEngine.getMap();

    }
    
    public void setPrepareCode(String code){
        myPrepareCode = code;
    }
    
    public void setExecuteCode(String code){
        myExecuteCode = code;
    }
    
    public void setStopCode(String code){
        myStopCode = code;
    }
    
    

}
