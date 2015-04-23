package screen.screens;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import levelPlatform.splashScreen.SplashScreen;
import resources.constants.INT;
import resources.constants.STRING;
import screen.controllers.ScreenController;
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
	private List<Sprite> texts = new ArrayList();
	private ImageView imageView;
	private ImageView startButtonImageView;
	private Text text;

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
		
		//COMMENTED OUT TO TEST @AUTHOR KYLE
		//throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
		
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
		Button addText = makeAddTextButton(textField);
		Button addAnimation = makeAddAnimationButton();
		Button save = makeSaveButton();
		Button trash = makeTrashButton();
		Button back = makeBackButton();
		this.viewableArea().setRight(createAddButtons(addStartButton, addImage, addBackgroundImage, addText, textField, addAnimation));
		this.viewableArea().setBottom(createSaveAndTrashButtons(save,trash));
		this.viewableArea().setTop(back);
		
	}
	
	private void configureDisplayArea() {
		
		double rectWidth = width-(double)INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_WIDTH;
		double rectHeight = height-(double)INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT;
		Rectangle displayArea = new Rectangle(rectWidth, rectHeight, Color.RED);
		this.viewableArea().setLeft(displayArea);
		this.setOnMouseClicked(e -> add(tag, e, displayArea));
		
	}

	private VBox createAddButtons(Button addStartButton, Button addImage, Button addBackgroundImage, Button addText, TextField textField, Button addAnimation) {
		
		VBox allAddButtons = new VBox(INT.SPLASH_EDIT_SCREEN_VERTICAL_SPACING);
		VBox addTextVBox = new VBox(40);
		addTextVBox.getChildren().addAll(addText, textField);
		allAddButtons.setAlignment(Pos.CENTER);
		allAddButtons.getChildren().addAll(addStartButton, addImage, addBackgroundImage,
				addTextVBox, addAnimation);
		
		return allAddButtons;
		
	}
	
	private HBox createSaveAndTrashButtons(Button save, Button trash) {
		
		HBox saveAndTrashButtons = new HBox(INT.SPLASH_EDIT_SCREEN_HORIZONTAL_SPACING);
		saveAndTrashButtons.getChildren().addAll(save, trash);
		
		return saveAndTrashButtons;
		
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

	private Button makeAddTextButton(TextField textField) {
		
		Button addText = new Button(STRING.SPLASH_EDIT_SCREEN.ADD_TEXT);
		setLargeButtonSize(addText);
		
		addText.setOnMouseClicked(e -> addText(textField.getText()));
		
		return addText;
		
	}
	
	private TextField makeAddTextTextField() {
		
		TextField textField = new TextField();
		
		return textField;
		
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
	
	public void addStartButton(Button button) {
		
		File file = null;
		Image image = null;

		try {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterJPG);

			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), 30.0, 30.0, false, false);	

		} catch (Exception ex) {	
			//TODO Load Default Image
		}

		ImageCursor imageCursor = new ImageCursor(image);
		getParent().setCursor(imageCursor);

		tag = "Start";
		startButtonImageView = new ImageView(image);

		this.setOnKeyPressed(e -> resize(startButtonImageView, e, imageCursor));

		button.setDisable(true);
		
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
			image = new Image(file.toURI().toString(), 30.0, 30.0, false, false);	

		} catch (Exception ex) {	
			//TODO Load Default Image
		}
		
		ImageCursor imageCursor = new ImageCursor(image);
		getParent().setCursor(imageCursor);

		tag = "Image";
		imageView = new ImageView(image);
		imageViewArray.add(imageView);

		this.setOnKeyPressed(e -> resize(imageView, e, imageCursor));

		
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
			//TODO Load Default Image
		}

		tag = "Background Image";
		imageView = new ImageView(image);
		
	}

	public void addText(String textString) {
		
		tag = "Text";
		text = new Text(textString);
		
	}

	public void addAnimation() {
		
		//TODO move values and strings elsewhere
		
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
		comboBox.setMinWidth(150);
		grid.add(comboBox, 0, 0);
		grid.setTranslateX(width - 250);
		grid.setTranslateY(height - 200);
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
		System.out.println("OH HI THERE");
		for (ImageView iv : imageViewArray) {

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
			
			imageView.setOnMousePressed(f -> imageMove(f));
			
			placeImageViewAtXYIsUsingSIDPixels(imageView, e.getX(), e.getY(), false);
						
		}

		else if(tag == "Background Image") {
			rectangle.setFill(new ImagePattern(imageView.getImage()));
		}
		 
		else if (tag == "Text") {
			
//			text = new Text("Well Hi");
			
			placeTextAtXYIsUsingSIDPixels(text, e.getX(), e.getY(), false);
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
	private void imageMove(MouseEvent f) {
		//TODO be able to pick which image to move, not just most recent
		imageView.setOnMouseReleased(e -> placeImage(e));
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


	private void resize(ImageView imageView, KeyEvent e, ImageCursor ic) {
		KeyCode keyCode = e.getCode();
		if(keyCode == KeyCode.RIGHT) {
			System.out.println("right");
			imageView.setScaleX(1.8);
			imageView.setScaleY(1.8);
		}
		else if(keyCode == KeyCode.LEFT) {
			System.out.println("left");
			imageView.setScaleX(0.6);
			imageView.setScaleY(0.6);
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
