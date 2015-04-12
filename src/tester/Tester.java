package tester;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Subclass this class and add the following method in the subclass to run:
 * 
 * 	public static void main(String[] args) {
 *      launch(args);
 *  }
 * 
 * When ready to add Node, just run the protected method "addNode(Node node)"
 * 
 * @author Ruslan
 */
public abstract class Tester extends Application {
	
	// Instance Variables
	private Stage stage;
	private Group root;
	private Scene scene;
	
	private double width, height;
	
	
	// Getters & Setters
	private void setStage(Stage stage) {
		this.stage = stage;
	}
	
	
	// Constructor & Helpers
	@Override
	public void start(Stage stage) throws Exception {

		setStage(stage);
		setTitle();
		configureStage();
		setupSceneAndRootGroup();
		showStage();
		
		test(stage);
		
	}
	
	private void setTitle() {
		stage.setTitle("Testing 'Image To Int 2D Array And Back'");
	}

	private void configureStage() {
		
		Screen screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();
	    
	    width = bounds.getWidth();
	    height = bounds.getHeight();
	    
	    setStageWidthHeight();
	    
	}
	
	private void setStageWidthHeight() {
		
		stage.setX(0);
		stage.setY(0);

		stage.setWidth(width);
		stage.setHeight(height);
		
	}
	
	private void setupSceneAndRootGroup() {
		
		root = new Group();
		scene = new Scene(root, width, height);
		
		stage.setScene(scene);
		
	}
	
	private void showStage() {
		stage.show();
	}
	
	
	// All other instance methods
	protected abstract void test(Stage stage);

	/**
	 * Add a node to the current scene
	 * 
	 * @param node
	 */
	protected void addNode(Node node) {
		root.getChildren().add(node);
	}
	
}

