package screen.spriteEditScreen;

import gameEngine.Action;
import gameEngine.Component;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import data.DataHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import resources.constants.INT;
import screen.Screen;
import screen.ScreenController;
import screen.levelEditScreen.LevelEditScreen;
import sprite.Sprite;

/**
 * 
 * @author Leo
 *
 */


public class SpriteEditScreen extends Screen {
	
	private SpriteEditScreenController controller;
	private Tab levelEditScreen;
	
	private Sprite editableSprite;
	
	private TextField spriteNameField;
	private ChoiceBox<String> tagChoicesHolder;

	private ResourceBundle tagResources;
	private ResourceBundle actionResources;
	private ResourceBundle componentResources;
//	private ResourceBundle physicsResources;
	
	private ObservableList<String> actionsToAdd;
	private ObservableList<String> actionsAdded;
	private ObservableList<String> componentsToAdd;
	private ObservableList<String> componentsAdded;
	
	
	private ChoiceBox<String> actionTypeBox;
	private TextField keycodeInputBox;
	private TextField actionValue;
	private TextField componentValue;
	
	private KeyCode currentCode;
	
	private Map<String,String> classPathMap;
	private Map<Button,ListView<String>> buttonToListMap;
	private Map<String, Action> actionMap;
	private Map<String,Boolean> keyCodesAreVisibleMap;
	private Map<String, Component> componentMap;
	
	public SpriteEditScreen(SpriteEditScreenController parent, Tab levelEditScreen, double width, double height) {

		this(parent, levelEditScreen, width, height, null);

	}

	public SpriteEditScreen(SpriteEditScreenController parent, Tab levelEditScreen, double width, double height, Sprite spriteToEdit) {

		super(width, height);
		
		this.levelEditScreen = levelEditScreen;
		this.controller = parent;

		if (spriteToEdit != null) {
			drawSpriteOnScreen(spriteToEdit);
			editableSprite = new Sprite(spriteToEdit);
		}
		
		else {
			editableSprite = new Sprite();
		}
		
		initializeRelevantResourceFiles();
		initializeObservableLists();
		initializeClassPathMap();
		initializeKeyCodesAreVisibleMap();
//		initializeOtherMaps(buttonToListMap,actionMap,componentMap);
		
		buttonToListMap = new HashMap<>();
		actionMap = new HashMap<>();
		componentMap = new HashMap<>();
		
		createLeftPane();
		createRightPane();
		createCenterPane();
		
	}
		

	private void initializeKeyCodesAreVisibleMap() {
		
		keyCodesAreVisibleMap = new HashMap<>();
		keyCodesAreVisibleMap.put(languageResources().getString("AlwaysRun"), false);
		keyCodesAreVisibleMap.put(languageResources().getString("NeedCode"), true);
		keyCodesAreVisibleMap.put(languageResources().getString("OnCollision"), false);
		
	}

	@Override
	protected void initializeRelevantResourceFiles() {
		super.initializeRelevantResourceFiles();
		tagResources = ResourceBundle.getBundle("resources.TagChoices");
		actionResources = ResourceBundle.getBundle("resources.spritePartProperties.action");
		componentResources = ResourceBundle.getBundle("resources.spritePartProperties.component");
//		physicsResources = ResourceBundle.getBundle("resources.spritePartProperties.physics");
	}
	
	private void initializeObservableLists() {
		actionsToAdd = FXCollections.observableArrayList(actionResources.keySet().stream()
																	.map(e -> languageResources().getString(e))
																	.collect(Collectors.toList()));
		actionsAdded = FXCollections.observableArrayList();
		componentsToAdd = FXCollections.observableArrayList(componentResources.keySet().stream()
																	.map(e -> languageResources().getString(e))
																	.collect(Collectors.toList()));
		componentsAdded = FXCollections.observableArrayList();
	}
	
//	@SuppressWarnings("unchecked")
//	protected void initializeOtherMaps(Map<? extends Object, ? extends Object>... maps) {
//		Arrays.asList(maps).forEach(e -> { e = new HashMap<>(); });
//	}
	
