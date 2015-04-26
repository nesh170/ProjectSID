package socCenter.profileScreen;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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
import socCenter.User;
import sprite.Sprite;

public class ProfileScreen extends Screen {

	// Instance Variables
	private ProfileScreenController controller;
	private Tab levelEditScreen;
	private SpriteEditModel model;

	private TextField spriteNameField;
	private TextField imageSizeField;
	private TextField soundPath;
	private ChoiceBox<String> tagChoicesHolder;

	private StackPane paneForImage;
	private ListView<String> imageListPane;

	private ResourceBundle actionResources;
	private ResourceBundle componentResources;
	private ResourceBundle behaviorLabels;
	
	private ListView<String> actionsToAddList;
	private ListView<String> actionsAddedList;
	private ListView<String> componentsToAddList;
	private ListView<String> componentsAddedList;

	private Text dataText;
	
	private CheckBox goalCheck;
	private TextField goToLevel;
	private final String defaultGoToLevelValue = "-1";

	private ChoiceBox<String> actionTypeBox;
	private TextField keycodeInputBox;
	private TextField actionValue;
	private TextField componentValue;
	
	private User loggedIn;



	// Constructors & Helpers
	public ProfileScreen(ProfileScreenController parent, Tab levelEditScreen, double width, double height) {

		this(parent, levelEditScreen, width, height, null);

	}

	public ProfileScreen(ProfileScreenController parent, Tab levelEditScreen, double width, double height, Sprite spriteToEdit) {

		super(width, height);
		
		initializeRelevantResourceFiles();
		
		//model = new SpriteEditModel(languageResources(), actionResources, componentResources, behaviorLabels);

		this.levelEditScreen = levelEditScreen;
		this.controller = parent;

		initializeObservableLists();
		initializeValueBoxListenersForLists();
		initializeInformationListenersForLists();
		// initializeOtherMaps(buttonToListMap,actionMap,componentMap);
		
		createLeftPane();
		createRightPane();
		createCenterPane();
		
		if (spriteToEdit != null) {
			drawSpriteOnScreen(spriteToEdit);
		}


	}
	
	public void tagsForUse(Set<String> tags) {
		tagChoicesHolder.setItems(FXCollections.observableArrayList(tags));
	}

	// All other instance methods

	@Override
	protected void initializeRelevantResourceFiles() {

		super.initializeRelevantResourceFiles();
		behaviorLabels = ResourceBundle
				.getBundle("resources.spritePartProperties.behaviorlabels");
		actionResources = ResourceBundle
				.getBundle("resources.spritePartProperties.action");
		componentResources = ResourceBundle
				.getBundle("resources.spritePartProperties.component");
		// physicsResources =
		// ResourceBundle.getBundle("resources.spritePartProperties.physics");
	}

	private void initializeObservableLists() {

		actionsToAddList = new ListView<>(model.setActionsToAdd(FXCollections.observableArrayList(actionResources
				.keySet().stream().map(e -> languageResources().getString(e))
				.collect(Collectors.toList()))));
		actionsAddedList = new ListView<>(model.setActionsAdded());
		componentsToAddList = new ListView<>(model.setComponentsToAdd(FXCollections.observableArrayList(componentResources
				.keySet().stream().map(e -> languageResources().getString(e))
				.collect(Collectors.toList()))));
		componentsAddedList = new ListView<>(model.setComponentsAdded());

	}

