package media;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.MediaPlayer;

public class SoundEffectManager {
	
	private Map<String, MediaPlayer> mySoundMap = new HashMap<>();

	public void playSound(String path){
		if(mySoundMap.containsKey(path)){
			MediaPlayer mediaPlayer = mySoundMap.get(path);
			mediaPlayer.stop();
			mediaPlayer.seek(mediaPlayer.getStartTime());
			mediaPlayer.play();
		}
		else{
			//MediaPlayer mediaPlayer = new MediaPlayer();
		}
	}
	
}
