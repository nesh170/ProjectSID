package screen.controllers;
import game.Game;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import data.DataHandler;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
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
import javafx.scene.control.TextArea;
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
import screen.Screen;
import screen.ScreenAnimation;
import screen.factories.ScreenFactory;
import screen.screens.GameEditScreen;
import screen.screens.GamePlayScreen;
import screen.screens.LevelEditScreen;
import screen.screens.MainMenuScreen;
import screen.screens.SplashEditScreen;
import screen.screens.SpriteEditScreen;
import screen.tab.TabManager;
import screen.util.ErrorMessageTextFieldFactory;
import sprite.Sprite;
import util.ErrorHandler;


/**
 * 
 * @author Ruslan
 * @author anika
 * @author Kyle
 * @author Leo
 * @author Michael
 * @author Yongjiao
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
	//Testing:
	private boolean GameEdit_Test = false;
	
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
		

		if(!GameEdit_Test)
			tabManager.setDefaultTab(createMainMenuScreen());
		else {
				//USED FOR TEST GAMEEDITSCREEN
				Game g = new Game();
				for(int i=0; i < 5; i++){
					Level newLevel = new Level(INT.DEFAULT_LEVEL_DISPLAY_WIDTH, 
							INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
					g.addLevel(newLevel);
					}
				createGameEditScreen(g);
			}
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
				STRING.MAIN_MENU_SCREEN.MAIN_MENU
				);		
	}
	
	private Tab createGameEditScreen(Game game) {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createGameEditScreen(game, gameEditScreenManager),
				STRING.GAME_EDIT.GAME_EDIT
				);
		
	}
	
	private Tab createSplashEditScreen(SplashScreen splashScreen, Game game) {

		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createSplashEditScreen(splashScreen, splashEditScreenManager, game),
				STRING.GAME_EDIT.SPLASH_SCREEN
				);
		
	}
	
	private Tab createLevelEditScreen(Game game, Level level) {

		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createLevelEditScreen(level, levelEditScreenManager, game),
				STRING.LEVEL_EDIT.LEVEL_EDIT
				);
	
	}
	
	private Tab createSpriteEditScreen(Tab tab, Sprite sprite) {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
					screenFactory.createSpriteEditScreen(tab, sprite, spriteEditScreenManager),
					STRING.SPRITE_EDIT.SPRITE_EDIT
					);
		
	}
	
	/**
	 * @author Anika
	 * @param tab
	 * @param sprites
	 * @return Tab
	 */
	private Tab createCollisionTableScreen(Tab tab, Set<String> spriteTags, Map<String, ObservableList<String>> spriteMap) {
		return tabManager.addTabWithScreenWithStringIdentifier(
					screenFactory.createCollisionTableScreen(spriteTags, collisionTableScreenManager, spriteMap),
					STRING.COLLISION_EDIT.COLLISION_TABLE_EDIT
					);
		
	}

	private Tab createGamePlayScreen(Game game) {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createGamePlayScreen(game, gamePlayScreenManager),
				STRING.GAME_PLAY.GAME_PLAY
				);
		
	}
	
	private Tab createGamePlayScreen() {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createGamePlayScreen(gamePlayScreenManager),
				STRING.GAME_PLAY.GAME_PLAY
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
		
		/**
		 * creates a new game after user specify the game name in pop up window.
		 */
		@Override
		public void confirmToCreateGame(Popup popup, TextField gameName,
				TextArea des) {
			Pattern currKeyPattern = Pattern.compile(STRING.REGEX.ANY_CHAR);
			Matcher m = currKeyPattern.matcher(gameName.getText());
			
			if (m.matches()) {
				Game newGame = new Game(gameName.getText());
				newGame.setDescription(des.getText());
				createGameEditScreen(newGame);
				popup.hide();
			}
			else {
				ScreenAnimation.shakePopUpWhenError(popup);
				gameName.getStyleClass().add(STRING.CSS.ERROR);
				gameName.setPromptText(STRING.ERROR.EMPTY_GAME_NAME);
			}
		}
		
		@Override
		public void loadGame() {
			
			File gameFile = DataHandler.chooseFile(stage);
			try {
				Game game = (Game) DataHandler.fromXMLFile(gameFile);
				createGameEditScreen(game);
			}
			catch (Exception e) {
				errorHandler.displayError(STRING.ERROR.ILLEGAL_FILE_PATH);
			}
			
		}

		@Override
		public void closeApplication() {

			close();
		}
	}
	
	private class GameEditScreenManager implements GameEditScreenController {
		/**TODO: BUG Here: returning is not working when new levels are added.
		 */

		@Override
		public void returnToMainMenuScreen(Popup popup) {
			
			hidePopup(popup);
			Tab gameEditTab = tabManager.getTabSelectionModel().getSelectedItem();	
			tabManager.removeTabAndChangeSelected(gameEditTab);
			
		}

		@Override
		public void loadLevelEditScreen(Game game, Level level) {
			
			createLevelEditScreen(game, level);
			
		}

		public void  showConfirmPopUpWithGame(Game game, Popup popup){
			popup.show(stage);
		}	
		/**
		 * creates a new level as well as LevelEditScreen for this new level.
		 * Adds the new level to append to end of levels list in game object.
		 */
		@Override
		public void loadLevelEditScreen(Game game, GameEditScreen gameEditScreen) {
			
			Level newLevel = new Level(INT.DEFAULT_LEVEL_DISPLAY_WIDTH, 
					INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
			createLevelEditScreen(game, newLevel);
			gameEditScreen.displayLevels();
			
		}
		
		@Override
		public void loadSplashEditScreen(Game game, GameEditScreen gameEditScreen) {
			
			SplashScreen newSplashScreen = new SplashScreen(INT.DEFAULT_LEVEL_DISPLAY_WIDTH,
					INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
			createSplashEditScreen(newSplashScreen, game);
			game.setSplash(newSplashScreen);
			gameEditScreen.displayApproporiateSplashButton();		
		
		}
		
		@Override
		public void playGame(Game game) {
			
			createGamePlayScreen();

		}

		@Override
		public void trashLevel(Game game, int levelIndex,GameEditScreen gameEditScreen) {
			
			game.removeLevel(levelIndex);			
			SequentialTransition st = gameEditScreen.animatesTrashLevel(
					gameEditScreen.trashLevelAnimationFinishedEvent());
			st.play();
		}


		@Override
		public void trashSplash(Game game, GameEditScreen gameEditScreen) {
			
			game.removeSplash();
			Transition t = gameEditScreen.animatesTrashPaper(INT.SPLASH,
					gameEditScreen.trashSplashAnimationFinishedEvent()); //can be replaced to not pass GameEditScreen updates splash display internally
			t.play();
		}


		@Override
		public void saveGame(Game game) {
			
			//File dir = DataHandler.chooseDir(stage);
			try {
				String imageFolderName = game.name() + STRING.GAME_EDIT.IMAGE_FOLDER;
				File folder = new File(imageFolderName);
				folder.mkdir();
				saveLevelSprites(game, imageFolderName);
				saveLevelBackgrounds(game, imageFolderName);
				saveSplashScreen(game, imageFolderName);

				DataHandler.toXMLFile(game, game.name(), folder.getPath());
			} catch (IOException e) {
				errorHandler.displayError(STRING.ERROR.ILLEGAL_FILE_PATH);
			}
		}
		
		private void saveLevelSprites(Game game, String imageFolderName) {
			game.levels().forEach(level -> level.sprites().forEach(sprite -> {
				String imagePath = sprite.getImagePath();
				String newImagePath = copyImage(imageFolderName, imagePath);
				sprite.setImagePath(newImagePath);
			}));
		}
		
		private void saveLevelBackgrounds(Game game, String imageFolderName) {
			try {
				game.levels().forEach(level -> {
					String imagePath = level.backgroundPath();
					String newImagePath = copyImage(imageFolderName, imagePath);
					level.setBackground(newImagePath);
				});
			}
			catch(Exception e) {
				
			}
		}

		private void saveSplashScreen(Game game, String imageFolderName) {
			try {
				game.splashScreen().sprites().forEach(sprite -> {
					String imagePath = sprite.getImagePath();
					String newImagePath = copyImage(imageFolderName, imagePath);
					sprite.setImagePath(newImagePath);
				});
			} catch (Exception e) {
				
			}
		}

		private String copyImage(String imageFolderName, String imagePath) {
			String[] imagePathSplit = imagePath.split("[\\\\/]");
			String newImagePath = imageFolderName+"/"+imagePathSplit[imagePathSplit.length - 1];
			Path fileCopy = (new File(newImagePath).toPath());
			FileInputStream in;
			try {
				in = new FileInputStream(imagePath);			
				Files.copy(in, fileCopy);
			} catch (Exception e) {
				//do nothing, file already exists but I don't care;
			}
			return newImagePath;
		}
		
		public void saveAndExit(Game game, Popup popup){
			saveGame(game);
			returnToMainMenuScreen(popup);
		}
		
		private void hidePopup(Popup popup){
			popup.hide();
		}	
	}


	private class SplashEditScreenManager implements SplashEditScreenController {

		@Override
		public void returnToGameEditScreen() {
			
			Tab splashTab = tabManager.getTabSelectionModel().getSelectedItem();
			tabManager.removeTabAndChangeSelected(splashTab);
			
		}	
		
		@Override
		public void saveSplashScreen(Game game, SplashScreen splashScreen) {
			game.setSplash(splashScreen);
		}
		
	}
	
	private class LevelEditScreenManager implements LevelEditScreenController {

		@Override
		public void returnToGameEditScreen() {
			
			Tab levelEditTab = tabManager.getTabSelectionModel().getSelectedItem();
			tabManager.removeTabAndChangeSelected(levelEditTab);	
			
		}
		
		@Override
		public void saveLevel(Game game, Level level) {
			if(!game.levels().contains(level)) {
				game.addLevel(level);
			}
		}

		@Override
		public void loadSpriteEditScreen(LevelEditScreen levelEditScreen, Sprite sprite) {
			
			Tab levelEditTab = tabManager.getTabSelectionModel().getSelectedItem();
			SpriteEditScreen spriteEditScreen = (SpriteEditScreen) createSpriteEditScreen(levelEditTab, sprite).getContent();
			spriteEditScreen.tagsForUse(levelEditScreen.getTags());
			
		}
		
		/*
		 * @Deprecated use loadSpriteEditScreen(LevelEditScreen levelEditScreen, Sprite sprite) instead, pass in null if necessary
		 */
		@Override
		@Deprecated 
		public void loadSpriteEditScreen(LevelEditScreen levelEditScreen) {
			
			Sprite newSprite = new Sprite();
			loadSpriteEditScreen(levelEditScreen, newSprite);
		}

		@Override
		/**
		 * collision table load screen
		 * @author Anika
		 * @param levelEditScreen
		 */
		public void loadCollisionTableScreen(LevelEditScreen levelEditScreen) {
			Tab collisionTableTab = tabManager.getTabSelectionModel().getSelectedItem();
			createCollisionTableScreen(collisionTableTab, levelEditScreen.getTags(), levelEditScreen.getSpriteMap());

		}
		
	}
	
	private class SpriteEditScreenManager implements SpriteEditScreenController {

		@Override
		public void returnToSelectedLevel(LevelEditScreen levelEditScreen, Tab switchTo, Sprite sprite) {
			
			returnToSelectedLevel(levelEditScreen, switchTo);
			levelEditScreen.addSprite(sprite);
			
		}
		
		@Override
		public void returnToSelectedLevel(LevelEditScreen levelEditScreen, Tab switchTo) {
			tabManager.removeTabAndChangeSelected(switchTo);
		}
		
	}
	
	private class CollisionTableScreenManager implements CollisionTableScreenController {

		@Override
		public void returnToLevel() {
			// TODO Auto-generated method stub
			
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
