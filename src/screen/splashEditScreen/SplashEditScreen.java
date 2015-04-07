package screen.splashEditScreen;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import levelPlatform.splashScreen.SplashScreen;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.ScreenController;
import sprite.Sprite;

/**
/* Screen to create a splash screen
 * 
 * @author Kyle
 *
 */
public class SplashEditScreen extends Screen {

	// Static variables


	// Instance variables
	private SplashEditScreenController controller;
	private SplashScreen splashScreen;
	private double width;
	private double height;

	private final Sprite startButton = new Sprite();
	private final List<Sprite> images = new ArrayList();
	private final List<Sprite> texts = new ArrayList();

	// Getters & Setters


	// Constructor & Helpers

	public SplashEditScreen(SplashEditScreenController parent, double width, double height, SplashScreen splashScreen) {

		super(width, height);
		
		this.controller = parent;
		
		configureSplashScreen(splashScreen, width, height);
		configureButtons();
		configureDisplayArea();
		
	}
	
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		//COMMENTED OUT TO TEST @AUTHOR KYLE
		//throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
		//Menu back = new Menu();
		//menuBar.getMenus().add(back);
	}
	
	//MenuBar
	//TODO: Back	
	//Buttons
	//TODO: Add Start Button
	//TODO: Add Image
	//TODO: Add Text
	//TODO: Add Animation

	
	
	private void configureSplashScreen(SplashScreen splashScreen, double width, double height) {
		this.splashScreen = splashScreen;
		this.width = width;
		this.height = height;
	}
	
	private void configureButtons() {
		
		Button addStartButton = makeAddStartButton();
		Button addImage = makeAddImageButton();
		Button addText = makeAddTextButton();
		Button addAnimation = makeAddAnimationButton();
		Button save = makeSaveButton();
		Button trash = makeTrashButton();
		Button back = makeBackButton();
		this.setRight(createAddButtons(addStartButton, addImage, addText, addAnimation));
		this.setBottom(createSaveAndTrashButtons(save,trash));
		this.setTop(back);
		
	}
	
	private void configureDisplayArea() {
		
		Rectangle displayArea = new Rectangle(width-(double)INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_WIDTH, height-(double)INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT); //obviously will change
		this.setLeft(displayArea);
		//this.setOnMouseClicked(e -> controller.);
		
	}

	private VBox createAddButtons(Button addStartButton, Button addImage, Button addText, Button addAnimation) {
		
		VBox allAddButtons = new VBox(INT.SPLASH_EDIT_SCREEN_VERTICAL_SPACING); //value will be moved somewhere else later
		allAddButtons.setAlignment(Pos.CENTER);
		allAddButtons.getChildren().addAll(addStartButton, addImage,
				addText, addAnimation);
		
		return allAddButtons;
		
	}
	
	private HBox createSaveAndTrashButtons(Button save, Button trash) {
		
		HBox saveAndTrashButtons = new HBox(INT.SPLASH_EDIT_SCREEN_HORIZONTAL_SPACING); //value will be moved somewhere else later
		saveAndTrashButtons.getChildren().addAll(save, trash);
		
		return saveAndTrashButtons;
		
	}

	private Button makeAddStartButton() {
		
		Button addStartButton = new Button(STRING.ADD_START_BUTTON);
		setLargeButtonSize(addStartButton);
		
		addStartButton.setOnMouseClicked(e -> addStartButton());
		
		return addStartButton;
		
	}

	private Button makeAddImageButton() {
		
		Button addImage = new Button(STRING.ADD_IMAGE);
		setLargeButtonSize(addImage);
		
		addImage.setOnMouseClicked(e -> addImage());
		
		return addImage;
		
	}

	private Button makeAddTextButton() {
		
		Button addText = new Button(STRING.ADD_TEXT);
		setLargeButtonSize(addText);
		
		addText.setOnMouseClicked(e -> addText());
		
		return addText;
		
	}

	private Button makeAddAnimationButton() {
		
		Button addAnimation = new Button(STRING.ADD_ANIMATION);
		setLargeButtonSize(addAnimation);
		
		addAnimation.setOnMouseClicked(e -> addAnimation());
		
		return addAnimation;
		
	}

	private Button makeSaveButton() {
		
		Button save = new Button(STRING.SAVE);
		setSmallButtonSize(save);
		
		save.setOnMouseClicked(e -> saveSplashScreen());
		
		return save;
		
	}

	private Button makeTrashButton() {
		
		Button trash = new Button(STRING.TRASH);
		setSmallButtonSize(trash);
		
		trash.setOnMouseClicked(e -> trashSplashScreen());
		
		return trash;
		
	}
	
	private Button makeBackButton() {
		
		Button back = new Button(STRING.BACK);
		setSmallButtonSize(back);
		
		back.setOnMouseClicked(e -> backSplashScreen());
		
		return back;
		
	}
	
	public void addStartButton() {
		// TODO Auto-generated method stub
		
	}

	public void addImage() {
		// TODO Auto-generated method stub
		
	}

	public void addText() {
		// TODO Auto-generated method stub
		
	}

	public void addAnimation() {
		// TODO Auto-generated method stub
		
	}

	public void saveSplashScreen() {
		// TODO Auto-generated method stub
		
	}

	public void trashSplashScreen() {
		// TODO Auto-generated method stub
		
	}

	public void backSplashScreen() {
		// TODO Auto-generated method stub
		
	}
	
	private void setLargeButtonSize(Button button) {
		button.setMinSize(INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_WIDTH, INT.SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT); //temporary values
	}
	
	private void setSmallButtonSize(Button button) {
		button.setMinSize(INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_WIDTH, INT.SPLASH_EDIT_SCREEN_SMALL_BUTTON_HEIGHT); //temporary values
	}

}
