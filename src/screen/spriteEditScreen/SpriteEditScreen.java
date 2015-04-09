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
		actionResources.keySet().forEach(e -> spritePropertiesMap.put(actionResources.getString(e), actionResources.getString(e)));
		componentResources.keySet().forEach(e -> spritePropertiesMap.put(componentResources.getString(e), componentResources.getString(e)));
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
		/*
		 * TODO Get rid of duplicated code in this method
		 * 
		 */
		
		HBox actionPane = new HBox();
		ListView<String> actionToAddList = new ListView<>(actionsToAdd);
		ListView<String> actionAddedList = new ListView<>(actionsAdded);
		
		VBox actionButtons = new VBox();
		actionButtons.setAlignment(Pos.CENTER);
		Button addAction = new Button(languageResources().getString("AddAction"));
		Button deleteAction = new Button(languageResources().getString("RemoveAction"));
		actionButtons.getChildren().addAll(addAction,deleteAction);
		
		actionPane.getChildren().addAll(actionToAddList,actionButtons,actionAddedList);
		
		HBox componentPane = new HBox();
		ListView<String> componentToAddList = new ListView<>(componentsToAdd);
		ListView<String> componentAddedList = new ListView<>(componentsAdded);
		
		VBox componentButtons = new VBox();
		componentButtons.setAlignment(Pos.CENTER);
		Button addComponent = new Button(languageResources().getString("AddComponent"));
		Button deleteComponent = new Button(languageResources().getString("RemoveComponent"));
		componentButtons.getChildren().addAll(addComponent,deleteComponent);
		
		setPrefButtonWidth(addAction,deleteAction,addComponent,deleteComponent);
		
		componentPane.getChildren().addAll(componentToAddList,componentButtons,componentAddedList);
		
		VBox actionAndComponentPane = new VBox();
		actionAndComponentPane.getChildren().addAll(actionPane,componentPane);
		
		return actionAndComponentPane;
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
