package screen;
import game.Game;

import java.io.File;

import javax.imageio.ImageIO;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;
import resources.constants.DOUBLE;
import resources.constants.STRING;
import screen.gameEditScreen.GameEditScreen;
import screen.gameEditScreen.GameEditScreenController;
import screen.gamePlayScreen.GamePlayScreen;
import screen.gamePlayScreen.GamePlayScreenController;
import screen.levelEditScreen.LevelEditScreen;
import screen.levelEditScreen.LevelEditScreenController;
import screen.mainMenu.MainMenuScreen;
import screen.mainMenu.MainMenuScreenController;
import screen.splashEditScreen.SplashEditScreen;
import screen.splashEditScreen.SplashEditScreenController;
import screen.spriteEditScreen.SpriteEditScreen;
import screen.spriteEditScreen.SpriteEditScreenController;
import screen.tab.TabManager;
import screen.util.ErrorMessageTextFieldFactory;
import screen.Screen;
import sprite.Sprite;


/**
 * 
 * @author Ruslan
 * @author anika
 * @author Kyle
 * @author Leo
 * @author Michael
 *
 */


// Description
/**
 * Responsibilities of the ScreenController:
 * 	- Width, Height of itself, and new Screen(s)
 * 	- JavaFX Stage, Group, Scene
 * 	- TabPane, changing between Screen
 * 		- ALL "getFile, closeApplication()" belong in the parent of all ScreenManagers, "Manager"
 * 		- Instantiate the "Manager" with a ScreenController and use (ScreenController)parent.stage() if you need the Stage
 */

// Meeting Notes
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
 * - Added 3 if (true) return true; (April Fools)
 * - Removed extends Scene
 * - Added Instance Variable Scene
 * 
 */

public class ScreenController {
	
	// Static Variables
	
	
	// Instance Variables
	// Sizing
	private double width, height;
	private double newScreenWidth, newScreenHeight;
	// JavaFX
	private Stage stage;
	private Group root;
	private Scene scene;
	private TabManager tabManager;
	// Assists in selecting the correct tab after opening / closing tabs
	private TextField errorMessageTextField;
	// Screen Managers
	private MainMenuScreenManager mainMenuScreenManager;
	private GameEditScreenManager gameEditScreenManager;
	private SplashEditScreenManager splashEditScreenManager;
	private LevelEditScreenManager levelEditScreenManager;
	private SpriteEditScreenManager spriteEditScreenManager;
	private GamePlayScreenManager gamePlayScreenManager;
	
	//Factories
	private ScreenFactory screenFactory;
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
	
	public Stage stage() {
		return this.stage;
	}
	
	public Scene scene() {
		return scene;
	}
	
	public void setCursor(ImageCursor imageCursor) {
		stage.getScene().setCursor(imageCursor);
	}
	
	// Constructors & Helpers
	public ScreenController(Stage stage, double width, double height) {
		
		this.root = new Group();
		this.scene = new Scene(root);
		
		configureStageAndRoot(stage, root);
		configureWidthAndHeight(width, height);
		configureNewScreenWidthAndHeight(width, height);
		configureFactories(width, height);
		configureScreenManagers();
		
		configureTabPane();
		
		createInitialScreens();
	
	}
	
	private void configureScreenManagers() {
		mainMenuScreenManager = new MainMenuScreenManager();
		gameEditScreenManager = new GameEditScreenManager();
		splashEditScreenManager = new SplashEditScreenManager();
		levelEditScreenManager = new LevelEditScreenManager();
		spriteEditScreenManager = new SpriteEditScreenManager();
		gamePlayScreenManager = new GamePlayScreenManager();
	}

