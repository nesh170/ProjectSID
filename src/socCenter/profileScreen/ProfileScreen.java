package socCenter.profileScreen;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import data.DataHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.controllers.SpriteEditScreenController;
import screen.screenmodels.SpriteEditModel;
import screen.screens.LevelEditScreen;
import socCenter.Avatar;
import socCenter.DefaultAvatarPack;
import socCenter.User;
import socCenter.mainPage.MainPageScreen;
import sprite.Sprite;

public class ProfileScreen extends Screen {

	// Instance Variables
	private ProfileScreenController controller;
	private Tab mainPageScreen;
	private SpriteEditModel model;
	private ProfileModel profileModel;
	private boolean isMyProfile;

	private TextField spriteNameField;
	private TextField imageSizeField;
	private DefaultAvatarPack avPack;
	/*private Map<String, Avatar> defaultAvatars;*/
	private ChoiceBox<String> defaultAvatarChoicesHolder;

	private StackPane paneForImage;
	private ListView<String> imageListPane;

	private ResourceBundle defaultAvResources;

	
	private CheckBox goalCheck;
	private TextField goToLevel;
	
	private TextField keycodeInputBox;
	
	private User profileUser;



	// Constructors & Helpers

	public ProfileScreen(ProfileScreenController parent, Tab mainPageScreen, double width, double height, User profileUser, boolean self) {

		super(width, height);
		this.profileUser = profileUser;

		this.mainPageScreen = mainPageScreen;
		this.controller = parent;
		this.isMyProfile = self;

		initializeRelevantResourceFiles();
		initializeAvatarPack();
		initializeObservableLists();
		initializeValueBoxListenersForLists();
		initializeInformationListenersForLists();
		// initializeOtherMaps(buttonToListMap,actionMap,componentMap);
		
		createLeftPane();
		createRightPane();
		createCenterPane();
		
		/*if (spriteToEdit != null) {
			drawSpriteOnScreen(spriteToEdit);
		}*/


	}
	
	private void initializeAvatarPack(){
		avPack = new DefaultAvatarPack(defaultAvResources);
		
	}

	// All other instance methods

	@Override
	protected void initializeRelevantResourceFiles() {

		super.initializeRelevantResourceFiles();
		defaultAvResources = ResourceBundle
				.getBundle("resources.socialCenterProperties.defaultAvatars");
	
		// physicsResources =
		// ResourceBundle.getBundle("resources.spritePartProperties.physics");
	}
	

	private void initializeObservableLists() {
		/*
		actionsToAddList = new ListView<>(model.setActionsToAdd(FXCollections.observableArrayList(actionResources
				.keySet().stream().map(e -> languageResources().getString(e))
				.collect(Collectors.toList()))));
		actionsAddedList = new ListView<>(model.setActionsAdded());
		componentsToAddList = new ListView<>(model.setComponentsToAdd(FXCollections.observableArrayList(componentResources
				.keySet().stream().map(e -> languageResources().getString(e))
				.collect(Collectors.toList()))));
		componentsAddedList = new ListView<>(model.setComponentsAdded());
		*/
	}

	//TODO get rid of some duplicated code in this method
	private void initializeValueBoxListenersForLists() {
		/*
		actionsToAddList.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> model.onAddListener(newValue,actionValue));
		componentsToAddList.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> model.onAddListener(newValue,componentValue));
			*/	
	}

	private void initializeInformationListenersForLists() {
		/*
		actionsAddedList.setOnMouseEntered(e -> dataText.setText(model.setDataText(actionsAddedList)));
		componentsAddedList.setOnMouseEntered(e -> dataText.setText(model.setDataText(componentsAddedList)));

		actionsAddedList.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldSelect, newSelect) -> dataText.setText(model.setDataText(newSelect)));
		componentsAddedList.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldSelect, newSelect) -> dataText.setText(model.setDataText(newSelect)));
		*/
	}
	


	// @SuppressWarnings("unchecked")
	// protected void initializeOtherMaps(Map<? extends Object, ? extends
	// Object>... maps) {
	// Arrays.asList(maps).forEach(e -> { e = new HashMap<>(); });
	// }

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

		Menu fileMenu = makeFileMenu(
				e -> model.saveSprite(spriteNameField.getText(), defaultAvatarChoicesHolder.getSelectionModel().getSelectedItem(), false, 0), 
				e -> exit(),
				e -> saveAndExit());

