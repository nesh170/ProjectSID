package media;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * 
 * @author Ruslan
 * @author (fill in if you made this stub please)
 *
 */

public class MediaManager {
	
	// Class variables
	private static MediaManager sharedInstance;
	
	
	// Instance variables
	private MediaPlayer mediaPlayer;
	
	private URL playURL;
	private URL pauseURL;

	
	// Class methods
	public static MediaManager sharedInstance() {
		
		if (sharedInstance == null)
			sharedInstance = new MediaManager();
		
		return sharedInstance;
		
	}
	
	// Constructor & Helpers

	
	// All other instance methods
	/**
	 * 
	 * Loads a new media file to play. After this call MediaManager.sharedInstance().play()
	 * @param String fileName
	 * 
	 */
	public void loadNewMedia(String fileName) {
		
		this.playURL = getClass().getResource(fileName);
		this.pauseURL = null;
		
		mediaPlayer.stop();
		
	}
	
	/**
	 * Pauses the playing of the media content.
	 */
	public void pause() {
		
		if (pauseURL != null) {
			
			mediaPlayer.pause();
			
			playURL = pauseURL;
			pauseURL = null;
			
		}
		
	}
	
	/**
	 * Resumes the playing of some media content. Call loadNewMedia(String fileLocation) prior to playing
	 * 
	 */
	public void play() {
		
		// playURL keeps track of next URL to play. don't play the same thing twice by design
		if (playURL != null) {

			final Media media = new Media(playURL.toString());
			
			this.mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			
			pauseURL = playURL;
			playURL = null;
			
		}
		
	}
	
	/**
	 * Stops the playing of music and restarts the track.
	 */
	public void stop() {

		if (pauseURL != null) {

			mediaPlayer.stop();

			playURL = null;
			pauseURL = null;

		}

	}
	
}
