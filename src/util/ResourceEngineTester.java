package util;

import javafx.application.Application;
import javafx.stage.Stage;

public class ResourceEngineTester extends Application{
	
	private static final String DEFAULT_PATH= "images";
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Image Chooser");
		String path = DEFAULT_PATH;
		@SuppressWarnings("unused")
        SpriteGallery gallery = new SpriteGallery(stage, path);
		stage.show();
	}

	/**
	 * Main executable function for running examples.
	 * 
	 * @param arguments Command-line arguments: none expected.
	 */
	public static void main(String[] args) {
		launch(args);
		
		
	}

}