	private void initializeClassPathMap() {
		classPathMap = new HashMap<>();
		actionResources.keySet().forEach(e -> classPathMap.put(languageResources().getString(e), actionResources.getString(e)));
		componentResources.keySet().forEach(e -> classPathMap.put(languageResources().getString(e),componentResources.getString(e)));
	}


	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		Menu fileMenu = makeFileMenu(e -> saveSprite(),
									e -> exit(),
									e -> saveAndExit()
									);
		
		
		menuBar.getMenus().addAll(fileMenu);
		
	}
	
	private void createLeftPane() {
		Node nameAndTagPane = createNameAndTagPane();
		Node imagePane = createAddImagePane();
		
		VBox leftPane = new VBox();
		leftPane.getChildren().addAll(nameAndTagPane, imagePane);
		
		this.viewableArea().setLeft(leftPane);
	}
	
	private void createRightPane() {
		
		VBox rightPane = new VBox();
		
		Node actionAndComponentPane = createActionAndComponentPane();
		Node physicsPane = createPhysicsPane();
		Node imageListPane = createImageListPane();
		
		rightPane.getChildren().addAll(actionAndComponentPane,physicsPane,imageListPane);
		this.viewableArea().setRight(rightPane);

	}
	
	private void createCenterPane() {
		
		Pane paneForImage = new Pane();
		
		this.viewableArea().setCenter(paneForImage);
		
		
	}
	
	private GridPane createNameAndTagPane() {
		
		GridPane nameAndTagPane = new GridPane();
		VBox.setVgrow(nameAndTagPane, Priority.ALWAYS);
		nameAndTagPane.setAlignment(Pos.CENTER);
		nameAndTagPane.setVgap(50);
		nameAndTagPane.setHgap(10);
		nameAndTagPane.getStyleClass().add("pane");
				
		Text nameLabel = new Text("Name"+":"); //TODO do not hardcode "name"
		spriteNameField = new TextField();
		spriteNameField.setPromptText("Enter the Sprite Name"); //TODO DO NOT HARDCODE THIS STRING
		
		Text tagLabel = new Text("Tag"+":"); //TODO do not hardcode "tag"
		ObservableList<String> tagChoices = FXCollections.observableArrayList();
		tagResources.keySet().forEach(e -> tagChoices.add(tagResources.getString(e)));
		tagChoicesHolder = new ChoiceBox<String>(tagChoices);
		
		nameAndTagPane.add(nameLabel, 0, 0);
		nameAndTagPane.add(spriteNameField, 1, 0);
		nameAndTagPane.add(tagLabel, 0, 1);
		nameAndTagPane.add(tagChoicesHolder, 1, 1);
		
		return nameAndTagPane;
		
	}
	
	private Pane createAddImagePane() {
		StackPane imagePane = new StackPane();
		VBox.setVgrow(imagePane, Priority.ALWAYS);
		imagePane.setAlignment(Pos.CENTER);
		
		Button imageButton = new Button();
		imageButton.setGraphic(new ImageView(new Image("images/addimage.png")));
		imageButton.setOnMouseClicked(e -> selectImageFile());
		
		imagePane.getChildren().add(imageButton);
		imagePane.getStyleClass().add("pane");
		
		return imagePane;
	}
	
	private VBox createActionAndComponentPane() {
		
		initializeActionTypeBox();
		keycodeInputBox = new TextField();
		keycodeInputBox.setPromptText(languageResources().getString("KeycodePrompt"));
		keycodeInputBox.setOnKeyTyped(e -> setCurrentKeycode(e));
		actionValue = new TextField();
		actionValue.setPromptText(languageResources().getString("ValuePrompt"));
		
		HBox actionPane = makeTwoSidedList(actionsToAdd,actionsAdded,
											languageResources().getString("AddAction"),languageResources().getString("RemoveAction"),
											e -> addAction(e), e -> removeAction(e),
											actionTypeBox,keycodeInputBox,actionValue);
		
		componentValue = new TextField();
		componentValue.setPromptText(languageResources().getString("ValuePrompt"));
		HBox componentPane = makeTwoSidedList(componentsToAdd,componentsAdded,
											languageResources().getString("AddComponent"),languageResources().getString("RemoveComponent"),
											e -> addComponent(e), e -> removeComponent(e),
											componentValue);
		
		VBox actionAndComponentPane = new VBox();
		actionAndComponentPane.getChildren().addAll(actionPane,componentPane);
		
		return actionAndComponentPane;
	}
	
	private void initializeActionTypeBox() {
		ObservableList<String> actionTypes = FXCollections.observableArrayList();
		actionTypes.addAll(
				languageResources().getString("AlwaysRun"),
				languageResources().getString("NeedCode"),
				languageResources().getString("OnCollision"));
		actionTypeBox = new ChoiceBox<String>(actionTypes);
		actionTypeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue<? extends String> ov, String oldSelect, String newSelect) {
				keycodeInputBox.setVisible(keyCodesAreVisibleMap.get(newSelect));
			}
			
		});
	}

	private HBox makeTwoSidedList(ObservableList<String> toAdd, ObservableList<String> added,
									String addText, String removeText,
									EventHandler<MouseEvent> onAdd, EventHandler<MouseEvent> onRemove,
									Control... userTextFields) {
		
		HBox twoSidedListContainer = new HBox();
		ListView<String> toAddList = new ListView<>(toAdd);
		ListView<String> addedList = new ListView<>(added);
		
		VBox fieldsAndButtons = new VBox();
		fieldsAndButtons.getStyleClass().add("pane");
		fieldsAndButtons.setAlignment(Pos.CENTER);
		Button add = new Button(addText);
		add.setOnMouseClicked(onAdd);
		Button delete = new Button(removeText);
		delete.setOnMouseClicked(onRemove);
		setPrefButtonWidth(add,delete);
		
		Arrays.asList(userTextFields).forEach(e -> fieldsAndButtons.getChildren().add(e));
		fieldsAndButtons.getChildren().addAll(add,delete);
		
		buttonToListMap.put(add, toAddList);
		buttonToListMap.put(delete, addedList);
		
		twoSidedListContainer.getChildren().addAll(toAddList,fieldsAndButtons,addedList);
		
		return twoSidedListContainer;

	}
	
	private ListView<String> createPhysicsPane() {
		return new ListView<String>();
	}
	
	private void setPrefButtonWidth(Button... buttons) {
		Arrays.asList(buttons).forEach(e -> e.setPrefWidth(INT.PREF_BUTTON_WIDTH));
	}
	
	private ListView<String> createImageListPane() {
		return new ListView<String>();
	}
	
	private void addAction(MouseEvent e) {
		String selected = buttonToListMap.get((Button) e.getSource()).getSelectionModel().getSelectedItem();
		if(selected!=null) {
			actionsToAdd.remove(selected);
			actionsAdded.add(selected);
			
			try {
				Action action = (Action) Class.forName(classPathMap.get(selected))
												.getConstructor(Sprite.class,Double.class,KeyCode[].class)
												.newInstance(editableSprite,Double.parseDouble(actionValue.getText()),new KeyCode[]{currentCode});
				actionMap.put(selected, action);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			keycodeInputBox.clear();
			actionValue.clear();
		}
	}
	
	private void removeAction(MouseEvent e) {
		String selected = buttonToListMap.get((Button) e.getSource()).getSelectionModel().getSelectedItem();
		if(selected!=null) {
			actionsAdded.remove(selected);
			actionsToAdd.add(selected);
			actionMap.keySet().remove(selected);
		}
	}
	
	private void addComponent(MouseEvent e) {
		String selected = buttonToListMap.get((Button) e.getSource()).getSelectionModel().getSelectedItem();
		if(selected!=null) {
			componentsToAdd.remove(selected);
			componentsAdded.add(selected);
			
			
			
			componentValue.clear();
		}
	}
	
	private void removeComponent(MouseEvent e) {
		String selected = buttonToListMap.get((Button) e.getSource()).getSelectionModel().getSelectedItem();
		if(selected!=null) {
			componentsAdded.remove(selected);
			componentsToAdd.add(selected);
		}
	}
	
	private void setCurrentKeycode(KeyEvent e) {
		currentCode = e.getCode();
		keycodeInputBox.setText(e.getText());
	}
	
	private void selectImageFile() {
		File file = DataHandler.chooseDir(new Stage());
	}
	
	private void saveSprite() {
		//TODO
	}
	
	private void exit() {
		//TODO
	}
	
	private void saveAndExit() {
		saveSprite();
		exit();
	}

	// All other instance methods
	private void drawSpriteOnScreen(Sprite sprite) {
		//TODO implement
	}

}
