package game;

import java.util.ArrayList;
import java.util.List;

import resources.constants.STRING;
import levelPlatform.LevelPlatform;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;


public class Game {

	// Static Variables
	

	// Instance Variables
	private List<Level> levels;
	private SplashScreen splashScreen;
	private String name = STRING.DEFAULT_GAME_NAME;;
	private String description;


	// Getters & Setters
	public List<Level> levels() {
		return levels;
	}

	public SplashScreen splashScreen() {
		return splashScreen;
	}
	
	public String name() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Constructor & Helpers
	/**
	 * create untitled game, set game name only when user saved it
	 */
	public Game() {

		this(null);

	}

	public Game(String name) {
		
		this.name = name;
		this.levels = new ArrayList<>();

	}

	// All other instance methods
	public void addLevel(Level level) {
		levels.add(level);
	}
	public void add(int index, Level level) {
		levels.add(index, level);		
	}
	
	public void setSplash(SplashScreen splashScreen) {
		this.splashScreen = splashScreen;
	}
	/**
	 * This method is for design alternative: when Level has index private instance variable to
	 * keep track of the ordering of level
	 * @param levelIndex
	 */
	public void removeLevel(int levelIndex) {
		levels.remove(levelIndex);
		//rearrange and updates the rest of list levels' levelIndex
	}

	public void removeSplash() {
		splashScreen = null;
	}

	/**
	 * used when user edits a screen
	 */
	public void setLevel(int levelIndex, Level l) {
		levels.set(levelIndex, l);
	}

	/**
	 * used by GameEdit for displaying proper visual content
	 * 
	 * @return if the game has splash screen set yet
	 */
	public boolean hasSplash() {
		return splashScreen != null;
	}

	/*
	 * should be levels.size()!=0, but temporary put incorrectly for other
	 * testing purposes /* do not change for now
	 */
	public boolean hasLevel() {
		return levels.size() != 0;
	}

	public int getLevelsSize() {
		return levels.size();
	}

	/**
	 * prints game for testing purposes
	 */
	@Override
	public String toString() {

		String s = "Game name is " + name + "\n" + "Game Level is "
				+ levels.toString() + "\n";

		if (splashScreen == null) {
			return s + "Game Splash Screen: null";
		}

		else {
			return s + "Game Splash Screen is not null";
		}

	}

	public void setDescription(String text) {
		description = text;
	}
	
}
