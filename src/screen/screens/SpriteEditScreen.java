package screen.screens;

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
import java.util.Set;
import java.util.stream.Collectors;

import data.DataHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
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
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.controllers.ScreenController;
import screen.controllers.SpriteEditScreenController;
import sprite.Sprite;
import sprite.SpriteImage;
import util.ImageToInt2DArray;


/**
 * 
 * @author Leo
 *
 */

public class SpriteEditScreen extends Screen {

	// Instance Variables
	private SpriteEditScreenController controller;
	private Tab levelEditScreen;

	private Sprite editableSprite;

	private TextField spriteNameField;
	private TextField imageSizeField;
	private ChoiceBox<String> tagChoicesHolder;

	private StackPane paneForImage;
	private ListView<String> imageListPane;

	private ResourceBundle actionResources;
	private ResourceBundle componentResources;
	private ResourceBundle behaviorLabels;

	private ObservableList<String> actionsToAdd;
	private ObservableList<String> actionsAdded;
	private ObservableList<String> componentsToAdd;
	private ObservableList<String> componentsAdded;
	private ObservableList<String> imagesAdded;
	
	private ListView<String> actionsToAddList;
	private ListView<String> actionsAddedList;
	private ListView<String> componentsToAddList;
	private ListView<String> componentsAddedList;

	private Text dataText;

	private ChoiceBox<String> actionTypeBox;
	private TextField keycodeInputBox;
	private TextField actionValue;
	private TextField componentValue;

	private KeyCode currentCode = KeyCode.UNDEFINED;

	private Map<String, String> classPathMap;
	private Map<String, Action> actionMap;
	private Map<String, Boolean> keyCodesAreVisibleMap;
	private Map<String, Component> componentMap;
	private Map<String, ImageAndFilePair> stringToImageMap;
	private Map<String, String> behaviorLabelsMap;
	private Map<String, String> createdBehaviorParameterMap;


	// Constructors & Helpers
	public SpriteEditScreen(SpriteEditScreenController parent, Tab levelEditScreen, double width, double height) {

		this(parent, levelEditScreen, width, height, null);

	}

	public SpriteEditScreen(SpriteEditScreenController parent, Tab levelEditScreen, double width, double height, Sprite spriteToEdit) {

		super(width, height);

		this.levelEditScreen = levelEditScreen;
		this.controller = parent;

		initializeRelevantResourceFiles();
		initializeObservableLists();
		initializeValueBoxListenersForLists();
		initializeInformationListenersForLists();
		initializeClassPathMap();
		initializeKeyCodesAreVisibleMap();
		initializeBehaviorLabelsMap();
		// initializeOtherMaps(buttonToListMap,actionMap,componentMap);

		actionMap = new HashMap<>();
		componentMap = new HashMap<>();
		stringToImageMap = new HashMap<>();
		createdBehaviorParameterMap = new HashMap<>();

		createLeftPane();
		createRightPane();
		createCenterPane();
		
		if (spriteToEdit != null) {
			drawSpriteOnScreen(spriteToEdit);
		}
		
		editableSprite = new Sprite();


	}
	
	public void tagsForUse(Set<String> tags) {
		tagChoicesHolder.setItems(FXCollections.observableArrayList(tags));
	}

	// All other instance methods
	private void initializeKeyCodesAreVisibleMap() {

		keyCodesAreVisibleMap = new HashMap<>();
		keyCodesAreVisibleMap.put(languageResources().getString("AlwaysRun"),
				false);
		keyCodesAreVisibleMap.put(languageResources().getString("NeedCode"),
				true);
//		keyCodesAreVisibleMap.put(languageResources().getString("OnCollision"),
//				false);

	}

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

		actionsToAdd = FXCollections.observableArrayList(actionResources
				.keySet().stream().map(e -> languageResources().getString(e))
				.collect(Collectors.toList()));
		actionsAdded = FXCollections.observableArrayList();
		componentsToAdd = FXCollections.observableArrayList(componentResources
				.keySet().stream().map(e -> languageResources().getString(e))
				.collect(Collectors.toList()));
		componentsAdded = FXCollections.observableArrayList();
		imagesAdded = FXCollections.observableArrayList();

