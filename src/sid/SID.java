package sid;

import authoringEnvironment.MainMenuScene;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SID extends Application {

	private Screen screen;
	private Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		stage = primaryStage;
		primaryStage.setTitle("");
		
		configureStage(stage);
		
	}

	private void configureStage(Stage primaryStage) {
		
		screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();
	    
	    double width = bounds.getWidth();
	    double height = bounds.getHeight();
	    
	    configureStage(width, height);
	    
	}
	
	private void configureStage(double width, double height) {
		
		stage.setX(0);
		stage.setY(0);

		stage.setWidth(width);
		stage.setHeight(height);
	
		stage.setScene(createMainScene());
		stage.show();
		
	}
	
	private Scene createMainScene() {
		
		Group mainGroup = new Group();
		return new MainMenuScene(mainGroup);
		
	}

	public static void main(String[] args) {
        launch(args);
    }
	
	
}

