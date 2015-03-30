package screen;

import resources.constants.DOUBLE;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;

/**
 * The superclass to the MainMenuScreen, GameEditScreen, LevelEditScreen, etc.
 * 
 * @author Michael
 * @author Ruslan
 *
 */

public abstract class Screen extends Group {

	// Instance variables
	// Sizing
	private double width, height;
	// JavaFX
	
	
	
	// Getters & Setters
	
	
	
	// Constructor & Helpers
	public Screen(double width, double height) {
		
		configureWidthAndHeight(width, height);
		
	}
	
	private void configureWidthAndHeight(double width, double height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	
	// All other instance methods
	protected void add(Node node) {
		this.getChildren().add(node);
	}
	
}
