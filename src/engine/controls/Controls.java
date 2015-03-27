package engine.controls;
import java.lang.reflect.Method;
import java.util.Map;
import javafx.scene.input.KeyCode;
/**
 * The Controls class takes in the Map<KeyCode, String> in it's contructor. This map maps the keyCode to a string which gets the appropriate
 * method using reflection
 *
 */
public class Controls {
    Map<KeyCode, String> myKeyCodeMap;
    
    public Controls(Map<KeyCode, String> map){
        myKeyCodeMap=map;
    }
    
    /**
     * This method runs a for each loop on the map to bind the keys to the appropriate method calls when they are pressed
     */
    public void bindKeysToEventHandlers(){
        
    }
    
    /**
     * Detach all the keyhandlers
     */
    public void detachAllHandlers(){
        
    }
    
    /**
     * This method gets the appropriate method using reflection from the various classes
     * @param methodName is the string representation of the method
     * @return the method object
     */
    public Method getMethod(String methodName){
        
        return null;
    }
    
    /**This method moves the player up.
     * 
     */
    public void Up(){
        
    }
    
    /**
     * This method moves the player down
     */
    public void Down(){
        
    }
    
    /**
     * This method moves the player left
     */
    public void Left(){
        
    }
    
    /**
     * This method moves the player right
     */
    public void Right(){
        
    }
}
