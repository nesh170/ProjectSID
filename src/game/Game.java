package game;

import java.util.ArrayList;
import java.util.List;

import levelPlatform.LevelPlatform;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;

public class Game {
	private List<Level>	myLevels;  
	private SplashScreen mySplash;
	private String myName;

	/**
	 * create untitled game, set game name only when user saved it
	 */
	public Game(){
		myLevels = new ArrayList<>();
	}
	public Game(String name) {
		myName = name;
		myLevels = new ArrayList<>();
	}
	public void addLevel(Level l){
		myLevels.add(l);
	}
	
	public void addSplash(SplashScreen s){
		mySplash = s;
	}
	
	public void removeLevel(int levelIndex){
		myLevels.remove(levelIndex);
	}
	
	public void removeSplash(){
		mySplash = null;
	}	
	public SplashScreen getSplash(){
		return mySplash;
	}
	public List<Level> getLevel(){
		return myLevels;
	}
	/**
	 * used when user edits a screen
	 */
	public void setLevel(int levelIndex, Level l){
		myLevels.set(levelIndex, l);
	}
	
	public String getName() {
		return myName;
	}
	
	public void setName(String name){
		myName = name;
	}
	/**
	 * used by GameEdit for displaying proper visual content 
	 * @return if the game has splash screen set yet
	 */
	public boolean hasSplash(){
		return mySplash != null;
	}
	public boolean hasLevel(){
		return myLevels!=null;
	}

	public int getLevelSize(){
		return myLevels.size();
	}
	/**
	 * prints game for testing purposes 
	 */
	@Override
	public String toString(){
		return "Game name is " + myName + "\nGame Level is " + myLevels.toString() + "\nGame Splash Screen";   	
	}
}
