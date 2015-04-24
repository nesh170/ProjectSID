package player;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class GroovyMenu {
    protected static final String ENTER = "enter";
    protected static final String TAG = "Sprite Tag:";
    protected static final String VARIABLE_LIST = "List Of Variables:";
    protected static final double MAX_HEIGHT = 100;
    
    protected VBox myVBox;
    protected Map<Method, TextArea> myTextFieldMap;
    protected String myCurrentSpriteTag;
    
    public void setUpGroovyDialog (List<String> tagList, BiConsumer<String, Object> engineMethod) {
        Stage stage = new Stage();
        setUpView(tagList, engineMethod);
        stage.setScene(new Scene(myVBox));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
    protected abstract void setUpView(List<String> tagList, BiConsumer<String, Object> engineMethod);
    
    protected void setUpVariableList (List<String> list) {
        ListView listView = new ListView<>(FXCollections.observableArrayList(list));
        listView.setMaxHeight(MAX_HEIGHT);
        myVBox.getChildren().addAll(new Text(VARIABLE_LIST), listView);
    }
}
