package screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import javax.imageio.ImageIO;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import level.Level;
import resources.constants.DOUBLE;
import screen.gameEditScreen.GameEditScreen;
import screen.gameEditScreen.GameEditScreenController;
import screen.gamePlayScreen.GamePlayScreenController;
import screen.levelEditScreen.LevelEditScreen;
import screen.levelEditScreen.LevelEditScreenController;
import screen.mainMenu.MainMenuScreen;
import screen.mainMenu.MainMenuScreenController;
import screen.splashEditScreen.SplashEditScreen;
import screen.splashEditScreen.SplashEditScreenController;
import screen.spriteEditScreen.SpriteEditScreenController;
import game.Game;
import screen.Screen;

/**
 * 
 * @author Ruslan
 * @author anika
 * @author Kyle
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
 * 
 */

public class ScreenController extends Scene implements ScreenControllerInterface {
	
	// Static Variables
	
	
	// Instance Variables
	// Sizing
	private double width, height;
	private double newScreenWidth, newScreenHeight;
	// JavaFX
	private Stage stage;
	private Group root;
	private TabPane tabPane;
	// ScreenController Inner Class Handlers
	MainMenuScreenController mainMenuScreenManager = new ScreenController.MainMenuScreenManager();
	GameEditScreenController gameEditScreenManager = new ScreenController.GameEditScreenManager();
	SplashEditScreenController splashEditScreenManager = new ScreenController.SplashEditScreenManager();
	LevelEditScreenController levelEditScreenManager = new ScreenController.LevelEditScreenManager();
	SpriteEditScreenController spriteEditScreenManager = new ScreenController.SpriteEditScreenManager();
	GamePlayScreenController gamePlayScreenManager = new ScreenController.GamePlayScreenManager();
	
	
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
	public ScreenController(Stage stage, Group root, double width, double height) {
		
		super(root, width, height, Color.TAN);
		
		configureStageAndRoot(stage, root);
		configureWidthAndHeight(width, height);
		configureNewScreenWidthAndHeight(width, height);
		
		configureTabPane();
		
		createInitialMainMenuScreen();
	
	}
	
	private void configureStageAndRoot(Stage stage, Group root) {
		
		this.root = root;
		this.stage = stage;
		
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
		
		instantiateTabPane();
		setTabPaneAlignmentAndSize();
		addTabPaneToThis();
		
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
	
	private void addTabPaneToThis() {
		root.getChildren().add(tabPane);
	}
	
	private void createInitialMainMenuScreen() {
		
		addTabWithScreenWithStringIdentifier(
				new MainMenuScreen(mainMenuScreenManager, newScreenWidth, newScreenHeight),
				"Main Menu");
		
		//USED FOR TEST SPLASHEDITSCREEN //DO NOT REMOVE //@AUTHOR KYLE
		addTabWithScreenWithStringIdentifier(
				new SplashEditScreen(newScreenWidth, newScreenHeight),
				"Splash Edit Screen");
		
		//USED FOR TEST LEVELEDITSCREEN --> No parent gameeditscreen yet,
		//so there will be no tab to return to, and there should be an error
		addTabWithScreenWithStringIdentifier(
				new LevelEditScreen(levelEditScreenManager,new Tab()
				,newScreenWidth,newScreenHeight),"leveleditScreen");
				
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

		tabPane.getTabs().addAll(tab);
		
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
	
	
	// Nested Classes
	// Inner class for handling MainMenuScreenController methods
	private class MainMenuScreenManager implements MainMenuScreenController {

		@Override
		public void createNewGame() {
			throw new IllegalStateException("unimplemented createNewGame in MainMenuScreenController");
		}

		@Override
		public void loadGameEditScreen(String recentGameName) {
			throw new IllegalStateException("unimplemented loadGameEditScreen in MainMenuScreenController");
		}

		@Override
		public void loadGame() {
			
			URI gameLocation;

			if ((gameLocation = getGameFileURI()) != null) {
				loadGame(gameLocation);
			}

		}
		
		/**
		 * @author Ruslan
		 * 
		 * Opens a FileChooser, select an XML that stores our Game
		 * @return URI of exact Game XML location
		 */
		private URI getGameFileURI() {
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose Game File");
			
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Game File", "*.xml*"));
			
			File file = fileChooser.showOpenDialog(stage);
			
			if (file != null) {
				return file.toURI();
			} else {
				return null;
			}
			
		}

		/**
		 * Called by loadGame() in the interface. Guaranteed that gameLocationOnDisk != null
		 * 
		 * @param gameLocationOnDisk
		 */
		private void loadGame(URI gameLocationOnDisk) {
			
			Game game = null;
			
			// TODO:
				/*
				 * Instantiate a DataManager or call DataManager.sharedInstance()
				 * Read off a Game from XML
				 */
			
			// Here, Game can potentiall still be null if the user selected a non-Game XML
			if (game != null) {
				
				
				// TODO
				/*
				 * Instantiate a GameEditScreen
				 * Set main menu tab to unclickable and unremovable
				 */
			} 
			
			else {
				displayError("Invalid Game file selected.");
			}
			
		}
		
		@Override
		public void closeApplication() {
			stage.close();		
		}
		
	}

	// Inner class for handling GameEditScreenController methods
	private class GameEditScreenManager implements GameEditScreenController {

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
		
	}
	
	// Inner class for handling SplashEditScreenController methods
	private class SplashEditScreenManager implements SplashEditScreenController {

		@Override
		public void returnToGameEditScreen() {
			throw new IllegalStateException("unimplemented returnToGameEditScreen in SplashEditScreenController");
		}

		@Override
		public void addStartButton() {
//			// TODO Auto-generated method stub
//			//ImageView imageView = new ImageView();
//			//Image image;
//			FileChooser fileChooser = new FileChooser();
//			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
//			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
//			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
//			
//			File file = fileChooser.showOpenDialog(null);
//			
//			//try {
//				Image image = new Image ("/voogasalad_ScrollingDeep/src/images/default_start_button.png"); //DEFAULT IMAGE TO LOAD
//				//BufferedImage start = ImageIO.read(file);
//				//image = SwingFXUtils.toFXImage(start, null);
//				//imageView.setImage(image);
//			//} //catch (IOException ex) {
//				//throw some sort of exception thing
//				
//			//}
//			MouseEvent
//			setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
			
		}

		@Override
		public void addImage() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addText() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addAnimation() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void saveSplashScreen() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void trashSplashScreen() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void backSplashScreen() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	// Inner class for handling LevelEditScreenController methods
	private class LevelEditScreenManager implements LevelEditScreenController {

		@Override
		public void returnToGameEditScreen(Tab currentGameScreen) {
			Tab levelTab = tabPane.getSelectionModel().getSelectedItem();
			tabPane.getTabs().remove(levelTab);
			tabPane.getSelectionModel().select(currentGameScreen);
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
