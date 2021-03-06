package gameEngine.components;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sprite.Sprite;
import util.DialogUtil;
import util.SIDFunctions;
import util.ScriptRunner;
import gameEngine.Component;
import gameEngine.actions.SetterGroovy;

import javax.script.ScriptException;

import data.DataHandler;
import jdk.nashorn.internal.objects.annotations.Setter;


public class GroovyComponent extends Component {

    private ScriptRunner scriptEngine;
    private String myPrepareCode;
    private String myUpdateCode;

    private static final String GROOVY_ERRTITLE = "Error Groovy Parsing";
    private static final String GROOVY_ERRMSG = "Groovy Parsing Error.";
    
    
    public GroovyComponent (Sprite sprite, Double value) {
        super(sprite, value);
    }
    
    public GroovyComponent deepCopy(){
        String copy = DataHandler.toXMLString(this);
        return (GroovyComponent) DataHandler.fromXMLString(copy);
    }

    @Override
    public void prepare () {
        try {
            scriptEngine = new ScriptRunner(SIDFunctions.addNoNull(Stream.of(mySprite,myValue).collect(Collectors.toList())));
            scriptEngine.prepareEngine();
            scriptEngine.evaluateScript(myPrepareCode);
        }
        catch (ScriptException e) {
        	 DialogUtil.displayMessage(GROOVY_ERRTITLE, GROOVY_ERRMSG);
        }

    }

    @Override
    protected void update () {
        try {
            scriptEngine.evaluateScript(myUpdateCode);
        }
        catch (ScriptException e) {
            // TODO Auto-generated catch block
        	 DialogUtil.displayMessage(GROOVY_ERRTITLE, GROOVY_ERRMSG);
        }
    }

    @SetterGroovy(name = "Component.Prepare()", type = "textBox")
    public void setPrepareCode (String code) {
        myPrepareCode = code;
    }

    @SetterGroovy(name = "Component.Update()", type = "textBox")
    public void setUpdateCode (String code) {
        myUpdateCode = code;
    }
    
    @Setter
    public void setSprite (Sprite sprite) {
        mySprite = sprite;
    }
    
    public List<Object> getVariableList () {
        return Collections.unmodifiableList(SIDFunctions.addNoNull(Stream.of(mySprite.getClass().getSimpleName(),myValue.getClass().getSimpleName()).collect(Collectors.toList())));
    }

}
