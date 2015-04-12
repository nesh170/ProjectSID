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
import sprite.SpriteImage;import util.ImageToInt2DArray;


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
	private TextField imageSizeField;
	private ChoiceBox<String> tagChoicesHolder;
	
	private StackPane paneForImage;
	private ListView<String> imageListPane;

	private ResourceBundle tagResources;
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
	
	private Map<String,String> classPathMap;
	private Map<String, Action> actionMap;
	private Map<String,Boolean> keyCodesAreVisibleMap;
	private Map<String, Component> componentMap;
	private Map<String, ImageView> stringToImageMap;
	private Map<String, String> behaviorLabelsMap;
	private Map<String, String> createdBehaviorParameterMap;
	
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
			if(editableSprite.spriteImage()==null) {
				editableSprite.setSpriteImage(new SpriteImage());
			}
			
		}
		
		else {
			editableSprite = new Sprite();
			editableSprite.setSpriteImage(new SpriteImage());
		}
		
		initializeRelevantResourceFiles();
		initializeObservableLists();
		initializeValueBoxListenersForLists();
		initializeInformationListenersForLists();
		initializeClassPathMap();
		initializeKeyCodesAreVisibleMap();
		initializeBehaviorLabelsMap();
//		initializeOtherMaps(buttonToListMap,actionMap,componentMap);
		
		actionMap = new HashMap<>();
		componentMap = new HashMap<>();
		stringToImageMap = new HashMap<>();
		createdBehaviorParameterMap = new HashMap<>();
		
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
		behaviorLabels = ResourceBundle.getBundle("resources.spritePartProperties.behaviorlabels");
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
		imagesAdded = FXCollections.observableArrayList();
		
		actionsToAddList = new ListView<>(actionsToAdd);
		actionsAddedList = new ListView<>(actionsAdded);
		componentsToAddList = new ListView<>(componentsToAdd);
		componentsAddedList = new ListView<>(componentsAdded);
		

		
	}
	
	private void initializeValueBoxListenersForLists() {
		
		actionsToAddList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> ov,
					String oldSelect, String newSelect) {
				if(newSelect==null || newSelect.isEmpty()) {
					actionValue.setPromptText("");
				}
				else {
					actionValue.setPromptText(behaviorLabelsMap.get(newSelect));
				}
				
			}
			
		});
		
		componentsToAddList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> ov,
					String oldSelect, String newSelect) {
				if(newSelect==null || newSelect.isEmpty()) {
					componentValue.setPromptText("");
				}
				else {
					componentValue.setPromptText(behaviorLabelsMap.get(newSelect));
				}
			}
			
		});
	}
	
	private void initializeInformationListenersForLists() {
		
		ChangeListener<String> changeListener = new ChangeListener<String> () {

			@Override
			public void changed(ObservableValue<? extends String> ov,
					String oldSelect, String newSelect) {
				dataText.setText(createdBehaviorParameterMap.get(newSelect));
				
			}
			
		};
		
		actionsAddedList.setOnMouseEntered(e -> setDataText(actionsAddedList));
		componentsAddedList.setOnMouseEntered(e -> setDataText(componentsAddedList));
		
		actionsAddedList.getSelectionModel().selectedItemProperty().addListener(changeListener);		
		componentsAddedList.getSelectionModel().selectedItemProperty().addListener(changeListener);
		
	}
	
	private void setDataText(ListView<String> listInFocus) {
		dataText.setText(createdBehaviorParameterMap.get(listInFocus.getSelectionModel().getSelectedItem()));
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
	
	private void initializeBehaviorLabelsMap() {
		behaviorLabelsMap = new HashMap<String,String>();
		behaviorLabels.keySet().forEach(e -> behaviorLabelsMap.put(languageResources().getString(e),behaviorLabels.getString(e)));
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
//		Node physicsPane = createPhysicsPane();
		initializeImageListPane();
		
		rightPane.getChildren().addAll(actionAndComponentPane,imageListPane);
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
		
		VBox imageButtonAndSizeText = new VBox();
		VBox.setVgrow(imageButtonAndSizeText, Priority.ALWAYS);
		imageButtonAndSizeText.setAlignment(Pos.CENTER);
		
		Button imageButton = new Button();
		imageButton.setGraphic(new ImageView(new Image("images/addimage.png")));
		imageButton.setOnMouseClicked(e -> selectImageFile());
		
		imageSizeField = new TextField();
		imageSizeField.setPromptText(languageResources().getString("ImageSize"));
		
		imageButtonAndSizeText.getChildren().addAll(imageButton,imageSizeField);
		
		imagePane.getChildren().add(imageButtonAndSizeText);
		imagePane.getStyleClass().add("pane");
						
		return imagePane;
	}
	
	private VBox createActionAndComponentPane() {
		
		Pane actionPane = initializeActionPaneBoxes();
		Pane componentPane = initializeComponentPaneBoxes();
		Node dataPane = createDataPane();
		
		VBox actionAndComponentPane = new VBox();
		actionAndComponentPane.getChildren().addAll(actionPane,componentPane,dataPane);
		
		return actionAndComponentPane;
	}
	
	private Node createDataPane() {
		HBox textArea = new HBox();
		textArea.getStyleClass().add("pane");
		dataText = new Text(languageResources().getString("DataText"));
		textArea.getChildren().add(dataText);
		return textArea;
	}
	
	private void initializeActionTypeBox() {
		ObservableList<String> actionTypes = FXCollections.observableArrayList();
		actionTypes.addAll(
				languageResources().getString("AlwaysRun"),
				languageResources().getString("NeedCode"),
				languageResources().getString("OnCollision"));
		actionTypeBox = new ChoiceBox<String>(actionTypes);
		actionTypeBox.getSelectionModel().select(0);
		actionTypeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue<? extends String> ov, String oldSelect, String newSelect) {
				keycodeInputBox.setVisible(keyCodesAreVisibleMap.get(newSelect));
				if(!keycodeInputBox.isVisible()) {
					currentCode = KeyCode.UNDEFINED;
					clearKeycodeInputBox();
				}
			}
			
		});
	}
	
	private Pane initializeActionPaneBoxes() {
		initializeActionTypeBox();
				
		keycodeInputBox = new TextField();
		keycodeInputBox.setOnKeyTyped(e -> clearKeycodeInputBox());
		keycodeInputBox.setVisible(false);
		keycodeInputBox.setPromptText(languageResources().getString("KeycodePrompt"));
		keycodeInputBox.setOnKeyReleased(e -> setCurrentKeycode(e));
		actionValue = new TextField();
//		actionValue.setPromptText(languageResources().getString("ValuePrompt"));
		
		HBox actionPane = makeTwoSidedList(actionsToAddList,actionsAddedList,
											languageResources().getString("AddAction"),languageResources().getString("RemoveAction"),
											e -> addAction(e), e -> removeAction(e),
											actionTypeBox,keycodeInputBox,actionValue);
		
		return actionPane;
	}
	private Pane initializeComponentPaneBoxes() {
				
		componentValue = new TextField();
//		componentValue.setPromptText(languageResources().getString("ValuePrompt"));
		HBox componentPane = makeTwoSidedList(componentsToAddList,componentsAddedList,
											languageResources().getString("AddComponent"),languageResources().getString("RemoveComponent"),
											e -> addComponent(e), e -> removeComponent(e),
											componentValue);
		
		return componentPane;
		
	}

	protected HBox makeTwoSidedList(ListView<String> toAddList, ListView<String> addedList,
									String addText, String removeText,
									EventHandler<MouseEvent> onAdd, EventHandler<MouseEvent> onRemove,
									Control... userTextFields) {
		
		HBox twoSidedListContainer = new HBox();
		
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
				
		twoSidedListContainer.getChildren().addAll(toAddList,fieldsAndButtons,addedList);
		
		return twoSidedListContainer;

	}
		
	private void setPrefButtonWidth(Button... buttons) {
		Arrays.asList(buttons).forEach(e -> e.setPrefWidth(INT.PREF_BUTTON_WIDTH));
	}
	
	private void initializeImageListPane() {
		
		imageListPane = new ListView<String>(imagesAdded);
		
		imageListPane.setOnKeyPressed(e -> {
			if(e.getCode().equals(KeyCode.DELETE) || e.getCode().equals(KeyCode.BACK_SPACE))  {
//				String selected = imageListPane.getSelectionModel().getSelectedItem();
//				stringToImageMap.remove(selected);
//				imagesAdded.remove(selected);
			}
		});
		
		imageListPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> ov,
					String oldSelect, String newSelect) {
				
				paneForImage.getChildren().clear();
				paneForImage.getChildren().add(stringToImageMap.get(newSelect));
				
//				Map<String,ImageView> newMap = new HashMap<>();
//				imagesAdded.clear();
//				int counter = 0;
//				for(String key:stringToImageMap.keySet()) {
//					String imageName = languageResources().getString("ImageName")+counter;
//					newMap.put(imageName,stringToImageMap.get(key));
//					imagesAdded.add(imageName);
//					counter++;
//				}
//				stringToImageMap = newMap;

			}
			
		});

	}
	
	private void addAction(MouseEvent e) {
		String selected = actionsToAddList.getSelectionModel().getSelectedItem();
		if(selected!=null) {
			
			try {
				KeyCode[] keylist = new KeyCode[1];
				if(currentCode!=null && !currentCode.equals(KeyCode.UNDEFINED)) {
					keylist[0] = currentCode;
				}
				Action action = (Action) Class.forName(classPathMap.get(selected))
												.getConstructor(Sprite.class,Double.class,KeyCode[].class)
												.newInstance(editableSprite,Double.parseDouble(actionValue.getText()),keylist);
				actionMap.put(selected, action);
				actionsToAdd.remove(selected);
				actionsAdded.add(selected);
				createdBehaviorParameterMap.put(selected,selected+"-> "+languageResources().getString("Keycode")+" "+currentCode.toString()+", "+languageResources().getString("Value")+" "+actionValue.getText());
				actionValue.getStyleClass().remove("text-field-error");
			} catch (InstantiationException | IllegalAccessException
					| InvocationTargetException | NoSuchMethodException | ClassNotFoundException e1) {
				System.out.println("Fix your constructors");
			} catch (NumberFormatException e1) {
				actionValue.getStyleClass().add("text-field-error");
			}
			
			keycodeInputBox.clear();
			actionValue.clear();
		}
	}
	
	private void removeAction(MouseEvent e) {
		String selected = actionsAddedList.getSelectionModel().getSelectedItem();
		if(selected!=null) {
			actionsAdded.remove(selected);
			actionsToAdd.add(selected);
			actionMap.keySet().remove(selected);
		}
	}
	
	private void addComponent(MouseEvent e) {
		String selected = componentsToAddList.getSelectionModel().getSelectedItem();
		if(selected!=null) {
			try {
				List<Double> values = new ArrayList<>();
				values.add(Double.parseDouble(componentValue.getText()));
				Component component = (Component) Class.forName(classPathMap.get(selected))
						.getConstructor(Sprite.class,List.class)
						.newInstance(editableSprite,values);
				componentMap.put(selected, component);
				componentsToAdd.remove(selected);
				componentsAdded.add(selected);
				createdBehaviorParameterMap.put(selected,selected+"-> "+languageResources().getString("Value")+" "+componentValue.getText());
				componentValue.getStyleClass().remove("text-field-error");

			} catch(InstantiationException | IllegalAccessException
					| InvocationTargetException | NoSuchMethodException | ClassNotFoundException e1) {
				System.out.println("Fix your constructors");
			} catch (NumberFormatException e1) {
				componentValue.getStyleClass().add("text-field-error");
			}
			
			
			
			componentValue.clear();
		}
	}
	
	private void removeComponent(MouseEvent e) {
		String selected = componentsAddedList.getSelectionModel().getSelectedItem();
		if(selected!=null) {
			componentsAdded.remove(selected);
			componentsToAdd.add(selected);
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
			if(imageSizeField.getText()!="" && imageSizeField.getText().matches("^[0-9]+$")) {
				imageSize = Integer.parseInt(imageSizeField.getText());
			}
			File file = DataHandler.chooseFile(new Stage());
			Image image = DataHandler.fileToImage(file,imageSize,imageSize,true);
			ImageView spriteImageRep = new ImageView(image);
			int currentImageNumber = imagesAdded.size();
			String imageName = languageResources().getString("ImageName")+currentImageNumber;
			stringToImageMap.put(imageName, spriteImageRep);
			imagesAdded.add(imageName);
			
			if(currentImageNumber == 0) {
				paneForImage.getChildren().add(spriteImageRep);
			}
			
			imageSizeField.clear();
						
		}
		catch (NullPointerException e) {
			//do nothing
		}
	}
	
	private void saveSprite() {
		stringToImageMap.keySet().forEach(e-> {
			Image image = stringToImageMap.get(e).getImage();
			int[][] convertedImage = ImageToInt2DArray.convertImageTo2DIntArray(image, (int) image.getWidth(), (int) image.getHeight());
			editableSprite.spriteImage().addImage(convertedImage);
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
		LevelEditScreen levelEdit = (LevelEditScreen) levelEditScreen.getContent();
		controller.returnToSelectedLevel(levelEdit, levelEditScreen, editableSprite);
	}
	
	private void saveAndExit() {
		if(spriteNameField.getText().isEmpty()) {
			
			spriteNameField.getStyleClass().add("text-field-error");
			
		}
		
		else{
			
			saveSprite();
			exit();

		}
		
	}

	// All other instance methods
	private void drawSpriteOnScreen(Sprite sprite) {
		//TODO implement
	}

}
