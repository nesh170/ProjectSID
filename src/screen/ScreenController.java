package screen;


import javax.imageio.ImageIO;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
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
import resources.constants.DOUBLE;
import resources.constants.STRING;
import screen.gameEditScreen.GameEditScreen;
import screen.gameEditScreen.GameEditScreenController;
import screen.gamePlayScreen.GamePlayScreenController;
import screen.levelEditScreen.LevelEditScreen;
import screen.levelEditScreen.LevelEditScreenController;
import screen.mainMenu.MainMenuScreen;
import screen.mainMenu.MainMenuScreenController;
import screen.splashEditScreen.SplashEditScreen;
import screen.splashEditScreen.SplashEditScreenController;
import screen.spriteEditScreen.SpriteEditScreen;
import screen.spriteEditScreen.SpriteEditScreenController;
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
	private Group myRoot;
	private Scene myScene;
	private TabPane tabPane;
	private SingleSelectionModel<Tab> singleSelectionModel;			// Assists in selecting the correct tab after opening / closing tabs

	// ScreenController Inner Class Handlers
	MainMenuScreenController myMainMenuScreenManager;
	GameEditScreenController myGameEditScreenManager;
	SplashEditScreenController splashEditScreenManager;
	LevelEditScreenController levelEditScreenManager;
	SpriteEditScreenController spriteEditScreenManager;
	GamePlayScreenController gamePlayScreenManager;
	
	
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
		
		configureControllers(stage);
		
		configureStageAndRoot(stage, myRoot);
		configureWidthAndHeight(width, height);
		configureNewScreenWidthAndHeight(width, height);
		
		configureTabPane();
		
		createInitialMainMenuScreen();
	
	}
	
	private void configureControllers(Stage stage) {
		
		myMainMenuScreenManager = new MainMenuScreenManager(stage);
		myGameEditScreenManager = new GameEditScreenManager();
		splashEditScreenManager = new SplashEditScreenManager(stage);
		levelEditScreenManager = new LevelEditScreenManager();
		spriteEditScreenManager = new SpriteEditScreenManager();
		gamePlayScreenManager = new GamePlayScreenManager();
		
	}

	private void configureStageAndRoot(Stage stage, Group root) {
		
		this.myRoot = root;
		
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
		instantiateSelectionModel();
		setTabPaneAlignmentAndSize();
		addTabPane();
		
	}
	
	private void instantiateTabPane() {
		tabPane = new TabPane();
	}
	
	private void instantiateSelectionModel() {
		singleSelectionModel = tabPane.getSelectionModel();
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
		
		//USED FOR TEST SPLASHEDITSCREEN //DO NOT REMOVE //@AUTHOR KYLE
		addTabWithScreenWithStringIdentifier(
				new SplashEditScreen(splashEditScreenManager, newScreenWidth, newScreenHeight),
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
		tab.onClosedProperty().set(e -> setCorrectTabModifiabilityAndViewability());

		tabPane.getTabs().add(tab);
		
		setCorrectTabModifiabilityAndViewability();

	}

	/**
	 * Take all tabs except the current one and make them unmodifiable. Make the current tab modifiable
	 * 
	 * @author Ruslan
	 */
	private void setCorrectTabModifiabilityAndViewability() {
		
		int tabPaneLastIndex = tabPane.getTabs().size() - 1;
		
		ObservableList<Tab> tabs = tabPane.getTabs();
		
		// All Tab(s) except the last one
		for (int i=0; i < tabPaneLastIndex; i++) {
			disableTab(tabs.get(i));
		}
		
		enableTab(tabs.get(tabPaneLastIndex));
	
	}
	
	private void disableTab(Tab tab) {
	
		tab.setClosable(false);
		tab.setDisable(true);
		
	}
	
	private void enableTab(Tab tab) {
		
		tab.setClosable(true);
		tab.setDisable(false);
		
		// Selects current, enabled tab
		// http://stackoverflow.com/questions/6902377/javafx-tabpane-how-to-set-the-selected-tab
		singleSelectionModel.select(tab);
		
	}
	
	private void removeTabAndChangeSelected(Tab selectedNew) {
		Tab tab = singleSelectionModel.getSelectedItem();
		tabPane.getTabs().remove(tab);
	
		setCorrectTabModifiabilityAndViewability();		
		singleSelectionModel.select(selectedNew);
	}
	
}