		actionsToAddList = new ListView<>(actionsToAdd);
		actionsAddedList = new ListView<>(actionsAdded);
		componentsToAddList = new ListView<>(componentsToAdd);
		componentsAddedList = new ListView<>(componentsAdded);

	}

	//TODO get rid of some duplicated code in this method
	private void initializeValueBoxListenersForLists() {
		
		actionsToAddList.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> onAddListener(newValue,actionValue));
		componentsToAddList.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> onAddListener(newValue,componentValue));
				
	}
	
	private void onAddListener(String newSelect, TextField textField) {
		
		if (newSelect == null || newSelect.isEmpty()) {
			textField.setPromptText("");
		}
		else {
			textField.setPromptText(behaviorLabelsMap
					.get(newSelect));
		}
		
	}

	private void initializeInformationListenersForLists() {

		actionsAddedList.setOnMouseEntered(e -> setDataText(actionsAddedList));
		componentsAddedList.setOnMouseEntered(e -> setDataText(componentsAddedList));

		actionsAddedList.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldSelect, newSelect) -> setDataText(newSelect));
		componentsAddedList.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldSelect, newSelect) -> setDataText(newSelect));

	}
	
	private void setDataText(String text) {
		dataText.setText(createdBehaviorParameterMap.get(text));
	}

	private void setDataText(ListView<String> listInFocus) {

		dataText.setText(createdBehaviorParameterMap.get(listInFocus
				.getSelectionModel().getSelectedItem()));
	}

	// @SuppressWarnings("unchecked")
	// protected void initializeOtherMaps(Map<? extends Object, ? extends
	// Object>... maps) {
	// Arrays.asList(maps).forEach(e -> { e = new HashMap<>(); });
	// }

	private void initializeClassPathMap() {

		classPathMap = new HashMap<>();

		actionResources.keySet().forEach(
				e -> classPathMap.put(
						languageResources().getString(e),
						actionResources.getString(e)));

		componentResources.keySet().forEach(
				e -> classPathMap.put(
						languageResources().getString(e),
						componentResources.getString(e)));

	}

	private void initializeBehaviorLabelsMap() {

		behaviorLabelsMap = new HashMap<String, String>();

		behaviorLabels.keySet().forEach(
				e -> behaviorLabelsMap.put(
						languageResources().getString(e),
						behaviorLabels.getString(e)));

	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

		Menu fileMenu = makeFileMenu(
				e -> saveSprite(), 
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

		VBox actionAndComponentPane = new VBox();
		actionAndComponentPane.getChildren().addAll(actionPane, componentPane,
				dataPane);

		return actionAndComponentPane;

	}

	private Node createDataPane() {

		HBox textArea = new HBox();

		textArea.getStyleClass().add(STRING.CSS.PANE);
		dataText = new Text(languageResources().getString("DataText"));
		textArea.getChildren().add(dataText);

		return textArea;

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
		
		keycodeInputBox.setVisible(
				keyCodesAreVisibleMap.get(select));

		if (!keycodeInputBox.isVisible()) {

			currentCode = KeyCode.UNDEFINED;
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
		// actionValue.setPromptText(languageResources().getString("ValuePrompt"));

		HBox actionPane = makeTwoSidedList(actionsToAddList, actionsAddedList,
				languageResources().getString("AddAction"), languageResources()
				.getString("RemoveAction"), e -> addAction(e),
				e -> removeAction(e), actionTypeBox, keycodeInputBox,
				actionValue);

		return actionPane;

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
			Control... userTextFields) {

		HBox twoSidedListContainer = new HBox();

		VBox fieldsAndButtons = new VBox();
		fieldsAndButtons.getStyleClass().add(STRING.CSS.PANE);
		fieldsAndButtons.setAlignment(Pos.CENTER);
		Button add = new Button(addText);
		add.setOnMouseClicked(onAdd);
		Button delete = new Button(removeText);
		delete.setOnMouseClicked(onRemove);
		setPrefButtonWidth(add, delete);

		Arrays.asList(userTextFields).forEach(
				e -> fieldsAndButtons.getChildren().add(e));
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

		imageListPane = new ListView<String>(imagesAdded);

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
				stringToImageMap.get(select).image());

	}

	private void addAction(MouseEvent e) {

		String selected = actionsToAddList.getSelectionModel()
				.getSelectedItem();
		if (selected != null) {

			try {
				KeyCode[] keylist = new KeyCode[1];
				if (currentCode != null
						&& !currentCode.equals(KeyCode.UNDEFINED)) {
					keylist[0] = currentCode;
				}
				Action action = (Action) Class
						.forName(classPathMap.get(selected))
						.getConstructor(Sprite.class, Double.class,
								KeyCode[].class)
								.newInstance(editableSprite,
										Double.parseDouble(actionValue.getText()),
										keylist);
				if(!keyCodesAreVisibleMap.get(actionTypeBox.getSelectionModel().getSelectedItem())) {
					action.runEveryFrame();
				}
				String parameterMapValue = selected + "-> "
						+ languageResources().getString("Keycode") + " "
						+ currentCode.toString() + ", "
						+ languageResources().getString("Value") + " "
						+ actionValue.getText();
				addBehaviorToListView(action, parameterMapValue, actionsToAdd, actionsAdded, actionMap, true);
				actionValue.getStyleClass().remove(STRING.CSS.ERROR);
			} catch (InstantiationException | IllegalAccessException
					| InvocationTargetException | NoSuchMethodException
					| ClassNotFoundException e1) {
				System.out.println("Fix your constructors");
			} catch (NumberFormatException e1) {
				actionValue.getStyleClass().add(STRING.CSS.ERROR);
			}

			keycodeInputBox.clear();
			actionValue.clear();

		}

	}

	private void removeAction(MouseEvent e) {

		String selected = actionsAddedList.getSelectionModel().getSelectedItem();

		if (selected != null) {
			
			addBehaviorToListView(actionMap.get(selected), "", actionsToAdd, actionsAdded, actionMap, false);

		}

	}

	private void addComponent(MouseEvent e) {

		String selected = componentsToAddList.getSelectionModel().getSelectedItem();

		if (selected != null) {

			try {

				List<Double> values = new ArrayList<>();
				values.add(Double.parseDouble(componentValue.getText()));
				Component component = (Component) Class
						.forName(classPathMap.get(selected))
						.getConstructor(Sprite.class, List.class)
						.newInstance(editableSprite, values);
				String parameterMapValue = selected + "-> "
						+ languageResources().getString("Value") + " "
						+ componentValue.getText();
				addBehaviorToListView(component, parameterMapValue, componentsToAdd, componentsAdded, componentMap, true);
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

		if (selected != null) {

			addBehaviorToListView(componentMap.get(selected),"",componentsToAdd,componentsAdded,componentMap,false);

		}
	}

	private void clearKeycodeInputBox() {
		keycodeInputBox.setText("");
	}

	private void setCurrentKeycode(KeyEvent e) {

		currentCode = e.getCode();
		keycodeInputBox.setText(currentCode.getName());

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

		int currentImageNumber = imagesAdded.size();
		String imageName = languageResources().getString("ImageName") + currentImageNumber;

		stringToImageMap.put(imageName, new ImageAndFilePair(spriteImageRep,path));
		imagesAdded.add(imageName);

		if (currentImageNumber == 0) {
			paneForImage.getChildren().add(spriteImageRep);
		}

		imageSizeField.clear();
	}

	private void saveSprite() {
		
		//TODO don't allow save if name isn't written 

		stringToImageMap.keySet().forEach(
				e -> {
					//TODO - need this to take in multiple image paths
					editableSprite.setImagePath(stringToImageMap.get(e).filePath());
					editableSprite.setDimensions(new Dimension2D((stringToImageMap).get(e).image().getImage().getWidth(),
														(stringToImageMap).get(e).image().getImage().getHeight()));
				});

		componentMap.keySet().forEach(e -> {
			editableSprite.addComponent(componentMap.get(e));
		});

		actionMap.keySet().forEach(e -> {
			editableSprite.addAction(actionMap.get(e));
		});

		editableSprite.setName(spriteNameField.getText());
		editableSprite.setTag(tagChoicesHolder.getSelectionModel().getSelectedItem());

	}

	private void exit() {

		LevelEditScreen levelEdit = (LevelEditScreen) levelEditScreen
				.getContent();

		controller.returnToSelectedLevel(levelEdit, levelEditScreen,
				editableSprite);

	}

	private void saveAndExit() {

		if (spriteNameField.getText().isEmpty()) {
			spriteNameField.getStyleClass().add(STRING.CSS.ERROR);
		}

		else {

			saveSprite();
			exit();

		}

	}

	private void drawSpriteOnScreen(Sprite sprite) {

		spriteNameField.setText(sprite.getName());
		tagChoicesHolder.getSelectionModel().select(sprite.tag());
		sprite.actionList().forEach(action -> {
			addBehaviorToListView(action, "", actionsToAdd, actionsAdded, actionMap, true);
		});
		sprite.componentList().forEach(component -> addBehaviorToListView(component, "", componentsToAdd, componentsAdded, componentMap, true));
		// TODO implement
		if(sprite.getImagePath()!=null) {
			Image image = DataHandler.fileToImage(new File(sprite.getImagePath()), sprite.dimensions().getWidth(), sprite.dimensions().getHeight(), false);
			addImageToPane(sprite.getImagePath(),image);
		}

	}
	
	
	/**
	 * 
	 * @param behavior - Must be an action or component unless a new type of behavior is added 
	 * @param valueString - This is the string that displays when selecting an added behavior
	 * @param start - (behavior)ToAdd observable list
	 * @param end - (behavior)Added obserable list
	 * @param behaviorMap - Map from a string to a behavior, <String,Behavior> *DO NOT USE ANY OTHER TYPES OF MAPS*
	 * @param adding - boolean that signifies whether the behavior is being added or removed
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addBehaviorToListView(Object behavior, String valueString, ObservableList<String> start, ObservableList<String> end, Map behaviorMap, boolean adding) {
		String stringToSwitch = languageResources().getString(trimClassName(behavior.getClass().getName()));
		if(adding) {
			createdBehaviorParameterMap.put(stringToSwitch, valueString);
			start.remove(stringToSwitch);
			end.add(stringToSwitch);
			behaviorMap.put(stringToSwitch, behavior);
		}
		else {
			createdBehaviorParameterMap.remove(stringToSwitch);
			start.add(stringToSwitch);
			end.remove(stringToSwitch);
			behaviorMap.remove(stringToSwitch);
		}
	}
	
	private String trimClassName(String classPath) {
		String[] split = classPath.split("\\.");
		return split[split.length - 1];
	}

}
