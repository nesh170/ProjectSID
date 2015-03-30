package screen.mainMenu;

import java.io.File;

import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import screen.Screen;

/**
 * The scene that contains pops up when the Authoring Environment 
 * application is opened.
 * Allows user to select whether they want to create a new game
 * or edit an existing one.
 * 
 * @author Michael
 * @author Ruslan
 *
 */

public class MainMenuScreen extends Screen {

	// Static variables
	
	
	// Instance variables
	MainMenuScreenController parent;
	
	
	// Getters & Setters
	
	
	// Constructor & Helpers
	public MainMenuScreen(MainMenuScreenController parent, double width, double height) {
		
		super(width, height);
		
		this.parent = parent;
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
		System.out.println("Implement addMenuItemsToMenuBar in MainMenuScreen");
		
	}
	
	// All other instance methods
	

}
