package player;

import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.Group;

public class GamePlayer implements GamePlayerInterface{

	Timeline myAnimation;	
	
	public GamePlayer() {
		//this is just a placeholder, group not actually created here
		Group root = new Group();
		myAnimation = new Timeline();
		
	}
	
	@Override
	public void start() {
		myAnimation.play();
	}

	@Override
	public void pause() {
		myAnimation.stop();
	}

	@Override
	public int getHighScore() {
		return 0;
	}

	@Override
	public void loadNewGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPreferences() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List findGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveGame() {
		// TODO Auto-generated method stub
		
	}

}