		menuBar.getMenus().addAll(fileMenu);

	}

	private void createLeftPane() {

		Node nameAndTagPane = createNameAndImagePane();
		Node imagePane = createAddImagePane();

		VBox leftPane = new VBox();
		leftPane.getChildren().addAll(nameAndTagPane, imagePane);

		this.viewableArea().setLeft(leftPane);

	}

	private void createRightPane() {

		VBox rightPane = new VBox();

		Node actionAndComponentPane = createCommentsPane();

		// Node physicsPane = createPhysicsPane();
		initializeImageListPane();

		rightPane.getChildren().addAll(actionAndComponentPane, imageListPane);
		this.viewableArea().setRight(rightPane);

	}

	private void createCenterPane() {

		paneForImage = new StackPane();
		paneForImage.setAlignment(Pos.CENTER);

		this.viewableArea().setCenter(paneForImage);

	}

	private GridPane createNameAndImagePane() {

		GridPane nameAndTagPane = new GridPane();
		VBox.setVgrow(nameAndTagPane, Priority.ALWAYS);
		nameAndTagPane.setAlignment(Pos.CENTER);
		nameAndTagPane.setVgap(50);
		nameAndTagPane.setHgap(10);
		nameAndTagPane.getStyleClass().add(STRING.CSS.PANE);

		Text nameLabel = new Text(languageResources().getString(STRING.SPRITE_EDIT.NAME) + ": ");

		Text userName = new Text(profileUser.getName());
		Text tagLabel = new Text(languageResources().getString("Tag") + ":");
		ImageView profilePic = new ImageView(profileUser.getImagePath());
		
	
		nameAndTagPane.add(nameLabel, 0, 0);
		nameAndTagPane.add(userName, 1, 0);
		nameAndTagPane.add(tagLabel, 0, 1);
		nameAndTagPane.add(profilePic, 1, 1);

		return nameAndTagPane;

	}

	private Pane createAddImagePane() {

		defaultAvatarChoicesHolder = avPack.getDefaultAvChoices();
		
		StackPane imagePane = new StackPane();
		VBox.setVgrow(imagePane, Priority.ALWAYS);
		imagePane.setAlignment(Pos.CENTER);

		VBox imageButtonAndSizeText = new VBox();
		VBox.setVgrow(imageButtonAndSizeText, Priority.ALWAYS);
		imageButtonAndSizeText.setAlignment(Pos.CENTER);

		Button imageButton = new Button();
		imageButton.setGraphic(new ImageView(new Image("images/save_avatar.png")));
		imageButton.setOnMouseClicked(e -> selectImageFile());

		
		imageButtonAndSizeText.getChildren()
		.addAll(imageButton, defaultAvatarChoicesHolder);

		imagePane.getChildren().add(imageButtonAndSizeText);
		imagePane.getStyleClass().add(STRING.CSS.PANE);

		return imagePane;

	}

	private VBox createCommentsPane() {

		VBox commentsPane = new VBox();
		ImageView ho = new ImageView(profileUser.getImagePath());
		commentsPane.getChildren().add(ho);
		return commentsPane;

	}


	private void initializeImageListPane() {
		imageListPane = new ListView<String>();
		
	}
	
	private void clearKeycodeInputBox() {
		keycodeInputBox.setText("");
	}


	private void selectImageFile() {
		String avName = defaultAvatarChoicesHolder.getSelectionModel().getSelectedItem();
		Avatar selectedAvy = avPack.getAvatar(avName);
		Image avatarView = new Image(selectedAvy.getURL());
		
		addImageToPane(selectedAvy.getURL(), avatarView);
		try {

			int imageSize = INT.DEFAULT_IMAGE_SIZE;

			if (imageSizeField.getText() != ""
					&& imageSizeField.getText().matches("^[0-9]+$")) {
				imageSize = Integer.parseInt(imageSizeField.getText());
			}

			File file = DataHandler.chooseFile(new Stage());
			Image image = DataHandler.fileToImage(file, imageSize, imageSize, true);
			String filePath = file.getPath();
			addImageToPane(filePath,image);

		} catch (NullPointerException e) {
			// do nothing
		}

	}
	
	private void addImageToPane(String path, Image image) {
		ImageView spriteImageRep = new ImageView(image);
		paneForImage.getChildren().clear();
		paneForImage.getChildren().add(spriteImageRep);
	
	}


	private void exit() {

		controller.returnToMainPage((MainPageScreen) mainPageScreen.getContent(), mainPageScreen, profileUser);
		

	}
	

	private void saveAndExit() {

		if (spriteNameField.getText().isEmpty()) {
			spriteNameField.getStyleClass().add(STRING.CSS.ERROR);
		}
		
		else {
			try {
				exit();
			}
			catch (NumberFormatException e) {
				goToLevel.getStyleClass().add(STRING.CSS.ERROR);
			}
		}

	}

	private void drawSpriteOnScreen(Sprite sprite) {

		spriteNameField.setText(sprite.getName());
		defaultAvatarChoicesHolder.getSelectionModel().select(sprite.tag());
		sprite.actionList().forEach(model.getActionConsumer());
		sprite.componentList().forEach(model.getComponentConsumer());
		goalCheck.setSelected(sprite.getGoalToLevel() >= 0);
		goToLevel.setText("" + sprite.getGoalToLevel());
		// TODO implement
		if(sprite.getImagePath()!=null) {
			Image image = DataHandler.fileToImage(new File(sprite.getImagePath()), sprite.dimensions().getWidth(), sprite.dimensions().getHeight(), false);
			addImageToPane(sprite.getImagePath(),image);
		}
	}
		
}

