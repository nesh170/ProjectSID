package media;

import java.io.File;

import data.DataHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class VideoPlayer {
	    
	public void init(Stage stage, String uri) throws Exception {
					
		stage.setTitle("Embedded Media Player");
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);

        Media media = new Media(uri.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        
        VideoController mediaControl = new VideoController(mediaPlayer);
        scene.setRoot(mediaControl);
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setOnCloseRequest(event -> { mediaPlayer.stop(); });
	}

}
