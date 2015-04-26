package media;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundEffectManager {
	
	private Map<String, MediaPlayer> mySoundMap = new HashMap<>();

	public void playSound(String path){
		if(mySoundMap.containsKey(path)){
			MediaPlayer mediaPlayer = mySoundMap.get(path);
			mediaPlayer.seek(mediaPlayer.getStartTime());
		}
		else{
			System.out.println(path);
			MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File(path).toURI().toString()));
			mySoundMap.put(path, mediaPlayer);
			mediaPlayer.play();
		}
	}
	
}
