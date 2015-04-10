package media;

import javafx.scene.media.MediaPlayer;

public class AudioController {

	private MediaPlayer myAudioPlayer;
	
	public AudioController(MediaPlayer mp) {
		this.myAudioPlayer = mp;
		play();
	}
	
	public void play() {
		myAudioPlayer.play();
	}
	
	public void stop() {
		myAudioPlayer.stop();
	}
	
	public void pause() {
		myAudioPlayer.stop();
	}
	
	public void reset() {
		myAudioPlayer.stop();
		myAudioPlayer.play();
	}
}
