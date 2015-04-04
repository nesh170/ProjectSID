package utils;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UtilsLoader {

	private final static int DEFAULT_WIDTH = 500;
	private final static int DEFAULT_HEIGHT = 200;
	private final static String DEFAULT_SOUND_PATH = "";
	private final static String DEFAULT_IMAGES_PATH = "";
	
	public UtilsLoader(Stage stage) {
		createLoaderChooser(stage);
	}

	private void createLoaderChooser(Stage stage) {
        //stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner(s);
	    Button sounds = new Button("Sounds");
	    Button images = new Button("Images");
        sounds.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 3,2,2,2;");
        sounds.setOnAction(event -> { System.exit(0); });
        images.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 3,2,2,2;");
        images.setOnAction(event -> { System.exit(0); });
        VBox vbox = new VBox(25);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(new Text("Choose what to load:"), sounds, images);
        Scene loaderScene = new Scene(vbox, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.setScene(loaderScene);
	}
	
}


