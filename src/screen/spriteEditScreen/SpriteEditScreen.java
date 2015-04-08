package screen.spriteEditScreen;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import screen.Screen;
import screen.ScreenController;
import screen.levelEditScreen.LevelEditScreen;
import sprite.Sprite;

/**
 * 
 * @author Leo
 *
 */


public class SpriteEditScreen extends Screen {
	
	private SpriteEditScreenController controller;
	private Tab levelEditScreen;

	public SpriteEditScreen(SpriteEditScreenController parent, Tab levelEditScreen, double width, double height) {

		this(parent, levelEditScreen, width, height, null);

	}

	public SpriteEditScreen(SpriteEditScreenController parent, Tab levelEditScreen, double width, double height, Sprite spriteToEdit) {

		super(width, height);
		
		this.levelEditScreen = levelEditScreen;
		this.controller = parent;

		if (spriteToEdit != null) {
			drawSpriteOnScreen(spriteToEdit);
		}
		
		createLeftPane();
		createRightPane();
		createCenterPane();
		
	}
		
	
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		Menu fileMenu = makeFileMenu(e -> saveSprite(),
									e -> exit(),
									e -> saveAndExit()
									);
		
	}
	
	private void createLeftPane() {
		VBox nameAndTagPane = createNameAndTagPane();
		Pane imagePane = createAddImagePane();
		
		VBox leftPane = new VBox();
		leftPane.getChildren().addAll(nameAndTagPane, imagePane);
		
		this.viewableArea().setLeft(leftPane);
	}
	
	private void createRightPane() {
		
		VBox rightPane = new VBox();
		
		this.viewableArea().setRight(rightPane);

	}
	
	private void createCenterPane() {
		
	}
	
	private VBox createNameAndTagPane() {
		return null;
	}
	
	private Pane createAddImagePane() {
		return null;
	}
	
	private VBox createActionAndComponentPane() {
		return null;
	}
	
	private void saveSprite() {
		//TODO
	}
	
	private void exit() {
		//TODO
	}
	
	private void saveAndExit() {
		saveSprite();
		exit();
	}

	// All other instance methods
	private void drawSpriteOnScreen(Sprite sprite) {
		//TODO implement
	}

}
