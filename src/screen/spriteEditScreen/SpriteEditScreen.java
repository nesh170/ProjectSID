package screen.spriteEditScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
		
		createLeftPane();
		createRightPane();
		createCenterPane();
		
	}
		
	
	private void initializeRelevantResourceFiles() {
		tagResources = ResourceBundle.getBundle("resources/TagChoices");
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
		ListView<String> actionToAddList = new ListView<>();
		ListView<String> actionAddedList = new ListView<>();
		
		VBox actionButtons = new VBox();
		Button addAction = new Button();
		Button deleteAction = new Button();
		actionButtons.getChildren().addAll(addAction,deleteAction);
		
		actionPane.getChildren().addAll(actionToAddList,actionButtons,actionAddedList);
		
		HBox componentPane = new HBox();
		ListView<String> componentToAddList = new ListView<>();
		ListView<String> componentAddedList = new ListView<>();
		
		VBox componentButtons = new VBox();
		Button addComponent = new Button();
		Button deleteComponent = new Button();
		componentButtons.getChildren().addAll(addComponent,deleteComponent);
		
		componentPane.getChildren().addAll(componentToAddList,componentButtons,componentAddedList);
		
		VBox actionAndComponentPane = new VBox();
		actionAndComponentPane.getChildren().addAll(actionPane,componentPane);
		
		return actionAndComponentPane;
	}
	
	private ListView<String> createPhysicsPane() {
		return new ListView<String>();
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