	private void configureStageAndRoot(Stage stage, Group root) {
		
		this.stage = stage;
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
	
	private void configureFactories(double width, double height) {
		this.screenFactory = new ScreenFactory(width, height);
	}
	
	private void configureTabPane() {
		
		TabPane tabPane = new TabPane();
		createTabManager(tabPane);
		setTabPaneAlignmentAndSize(tabPane);
		addTabPane(tabPane);
		
	}
	
	private void createTabManager(TabPane tabPane) {
		tabManager = new TabManager(tabPane);
	}

	private void setTabPaneAlignmentAndSize(TabPane tabPane) {
		
		tabPane.sideProperty().set(Side.BOTTOM);
		
		tabPane.minHeightProperty().set(newScreenHeight);
		tabPane.maxHeightProperty().set(newScreenHeight);
		
		tabPane.minWidthProperty().set(newScreenWidth);
		tabPane.maxWidthProperty().set(newScreenWidth);
		
		tabPane.tabMinHeightProperty().set(DOUBLE.TAB_HEIGHT);
		tabPane.tabMaxHeightProperty().set(DOUBLE.TAB_HEIGHT);
		
	}
	
	private void addTabPane(TabPane tabPane) {
		root.getChildren().add(tabPane);
	}
	

	private void createInitialScreens() {
		
		//createMainMenuScreen();
		
		//USED FOR TEST LEVELEDITSCREEN
		createGameEditScreen(null);
		
		//USED FOR TEST SPLASHEDITSCREEN //DO NOT REMOVE //@AUTHOR KYLE
		//createSplashEditScreen(null);
		
		//USED FOR TEST LEVELEDITSCREEN --> No parent gameeditscreen yet,
		//so there will be no tab to return to, and there should be an error
		//createLevelEditScreen(null);

	}
	
	
	// All other instance methods
	// Public
	/**
	 * Closes the Application.
	 */
	public void close() {
		stage.close();
	}
	
	public void displayError(String error) {
		
		cleanUpOldErrorMesssage();
		instantiateErrorMessage(error);
		configureErrorMessageOffsets();
		addErrorMessage();
		
	}
	
	/**
	 * Methods below are helpers for Error Messages
	 * 
	 * @author Ruslan
	 */
	private void cleanUpOldErrorMesssage() {
		
		if (errorMessageTextField != null) {
			root.getChildren().remove(errorMessageTextField);
		}		
		
	}
	
	private void instantiateErrorMessage(String error) {
		errorMessageTextField = ErrorMessageTextFieldFactory.configureNewErrorMessageTextField(error);
	}
	
	private void configureErrorMessageOffsets() {
		errorMessageTextField.setTranslateY(DOUBLE.MENU_BAR_HEIGHT);
	}
	
	private void addErrorMessage() {
		root.getChildren().add(errorMessageTextField);
	}

	
	public File getFileUsingFileChooser(FileChooser fileChooser) {
		throw new IllegalStateException("unimplemented getFileUsingFileChooser in ScreenController");
	}


	private Tab createMainMenuScreen() {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createMainMenuScreen(mainMenuScreenManager),
				STRING.MAIN_MENU
				);		
	}

	
	private Tab createGameEditScreen(Game game) {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createGameEditScreen(game, gameEditScreenManager),
				STRING.GAME_EDIT
				);
		
	}

	
	private Tab createSplashEditScreen(SplashScreen splashScreen) {

		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createSplashEditScreen(splashScreen, splashEditScreenManager),
				STRING.SPLASH_SCREEN
				);
		
	}

	
	private Tab createLevelEditScreen(Level level) {

		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createLevelEditScreen(level, levelEditScreenManager),
				STRING.LEVEL_EDIT
				);
	
	}

	
	private Tab createSpriteEditScreen(Tab levelEditTab, Sprite sprite) {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
					screenFactory.createSpriteEditScreen(levelEditTab, sprite, spriteEditScreenManager),
					STRING.SPRITE_EDIT
					);
		
	}

	
	private Tab createGamePlayScreen(Level level) {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createGamePlayScreen(level, gamePlayScreenManager),
				STRING.GAME_PLAY
				);
		
	}

	private class MainMenuScreenManager implements MainMenuScreenController {

		@Override
		public void createNewGame() {
			
			Game newGame = new Game();
			createGameEditScreen(newGame);
			
		}

		@Override
		public void loadGameEditScreen(String recentGameName) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadGame() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void closeApplication() {

			close();
		}
		
	}
	
	private class GameEditScreenManager implements GameEditScreenController {

		@Override
		public void returnToMainMenuScreen() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadSplashEditScreen(Game game) {
			
		}

		@Override
		public void loadLevelEditScreen(Level level) {
			
			createLevelEditScreen(level);
			
		}

		@Override
		public void loadLevelEditScreen() {
			
		}

		@Override
		public void loadSplashEditScreen() {
			
		}

		@Override
		public void playGame(Game game) {
			
		}

		@Override
		public void trashLevel(Level level) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class SplashEditScreenManager implements SplashEditScreenController {

		@Override
		public void returnToGameEditScreen(Tab tab) {
			Tab levelEditTab = tabManager.getTabSelectionModel().getSelectedItem();
			tabManager.getTabSelectionModel().select(tab);
			tabManager.removeTab(levelEditTab);	
			
		}
		
	}
	
	private class LevelEditScreenManager implements LevelEditScreenController {

		@Override
		public void returnToGameEditScreen(Tab tab) {
			Tab levelEditTab = tabManager.getTabSelectionModel().getSelectedItem();
			tabManager.getTabSelectionModel().select(tab);
			tabManager.removeTab(levelEditTab);							
		}

		@Override
		public void loadSpriteEditScreen(Sprite sprite) {
			Tab levelEditTab = tabManager.getTabSelectionModel().getSelectedItem();
			createSpriteEditScreen(levelEditTab, sprite);					
		}
		
	}
	
	private class SpriteEditScreenManager implements SpriteEditScreenController {

		@Override
		public void returnToSelectedLevel(LevelEditScreen levelEditScreen,
				Tab switchTo, Sprite sprite) {
			tabManager.removeTabAndChangeSelected(switchTo);
			levelEditScreen.addSprite(sprite);
		}
		
	}
	
	private class GamePlayScreenManager implements GamePlayScreenController {

		@Override
		public void returnToMainMenuScreen() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadSplashEditScreen() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadLevelEditScreen() {
			// TODO Auto-generated method stub
			
		}

		
	}

}
