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
	private Stage stage;
	private Group root;
	private TabPane tabPane;
	
	
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
				new MainMenuScreen(this, newScreenWidth, newScreenHeight),
				"Main Menu");
				
	}
	
	
	// All other instance methods
	// Public
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
	public void loadGame() {
		
		URI gameLocation;

		if ((gameLocation = getGameFileURI()) != null) {
			loadGame(gameLocation);
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
	
	@Override
	public void closeApplication() {
		stage.close();
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
	
}
