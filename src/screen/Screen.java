package screen;

import resources.constants.COLOR;
import resources.constants.DOUBLE;
import resources.constants.STRING;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The superclass to the MainMenuScreen, GameEditScreen, LevelEditScreen, etc.
 * 
 * @author Michael
 * @author Ruslan
 *
 */

public abstract class Screen extends BorderPane {

	// Instance variables
	// Sizing
//	private double width, height;
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
		configureBackgroundColor();
		
	}
	
	private void configureWidthAndHeight(double width, double height) {
		
		this.setWidth(width);
		this.setHeight(height);
		
	}
	
	private void configureMenuBar() {
		
		VBox menuBarWrapper = instantiateMenuBar();
		addMenuItemsToMenuBar(menuBar);				// passes MenuBar to abstract method, never exposes MenuBar instance variable
		addMenuBarToThis(menuBarWrapper);
		
	}
	
	private VBox instantiateMenuBar() {
		
		VBox menuBarWrapper = new VBox();
		
		menuBar = new MenuBar();
		VBox.setVgrow(menuBar, Priority.NEVER);
		menuBarWrapper.getChildren().add(menuBar);
		
		menuBar.setPrefWidth(this.getWidth());
		menuBar.setPrefHeight(this.getHeight() * DOUBLE.percentHeightMenuBar);
		
		return menuBarWrapper;
		
	}
	
	
	protected abstract void addMenuItemsToMenuBar(MenuBar menuBar);
	
	private void addMenuBarToThis(VBox wrapper) {
		this.setTop(wrapper);
	}
	
	private void configureBackgroundColor() {
		this.setStyle(STRING.FX_BACKGROUND_COLOR_PREDICATE+STRING.DEFAULT_FX_BACKGROUND_COLOR);
	}
	
	// All other instance methods
	protected void add(Node node) {
		this.getChildren().add(node);
	}
	
	protected Button makeButtonForPane(String text, EventHandler<ActionEvent> lambda) {

		Button button = new Button(text);
		button.setOnAction(lambda);
		VBox.setVgrow(button, Priority.NEVER);
		return button;

	}

		
}
