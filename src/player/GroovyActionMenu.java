package player;

import gameEngine.actions.GroovyAction;
import gameEngine.actions.Setter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class GroovyActionMenu {
    private static final String ENTER = "enter";
    private static final String TAG = "Sprite Tag:";
    private static final String VARIABLE_LIST = "List Of Variables:";

    private VBox myVBox;
    private Map<Method, TextArea> myTextFieldMap;
    private GroovyAction myGroovyAction;
    private String myCurrentSpriteTag;

    public GroovyActionMenu (GroovyAction groovyAction) {
        myGroovyAction = groovyAction;
    }

    public void setUpGroovyDialog (List<String> tagList, BiConsumer<String, GroovyAction> engineMethod) {
        Stage stage = new Stage();
        setUpView(tagList, engineMethod);
        stage.setScene(new Scene(myVBox));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void setUpView (List<String> tagList, BiConsumer<String, GroovyAction> engineMethod) {
        myTextFieldMap = new HashMap<Method, TextArea>();
        myVBox = new VBox(8);
        setUpVariableList(myGroovyAction.getVariableList());
        setUpChoiceBox(tagList);
        List<Method> methodList =
                Stream.of(GroovyAction.class.getMethods()).collect(Collectors.toList());
        methodList
                .stream()
                .filter(x -> x.getAnnotations().length != 0)
                .collect(Collectors.toList())
                .forEach(method -> addTextBox(method));
        enterButton(engineMethod);

    }

    private void setUpVariableList (List<String> list) {
        myVBox.getChildren().addAll(new Text(VARIABLE_LIST));
        list.stream().forEach(variableName -> myVBox.getChildren().add(new Text(variableName)));;
    }

    private void setUpChoiceBox (List<String> tagList) {
        VBox box = new VBox();
        ChoiceBox<String> spriteTag = new ChoiceBox<>(FXCollections.observableArrayList(tagList));
        spriteTag.setValue(tagList.get(0));
        box.getChildren().addAll(new Text(TAG), spriteTag);
        spriteTag.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {

                    @Override
                    public void changed (ObservableValue<? extends String> ov,
                                         String oldValue,
                                         String newValue) {
                        myCurrentSpriteTag = newValue;
                        //TODO debug this actions
                    }
                });
        myVBox.getChildren().add(box);

    }

    private void addTextBox (Method method) {
        VBox box = new VBox();
        TextArea field = new TextArea();
        String fieldName = ((Setter) method.getAnnotations()[0]).name();
        field.setPromptText(fieldName);
        myTextFieldMap.put(method, field);
        box.getChildren().addAll(new Text(fieldName), field);
        myVBox.getChildren().add(box);
    }

    private void enterButton (BiConsumer<String, GroovyAction> engineMethod) {
        Button enter = new Button(ENTER);
        enter.setOnAction(e -> handleEnter(engineMethod));
        myVBox.getChildren().add(enter);
    }

    private void handleEnter (BiConsumer<String, GroovyAction> engineMethod) {
        myTextFieldMap.keySet().stream()
                .forEach(method -> handleMethod(method, myTextFieldMap.get(method).getText()));
        engineMethod.accept(myCurrentSpriteTag, myGroovyAction);
    }

    private void handleMethod (Method method, String text) {
        try {
            method.invoke(myGroovyAction, text);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
