package screen.screens;

import game.Game;
import gameEngine.actions.KillAction;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import levelPlatform.splashScreen.SplashScreen;
import resources.constants.COLOR;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.controllers.SplashEditScreenController;
import screen.screenmodels.SplashEditModel;
import sprite.Sprite;

/**
/* Screen to create a splash screen
 * 
 * @author Kyle Milden
 *
 */
public class SplashEditScreen extends LevelPlatformCapableScreen {
	
	private double width;
	private double height;
	private int counter;
	private Game game;
	private SplashEditScreenController controller;
	private SplashEditModel splashEditModel;
	private String tag;
	private Map<Sprite, Integer> goalMap;

	/**
	 * This is the constructor to create a Splash Edit Screen
	 * 
	 * @param parent
	 * @param game
	 * @param width
	 * @param height
	 * @param splashScreen
	 */
	public SplashEditScreen(SplashEditScreenController parent, Game game, double width, double height, SplashScreen splashScreen) {
		super(width, height);
		
		this.controller = parent;
		this.game = game;
		
		configureSplashScreen(splashScreen, width, height);
		configureButtons();
		configureDisplayArea();
		this.getStyleClass().add(STRING.CSS.PANE);
		goalMap = new HashMap<Sprite, Integer>();
	}
	
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		//NO MENUBAR FOR THIS CLASS
	}
	
	private void configureSplashScreen(SplashScreen splashScreen, double width, double height) {
		splashEditModel = new SplashEditModel(splashScreen);
		this.width = width;
		this.height = height;
	}
	
	private void configureButtons() {
		Button addImage = makeAddImageButton();
		Button addBackgroundImage = makeAddBackgroundImageButton();
		TextField textField = makeAddTextTextField();
		ColorPicker colorPicker = createColorPicker();
		ComboBox<String> fontPicker = createFontPicker();
		Button addText = makeAddTextButton(textField, colorPicker, fontPicker);
		Button addAnimation = makeAddAnimationButton();
		Button save = makeSaveButton();
		Button trash = makeTrashButton();
		Button back = makeBackButton();
		HBox hbox = createBackButtonAndTextFields(back);
		
		this.viewableArea().setRight(createAddButtons(addImage, addBackgroundImage, addText, textField, colorPicker, fontPicker, addAnimation));
		this.viewableArea().setBottom(createSaveAndTrashButtons(save,trash));
		this.viewableArea().setTop(hbox);
	}
	
	private void configureDisplayArea() {
		double rectWidth = width-DOUBLE.SPLASH_EDIT_SCREEN_LARGE_BUTTON_WIDTH;
		double rectHeight = height-DOUBLE.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT;
		
		Rectangle displayArea = new Rectangle(rectWidth, rectHeight, COLOR.DEFAULT_SPLASH_SCREEN_COLOR);
		
		this.viewableArea().setLeft(displayArea);
		this.setOnMouseClicked(e -> add(e, displayArea));
	}

	private VBox createAddButtons(Button addImage, Button addBackgroundImage, Button addText, TextField textField, ColorPicker colorPicker, ComboBox fontPicker, Button addAnimation) {	
		VBox allAddButtons = new VBox(INT.SPLASH_EDIT_SCREEN_VERTICAL_SPACING);
		VBox addTextVBox = new VBox(INT.SPLASH_EDIT_ADD_TEXT_VBOX_HEIGHT);
		
		addTextVBox.getChildren().addAll(addText, textField, colorPicker, fontPicker);
		allAddButtons.setAlignment(Pos.CENTER);
		allAddButtons.getChildren().addAll(addImage, addBackgroundImage, addTextVBox, addAnimation);
		
		return allAddButtons;	
	}
	
	private HBox createSaveAndTrashButtons(Button save, Button trash) {	
		HBox saveAndTrashButtons = new HBox(INT.SPLASH_EDIT_SCREEN_HORIZONTAL_SPACING);
		saveAndTrashButtons.getChildren().addAll(save, trash);
		
		return saveAndTrashButtons;	
	}

	private HBox createBackButtonAndTextFields(Button back) {	
		HBox hbox = new HBox(INT.SPLASH_EDIT_BACK_BUTTON_HBOX_SPACING);
		HBox imageHBox = new HBox();
		HBox textHBox = new HBox();
		
		Text imageText = new Text(STRING.SPLASH_EDIT_SCREEN.ENTER_IMAGE_INDEX);
		Text textText = new Text(STRING.SPLASH_EDIT_SCREEN.ENTER_TEXT_CONTENT);
		
		TextField imageTextField = createTextField();
		TextField textTextField = createTextField();
		
		imageTextField.setOnAction(e -> chooseImage(imageTextField));
		textTextField.setOnAction(f -> chooseText(textTextField));
		
		imageHBox.getChildren().addAll(imageText, imageTextField);
		textHBox.getChildren().addAll(textText, textTextField);
		
		imageHBox.setAlignment(Pos.BOTTOM_CENTER);
		textHBox.setAlignment(Pos.BOTTOM_CENTER);
		
		hbox.getChildren().addAll(back, imageHBox, textHBox);
		hbox.setAlignment(Pos.BOTTOM_LEFT);
		
		return hbox;	
	}

	private Button makeAddImageButton() {
		Button addImage = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_IMAGE);
		setLargeButtonSize(addImage);
		addImage.setOnMouseClicked(e -> addImage(e));
		
		return addImage;
	}
	
	private Button makeAddBackgroundImageButton() {
		Button addBackgroundImage = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_BACKGROUND_IMAGE);
		setLargeButtonSize(addBackgroundImage);	
		addBackgroundImage.setOnMouseClicked(e -> addBackgroundImage());
		
		return addBackgroundImage;
	}

	private Button makeAddTextButton(TextField textField, ColorPicker colorPicker, ComboBox fontPicker) {	
		Button addText = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_TEXT);
		setLargeButtonSize(addText);	
		addText.setOnMouseClicked(e -> addText(textField, colorPicker.getValue(), fontPicker));
		
		return addText;	
	}
	
	private TextField makeAddTextTextField() {	
		TextField textField = new TextField();
		
		return textField;	
	}
	
	private ColorPicker createColorPicker() {
		ColorPicker colorPicker = new ColorPicker();
		colorPicker.setMinHeight(INT.SPLASH_EDIT_COLOR_PICKER_HEIGHT);
		
		return colorPicker;
	}
	
	private ComboBox<String> createFontPicker() {
		String[] fonts = new String[]{STRING.SPLASH_EDIT_SCREEN.APPLE_CHANCERY, STRING.SPLASH_EDIT_SCREEN.ARIAL, 
				STRING.SPLASH_EDIT_SCREEN.CENTURY_GOTHIC, STRING.SPLASH_EDIT_SCREEN.MARKER_FELT,
				STRING.SPLASH_EDIT_SCREEN.MONOTYPE_CORSIVA, STRING.SPLASH_EDIT_SCREEN.TIMES, STRING.SPLASH_EDIT_SCREEN.VERDANA};

		final ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll(fonts); 
		comboBox.setMinWidth(INT.SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH);
		
		return comboBox;
	}	

	private Button makeAddAnimationButton() {
		Button addAnimation = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_ANIMATION);
		setLargeButtonSize(addAnimation);
		addAnimation.setOnMouseClicked(e -> addAnimation());
		
		return addAnimation;
	}

	private Button makeSaveButton() {
		Button save = new Button(STRING.SPLASH_EDIT_SCREEN.SAVE);
		setSmallButtonSize(save);
		save.setOnMouseClicked(e -> saveSplashScreen(e));
		
		return save;
	}

	private Button makeTrashButton() {	
		Button trash = new Button(STRING.SPLASH_EDIT_SCREEN.TRASH);
		setSmallButtonSize(trash);	
		trash.setOnMouseClicked(e -> trashSplashScreen());	
		
		return trash;	
	}
	
	private Button makeBackButton() {	
		Button back = new Button(STRING.SPLASH_EDIT_SCREEN.BACK);
		setSmallButtonSize(back);	
		back.setOnMouseClicked(e -> backSplashScreen());	
		
		return back;		
	}
	
	private TextField createTextField() {	
		TextField textField = new TextField();
		textField.setMinSize(INT.SPLASH_EDIT_TEXTFIELD_WIDTH, INT.SPLASH_EDIT_TEXTFIELD_HEIGHT);
		
		return textField;	
	}
	
