package gameEngine.actions;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.script.ScriptException;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import util.ScriptRunner;
import gameEngine.Action;


public class GroovyAction extends Action {

    private ScriptRunner scriptEngine;
    private String myExecuteCode;
    private String myStopCode;
    private String myPrepareCode;

    public GroovyAction (Sprite sprite) {
        super(sprite);
        scriptEngine = new ScriptRunner(Stream.of(sprite).collect(Collectors.toList()));
    }

    public GroovyAction (Sprite sprite, KeyCode ... keys) {
        super(sprite, keys);
        scriptEngine = new ScriptRunner(Stream.of(sprite, keys).collect(Collectors.toList()));
    }

    public GroovyAction (Sprite sprite, Double val, KeyCode ... keys) {
        super(sprite, val, keys);
        scriptEngine = new ScriptRunner(Stream.of(sprite, val).collect(Collectors.toList()));
    }

    @Override
    public void prepare () {
        try {
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

    public void setPrepareCode (String code) {
        myPrepareCode = code;
    }

    public void setExecuteCode (String code) {
        myExecuteCode = code;
    }

    public void setStopCode (String code) {
        myStopCode = code;
    }

}
