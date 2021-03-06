package sid;

import screen.controllers.ScreenController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SID extends Application {

	private ScreenController screenController;

	// Static methods
	public static void main(String[] args) {
        launch(args);
    }
	// random comment
	
	// Instance Methods
	@Override
	public void start(Stage stage) throws Exception {

		setTitle(stage);
		configureStage(stage);
		showStage(stage);
		
	}
	/*
	 * James - talk about player, then hand over to Patrick
	 * Patrick - demonstrate in game/ introduce others to talk about their topic
	 * Gray - sprite
	 * siva- levelView/camera
	 * Emmanuel - action/component
	 */
	
	private void setTitle(Stage stage) {
		stage.setTitle("[S]crolling [I]n The [D]eep");
	}

	private void configureStage(Stage stage) {
		
		Screen screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();
	    
	    double width = bounds.getWidth();
	    double height = bounds.getHeight();
	    
	    setStageWidthHeight(stage, width, height);
	    configureScreenController(stage, width, height);
	    
	}
	
	private void setStageWidthHeight(Stage stage, double width, double height) {
		
		stage.setX(0);
		stage.setY(0);

		stage.setWidth(width);
		stage.setHeight(height);
		
	}
	
	private void configureScreenController(Stage stage, double width, double height) {
		
		screenController = new ScreenController(stage, width, height);
		stage.setScene(screenController.scene());
		
	}
	
	private void showStage(Stage stage) {
		stage.show();
	}
	
}

