package screen.screens;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
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
import javafx.scene.text.Font;
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
	
	//TODO: Trash Capabilities
	//TODO: Fix Moving after Resize
	//TODO: Be Able to Add Fonts to Text
	//TODO: Animation Stuff

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
	private ImageView imageView;
	private ImageView startButtonImageView;
	private Text text;
	private List<Text> texts = new ArrayList();
	private int counter;

	// Getters & Setters


	// Constructor & Helpers

	public SplashEditScreen(SplashEditScreenController parent, double width, double height, SplashScreen splashScreen) {
 
		super(width, height);
		
		this.controller = parent;
		
		configureSplashScreen(splashScreen, width, height);
		configureButtons();
		configureDisplayArea();
		this.getStyleClass().add(STRING.CSS.PANE);
		
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
		ComboBox<String> fontPicker = createFontPicker();
		Button addText = makeAddTextButton(textField, colorPicker, fontPicker);
		Button addAnimation = makeAddAnimationButton();
		Button save = makeSaveButton();
		Button trash = makeTrashButton();
		Button back = makeBackButton();
		HBox hbox = createBackButtonAndTextFields(back);
		
		this.viewableArea().setRight(createAddButtons(addStartButton, addImage, addBackgroundImage, addText, textField, colorPicker, fontPicker, addAnimation));
		this.viewableArea().setBottom(createSaveAndTrashButtons(save,trash));
		this.viewableArea().setTop(hbox);
		
	}
	
	private void configureDisplayArea() {
		
		double rectWidth = width-DOUBLE.SPLASH_EDIT_SCREEN_LARGE_BUTTON_WIDTH;
		double rectHeight = height-DOUBLE.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT;
		
		Rectangle displayArea = new Rectangle(rectWidth, rectHeight, COLOR.DEFAULT_SPLASH_SCREEN_COLOR);
		
		this.viewableArea().setLeft(displayArea);
		this.setOnMouseClicked(e -> add(tag, e, displayArea));
		
	}

	private VBox createAddButtons(Button addStartButton, Button addImage, Button addBackgroundImage, Button addText, TextField textField, ColorPicker colorPicker, ComboBox fontPicker, Button addAnimation) {
		
		VBox allAddButtons = new VBox(INT.SPLASH_EDIT_SCREEN_VERTICAL_SPACING);
		VBox addTextVBox = new VBox(INT.SPLASH_EDIT_ADD_TEXT_VBOX_HEIGHT);
		
		addTextVBox.getChildren().addAll(addText, textField, colorPicker, fontPicker);
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
		textField.setMinSize(INT.SPLASH_EDIT_TEXTFIELD_WIDTH, INT.SPLASH_EDIT_TEXTFIELD_HEIGHT);

		return textField;
		
	}
	
	public void addStartButton(Button button) {
		try {
			
			File file;
			Image image;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.JPG_LONG, STRING.SPLASH_EDIT_SCREEN.JPG_SHORT);
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.PNG_LONG, STRING.SPLASH_EDIT_SCREEN.PNG_SHORT);
			fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterJPG);

			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, false, false);	

			button.setDisable(true);
			
			ImageCursor imageCursor = new ImageCursor(image);
			getParent().setCursor(imageCursor);

			tag = STRING.SPLASH_EDIT_SCREEN.TAG_START;
			startButtonImageView = new ImageView(image);

			this.setOnKeyPressed(e -> resizeAndRotate(startButtonImageView, e));
		} catch (Exception ex) {	
			
			//NO CATCH
			
		}
	}
	
	public void addImage() {
		
		File file = null;
		Image image = null;

		try {
			
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.JPG_LONG, STRING.SPLASH_EDIT_SCREEN.JPG_SHORT);
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.PNG_LONG, STRING.SPLASH_EDIT_SCREEN.PNG_SHORT);
			fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterJPG);

			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, DOUBLE.SPLASH_EDIT_DEFAULT_SIZE, false, false);	

		} catch (Exception ex) {
			
			//NO CATCH
			
		}
		
		ImageCursor imageCursor = new ImageCursor(image);
		getParent().setCursor(imageCursor);

		tag = STRING.SPLASH_EDIT_SCREEN.TAG_IMAGE;
		imageView = new ImageView(image);
		imageViewArray.add(imageView);

		this.setOnKeyPressed(e -> resizeAndRotate(imageView, e));

		
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
		try {
			File file = null;
			Image image = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.JPG_LONG, STRING.SPLASH_EDIT_SCREEN.JPG_SHORT);
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(STRING.SPLASH_EDIT_SCREEN.PNG_LONG, STRING.SPLASH_EDIT_SCREEN.PNG_SHORT);
			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), 0, 0, false, false);

			tag = STRING.SPLASH_EDIT_SCREEN.TAG_BACKGROUND_IMAGE;
			imageView = new ImageView(image);

		} catch (Exception ex) {	
			
			//NO CATCH
			
		}
	}

	public void addText(TextField textField, Color color, ComboBox fontPicker) {
		
		tag = STRING.SPLASH_EDIT_SCREEN.TAG_TEXT;
		text = new Text(textField.getText());
		text.fillProperty().setValue(color);
		
		if(fontPicker.getValue() != null) {		
			text.setFont(Font.font(fontPicker.getValue().toString()));
		}
		
		texts.add(text);
		
		textField.clear();
	
		this.setOnKeyPressed(e -> resizeAndRotate(texts.get(counter-1), e));
		
		counter++;	
		
	}

	public void addAnimation() {
		
//		String[] animations = new String[]{"Stars",
//	            "Animation 2",
//	            "Animation 3"};
//
//		GridPane grid = new GridPane();
//		final ComboBox<String> comboBox = new ComboBox<String>();
//		comboBox.getItems().addAll(animations); 
//		comboBox.getSelectionModel().selectedIndexProperty().addListener(new
//				ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue ov,
//					Number value, Number new_value) {
//					animation(animations[new_value.intValue()]);
//			}
//
//		});
//		comboBox.setMinWidth(INT.SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH);
//		grid.add(comboBox, INT.SPLASH_EDIT_SCREEN_COMBO_BOX_GRID_LOCATION, INT.SPLASH_EDIT_SCREEN_COMBO_BOX_GRID_LOCATION);
//		grid.setTranslateX(width - INT.SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH_X);
//		grid.setTranslateY(height - INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT - INT.SPLASH_EDIT_SCREEN_COMBO_BOX_HEIGHT);
//		this.getChildren().add(grid);
		
	}
	
