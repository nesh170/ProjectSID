package screen;

import java.io.File;
import java.net.URI;
import java.util.Collection;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import level.Level;
import resources.constants.DOUBLE;
import screen.gameEditScreen.GameEditScreenController;
import screen.gamePlayScreen.GamePlayScreenController;
import screen.levelEditScreen.LevelEditScreenController;
import screen.mainMenu.MainMenuScreen;
import screen.mainMenu.MainMenuScreenController;
import screen.splashEditScreen.SplashEditScreenController;
import screen.spriteEditScreen.SpriteEditScreenController;
import game.Game;
import screen.Screen;

/**
 * 
 * @author Ruslan
 * @author anika
 * @author Michael
 *
 */

/**
 * ----------------------------------------------------------------------
 * Note from March 28 meeting (Michael, Anika, Leo, Yongjiao)
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
 * ----------------------------------------------------------------------
 * Note from March 29th (Ruslan)
 * 
 * The Display functionality would be better suited in ScreenController
 * 
 * ----------------------------------------------------------------------
 * Note from March 31st (Ruslan)
 * 
 * Significant refactor of the front end. ScreenController only implements ScreenControllerInterface,
 * 
 *  - instantiates its nested classes as Managers 
 *  - that it then passes into new Screen subclass instances
 *  - and places those in the ScreenController's tabPane
 *  - via "addTabWithScreenWithStringIdentifier(ScreenSubClass instance, String identifier)"
 * 
 * ----------------------------------------------------------------------
 * Note from April 1st (Michael)
 * 
 * -Added 3 if (true) return true; (April Fools)
 * - Removed extends Scene
 * - Added Instance Variable Scene
 * 
 */

public class ScreenController implements ScreenControllerInterface {
	
	// Static Variables
	
	
	// Instance Variables
	// Sizing
	private double myWidth, myHeight;
	private double newScreenWidth, newScreenHeight;
	
	// JavaFX
	private Stage stage;
	private Group myRoot;
	private Scene myScene;
	private TabPane tabPane;
	
	// ScreenController Inner Class Handlers
	SplashEditScreenController splashEditScreenManager = new ScreenController.SplashEditScreenManager();
	LevelEditScreenController levelEditScreenManager = new ScreenController.LevelEditScreenManager();
	SpriteEditScreenController spriteEditScreenManager = new ScreenController.SpriteEditScreenManager();
	GamePlayScreenController gamePlayScreenManager = new ScreenController.GamePlayScreenManager();
	MainMenuScreenController myMainMenuScreenManager;
	GameEditScreenController myGameEditScreenManager;
	
	
	// Getters & Setters (static)
	
	
	// Getters & Setters (instance)
	public double width() {
		return this.myWidth;
	}
	
	public double height() {
		return this.myHeight;
	}
	
	public double newScreenWidth() {
		return this.newScreenWidth;
	}
	
