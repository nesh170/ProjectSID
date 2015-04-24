package screen.screens;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import sprite.Sprite;

/**
/* Screen to create a splash screen
 * 
 * @author Kyle
 *
 */
public class SplashEditScreen extends LevelPlatformCapableScreen {

	// Static variables


	// Instance variables
	private SplashEditScreenController controller;
	private SplashScreen splashScreen;
	private double width;
	private double height;
	private String tag;

	private Sprite startButton = new Sprite();
	private List<Sprite> images = new ArrayList();
	private List<ImageView> imageViewArray = new ArrayList();
	//private List<Sprite> texts = new ArrayList();
	private ImageView imageView;
	private ImageView startButtonImageView;
	private Text text;
	private List<Text> texts = new ArrayList();
	private int counter;
	//private TextField textCounter = new TextField();

	// Getters & Setters


	// Constructor & Helpers

	public SplashEditScreen(SplashEditScreenController parent, double width, double height, SplashScreen splashScreen) {
 
		super(width, height);
		
		this.controller = parent;
		
		configureSplashScreen(splashScreen, width, height);
		configureButtons();
		configureDisplayArea();
		this.getStyleClass().add("pane");
		
	}
	
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		//NO MENUBAR FOR THIS CLASS
		
	}
	
	private void configureSplashScreen(SplashScreen splashScreen, double width, double height) {
		
		this.splashScreen = splashScreen;
		this.width = width;
		this.height = height;
		
	}
	
	private void configureButtons() {
		
		Button addStartButton = makeAddStartButton();
		Button addImage = makeAddImageButton();
		Button addBackgroundImage = makeAddBackgroundImageButton();
		TextField textField = makeAddTextTextField();
		ColorPicker colorPicker = createColorPicker();
		Button addText = makeAddTextButton(textField, colorPicker);
		Button addAnimation = makeAddAnimationButton();
		Button save = makeSaveButton();
		Button trash = makeTrashButton();
		Button back = makeBackButton();
		HBox hbox = createBackButtonAndTextFields(back);
		
		this.viewableArea().setRight(createAddButtons(addStartButton, addImage, addBackgroundImage, addText, textField, colorPicker, addAnimation));
		this.viewableArea().setBottom(createSaveAndTrashButtons(save,trash));
		this.viewableArea().setTop(hbox);
		
	}
	
	private void configureDisplayArea() {
		
		double rectWidth = width-(double)INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_WIDTH;
		double rectHeight = height-(double)INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT;
		
		Rectangle displayArea = new Rectangle(rectWidth, rectHeight, COLOR.DEFAULT_SPLASH_SCREEN_COLOR);
		
		this.viewableArea().setLeft(displayArea);
		this.setOnMouseClicked(e -> add(tag, e, displayArea));
		
	}

	private VBox createAddButtons(Button addStartButton, Button addImage, Button addBackgroundImage, Button addText, TextField textField, ColorPicker colorPicker, Button addAnimation) {
		
		VBox allAddButtons = new VBox(INT.SPLASH_EDIT_SCREEN_VERTICAL_SPACING);
		VBox addTextVBox = new VBox(INT.SPLASH_EDIT_ADD_TEXT_VBOX_HEIGHT);
		
		addTextVBox.getChildren().addAll(addText, textField, colorPicker);
		allAddButtons.setAlignment(Pos.CENTER);
		allAddButtons.getChildren().addAll(addStartButton, addImage, addBackgroundImage, addTextVBox, addAnimation);
		
		return allAddButtons;
		
	}
	
	private HBox createSaveAndTrashButtons(Button save, Button trash) {
		
		HBox saveAndTrashButtons = new HBox(INT.SPLASH_EDIT_SCREEN_HORIZONTAL_SPACING);
		saveAndTrashButtons.getChildren().addAll(save, trash);
		
		return saveAndTrashButtons;
		
	}

	private HBox createBackButtonAndTextFields(Button back) {
		
		HBox hbox = new HBox(30);
		HBox imageHBox = new HBox();
		HBox textHBox = new HBox();
		
		Text imageText = new Text("Enter Image Index:");
		Text textText = new Text("Enter Text Content:");
		
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
	
	private Button makeAddStartButton() {
		
		Button addStartButton = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_START_BUTTON);
		setLargeButtonSize(addStartButton);
		
		addStartButton.setOnMouseClicked(e -> addStartButton(addStartButton));
		
		return addStartButton;
		
	}

	private Button makeAddImageButton() {
		
		Button addImage = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_IMAGE);
		setLargeButtonSize(addImage);
		
		addImage.setOnMouseClicked(e -> addImage());
		
		return addImage;
		
	}
	
	private Button makeAddBackgroundImageButton() {
		
		Button addBackgroundImage = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_BACKGROUND_IMAGE);
		setLargeButtonSize(addBackgroundImage);
		
		addBackgroundImage.setOnMouseClicked(e -> addBackgroundImage());
		
		return addBackgroundImage;
		
	}

	private Button makeAddTextButton(TextField textField, ColorPicker colorPicker) {
		
		Button addText = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_TEXT);
		setLargeButtonSize(addText);	
		
		addText.setOnMouseClicked(e -> addText(textField, colorPicker.getValue()));
		
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

	private Button makeAddAnimationButton() {
		
		Button addAnimation = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_ANIMATION);
		setLargeButtonSize(addAnimation);
		
		addAnimation.setOnMouseClicked(e -> addAnimation());
		
		return addAnimation;
		
	}

	private Button makeSaveButton() {
		
		Button save = new Button(STRING.SPLASH_EDIT_SCREEN.SAVE);
		setSmallButtonSize(save);
		
		save.setOnMouseClicked(e -> saveSplashScreen());
		
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
		textField.setMinSize(150, 30);

		return textField;
		
	}
	
	public void addStartButton(Button button) {
		
		File file = null;
		Image image = null;

		try {
			
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterJPG);

			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, false, false);	

			button.setDisable(true);
			
		} catch (Exception ex) {	
			
			//NO CATCH
			
		}

		ImageCursor imageCursor = new ImageCursor(image);
		getParent().setCursor(imageCursor);

		tag = "Start";
		startButtonImageView = new ImageView(image);

		this.setOnKeyPressed(e -> resizeImage(startButtonImageView, e, imageCursor));

	}
	
	public void addImage() {
		
		File file = null;
		Image image = null;

		try {
			
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, false, false);	

		} catch (Exception ex) {
			
			//NO CATCH
			
		}
		
		ImageCursor imageCursor = new ImageCursor(image);
		getParent().setCursor(imageCursor);

		tag = "Image";
		imageView = new ImageView(image);
		imageViewArray.add(imageView);

		this.setOnKeyPressed(e -> resizeImage(imageView, e, imageCursor));

		
	}
	
	private void chooseText(TextField textField) {
	
		String textValue = textField.getText();
		
		for(Text t : texts) {		
			if(t.getText().equals(textValue)) {
				text = t;
			}
		}
		
		textField.clear();

	}
	
	private void chooseImage(TextField textField) {
		
		int index = Integer.parseInt(textField.getText()) - 1;

		if(!imageViewArray.get(index).getImage().equals(null)) {
			imageView = imageViewArray.get(index);
		}
		
		textField.clear();

	}

	public void addBackgroundImage() {
		
		File file = null;
		Image image = null;

		try {
			
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), 0, 0, false, false);	

		} catch (Exception ex) {	
			
			//NO CATCH
			
		}

		tag = "Background Image";
		imageView = new ImageView(image);
		
	}

	public void addText(TextField textField, Color color) {
		
		tag = "Text";
		text = new Text(textField.getText());
		text.fillProperty().setValue(color);
		texts.add(text);
		
		textField.clear();
	
		this.setOnKeyPressed(e -> resizeText(texts.get(counter), e));
		
	}

	public void addAnimation() {
		
		String[] animations = new String[]{"Stars",
	            "Animation 2",
	            "Animation 3"};

		GridPane grid = new GridPane();
		final ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll(animations); 
		comboBox.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue ov,
					Number value, Number new_value) {
					animation(animations[new_value.intValue()]);
			}

		});
		comboBox.setMinWidth(INT.SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH);
		grid.add(comboBox, INT.SPLASH_EDIT_SCREEN_COMBO_BOX_GRID_LOCATION, INT.SPLASH_EDIT_SCREEN_COMBO_BOX_GRID_LOCATION);
		grid.setTranslateX(width - INT.SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH_X);
		grid.setTranslateY(height - INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT - INT.SPLASH_EDIT_SCREEN_COMBO_BOX_HEIGHT);
		this.getChildren().add(grid);
		
	}
	
	private void animation(String animation) {
		
		if(animation == "Stars") {
			stars();
		}
		else if(animation == "Animation 2") {
			//another animation
		}
		else if(animation == "Animation 3") {
			//another animation
		}
		
	}

	private void stars() {	

	}

	public void saveSplashScreen() {	
		// automatic	
	}

	public void trashSplashScreen() {	
		for (ImageView iv : imageViewArray) {
			System.out.println(iv.getId());
		}
		//this.remove(startButtonImageView);
	}

	public void backSplashScreen() {	
		
		controller.returnToGameEditScreen();	
		
	}
	
	private void add(String tag, MouseEvent e, Rectangle rectangle) {
		
		if(tag == "Start") {		
			
			startButton = new Sprite(new Point2D(e.getX(), e.getY())); 
			getParent().setCursor(Cursor.DEFAULT);

			startButtonImageView.setOnMousePressed(f -> startButtonMove(f));
			
			placeImageViewAtXYIsUsingSIDPixels(startButtonImageView, e.getX(), e.getY(), false);
			
		}
		
		else if (tag == "Image") {
			
			images.add(new Sprite(new Point2D(e.getX(), e.getY())));
			getParent().setCursor(Cursor.DEFAULT);
			
			imageView.setOnMousePressed(f -> imageMove(imageView, f));
			
			placeImageViewAtXYIsUsingSIDPixels(imageView, e.getX(), e.getY(), false);
						
		}

		else if(tag == "Background Image") {
			
			rectangle.setFill(new ImagePattern(imageView.getImage()));
			
		}
		 
		else if (tag == "Text") {
			
			text.setOnMousePressed(f -> textMove(text, f));
			
			placeTextAtXYIsUsingSIDPixels(texts.get(counter), e.getX(), e.getY(), false);
			
			counter++;
			
			
		}

	}	

	private void placeImageViewAtXYIsUsingSIDPixels(ImageView imageView, double x, double y, boolean isUsingSIDPixels) {

		this.getChildren().add(imageView);
		imageView.setX(x);
		imageView.setY(y);
		
	}
	
	private void placeTextAtXYIsUsingSIDPixels(Text text, double x, double y, boolean isUsingSIDPixels) {

		this.getChildren().add(text);
		text.setX(x);
		text.setY(y);
		
	}


	private void startButtonMove(MouseEvent f) {
		
		startButtonImageView.setOnMouseReleased(e -> placeStartButton(e));
		
	}
	
	private void imageMove(ImageView imageView, MouseEvent f) {

		imageView.setOnMouseReleased(e -> placeImage(e));
		
	}
	
	private void textMove(Text text, MouseEvent f) {
		text.setOnMouseReleased(e -> placeText(text, e));
	}

	private void placeStartButton(MouseEvent e) {
		
		startButtonImageView.setX(e.getX());
		startButtonImageView.setY(e.getY());
		tag = null;
		
	}
	
	private void placeImage(MouseEvent e) {
		
		imageView.setX(e.getX());
		imageView.setY(e.getY());
		tag = null;
		
	}
	
	private void placeText(Text text, MouseEvent e) {
		
		text.setX(e.getX());
		text.setY(e.getY());
		tag = null;
		
	}

	private void resizeImage(ImageView imageView, KeyEvent e, ImageCursor ic) {
		
		KeyCode keyCode = e.getCode();
		
		if(keyCode == KeyCode.RIGHT) {
			
			imageView.setScaleX(DOUBLE.SPLASH_EDIT_SCALE_UP * imageView.getScaleX());
			imageView.setScaleY(DOUBLE.SPLASH_EDIT_SCALE_UP * imageView.getScaleY());
			
		}
		
		else if(keyCode == KeyCode.LEFT) {
			
			imageView.setScaleX(DOUBLE.SPLASH_EDIT_SCALE_DOWN * imageView.getScaleX());
			imageView.setScaleY(DOUBLE.SPLASH_EDIT_SCALE_DOWN * imageView.getScaleY());
			
		}
		
	}
	
	private void resizeText(Text text, KeyEvent e) {
		
		KeyCode keyCode = e.getCode();
		
		if(keyCode == KeyCode.RIGHT) {
			
			text.setScaleX(DOUBLE.SPLASH_EDIT_SCALE_UP * text.getScaleX());
			text.setScaleY(DOUBLE.SPLASH_EDIT_SCALE_UP * text.getScaleY());
			
		}
		
		else if(keyCode == KeyCode.LEFT) {
			
			text.setScaleX(DOUBLE.SPLASH_EDIT_SCALE_DOWN * text.getScaleX());
			text.setScaleY(DOUBLE.SPLASH_EDIT_SCALE_DOWN * text.getScaleY());
			
		}
		
	}

	private void setLargeButtonSize(Button button) {
		
		button.setMaxWidth(width-this.viewableArea().getWidth());
		button.setPrefHeight(INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT);
	
	}
	
	private void setSmallButtonSize(Button button) {
		
		button.setMinSize(INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_WIDTH, INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_HEIGHT);
		
	}

}
