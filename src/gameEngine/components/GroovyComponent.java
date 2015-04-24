package gameEngine.components;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import sprite.Sprite;
import util.ScriptRunner;
import gameEngine.Component;
import gameEngine.actions.GroovyAction;
import javax.script.ScriptException;


public class GroovyComponent extends Component {

    private ScriptRunner scriptEngine;
    private String myPrepareCode;
    private String myUpdateCode;

    public GroovyComponent (Sprite sprite, List<Double> valueList) {
        super(sprite, valueList);
        scriptEngine = new ScriptRunner(Stream.of(sprite, valueList).collect(Collectors.toList()));

    }
    
    public GroovyComponent deepCopy(){
        GroovyComponent copy = new GroovyComponent(null,myValueList);
        copy.setPrepareCode(myPrepareCode);
        copy.setUpdateCode(myUpdateCode);
        return copy;
    }

    @Override
    public void prepare () {
        try {
            scriptEngine.prepareEngine();
            scriptEngine.evaluateScript(myPrepareCode);
        }
        catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void update () {
        try {
            scriptEngine.evaluateScript(myUpdateCode);
        }
        catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setPrepareCode (String code) {
        myPrepareCode = code;
    }

    public void setUpdateCode (String code) {
        myUpdateCode = code;
    }
    
    public void setSprite (Sprite sprite) {
        mySprite = sprite;
    }

}
