package gameEngine.components;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import sprite.Sprite;
import util.ScriptRunner;
import gameEngine.Component;
import javax.script.ScriptException;
import jdk.nashorn.internal.objects.annotations.Setter;


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

    @Setter
    public void setPrepareCode (String code) {
        myPrepareCode = code;
    }

    @Setter
    public void setUpdateCode (String code) {
        myUpdateCode = code;
    }
    
    @Setter
    public void setSprite (Sprite sprite) {
        mySprite = sprite;
    }

}