	//TODO get rid of some duplicated code in this method
	private void initializeValueBoxListenersForLists() {
		
		actionsToAddList.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> model.onAddListener(newValue,actionValue));
		componentsToAddList.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> model.onAddListener(newValue,componentValue));
				
	}

	private void initializeInformationListenersForLists() {

		actionsAddedList.setOnMouseEntered(e -> dataText.setText(model.setDataText(actionsAddedList)));
		componentsAddedList.setOnMouseEntered(e -> dataText.setText(model.setDataText(componentsAddedList)));

		actionsAddedList.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldSelect, newSelect) -> dataText.setText(model.setDataText(newSelect)));
		componentsAddedList.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldSelect, newSelect) -> dataText.setText(model.setDataText(newSelect)));

	}
	


	// @SuppressWarnings("unchecked")
	// protected void initializeOtherMaps(Map<? extends Object, ? extends
	// Object>... maps) {
	// Arrays.asList(maps).forEach(e -> { e = new HashMap<>(); });
	// }

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

		Menu fileMenu = makeFileMenu(
				e -> model.saveSprite(spriteNameField.getText(), tagChoicesHolder.getSelectionModel().getSelectedItem(), false, 0), 
				e -> exit(),
				e -> saveAndExit());

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

	private GridPane createNameAndTagPane() {

		GridPane nameAndTagPane = new GridPane();
		VBox.setVgrow(nameAndTagPane, Priority.ALWAYS);
		nameAndTagPane.setAlignment(Pos.CENTER);
		nameAndTagPane.setVgap(50);
		nameAndTagPane.setHgap(10);
		nameAndTagPane.getStyleClass().add(STRING.CSS.PANE);

		Text nameLabel = new Text(languageResources().getString(STRING.SPRITE_EDIT.NAME) + ":");

		spriteNameField = new TextField();
		spriteNameField.setPromptText(languageResources().getString(STRING.SPRITE_EDIT.SPRITE_PROMPT));

		Text tagLabel = new Text(languageResources().getString("Tag") + ":");

		tagChoicesHolder = new ChoiceBox<String>();

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

		VBox imageButtonAndSizeText = new VBox();
		VBox.setVgrow(imageButtonAndSizeText, Priority.ALWAYS);
		imageButtonAndSizeText.setAlignment(Pos.CENTER);

		Button imageButton = new Button();
		imageButton.setGraphic(new ImageView(new Image("images/addimage.png")));
		imageButton.setOnMouseClicked(e -> selectImageFile());

		imageSizeField = new TextField();
		imageSizeField
		.setPromptText(languageResources().getString(languageResources().getString(STRING.SPRITE_EDIT.IMAGE_PROMPT)));

		imageButtonAndSizeText.getChildren()
		.addAll(imageButton, imageSizeField);

		imagePane.getChildren().add(imageButtonAndSizeText);
		imagePane.getStyleClass().add(STRING.CSS.PANE);

		return imagePane;

	}

	private VBox createActionAndComponentPane() {

		Pane actionPane = initializeActionPaneBoxes();
		Pane componentPane = initializeComponentPaneBoxes();
		Node dataPane = createDataPane();
		Node goalCheckboxPane = createGoalCheckbox();

		VBox actionAndComponentPane = new VBox();
		actionAndComponentPane.getChildren().addAll(actionPane, componentPane,
				dataPane,goalCheckboxPane);

		return actionAndComponentPane;

	}

	private Node createDataPane() {

		HBox textArea = new HBox();

		textArea.getStyleClass().add(STRING.CSS.PANE);
		dataText = new Text(languageResources().getString("DataText"));
		textArea.getChildren().add(dataText);

		return textArea;

	}
	
	private HBox createGoalCheckbox() {
		HBox goalArea = new HBox();
		goalArea.getStyleClass().add(STRING.CSS.PANE);
		goalArea.alignmentProperty().set(Pos.CENTER);
		goalArea.setSpacing(DOUBLE.BUTTON_SPACING);
		
		goalCheck = new CheckBox(languageResources().getString("OnGoal"));
		goalCheck.selectedProperty().addListener((observable, oldBool, newBool) -> {
			goToLevel.setVisible(newBool);
			if(newBool) {
				goToLevel.clear();
			}
			else {
				goToLevel.setText(defaultGoToLevelValue);
			}
		});
		goToLevel = new TextField();
		goToLevel.setPromptText("LevelNum");
		goToLevel.setText(defaultGoToLevelValue);
		goToLevel.setVisible(false);
		
		goalArea.getChildren().addAll(goalCheck,goToLevel);
		
		return goalArea;
	}

	private void initializeActionTypeBox() {

		ObservableList<String> actionTypes = FXCollections.observableArrayList();

		actionTypes.addAll(
				languageResources().getString("AlwaysRun"),
				languageResources().getString("NeedCode"));
				//languageResources().getString("OnCollision")
		actionTypeBox = new ChoiceBox<String>(actionTypes);
		actionTypeBox.getSelectionModel().select(0);

		actionTypeBox.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldSelect, newSelect) -> setKeycodeInputBoxVisibility(newSelect));

	}
	
	private void setKeycodeInputBoxVisibility(String select) {
		
		keycodeInputBox.setVisible(model.getVisibilty(select));

		if (!keycodeInputBox.isVisible()) {

			model.setCurrentCode(KeyCode.UNDEFINED);
			clearKeycodeInputBox();
		}
		
	}

	private Pane initializeActionPaneBoxes() {

		initializeActionTypeBox();

		keycodeInputBox = new TextField();
		keycodeInputBox.setOnKeyTyped(e -> clearKeycodeInputBox());
		keycodeInputBox.setVisible(false);
		keycodeInputBox.setPromptText(languageResources().getString(
				"KeycodePrompt"));
		keycodeInputBox.setOnKeyReleased(e -> setCurrentKeycode(e));
		actionValue = new TextField();
		
		HBox soundPane = makeAddSoundPane();
		
		// actionValue.setPromptText(languageResources().getString("ValuePrompt"));

		HBox actionPane = makeTwoSidedList(actionsToAddList, actionsAddedList,
				languageResources().getString("AddAction"), languageResources()
				.getString("RemoveAction"), e -> addAction(e),
				e -> removeAction(e), actionTypeBox, keycodeInputBox,
				actionValue, soundPane);

		return actionPane;

	}
	
	private HBox makeAddSoundPane() {
		HBox soundPane = new HBox();
		Button select = new Button(languageResources().getString("SelectSound"));
		Tooltip onSelectHover = new Tooltip(select.getText());
		select.setOnMouseEntered(e -> onSelectHover.show(select, e.getSceneX(), e.getSceneY()));
		select.setOnMouseExited(e -> onSelectHover.hide());
		soundPath = new TextField();
		soundPath.setEditable(false);
		select.setOnMouseClicked(e -> setSoundPathText());
		
		soundPane.getChildren().addAll(select, soundPath);
		return soundPane;
	}
	
	private void setSoundPathText() {
		soundPath.getStyleClass().remove(STRING.CSS.ERROR);
		try {
			File file = DataHandler.chooseFile(new Stage());
			if(file.getName().endsWith(".mp3") || file.getName().endsWith(".m4a")) {
				soundPath.setText(file.getPath());
			}
			else {
				soundPath.getStyleClass().add(STRING.CSS.ERROR);
			}
		} catch (NullPointerException e) {
			//IDC, do nothing
		}
		
	}
	
	private Pane initializeComponentPaneBoxes() {

		componentValue = new TextField();

		// componentValue.setPromptText(languageResources().getString("ValuePrompt"));
		HBox componentPane = makeTwoSidedList(componentsToAddList,
				componentsAddedList,
				languageResources().getString("AddComponent"),
				languageResources().getString("RemoveComponent"),
				e -> addComponent(e), e -> removeComponent(e), componentValue);

		return componentPane;

	}

	protected HBox makeTwoSidedList(ListView<String> toAddList,
			ListView<String> addedList, String addText, String removeText,
			EventHandler<MouseEvent> onAdd, EventHandler<MouseEvent> onRemove,
			Region... userTextFields) {

		HBox twoSidedListContainer = new HBox();

		VBox fieldsAndButtons = new VBox();
		fieldsAndButtons.setMaxWidth(Double.MAX_VALUE);
		fieldsAndButtons.getStyleClass().add(STRING.CSS.PANE);
		fieldsAndButtons.setAlignment(Pos.CENTER);
		Button add = new Button(addText);
		add.setOnMouseClicked(onAdd);
		Button delete = new Button(removeText);
		delete.setOnMouseClicked(onRemove);
		setPrefButtonWidth(add, delete);

		Arrays.asList(userTextFields).forEach(e -> {
			e.setMinWidth(DOUBLE.FIELDS_AND_BUTTONS_WIDTH);
			e.setMaxWidth(DOUBLE.FIELDS_AND_BUTTONS_WIDTH);
			fieldsAndButtons.getChildren().add(e);
		});
		fieldsAndButtons.getChildren().addAll(add, delete);

		twoSidedListContainer.getChildren().addAll(toAddList, fieldsAndButtons,
				addedList);

		return twoSidedListContainer;

	}

	private void setPrefButtonWidth(Button... buttons) {

		Arrays.asList(buttons).forEach(
				e -> e.setPrefWidth(INT.PREF_BUTTON_WIDTH));

	}

	private void initializeImageListPane() {

		imageListPane = new ListView<String>(model.setImagesAdded());

		imageListPane.setOnKeyPressed(e -> {

			if (e.getCode().equals(KeyCode.DELETE)
					|| e.getCode().equals(KeyCode.BACK_SPACE)) {
				// String selected =
				// imageListPane.getSelectionModel().getSelectedItem();
				// stringToImageMap.remove(selected);
				// imagesAdded.remove(selected);
			}

		});

		imageListPane.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldSelect, newSelect) -> onSelectNewImage(newSelect));
	}
	
	private void onSelectNewImage(String select) {
		
		paneForImage.getChildren().clear();
		paneForImage.getChildren().add(
				model.getSelectedImage(select));

	}

	private void addAction(MouseEvent e) {

		String selected = actionsToAddList.getSelectionModel()
				.getSelectedItem();
		if (selected != null) {

			try {
				model.addAction(selected, actionValue.getText(), actionTypeBox.getSelectionModel().getSelectedItem(), soundPath.getText());
				actionValue.getStyleClass().remove(STRING.CSS.ERROR);
			} catch (InstantiationException | IllegalAccessException
					| InvocationTargetException | NoSuchMethodException
					| ClassNotFoundException e1) {
				System.out.println("Fix your constructors");
			} catch (NumberFormatException e1) {
				actionValue.getStyleClass().add(STRING.CSS.ERROR);
			}
			
			soundPath.clear();
			keycodeInputBox.clear();
			actionValue.clear();

		}

	}

	private void removeAction(MouseEvent e) {

		String selected = actionsAddedList.getSelectionModel().getSelectedItem();
		model.removeAction(selected);
	}

	private void addComponent(MouseEvent e) {

		String selected = componentsToAddList.getSelectionModel().getSelectedItem();

		if (selected != null) {

			try {
				
				model.addComponent(selected, componentValue.getText());
				componentValue.getStyleClass().remove(STRING.CSS.ERROR);

			} catch (InstantiationException | IllegalAccessException
					| InvocationTargetException | NoSuchMethodException
					| ClassNotFoundException e1) {
				System.out.println("Fix your constructors");
			} catch (NumberFormatException e1) {
				componentValue.getStyleClass().add(STRING.CSS.ERROR);
			}

			componentValue.clear();

		}
	}

	private void removeComponent(MouseEvent e) {

		String selected = componentsAddedList.getSelectionModel().getSelectedItem();
		model.removeComponent(selected);
		
	}
	private void clearKeycodeInputBox() {
		keycodeInputBox.setText("");
	}

	private void setCurrentKeycode(KeyEvent e) {

		model.setCurrentCode(e.getCode());
		keycodeInputBox.setText(e.getCode().getName());

	}

	private void selectImageFile() {

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
		
		model.addImageToList(path, spriteImageRep);
		if (model.getNumImages() == 1) {
			paneForImage.getChildren().add(spriteImageRep);
		}

		imageSizeField.clear();
	}


	private void exit(Sprite sprite) {

		controller.returnToMainPage(loggedIn);
		

	}
	
	private void exit() {
		
		controller.returnToMainPage(loggedIn);


	}

	private void saveAndExit() {

		if (spriteNameField.getText().isEmpty()) {
			spriteNameField.getStyleClass().add(STRING.CSS.ERROR);
		}
		
		else {
			try {
				model.saveSprite(spriteNameField.getText(), tagChoicesHolder.getSelectionModel().getSelectedItem(), goalCheck.isSelected(), Integer.parseInt(goToLevel.getText()));
				exit(model.retrieveEditedSprite());
			}
			catch (NumberFormatException e) {
				goToLevel.getStyleClass().add(STRING.CSS.ERROR);
			}
		}

	}

	private void drawSpriteOnScreen(Sprite sprite) {

		spriteNameField.setText(sprite.getName());
		tagChoicesHolder.getSelectionModel().select(sprite.tag());
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

