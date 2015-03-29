package screen;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * The superclass to the MainMenuScreen, GameEditScreen, LevelEditScreen, etc.
 * Contains methods to add Node, and internally contains the root Group, which
 * can be added to via add(Node node) - unnecessary to "expose Group root".
 * 
 * Access the Scene to add to your Stage via the public method:
 *  "subclass_of_Screen.scene()"
 * 
 * @author Michael
 * @author Ruslan
 *
 */

public abstract class Screen {

	// Instance variables
	// JavaFX
	private Scene scene;			// readonly
	private Group root;				// not exposed
	
	
	// Getters & Setters
	public Scene scene() {
		return this.scene;
	}	
	
	// Constructor & Helpers
	public Screen(double width, double height) {
		
		createSceneAndRoot(width, height);
		
	}
	
	private void createSceneAndRoot(double width, double height) {
		
		this.root = new Group();
		this.scene = new Scene(this.root, width, height);
		
	}
	
	
	// All other instance methods
	public void add(Node node) {
		root.getChildren().add(node);
	}
	
}