	public double newScreenHeight() {
		return this.newScreenHeight;
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	
	// Constructors & Helpers
	public ScreenController(Stage stage, double width, double height) {
		
		myRoot = new Group();
		myScene = new Scene(myRoot);
		
		configureStageAndRoot(stage, myRoot);
		configureWidthAndHeight(width, height);
		configureNewScreenWidthAndHeight(width, height);
		
		configureTabPane();
		
		createInitialMainMenuScreen();
	
	}
	
		myMainMenuScreenManager = new MainMenuScreenManager(stage);
		myGameEditScreenManager = new GameEditScreenManager();
	private void configureStageAndRoot(Stage stage, Group root) {
		
		this.myRoot = root;
		this.stage = stage;
		
	}
	
	private void configureWidthAndHeight(double width, double height) {
		
		this.myWidth = width;
		this.myHeight = height;
		
	}
	
	private void configureNewScreenWidthAndHeight(double width, double height) {
					
		double newScreenHeight = height - DOUBLE.TAB_HEIGHT;
		
		this.newScreenWidth = width;
		this.newScreenHeight = newScreenHeight;
		
	}
	
	private void configureTabPane() {
		
		instantiateTabPane();
		setTabPaneAlignmentAndSize();
		addTabPane();
		
	}
	
	private void instantiateTabPane() {
		tabPane = new TabPane();
	}
	
	private void setTabPaneAlignmentAndSize() {
		
		tabPane.sideProperty().set(Side.BOTTOM);
		
		tabPane.minHeightProperty().set(newScreenHeight);
		tabPane.maxHeightProperty().set(newScreenHeight);
		
		tabPane.minWidthProperty().set(newScreenWidth);
		tabPane.maxWidthProperty().set(newScreenWidth);
		
		tabPane.tabMinHeightProperty().set(DOUBLE.TAB_HEIGHT);
		tabPane.tabMaxHeightProperty().set(DOUBLE.TAB_HEIGHT);
		
	}
	
	private void addTabPane() {
		myRoot.getChildren().add(tabPane);
	}
	
	private void createInitialMainMenuScreen() {
		
		addTabWithScreenWithStringIdentifier(
				new MainMenuScreen(myMainMenuScreenManager, newScreenWidth, newScreenHeight),
				"Main Menu");
				
	}
	
	
	// All other instance methods
	// Public
	@Override
	public void displayError(String error) {
		throw new IllegalStateException("unimplemented displayError in ScreenControllerInterface");
	}
	
	// Private
	/**
	 * Method for adding new Tab items
	 * 
	 * @param Screen (to add)
	 * @param String (title)
	 */
	private void addTabWithScreenWithStringIdentifier(Screen screen, String string) {

		Tab tab = new Tab();

		tab.setText(string);
		tab.setId(string);
		
		tab.setContent(screen);

		tabPane.getTabs().add(tab);
		
		setCorrectTabModifiability();

	}

	/**
	 * Take all tabs except the current one and make them unmodifiable. Make the current tab modifiable
	 * 
	 * @author Ruslan
	 */
	private void setCorrectTabModifiability() {
		
		int tabPaneListSize = tabPane.getTabs().size();
		ObservableList<Tab> tabs = tabPane.getTabs();
		
		// All Tab(s) except the last one
		for (int i=0; i < tabPaneListSize-1; i++) {
			disableTab(tabs.get(i));
		}
		
		enableTab(tabs.get(tabPaneListSize-1));
	
	}
	
	private void disableTab(Tab tab) {
	
		tab.setClosable(false);
		tab.setDisable(true);
		
	}
	
	private void enableTab(Tab tab) {
		
		tab.setClosable(true);
		tab.setDisable(false);
		
	}
	
	// Inner class for handling SplashEditScreenController methods
	private class SplashEditScreenManager implements SplashEditScreenController {

		@Override
		public void returnToGameEditScreen() {
			throw new IllegalStateException("unimplemented returnToGameEditScreen in SplashEditScreenController");
		}
		
	}
	
	// Inner class for handling LevelEditScreenController methods
	private class LevelEditScreenManager implements LevelEditScreenController {

		@Override
		public void returnToGameEditScreen() {
			throw new IllegalStateException("unimplemented returnToGameEditScreen in LevelEditScreenController");
		}

		@Override
		public void loadSpriteEditScreen() {
			throw new IllegalStateException("unimplemented loadSpriteEditScreen in LevelEditScreenController");
		}
		
	}
	
	// Inner class for handling SpriteEditScreenController methods
	private class SpriteEditScreenManager implements SpriteEditScreenController {

		@Override
		public void returnToSelectedLevel() {
			throw new IllegalStateException("unimplemented returnToSelectedLevel in SpriteEditScreenController");
		}
		
	}
	
	// Inner class for handling GamePlayScreenController methods
	private class GamePlayScreenManager implements GamePlayScreenController {

		@Override
		public void returnToMainMenuScreen() {
			throw new IllegalStateException("unimplemented returnToMainMenuScreen in GamePlayScreenController");
		}

		@Override
		public void loadSplashEditScreen() {
			throw new IllegalStateException("unimplemented loadSplashEditScreen in GamePlayScreenController");
		}

		@Override
		public void loadLevelEditScreen() {
			throw new IllegalStateException("unimplemented loadLevelEditScreen in GamePlayScreenController");
		}
		
	}
	
}
