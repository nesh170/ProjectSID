package player;

import java.lang.reflect.Method;
import java.util.Map;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class GroovyMenu {
    protected static final String ENTER = "enter";
    protected static final String TAG = "Sprite Tag:";
    protected static final String VARIABLE_LIST = "List Of Variables:";
    
    protected VBox myVBox;
    protected Map<Method, TextArea> myTextFieldMap;
    protected String myCurrentSpriteTag;
}
