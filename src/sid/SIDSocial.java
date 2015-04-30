package sid;

import socCenter.Avatar;
import socCenter.SocialCenterScreenController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class SIDSocial extends Application{

	private SocialCenterScreenController screenController;

	// Static methods
	public static void main(String[] args) {
        launch(args);
    }

	
	// Instance Methods
	@Override
	public void start(Stage stage) throws Exception {

		setTitle(stage);
		configureStage(stage);
		showStage(stage);
		
	}
	
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
		
		screenController = new SocialCenterScreenController(stage, width, height);
		stage.setScene(screenController.scene());
		
	}
	
	private void showStage(Stage stage) {
		stage.show();
	}
	
	public Avatar getAvatar(){
		return screenController.getAvatar();
	}
	
}
