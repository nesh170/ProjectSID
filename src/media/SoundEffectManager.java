package media;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundEffectManager {
	
	private static SoundEffectManager sharedInstance;
	
	private Map<String, MediaPlayer> mySoundMap = new HashMap<>();
	
	public static SoundEffectManager sharedInstance(){
		if(sharedInstance == null){
			sharedInstance = new SoundEffectManager();
		}
		return sharedInstance;
	}

	public void playSound(String path){
		if(mySoundMap.containsKey(path)){
			MediaPlayer mediaPlayer = mySoundMap.get(path);
			mediaPlayer.seek(mediaPlayer.getStartTime());
		}
		else{
			try{
			System.out.println("The path is: " + path);
			MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File(path).toURI().toString()));
			mySoundMap.put(path, mediaPlayer);
			mediaPlayer.play();
			}
			catch(Exception e){
			}
		}
	}
	
}
