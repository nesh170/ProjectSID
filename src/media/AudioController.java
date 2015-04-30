package media;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioController {

	private MediaPlayer myAudioPlayer;
	private Duration myPauseTime;
	
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
		myAudioPlayer.pause();
	}
	
	public void reset() {
		myAudioPlayer.stop();
		myAudioPlayer.play();
	}

	public void setVol(double newVol) {
		myAudioPlayer.setVolume(newVol);
	}
	
}
