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
import javafx.scene.control.TextArea;
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
	private boolean isMyProfile;

	private DefaultAvatarPack avPack;
	private ImageView profilePic;
	/*private Map<String, Avatar> defaultAvatars;*/
	private ChoiceBox<String> defaultAvatarChoicesHolder;

	private StackPane paneForImage;
	private ListView<String> imageListPane;

	private ResourceBundle defaultAvResources;
	private ResourceBundle socialButtons;

	
	private User profileUser;



	// Constructors & Helpers

	public ProfileScreen(ProfileScreenController parent, Tab mainPageScreen, double width, double height,
			User profileUser, User loggedIn, boolean self) {

		super(width, height);
		this.profileUser = profileUser;

		this.mainPageScreen = mainPageScreen;
		this.controller = parent;
		this.isMyProfile = self;

		initializeRelevantResourceFiles();
		initializeAvatarPack();

		// initializeOtherMaps(buttonToListMap,actionMap,componentMap);
		
		createLeftPane();
		createRightPane();
		createCenterPane();
		

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
		socialButtons = ResourceBundle
				.getBundle("resources.socialCenterProperties.socialButtons");
	
	}
	

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

		Menu fileMenu = makeFileMenu(
				e -> controller.loadPlayerWithAvatar(profileUser.getImage()), 
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
		profilePic = new ImageView(profileUser.getImagePath());
		
	
		nameAndTagPane.add(nameLabel, 0, 0);
		nameAndTagPane.add(userName, 1, 0);;
		nameAndTagPane.add(profilePic, 0, 1);

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
		if(isMyProfile) imageButton.setOnMouseClicked(e -> saveImageSelection());

		
		imageButtonAndSizeText.getChildren()
		.addAll(imageButton, defaultAvatarChoicesHolder);

		imagePane.getChildren().add(imageButtonAndSizeText);
		imagePane.getStyleClass().add(STRING.CSS.PANE);

		return imagePane;

	}

	private GridPane createCommentsPane() {

		GridPane commentsPane = new GridPane();
		VBox.setVgrow(commentsPane, Priority.ALWAYS);
		commentsPane.setAlignment(Pos.CENTER);
		commentsPane.setVgap(50);
		commentsPane.setHgap(5);
		commentsPane.getStyleClass().add(STRING.CSS.PANE);
		
		TextField commentArea = new TextField();
		commentsPane.add(commentArea, 0, 0);
		
		Button submitComment = new Button(socialButtons.getString("SubmitComment"));
		submitComment.setOnAction(e -> profileUser.addComment(commentArea.getText()));
		commentsPane.add(submitComment, 1, 0);
		
		return commentsPane;

	}


	private void initializeImageListPane() {
		imageListPane = new ListView<String>();
		
	}
	
	private void saveImageSelection() {
		String avName = defaultAvatarChoicesHolder.getSelectionModel().getSelectedItem();
		Avatar selectedAvy = avPack.getAvatar(avName);
		Image avatarImage = new Image(selectedAvy.getURL());
		

		profileUser.setImagePath(selectedAvy.getURL());
		controller.saveChanges(profileUser);
		profilePic.setImage(avatarImage);

	}
	

	private void exit() {

		controller.returnToMainPage((MainPageScreen) mainPageScreen.getContent(), mainPageScreen);
		

	}
	

	private void saveAndExit() {
		exit();

	}

		
}

