package media;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VideoPlayer {
	    
	public void init(Stage stage, Media video) throws Exception {
					
		stage.setTitle("Embedded Media Player");
        Group root = new Group();
        
        Scene scene = new Scene(root, video.getWidth(), video.getHeight());
        
        MediaPlayer mediaPlayer = new MediaPlayer(video);
        mediaPlayer.setAutoPlay(true);
        
        VideoController mediaControl = new VideoController(mediaPlayer);
        scene.setRoot(mediaControl);
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> { mediaPlayer.stop(); });
	}

}
