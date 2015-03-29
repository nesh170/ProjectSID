package screen;

import java.util.Collection;

import screen.gameEditScreen.GameEditScreenController;
import screen.levelEditScreen.LevelEditScreenController;
import screen.mainMenu.MainMenuScreenController;
import screen.splashEditScreen.SplashEditScreenController;
import screen.spriteEditScreen.SpriteEditScreenController;
import gameEngine.Game;
import gameEngine.Level;
import screen.Screen;

/**
 * 
 * @author Ruslan
 * @author anika
 *
 */

/**
 * 
 * Notes from March 28 meeting (Michael, Anika, Leo, Yongjiao)
 * 
 * abstract Display extends FX object
 * 	Collection<Display>
 *  width
 *  height
 * 
 * Stage has multiple scenes
 * each scene has Display, Menu bar
 * 
 * everything opens in a new tab (use Controller class for flow)
 * 
 * 
 * Note from March 29th (Ruslan)
 * 
 * The Display functionality would be better suited in ScreenController
 * 
 * 
 */

public class ScreenController implements MainMenuScreenController, 
										GameEditScreenController, 
										SplashEditScreenController,
										LevelEditScreenController,
										SpriteEditScreenController {
	
	// Static Variables
	
	
	
	// Instance Variables
	// Sizing
	private double width, height;
	private double newScreenWidth, newScreenHeight;
	// Screen
	private Collection<Screen> screens;
	private Screen screen;
	
	
	// Getters & Setters (static)
	
	
	
	// Getters & Setters (instance)
	
	
	
	// Constructors & Helpers
	public ScreenController(double width, double height) {

		
	
	}

	@Override
	public void returnToSelectedLevel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSpriteEditScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnToGameEditScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createNewGame() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void returnToMainMenuScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadLevelEditScreen(Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGame(String gameFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGameEditScreen(String recentGameName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSplashEditScreen(Game game) {
		// TODO Auto-generated method stub
		
	}

}
