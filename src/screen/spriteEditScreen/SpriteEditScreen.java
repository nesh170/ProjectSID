package screen.spriteEditScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
	
	private TextField spriteNameField;
	private ChoiceBox<String> tagChoicesHolder;

	private ResourceBundle tagResources;
	private ResourceBundle actionResources;
	private ResourceBundle componentResources;
	private ResourceBundle physicsResources;
	
	private ObservableList<String> actionsToAdd;
	private ObservableList<String> actionsAdded; 
	private ObservableList<String> componentsToAdd;
	private ObservableList<String> componentsAdded;
	
	private Map<String,String> spritePropertiesMap;
	private Map<Button,ListView<String>> buttonToListMap;
	
	public SpriteEditScreen(SpriteEditScreenController parent, Tab levelEditScreen, double width, double height) {

		this(parent, levelEditScreen, width, height, null);

	}

	public SpriteEditScreen(SpriteEditScreenController parent, Tab levelEditScreen, double width, double height, Sprite spriteToEdit) {

		super(width, height);
		
		this.levelEditScreen = levelEditScreen;
		this.controller = parent;

		if (spriteToEdit != null) {
			drawSpriteOnScreen(spriteToEdit);
		}
		
		initializeRelevantResourceFiles();
		initializeObservableLists();
		initializeSpritePropertiesMap();
		
		buttonToListMap = new HashMap<>();
		
		createLeftPane();
		createRightPane();
		createCenterPane();
		
	}
		

	@Override
	protected void initializeRelevantResourceFiles() {
		super.initializeRelevantResourceFiles();
		tagResources = ResourceBundle.getBundle("resources.TagChoices");
		actionResources = ResourceBundle.getBundle("resources.spritePartProperties.action");
		componentResources = ResourceBundle.getBundle("resources.spritePartProperties.component");
		physicsResources = ResourceBundle.getBundle("resources.spritePartProperties.physics");
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
	
	private void initializeSpritePropertiesMap() {
		spritePropertiesMap = new HashMap<>();
		actionResources.keySet().forEach(e -> spritePropertiesMap.put(actionResources.getString(e), languageResources().getString(e)));
		componentResources.keySet().forEach(e -> spritePropertiesMap.put(componentResources.getString(e), languageResources().getString(e)));
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
		
		imagePane.getChildren().add(imageButton);
		imagePane.getStyleClass().add("pane");
		
		return imagePane;
	}
	
	private VBox createActionAndComponentPane() {
		
		HBox actionPane = makeTwoSidedList(actionsToAdd,actionsAdded,
											languageResources().getString("AddAction"),languageResources().getString("RemoveAction"),
											e -> addAction(e), e -> removeAction(e));
		HBox componentPane = makeTwoSidedList(componentsToAdd,componentsAdded,
											languageResources().getString("AddComponent"),languageResources().getString("RemoveComponent"),
											e -> addComponent(e), e -> removeComponent(e));
		
		VBox actionAndComponentPane = new VBox();
		actionAndComponentPane.getChildren().addAll(actionPane,componentPane);
		
		return actionAndComponentPane;
	}
	
	private HBox makeTwoSidedList(ObservableList<String> toAdd, ObservableList<String> added,
									String addText, String removeText,
									EventHandler<MouseEvent> onAdd, EventHandler<MouseEvent> onRemove) {
		
		HBox twoSidedListContainer = new HBox();
		ListView<String> toAddList = new ListView<>(toAdd);
		ListView<String> addedList = new ListView<>(added);
		
		VBox buttons = new VBox();
		buttons.setAlignment(Pos.CENTER);
		Button add = new Button(addText);
		add.setOnMouseClicked(onAdd);
		Button delete = new Button(removeText);
		delete.setOnMouseClicked(onRemove);
		setPrefButtonWidth(add,delete);
		buttons.getChildren().addAll(add,delete);
		
		buttonToListMap.put(add, toAddList);
		buttonToListMap.put(delete, addedList);
		
		twoSidedListContainer.getChildren().addAll(toAddList,buttons,addedList);
		
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
		}
	}
	
	private void removeAction(MouseEvent e) {
		String selected = buttonToListMap.get((Button) e.getSource()).getSelectionModel().getSelectedItem();
		if(selected!=null) {
			actionsAdded.remove(selected);
			actionsToAdd.add(selected);
		}
	}
	
	private void addComponent(MouseEvent e) {
		String selected = buttonToListMap.get((Button) e.getSource()).getSelectionModel().getSelectedItem();
		if(selected!=null) {
			componentsToAdd.remove(selected);
			componentsAdded.add(selected);
		}
	}
	
	private void removeComponent(MouseEvent e) {
		String selected = buttonToListMap.get((Button) e.getSource()).getSelectionModel().getSelectedItem();
		if(selected!=null) {
			componentsAdded.remove(selected);
			componentsToAdd.add(selected);
		}
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