//	private void animation(String animation) {
//		
//		if(animation == "Stars") {
//			stars();
//		}
//		else if(animation == "Animation 2") {
//			//another animation
//		}
//		else if(animation == "Animation 3") {
//			//another animation
//		}
//		
//	}

//	private void stars() {	
//
//	}

	public void saveSplashScreen() {	
		// automatic	
	}

	private void trashSplashScreen() {	
		
		String[] options = new String[]{STRING.SPLASH_EDIT_SCREEN.TRASH_START_BUTTON,
	            STRING.SPLASH_EDIT_SCREEN.TRASH_IMAGE,
	            STRING.SPLASH_EDIT_SCREEN.TRASH_TEXT};

		GridPane grid = new GridPane();
		final ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll(options); 
		comboBox.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue ov,
					Number value, Number new_value) {
					deleteItem(options[new_value.intValue()]);
					comboBox.setVisible(false);
			}

		});
		
		comboBox.setMinWidth(INT.SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH);
		grid.add(comboBox, INT.SPLASH_EDIT_SCREEN_COMBO_BOX_GRID_LOCATION, INT.SPLASH_EDIT_SCREEN_COMBO_BOX_GRID_LOCATION);
		grid.setTranslateX(INT.SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH_2X);
		grid.setTranslateY(height - INT.SPLASH_EDIT_SCREEN_COMBO_BOX_HEIGHT);
		this.getChildren().add(grid);
		
	}
	
	private void deleteItem(String option) {
		
		if(option.equals(STRING.SPLASH_EDIT_SCREEN.TRASH_START_BUTTON)) {
			this.getChildren().remove(startButtonImageView);
			startButtonImageView = null;
		}
		else if(option.equals(STRING.SPLASH_EDIT_SCREEN.TRASH_IMAGE)) {
			this.getChildren().remove(imageView);
			imageViewArray.remove(imageView);
		}
		else if(option.equals(STRING.SPLASH_EDIT_SCREEN.TRASH_TEXT)) {
			this.getChildren().remove(text);
			texts.remove(text);
		}
		
	}

	public void backSplashScreen() {	
		
		controller.returnToGameEditScreen();	
		
	}
	
	private void add(String tag, MouseEvent e, Rectangle rectangle) {
		
		if(tag == STRING.SPLASH_EDIT_SCREEN.TAG_START) {		
			
			startButton = new Sprite(new Point2D(e.getX(), e.getY())); 
			getParent().setCursor(Cursor.DEFAULT);

			startButtonImageView.setOnMousePressed(f -> imageViewMove(startButtonImageView, f));
			
			placeImageViewAtXY(startButtonImageView, e.getX(), e.getY());
			
		}
		
		else if (tag == STRING.SPLASH_EDIT_SCREEN.TAG_IMAGE) {
			
			images.add(new Sprite(new Point2D(e.getX(), e.getY())));
			getParent().setCursor(Cursor.DEFAULT);
			
			imageView.setOnMousePressed(f -> imageViewMove(imageView, f));
			
			placeImageViewAtXY(imageView, e.getX(), e.getY());
						
		}

		else if(tag == STRING.SPLASH_EDIT_SCREEN.TAG_BACKGROUND_IMAGE) {
			
			rectangle.setFill(new ImagePattern(imageView.getImage()));
			
		}
		 
		else if (tag == STRING.SPLASH_EDIT_SCREEN.TAG_TEXT) {
			
			text.setOnMousePressed(f -> textMove(text, f));
			
			placeTextAtXY(texts.get(counter-1), e.getX(), e.getY());
			
		}

	}	

	private void placeImageViewAtXY(ImageView imageView, double x, double y) {

		this.getChildren().add(imageView);
		imageView.setX(x);
		imageView.setY(y);
		
	}
	
	private void placeTextAtXY(Text text, double x, double y) {

		this.getChildren().add(text);
		text.setX(x);
		text.setY(y);
		
	}
	
	private void imageViewMove(ImageView imageView, MouseEvent f) {
		
		imageView.setOnMouseReleased(e -> placeImageView(imageView, e));
		
	}
	
	private void textMove(Text text, MouseEvent f) {
		
		text.setOnMouseReleased(e -> placeText(text, e));
		
	}
	
	private void placeImageView(ImageView imageView, MouseEvent e) {
		
		imageView.setX(e.getX());
		imageView.setY(e.getY());
		tag = "";	
		
	}
	
	private void placeText(Text text, MouseEvent e) {
		
		text.setX(e.getX());
		text.setY(e.getY());
		tag = "";
		
	}
	
//	private void placeNode(Node node, MouseEvent e) {
//		
//		node.setX(e.getX());
//		node.setY(e.getY());
//		tag = null;
//		
//	}
	
	private void resizeAndRotate(Node node, KeyEvent e) {
		
		KeyCode keyCode = e.getCode();
		
		if(keyCode == KeyCode.UP) {
			
			node.setScaleX(DOUBLE.SPLASH_EDIT_SCALE_UP * node.getScaleX());
			node.setScaleY(DOUBLE.SPLASH_EDIT_SCALE_UP * node.getScaleY());
			
		}
		
		else if(keyCode == KeyCode.DOWN) {
			
			node.setScaleX(DOUBLE.SPLASH_EDIT_SCALE_DOWN * node.getScaleX());
			node.setScaleY(DOUBLE.SPLASH_EDIT_SCALE_DOWN * node.getScaleY());
			
		}
		
		else if(keyCode == KeyCode.RIGHT) {
			
			node.setRotate(node.getRotate() + INT.SPLASH_EDIT_ROTATE_FACTOR);
			
		}
		
		else if(keyCode == KeyCode.LEFT) {
			
			node.setRotate(node.getRotate() - INT.SPLASH_EDIT_ROTATE_FACTOR);
			
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
