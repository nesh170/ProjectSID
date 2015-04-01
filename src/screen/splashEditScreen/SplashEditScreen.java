package screen.splashEditScreen;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resources.constants.STRING;
import screen.Screen;
import screen.mainMenu.MainMenuScreenController;


/**
/* Screen to create a splash screen
 * 
 * @author Kyle
 *
 */
public class SplashEditScreen extends Screen {

	// Static variables


	// Instance variables
	SplashEditScreenController parent;


	// Getters & Setters


	// Constructor & Helpers

	public SplashEditScreen(double width, double height) {

		super(width, height);
		
		configureParent(parent);
		configureButtons();
		configureDisplayArea();
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		//COMMENTED OUT TO TEST @AUTHOR KYLE
		//throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
	}
	
	//MenuBar
	//TODO: Back
	
	//Buttons
	//TODO: Add Start Button
	//TODO: Add Image
	//TODO: Add Text
	//TODO: Add Animation
	
	private void configureParent(SplashEditScreenController parent) {
		this.parent = parent;
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
		Rectangle displayArea = new Rectangle(600, 600, Color.DIMGRAY); //obviously will change
		this.setCenter(displayArea);
	}

	private VBox createAddButtons(Button addStartButton, Button addImage,
			Button addText, Button addAnimation) {
		// TODO Auto-generated method stub
		VBox allAddButtons = new VBox(60); //value will be moved somewhere else later
		allAddButtons.setAlignment(Pos.CENTER);
		allAddButtons.getChildren().addAll(addStartButton, addImage,
				addText, addAnimation);
		return allAddButtons;
	}
	
	private HBox createSaveAndTrashButtons(Button save, Button trash) {
		// TODO Auto-generated method stub
		HBox saveAndTrashButtons = new HBox(40); //value will be moved somewhere else later
		saveAndTrashButtons.getChildren().addAll(save, trash);
		
		return saveAndTrashButtons;
	}

	private Button makeAddStartButton() {
		// TODO Auto-generated method stub
		Button addStartButton = new Button(STRING.ADDSTARTBUTTON);
		setLargeButtonSize(addStartButton);
		
		addStartButton.setOnMouseClicked(e -> parent.addStartButton());
		
		return addStartButton;
		
	}

	private Button makeAddImageButton() {
		// TODO Auto-generated method stub
		Button addImage = new Button(STRING.ADDIMAGE);
		setLargeButtonSize(addImage);
		
		addImage.setOnMouseClicked(e -> parent.addImage());
		
		return addImage;
		
	}

	private Button makeAddTextButton() {
		// TODO Auto-generated method stub
		Button addText = new Button(STRING.ADDTEXT);
		setLargeButtonSize(addText);
		
		addText.setOnMouseClicked(e -> parent.addText());
		
		return addText;
		
	}

	private Button makeAddAnimationButton() {
		// TODO Auto-generated method stub
		Button addAnimation = new Button(STRING.ADDANIMATION);
		setLargeButtonSize(addAnimation);
		
		addAnimation.setOnMouseClicked(e -> parent.addAnimation());
		
		return addAnimation;
		
	}

	private Button makeSaveButton() {
		// TODO Auto-generated method stub
		Button save = new Button(STRING.SAVE);
		setSmallButtonSize(save);
		
		save.setOnMouseClicked(e -> parent.saveSplashScreen());
		
		return save;
		
	}

	private Button makeTrashButton() {
		// TODO Auto-generated method stub
		Button trash = new Button(STRING.TRASH);
		setSmallButtonSize(trash);
		
		trash.setOnMouseClicked(e -> parent.trashSplashScreen());
		
		return trash;
		
	}
	
	//Should this be a button or in the MenuBar?????????
	//I think it looks better as a button, but we originally said it would be in
	//the MenuBar
	private Button makeBackButton() {
		// TODO Auto-generated method stub
		Button back = new Button(STRING.BACK);
		setSmallButtonSize(back);
		
		back.setOnMouseClicked(e -> parent.backSplashScreen());
		
		return back;
		
	}
	
	private void setLargeButtonSize(Button button) {
		button.setMinSize(150, 100); //temporary values
	}
	
	private void setSmallButtonSize(Button button) {
		button.setMinSize(150, 50); //temporary values
	}

}
