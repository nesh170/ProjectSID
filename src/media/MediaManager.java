package media;

public interface MediaManager {

	/**
	 * Loads a new media file to play.
	 */
	public void loadNewMedia();
	
	/**
	 * Pauses the playing of the media content.
	 */
	public void pause();
	
	/**
	 * Resumes the playing of some media content.
	 */
	public void play();
	
	/**
	 * Stops the playing of music and restarts the track.
	 */
	public void stop();
	
}