//	private Image createImageFromFile(double size) {	
//		File file = null;
//		Image image = null;
//		FileChooser fileChooser = new FileChooser();
//		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.JPG_LONG, STRING.SPLASH_EDIT_SCREEN.JPG_SHORT);
//		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.PNG_LONG, STRING.SPLASH_EDIT_SCREEN.PNG_SHORT);
//		fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
//		file = fileChooser.showOpenDialog(null);
//		image = new Image(file.toURI().toString(), size, size, false, false);
//		
//		return image;	
//	}
	
	private void addImage(MouseEvent e) {
		try {	
			File file = null;
			Image image = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.JPG_LONG, STRING.SPLASH_EDIT_SCREEN.JPG_SHORT);
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.PNG_LONG, STRING.SPLASH_EDIT_SCREEN.PNG_SHORT);
			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, false, false);
			//Image image = createImageFromFile(DOUBLE.SPLASH_EDIT_DEFAULT_SIZE);
			ImageCursor imageCursor = new ImageCursor(image);
			getParent().setCursor(imageCursor);
			tag = STRING.SPLASH_EDIT_SCREEN.TAG_IMAGE;
			splashEditModel.createImageView(image);
			splashEditModel.addImageView(e, file.getPath());
			this.setOnKeyPressed(ee -> splashEditModel.resizeAndRotateImage(ee));
		} catch (Exception ex) {		
			//NO CATCH		
		}
	}
	
	private void chooseText(TextField textField) {	
		String textValue = textField.getText();		
		splashEditModel.selectText(textValue);	
		textField.clear();
	}
	
	private void chooseImage(TextField textField) {	
		int index = Integer.parseInt(textField.getText()) - 1;
		splashEditModel.selectImage(index, counter);	
		textField.clear();
	}

	private void addBackgroundImage() {
		try {
			File file = null;
			Image image = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.JPG_LONG, STRING.SPLASH_EDIT_SCREEN.JPG_SHORT);
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.PNG_LONG, STRING.SPLASH_EDIT_SCREEN.PNG_SHORT);
			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), width, height, false, false);
			//Image image = createImageFromFile(0);
			tag = STRING.SPLASH_EDIT_SCREEN.TAG_BACKGROUND_IMAGE;
			splashEditModel.createImageView(image);
			splashEditModel.setBackgroundImage(file.getPath());
		} catch (Exception ex) {			
			//NO CATCH			
		}
	}

	private void addText(TextField textField, Color color, ComboBox fontPicker) {
		tag = STRING.SPLASH_EDIT_SCREEN.TAG_TEXT;
		splashEditModel.createText(textField.getText());
		splashEditModel.colorText(color);
		
		if(fontPicker.getValue() != null) {		
			splashEditModel.fontText(fontPicker);
		}
		
		splashEditModel.addText();	
		textField.clear();
		this.setOnKeyPressed(e -> splashEditModel.resizeAndRotateText(counter-1, e));	
		counter++;		
	}

	private void addAnimation() {
		//Not Being Implemented
	}
	
	private void saveSplashScreen(MouseEvent e) {
		splashEditModel.createImageView(null);
		splashEditModel.placeImageViewOffScreen();
		splashEditModel.addImageView(e, null);
		Sprite sprite = new Sprite(new Point2D(INT.SPLASH_EDIT_OFFSCREEN, INT.SPLASH_EDIT_OFFSCREEN));
		sprite.addAction(new KillAction(sprite, 0.0, KeyCode.ENTER));
		splashEditModel.addSpriteImageToSpriteList(sprite);
		controller.saveSplashScreen(game, splashEditModel.getSplashScreen());
		goalMap.put(sprite, 1);
		splashEditModel.setPlayerSprite(sprite);
		splashEditModel.setSprites(sprite);
		splashEditModel.setGoalMap(goalMap);
		splashEditModel.saveSplashScreen();
	}

	private void trashSplashScreen() {		
		String[] options = new String[]{STRING.SPLASH_EDIT_SCREEN.TRASH_START_BUTTON,
	            STRING.SPLASH_EDIT_SCREEN.TRASH_IMAGE,
	            STRING.SPLASH_EDIT_SCREEN.TRASH_TEXT};

		GridPane grid = new GridPane();
		final ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll(options); 
		comboBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> deleteItem(comboBox, options[new_value.intValue()]));	
		comboBox.setMinWidth(INT.SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH);
		grid.add(comboBox, INT.SPLASH_EDIT_SCREEN_COMBO_BOX_GRID_LOCATION, INT.SPLASH_EDIT_SCREEN_COMBO_BOX_GRID_LOCATION);
		grid.setTranslateX(INT.SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH_2X);
		grid.setTranslateY(height - INT.SPLASH_EDIT_SCREEN_COMBO_BOX_HEIGHT);
		this.getChildren().add(grid);	
	}
	
	private void deleteItem(ComboBox comboBox, String option) {
		if(option.equals(STRING.SPLASH_EDIT_SCREEN.TRASH_IMAGE)) {
			this.getChildren().remove(splashEditModel.getImageView());
			splashEditModel.removeImageViewFromImageViewArray();
			if(!splashEditModel.getImageViewArray().isEmpty()) {
				splashEditModel.selectImage(0, counter);
			}
		}
		else if(option.equals(STRING.SPLASH_EDIT_SCREEN.TRASH_TEXT)) {
			this.getChildren().remove(splashEditModel.getText());
			splashEditModel.removeTextFromTextArray();
			if(!splashEditModel.getTexts().isEmpty()) {
				splashEditModel.selectText(splashEditModel.getTexts().get(0).getText());
			}
		}
		comboBox.setVisible(false);
	}

	private void backSplashScreen() {			
		controller.returnToGameEditScreen();		
	}
	
	private void add(MouseEvent e, Rectangle rectangle) {	
		if (tag == STRING.SPLASH_EDIT_SCREEN.TAG_IMAGE) {
			splashEditModel.addSpriteImageToSpriteList(new Sprite(new Point2D(e.getX(), e.getY())));
			getParent().setCursor(Cursor.DEFAULT);		
			splashEditModel.getImageView().setOnMousePressed(f -> splashEditModel.imageViewMove(f));		
			splashEditModel.placeImageView(e);
			this.getChildren().remove(splashEditModel.getImageView());
			this.getChildren().add(splashEditModel.getImageView());	
		}
		else if(tag == STRING.SPLASH_EDIT_SCREEN.TAG_BACKGROUND_IMAGE) {	
			rectangle.setFill(new ImagePattern(splashEditModel.getImageView().getImage()));	
			//splashEditModel.setBackgroundImage(splash);
		}		 
		else if (tag == STRING.SPLASH_EDIT_SCREEN.TAG_TEXT) {
			splashEditModel.placeTextAtXY(counter-1, e);
			this.getChildren().remove(splashEditModel.getText());
			this.getChildren().add(splashEditModel.getText());		
			splashEditModel.getText().setOnMousePressed(f -> splashEditModel.textMove(f));	
		}

		tag = "";
	}	

	private void setLargeButtonSize(Button button) {	
		button.setMaxWidth(width-this.viewableArea().getWidth());
		button.setPrefHeight(INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT);
	}
	
	private void setSmallButtonSize(Button button) {		
		button.setMinSize(INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_WIDTH, INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_HEIGHT);	
	}
	
}