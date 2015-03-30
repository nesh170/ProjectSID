package screen;

import java.util.Collection;

import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import level.Level;
import resources.constants.DOUBLE;
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
	private TabPane tabPane;
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
		
		configureTabPane();
		
		createInitialGameEditScreen();
	
	}
	
	private void configureRoot(Group root) {
		this.root = root;
	}
	
	private void configureWidthAndHeight(double width, double height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	private void configureNewScreenWidthAndHeight(double width, double height) {
					
		double newScreenHeight = height - DOUBLE.TAB_HEIGHT;
		
		this.newScreenWidth = width;
		this.newScreenHeight = newScreenHeight;
		
	}
	
	private void configureTabPane() {
		
		tabPane = new TabPane();
		Tab tab = new Tab();
		tab.setText("hello");
		tab.setContent(new Rectangle(200, 50, Color.LIGHTSTEELBLUE));
		
		Tab tab1 = new Tab();
		tab1.setText("hi");
		tab1.setContent(new Rectangle(300,300, Color.RED));
		
		tabPane.getTabs().addAll(tab, tab1);
		
		root.getChildren().add(tabPane);
		
	}
	
	private void createInitialGameEditScreen() {
		
		
		
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

	@Override
	public void displayError(String error) {
		throw new IllegalStateException("unimplemented displayError in ScreenControllerInterface");
	}

}
