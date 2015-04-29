package gameEngine.actions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.script.ScriptException;
import data.DataHandler;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import util.ScriptRunner;
import util.SIDFunctions;
import gameEngine.Action;

@ActionName(DisplayName = "Custom Groovy")
public class GroovyAction extends Action {

    private ScriptRunner scriptEngine;
    private String myPrepareCode;
    private String myExecuteCode;
    private String myStopCode;

    public GroovyAction (Sprite sprite) {
        super(sprite);
    }

    public GroovyAction (Sprite sprite, KeyCode ... keys) {
        super(sprite, keys);
    }

    public GroovyAction (Sprite sprite, Double val, KeyCode ... keys) {
        super(sprite, val, keys);
    }

    public GroovyAction deepCopy () {
        String copy = DataHandler.toXMLString(this);
        return (GroovyAction) DataHandler.fromXMLString(copy);
    }

    @Override
    public void prepare () {
        try {
            scriptEngine = new ScriptRunner(SIDFunctions.addNoNull(Stream.of(mySprite,value,myKeyCode.get(0)).collect(Collectors.toList())));
            scriptEngine.prepareEngine();
            scriptEngine.evaluateScript(myPrepareCode);
        }
        catch (ScriptException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void execute () {
        try {
            scriptEngine.evaluateScript(myExecuteCode);
        }
        catch (ScriptException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop () {
        try {
            scriptEngine.evaluateScript(myStopCode);
        }
        catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @SetterGroovy(name = "Action.prepare()", type = "textBox")
    public void setPrepareCode (String code) {
        myPrepareCode = code;
    }

    @SetterGroovy(name = "Action.execute()", type = "textBox")
    public void setExecuteCode (String code) {
        myExecuteCode = code;
    }

    @SetterGroovy(name = "Action.stop()", type = "textBox")
    public void setStopCode (String code) {
        myStopCode = code;
    }

    public List<Object> getVariableList () {
        return Collections.unmodifiableList(SIDFunctions.addNoNull(Stream.of(mySprite.getClass().getSimpleName(),value.getClass().getSimpleName(),myKeyCode.get(0).getClass().getSimpleName()).collect(Collectors.toList())));
    }

    @Override
    protected void doAction () {}


}
