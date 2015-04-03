package utils;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UtilsLoader {

	private final static String DEFAULT_SOUND_PATH = "";
	private final static String DEFAULT_IMAGES_PATH = "";
	
	public UtilsLoader() {
		createLoaderChooser(new Stage());
	}

	private void createLoaderChooser(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner(s);
	    Button sounds = new Button("Sounds");
	    Button images = new Button("Images");
        sounds.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 3,2,2,2;");
        sounds.setOnAction(event -> { System.exit(0); });
        images.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 3,2,2,2;");
        images.setOnAction(event -> { System.exit(0); });
        VBox vbox = new VBox(300);
        vbox.getChildren().addAll(new Text("Your Games"), sounds, images);
        Scene loaderScene = new Scene(vbox, 300, 200);
        stage.setScene(loaderScene);
	}
	
}


