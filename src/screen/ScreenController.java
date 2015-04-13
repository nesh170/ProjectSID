package screen;
import game.Game;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.imageio.ImageIO;

import data.DataHandler;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.Stage;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.collisionTableScreen.CollisionTableScreenController;
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
import util.ErrorHandler;


/**
 * 
 * @author Ruslan
 * @author anika
 * @author Kyle
 * @author Leo
 * @author Michael
 * @Yongjiao
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
	private ErrorHandler errorHandler;
	// Screen Managers
	private MainMenuScreenManager mainMenuScreenManager;
	private GameEditScreenManager gameEditScreenManager;
	private SplashEditScreenManager splashEditScreenManager;
	private LevelEditScreenManager levelEditScreenManager;
	private SpriteEditScreenManager spriteEditScreenManager;
	private GamePlayScreenManager gamePlayScreenManager;
	private CollisionTableScreenManager collisionTableScreenManager;
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
		this.scene.getStylesheets().add("resources/SID.css"); //possibly put this in a string class or properties file?
		
		configureStageAndRoot(stage, root);
		configureWidthAndHeight(width, height);
		configureNewScreenWidthAndHeight(width, height);
		configureFactories(newScreenWidth, newScreenHeight - 40);
		configureErrorHandling(root);
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
		collisionTableScreenManager = new CollisionTableScreenManager();
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
	
	private void configureErrorHandling(Group root) {
		errorHandler = new ErrorHandler(root);
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
		
		tabManager.setDefaultTab(createMainMenuScreen());
		
		//USED FOR TEST GAMEEDITSCREEN
		//createGameEditScreen(null);
		
		//USED FOR TEST SPLASHEDITSCREEN //DO NOT REMOVE //@AUTHOR KYLE
		//createSplashEditScreen(null);
		
		//USED FOR TEST LEVELEDITSCREEN --> No parent gameeditscreen yet,
		//so there will be no tab to return to, and there should be an error
		//createLevelEditScreen(null);

	}
	
	
	// All other instance methods
	/**
	 * Closes the Application.
	 */
	public void close() {
		stage.close();
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

	
	private Tab createSpriteEditScreen(Tab tab, Sprite sprite) {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
					screenFactory.createSpriteEditScreen(tab, sprite, spriteEditScreenManager),
					STRING.SPRITE_EDIT
					);
		
	}
	
	
	/**
	 * @author Anika
	 * @param tab
	 * @param sprites
	 * @return Tab
	 */
	private Tab createCollisionTableScreen(Tab tab, List<String> sprites) {
		System.out.println("This is for debugging: in createCollisionTableScreen in ScreenController");
		return tabManager.addTabWithScreenWithStringIdentifier(
					screenFactory.createCollisionTableScreen(sprites, collisionTableScreenManager),
					STRING.COLLISION_TABLE_EDIT
					);
		
	}

	
	private Tab createGamePlayScreen(Game game) {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createGamePlayScreen(game, gamePlayScreenManager),
				STRING.GAME_PLAY
				);
		
	}
	
	private Tab createGamePlayScreen() {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createGamePlayScreen(gamePlayScreenManager),
				STRING.GAME_PLAY
				);
		
	}

	/**
	 * A series of private, nested classes that implement ScreenController interfaces. 
	 * These classes conveniently use any necessary ScreenController instance variables
	 * while protecting us from entirely passing in the ScreenController into Screen objects.
	 * Screen objects use the provided methods in the ScreenController interfaces to 
	 * perform transitioning, closing, opening, etc. actions
	 * 
	 * @author AuthoringEnvironment Team
	 *
	 */
	private class MainMenuScreenManager implements MainMenuScreenController {
		/**
		 * Display a pop up to specify game name and description
		 */
		@Override
		public void createNewGame(Popup popup) {
			popup.show(stage);
		}
		
		@Override
		public void loadGame() {
			
			File gameFile = DataHandler.chooseFile(stage);
			try {
				Game game = (Game) DataHandler.fromXMLFile(gameFile);
				createGameEditScreen(game);
			}
			catch (Exception e) {
				errorHandler.displayError(STRING.ILLEGAL_FILE_PATH);
			}
			
		}

		@Override
		public void closeApplication() {

			close();
		}
		/**
		 * creates a new game after user specify the game name in pop up window.
		 */
		@Override
		public void confirmToCreateGame(Popup popup, TextField gameName,
				TextField des) {
			Game newGame = new Game(gameName.getText());
            newGame.setDescription(des.getText());
            createGameEditScreen(newGame);
            popup.hide();
		}
		
	}
	
	private class GameEditScreenManager implements GameEditScreenController {

		@Override
		public void returnToMainMenuScreen() {
			
			//MainMenuScreen is singleton
			Tab gameEditTab = tabManager.getTabSelectionModel().getSelectedItem();
			//tabManager.removeTab(gameEditTab);	
			tabManager.removeTabAndChangeSelected(gameEditTab);

		}

		@Override
		public void loadLevelEditScreen(Level level) {

			createLevelEditScreen(level);
			
		}

		@Override
		public void loadLevelEditScreen(Game game) {
			//Create new Level, add to end of game
			Level newLevel = new Level(INT.DEFAULT_LEVEL_DISPLAY_WIDTH, 
					INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
			createLevelEditScreen(newLevel);
			game.addLevel(newLevel);
			
		}

		@Override
		public void loadSplashEditScreen(Game game) {
			//Create new SplashEditScreen
			SplashScreen newSplashScreen = new SplashScreen(INT.DEFAULT_LEVEL_DISPLAY_WIDTH,
					INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
			createSplashEditScreen(newSplashScreen);
			game.addSplash(newSplashScreen);
			
		}
		/**
		 * reloads GameEditScreen, needs to reloads the game to reflect those changes on the screen
		 * Used when remove is clicked, so that GameEditScreen correctly reflects the removal of level or splash
		 * 
		 * OR to addListener() when change happened, use animation to reflect changes instead of refreshes whole screen
		 * addListener( o-> animationToReduceLevelImage); (use easy method for now)
		 * @param game
		 */
		public void reloadGameEditScreen(Game game){
			Screen newScreen = screenFactory.createGameEditScreen(game, gameEditScreenManager);
			
			tabManager.addTabWithScreenWithStringIdentifier(
						screenFactory.createGameEditScreen(game, gameEditScreenManager),
						STRING.GAME_EDIT
						);
			tabManager.replaceTab(newScreen);
		}
		
		@Override
		public void playGame(Game game) {
			createGamePlayScreen();

		}

		@Override
		public void trashLevel(Game game, int levelIndex) {
			
			game.removeLevel(levelIndex);
			reloadGameEditScreen(game);
		}


		@Override
		public void trashSplash(Game game) {
			
			game.removeSplash();
			reloadGameEditScreen(game);
		}


		@Override
		public void saveGame(Game game) {
			
			File dir = DataHandler.chooseDir(stage);
			try {
				DataHandler.toXMLFile(game, game.name(), dir.getPath());
			} catch (IOException e) {
				errorHandler.displayError(STRING.ILLEGAL_FILE_PATH);
			}
			
		}
	}

	private class SplashEditScreenManager implements SplashEditScreenController {

		@Override
		public void returnToGameEditScreen() {
			
			Tab splashTab = tabManager.getTabSelectionModel().getSelectedItem();
			tabManager.removeTabAndChangeSelected(splashTab);
			
		}		
		
	}
	
	private class LevelEditScreenManager implements LevelEditScreenController {

		@Override
		public void returnToGameEditScreen() {
			
			Tab levelEditTab = tabManager.getTabSelectionModel().getSelectedItem();
			tabManager.removeTabAndChangeSelected(levelEditTab);	
			
		}

		@Override
		public void loadSpriteEditScreen(LevelEditScreen levelEditScreen, Sprite sprite) {
			
			
			Tab levelEditTab = tabManager.getTabSelectionModel().getSelectedItem();
			createSpriteEditScreen(levelEditTab, sprite);
			levelEditScreen.addSprite(sprite);
			
		}
		
		@Override
		public void loadSpriteEditScreen(LevelEditScreen levelEditScreen) {
			
			Sprite newSprite = new Sprite();
			loadSpriteEditScreen(levelEditScreen, newSprite);
		}
		
	}
	
	private class SpriteEditScreenManager implements SpriteEditScreenController {

		@Override
		public void returnToSelectedLevel(LevelEditScreen levelEditScreen, Tab switchTo, Sprite sprite) {
			
			tabManager.removeTabAndChangeSelected(switchTo);
			levelEditScreen.addSprite(sprite);
			
		}
		
	}
	
	private class GamePlayScreenManager implements GamePlayScreenController {

		@Override
		public void returnToMainMenuScreen() {
			
			tabManager.changeToDefault();
			
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
