package screen;

import java.util.Collection;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import level.Level;
import screen.gameEditScreen.GameEditScreenController;
import screen.levelEditScreen.LevelEditScreenController;
import screen.mainMenu.MainMenuScreenController;
import screen.splashEditScreen.SplashEditScreenController;
import screen.spriteEditScreen.SpriteEditScreenController;
import game.Game;
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

public class ScreenController extends Scene implements 	MainMenuScreenController, 
														GameEditScreenController, 
														SplashEditScreenController,
														LevelEditScreenController,
														SpriteEditScreenController {
	
	// Static Variables
	
	
	
	// Instance Variables
	// Sizing
	private double width, height;
	private double newScreenWidth, newScreenHeight;
	
	// JavaFX
	private Group root;
	
	// Screen
	private Collection<Screen> screens;
	private Screen screen;
	
	
	// Getters & Setters (static)
	
	
	
	// Getters & Setters (instance)
	public double width() {
		return this.width;
	}
	
	public double height() {
		return this.height;
	}
	
	public double newScreenWidth() {
		return this.newScreenWidth;
	}
	
	public double newScreenHeight() {
		return this.newScreenHeight;
	}
	
	
	// Constructors & Helpers
	public ScreenController(Group root, double width, double height) {
		
		super(root, width, height, Color.TAN);
		
		configureRoot(root);
		configureWidthAndHeight(width, height);
		configureNewScreenWidthAndHeight(width, height);
	
	}
	
	private void configureRoot(Group root) {
		this.root = root;
	}
	
	private void configureWidthAndHeight(double width, double height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	private void configureNewScreenWidthAndHeight(double width, double height) {
			
		System.out.println("TODO: appropriately size newScreenHeight in ScreenController.java in [configureNewScreenWidthAndHeight]"
				+": base it on total height minus the height of a tab bar and menu bar");
		
		double newScreenHeight = 100.0;
		
		this.newScreenWidth = width;
		this.newScreenHeight = newScreenHeight;
		
	}
	
	
	// All other instance methods
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
