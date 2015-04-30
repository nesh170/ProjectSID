package player;

import gameEngine.actions.GroovyAction;
import gameEngine.actions.SetterGroovy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class GroovyActionMenu extends GroovyMenu {

    private static final String KEYCODE = "Insert KeyCode";
    private static final String VALUE_FIELD = "Insert Double Value";
    private GroovyAction myGroovyAction;

    public GroovyActionMenu (GroovyAction groovyAction) {
        myGroovyAction = groovyAction;
    }

    @Override
    protected void setUpView (List<String> tagList, BiConsumer<String, Object> engineMethod) {
        myTextFieldMap = new HashMap<Method, TextArea>();
        myVBox = new VBox(8);
        setUpVariableList(myGroovyAction.getVariableList());
        Spinner<Double> valueField = setUpValueField();
        setUpKeyCodeBox();
        setUpChoiceBox(tagList);
        List<Method> methodList =
                Stream.of(GroovyAction.class.getMethods()).collect(Collectors.toList());
        methodList
                .stream()
                .filter(x -> x.getAnnotations().length != 0)
                .collect(Collectors.toList())
                .forEach(method -> addTextBox(method));
        enterButton(engineMethod,valueField);

    }
    
    private Spinner<Double> setUpValueField () {
        Spinner<Double> valueField = new Spinner<Double>(0.0,Double.MAX_VALUE,0.0);
        valueField.setEditable(true);
        valueField.setMaxHeight(MAX_HEIGHT);
        myVBox.getChildren().addAll(new Text(VALUE_FIELD), valueField);
        return valueField;
    }

    private void setUpKeyCodeBox(){
        TextField keyInput = new TextField();
        keyInput.setOnKeyTyped(e -> keyInput.clear());
        keyInput.setOnKeyReleased(key -> handleKeyCode(keyInput, key.getCode()));
        myVBox.getChildren().addAll(new Text(KEYCODE), keyInput);
    }

    private void handleKeyCode (TextField keyInput, KeyCode code) {
        keyInput.clear();
        keyInput.setText(code.getName());
        myGroovyAction.setKeyCode(Stream.of(code).collect(Collectors.toList()));
    }

    private void setUpChoiceBox (List<String> tagList) {
        VBox box = new VBox();
        ChoiceBox<String> spriteTag = new ChoiceBox<>(FXCollections.observableArrayList(tagList));
        box.getChildren().addAll(new Text(TAG), spriteTag);
        spriteTag.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {

                    @Override
                    public void changed (ObservableValue<? extends String> ov,
                                         String oldValue,
                                         String newValue) {
                        myCurrentSpriteTag = newValue;
                    }
                });
        spriteTag.setValue(tagList.get(0));
        myVBox.getChildren().add(box);

    }

    private void addTextBox (Method method) {
        VBox box = new VBox();
        TextArea field = new TextArea();
        String fieldName = method.getAnnotation(SetterGroovy.class).name();
        field.setPromptText(fieldName);
        field.setMaxHeight(MAX_HEIGHT);
        myTextFieldMap.put(method, field);
        box.getChildren().addAll(new Text(fieldName), field);
        myVBox.getChildren().add(box);
    }

    private void enterButton (BiConsumer<String, Object> engineMethod, Spinner<Double> valueField) {
        Button enter = new Button(ENTER);
        enter.setOnAction(e -> handleEnter(engineMethod,valueField));
        myVBox.getChildren().add(enter);
    }

    private void handleEnter (BiConsumer<String, Object> engineMethod, Spinner<Double> valueField) {
        myTextFieldMap.keySet().stream()
                .forEach(method -> handleMethod(method, myTextFieldMap.get(method).getText()));
        myGroovyAction.setValue(valueField.getValue());
        engineMethod.accept(myCurrentSpriteTag, myGroovyAction);
        myStage.close();
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
