package player;

import javafx.animation.Timeline;
import javafx.scene.Group;

/**
 * The GamePlayer class is responsible for visualizing all elements of the game while 
 * it is being played. The class creates a group which is passed to engine where all 
 * data is updated. The GamePlayer contains and displays this group.  
 *
 */
public class GamePlayer {

	Timeline myAnimation;
	Group myRoot;
	
	public GamePlayer() {
		myAnimation = new Timeline();
		myRoot = new Group();
	}
	
	/**
	 * This method pauses the Timeline and animation of the game
	 */
	public void pause() {
		
	}
	
	/**
	 * This method starts the Timeline and animation of the game.
	 */
	public void start() {
		
	}
	
}
