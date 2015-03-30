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
	private MenuBar menuBar;
	
	
	// Getters & Setters
	
	
	
	// Constructor & Helpers
	/**
	 * 
	 * @param width
	 * @param height of total height minus the tab pane
	 * 
	 */
	public Screen(double width, double height) {
		
		configureWidthAndHeight(width, height);
		configureMenuBar();
		
	}
	
	private void configureWidthAndHeight(double width, double height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	private void configureMenuBar() {
		
		instantiateMenuBar();
		addMenuItemsToMenuBar(menuBar);				// passes MenuBar to abstract method, never exposes MenuBar instance variable
		addMenuBarToThis();
		
	}
	
	private void instantiateMenuBar() {
		
		menuBar = new MenuBar();
		menuBar.setPrefWidth(width);
		menuBar.setPrefHeight(height * DOUBLE.percentHeightMenuBar);
		
	}
	
	protected abstract void addMenuItemsToMenuBar(MenuBar menuBar);
	
	private void addMenuBarToThis() {
		this.getChildren().add(menuBar);
	}
	
	// All other instance methods
	protected void add(Node node) {
		this.getChildren().add(node);
	}
	
}
