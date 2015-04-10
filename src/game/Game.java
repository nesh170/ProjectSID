package game;

import java.util.List;

import levelPlatform.LevelPlatform;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;

public class Game {
	private List<Level>	myLevels;  
	private LevelPlatform mySplash;

	
	public void addLevel(Level l){
		myLevels.add(l);
	}
	
	public void addSplash(SplashScreen s){
		mySplash = s;
	}
	
	public void removeLevel( int levelIndex){
		myLevels.remove(levelIndex);
	}
	public void removeSplash(){
		mySplash = null;
	}
}
