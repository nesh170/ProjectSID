package screen;

import levelPlatform.level.Level;
import game.Game;
import screen.gameEditScreen.GameEditScreenController;
import screen.manager.ScreenManager;
/**
 * 
 * @author Yongjiao
 *
 */
// Inner class for handling GameEditScreenController methods
public class GameEditScreenManager extends ScreenManager implements GameEditScreenController {

	// Static Variables
	
	
	// Instance Variables
	
	
	// Getters & Setters
	
	
	// Static Methods
	
	
	// Constructor & Helpers
	public GameEditScreenManager(ScreenController parent) {
		
		super(parent);
		
	}
	
	@Override
	public void returnToMainMenuScreen() {
		throw new IllegalStateException("unimplemented returnToMainMenuScreen in GameEditScreenController");
	}
	
	@Override
	public void loadSplashEditScreen(Game game) {
		throw new IllegalStateException("unimplemented loadSplashEditScreen in GameEditScreenController");
	}
	
	@Override
	public void loadLevelEditScreen(Level level) {
		throw new IllegalStateException("unimplemented loadLevelEditScreen in GameEditScreenController");
	}

	/**
	 * remove the level from list of levels created 
	 */
	public void trashLevel(Level level) {
		
	}
	
	/**
	 * removes the splash screen from list of splash screen created
	 */
	//public void trashSplash(SplashScreen splash){}		
	/**
	 * 
	 */
	public void playGame(Game game){

	}
	
	@Override
	public void loadLevelEditScreen() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void loadSplashEditScreen() {
		// TODO Auto-generated method stub

	}
	
}