package player;

import gameEngine.GameEngineAbstract;

import java.util.List;

import javafx.animation.Timeline;

public class GamePlayer implements Player{

	private GameEngineAbstract myEngine;
	private Timeline myTimeline;
	private int myNumFrames = 30;
	
	public GamePlayer() {
		myTimeline = new Timeline();
	}
	
	private void initialize(GameEngineAbstract engine) {
		myEngine = engine;
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHighScore() {
		// TODO Auto-generated method stub
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
