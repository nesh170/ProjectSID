package screen.screens;

import game.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import resources.ScreenButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.controllers.GameEditScreenController;
import screen.controllers.ScreenController;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;


/**
 * The screen where users edit a game allows users to edit a level or edit a
 * sprite.
 * 
 * @author Yongjiao
 * @author Anika
 */
// Splash double click and right click
public class GameEditScreen extends Screen {

	// Static Variables
	private static boolean TESTING = false;		// change this to true to debug, but only push "false"
	
	
	// Instance variables
	private GameEditScreenController controller;
	
	private Game game;
	private SplashScreen splashScreen;
	private ObservableList<Level> levels;
	// initalized for testing purpose
	private Level selectedLevel = new Level(INT.DEFAULT_LEVEL_WIDTH, INT.DEFAULT_LEVEL_HEIGHT);
	private int selectedIndex = 0;
	// JavaFX
	private StackPane levelDisplay;
	private VBox splashDisplay;


	// Getters & Setters
	/**
	 * add a Level to a Game
	 * 
	 * @return int index of where the inserted Level is
	 */
	public int addLevel(Level level) {
		
		levels.add(level);
		return levels.indexOf(level);
		
	}
	
	/**
	 * add to current game level
	 * 
	 * @param level
	 * @return
	 */
	public void setLevel(int index, Level level) {
		levels.add(index, level);
	}
	
	
	// Constructor & Helpers
	/**
	 * Set up GameEdit screen from previously created game to re-edit game.
	 * 
	 * @param controller
	 *            , width, height, game
	 */
	public GameEditScreen(Game game, GameEditScreenController controller, double width, double height) {

		super(width, height);
		
		this.game = game;
		
		this.setStyle(STRING.COLORS.FX_GAME_EDIT_BACKGROUND);
		
		if (GameEditScreen.TESTING) {
			
			this.game.setSplash(new SplashScreen(300, 400)); 	// those line tests
															// weather splash
															// display area will
															// change if there is
															// splash screen in
															// myGame
			
			System.out.println(this.game);
			
		}
		
		initialize(controller);
		
	}

	/**
	 * @param controller
	 */
	private void initialize(GameEditScreenController controller) {

		this.controller = controller;
		configureButtons();
	}

	private void configureButtons() {

		configureLevelDisplay();
		this.setCenter(levelDisplay);

		ConfigureSplashDisplay();
		this.setLeft(splashDisplay);
		
	}

	/**
	 * Displays a splash
	 * 
	 * @return
	 */
	private void ConfigureSplashDisplay() {

		splashDisplay = new VBox();
		splashDisplay.setAlignment(Pos.CENTER);
		
		StackPane sp = new StackPane();
		splashDisplay.getChildren().add(sp);

		sp.getChildren().addAll(makeText(STRING.GAME_EDIT.SPLASH_SCREEN));
		Button s;
		
		// Testing
		if (GameEditScreen.TESTING) {
			System.out.println(game.hasSplash());
		}
		
		if (!game.hasSplash()) {
			
			s = makeAddSignWhenEmpty("Add New Splash Screen",
					e -> controller.loadSplashEditScreen(game));
			
		}
			
		else {
			s = displayMySplash();
		}
			
		sp.getChildren().add(s);
		
	}

	private Button displayMySplash() {

		Button b = makeTempLevelSplashDisplayImage(STRING.GAME_EDIT.SPLASH_TMP, INT.SPLASH);
		
		// b.setOnMouseClicked(e -> this.handleDouleRightClick(b)); //parameter:
		// myGame.getSplash().ImageRepresentation();
		
		return b;
		
	}

	private Text makeText(String s) {

		Text text = new Text(s);
		Reflection r = new Reflection();
		
		r.setFraction(0.6f);
		
		text.setEffect(r);
		text.setFont(Font.font("SERIF", FontWeight.BOLD, 30));
		text.setTranslateY(-300); // ?? uncertain of how offset works but this
									// works for now
		
		return text;
		
	}

	private Button makeAddSignWhenEmpty(String s, EventHandler<MouseEvent> lamda) {

		ImageView addsign = new ImageView(new Image(STRING.GAME_EDIT.ADD_IMG));
		
		addsign.setFitHeight(INT.GAMEEDIT_ADD_SIGN_DIM);
		addsign.setFitWidth(INT.GAMEEDIT_ADD_SIGN_DIM);
		Button b = new Button(s, addsign);
		b.setContentDisplay(ContentDisplay.TOP);
		b.setOnMouseClicked(lamda);
		b.setMinSize(INT.DEFAULT_LEVEL_DISPLAY_WIDTH,
				INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		b.setGraphic(addsign);
		
		return b;

	}

	/**
	 * display a note on editing and removing a level
	 */
	private Text displayNote() {

		Text t = new Text(STRING.GAME_EDIT.NOTE);
		t.setTranslateY(210);
		t.setTranslateX(250);
		return t;

	}

	private void configureLevelDisplay() {

		// , DisplayLevels(myLevels)
		levelDisplay = new StackPane();
		ScrollPane levelSP = this.displayLevels(levels);

		ImageView add = makeButton(STRING.GAME_EDIT.PLUS_IMG,
				e -> controller.loadLevelEditScreen(game));
		levelDisplay.setAlignment(add, Pos.TOP_RIGHT);

		ImageView play = makeButton(STRING.GAME_EDIT.PLAY_IMG,
				e -> controller.playGame(game));
		levelDisplay.setAlignment(play, Pos.TOP_CENTER);

		ImageView back = makeButton(STRING.GAME_EDIT.BACK_IMG,
				e -> controller.returnToMainMenuScreen());
		levelDisplay.setAlignment(back, Pos.TOP_LEFT);

		// ImageView trash = makeButton(STRING.TRASH_IMG, e ->
		// controller.trashLevel(myGame, selectedIndex));
		// levelDisplay.setAlignment(trash, Pos.BOTTOM_RIGHT);

		levelDisplay.getChildren().addAll(levelSP, back, add, play,
				displayNote());

	}

	/**
	 * display list of levels that are represented by images in parallel
	 * 
	 * @param ObservableList
	 *            <Level>
	 */
	private ScrollPane displayLevels(List<Level> levels) {

		// TODO: get ListView: List<LevelView>: a group to represent each level,
		// in replace of ImageView below
		// TableView<ObservableList> levelTable = new TableView();
		ScrollPane sp = configureScrollPane();
		HBox levelHB = configureHBox();
		sp.setContent(levelHB);

		if (game.hasLevel()) {
			displayLevelsInParallel(levelHB);
		}

		else {
			displayLevelsWhenEmpty(levelHB);
		}

		return sp;

	}

	private HBox configureHBox() {

		HBox hb = new HBox(INT.GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE);
		hb.setAlignment(Pos.CENTER);
		return hb;

	}

	private ScrollPane configureScrollPane() {

		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true); // wont extend out of ScrollPane vertically
		sp.setPannable(true);
		return sp;

	}

	private void displayLevelsWhenEmpty(HBox hb) {

		// can't add ObservableList to a HBox directly
		hb.setAlignment(Pos.CENTER);

		hb.getChildren().addAll(
				this.makeAddSignWhenEmpty("Add New Level Here",
						e -> controller.loadLevelEditScreen(game)));

	}

	private void displayLevelsInParallel(HBox hb) {

		// can't add ObservableList to a HBox directly
		Button level1 = makeTempLevelSplashDisplayImage(
				STRING.GAME_EDIT.LEVEL1IMAGE, INT.LEVEL); // tmp string path
		Button level2 = makeTempLevelSplashDisplayImage(
				STRING.GAME_EDIT.LEVEL2IMAGE, INT.LEVEL);
		Button level3 = makeTempLevelSplashDisplayImage(
				STRING.GAME_EDIT.SPRITEIMAGE, INT.LEVEL);
		hb.getChildren().addAll(level1, level2, level3);

	}

	// temporary methods to display level/Splash Image
	private Button makeTempLevelSplashDisplayImage(String path, int flag) {

		Button b = new Button();
		ImageView img = new ImageView(new Image(path));
		img.setFitHeight(INT.DEFAULT_LEVEL_DISPLAY_HEIGHT);
		img.setFitWidth(INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
		b.setGraphic(img);
		b.setOnMouseClicked(handleDouleRightClick(b, flag));
		return b;
	}

	/**
	 * handles mouse event: double left mouse click and right mouse click
	 * 
	 * @param node
	 * @return EventHandler<MouseEvent>
	 */
	private EventHandler<MouseEvent> handleDouleRightClick(Node node, int flag) {

		return new EventHandler<MouseEvent>() { // double Click to edit a screen

			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					
					if (mouseEvent.getClickCount() == 2) {
						
						if (flag == INT.LEVEL) {
							controller.loadLevelEditScreen(selectedLevel);
						}
							
						else {
							controller.loadSplashEditScreen(game);
						}
							
					}
					
				}
				
				else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
					
					if (flag == INT.LEVEL) {
						makeRightClickMenu(
								e -> controller
										.loadLevelEditScreen(selectedLevel),
								e -> controller.trashLevel(game,
										selectedIndex)).show(node,
								mouseEvent.getScreenX(),
								mouseEvent.getScreenY());
					}
						
					else {
						makeRightClickMenu(
								e -> controller.loadSplashEditScreen(game),
								e -> controller.trashSplash(game)).show(node,
								mouseEvent.getSceneX(), mouseEvent.getSceneY());
					}
						
				}
				
			}
			
		};
		
	}

	private ImageView makeButton(String location, EventHandler<MouseEvent> lamda) {

		ImageView b = new ImageView(new Image(location));
		b.setFitHeight(80);
		b.setFitWidth(80);
		b.setOnMouseClicked(lamda);
		
		return b;

	}

	/**
	 * make a right click menu for editing and removing a level
	 */
	private ContextMenu makeRightClickMenu(EventHandler<ActionEvent> toEdit, EventHandler<ActionEvent> toRemove) { // pass in Level

		final ContextMenu rMenu = new ContextMenu();
		MenuItem edit = new MenuItem("Edit");
		edit.setOnAction(toEdit);
		MenuItem remove = new MenuItem("remove");
		remove.setOnAction(toRemove);
		rMenu.getItems().addAll(edit, remove);
		
		return rMenu;
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {

		Menu fileMenu = makeFileMenu(o -> controller.saveGame(game),
				o -> controller.returnToMainMenuScreen(),
				o -> controller.returnToMainMenuScreen());
		menuBar.getMenus().addAll(fileMenu, makeLevelMenu(), makeSplashMenu(),
				makeGameMenu(), makeTrashMenu());

	}

	private Menu makeLevelMenu() {

		Menu levelMenu = new Menu("Level");
		MenuItem addLevel = new MenuItem("Add new Level");
		addLevel.setOnAction(o -> controller.loadLevelEditScreen(game));
		MenuItem editLevel = new MenuItem("Edit Level");
		editLevel.setOnAction(o -> controller.loadLevelEditScreen(game
				.levels().get(selectedIndex))); // references to the specific
												// level within a game
		levelMenu.getItems().addAll(addLevel, editLevel);
		return levelMenu;

	}

	private Menu makeSplashMenu() {

		Menu splashMenu = new Menu("Splash Screen");
		MenuItem addSplash = new MenuItem("Add new Splash Screen");
		addSplash.setOnAction(o -> controller.loadSplashEditScreen(game));
		MenuItem editSplash = new MenuItem("Edit Splash Screen");
		editSplash.setOnAction(o -> controller.loadSplashEditScreen(game));
		splashMenu.getItems().addAll(addSplash, editSplash);
		return splashMenu;

	}

	private Menu makeGameMenu() {

		Menu gameMenu = new Menu("Game");
		MenuItem addPlay = new MenuItem("Play Game");
		addPlay.setOnAction(o -> controller.playGame(game));
		gameMenu.getItems().addAll(addPlay);
		return gameMenu;
		
	}

	private Menu makeTrashMenu() {

		ImageView trashImage = new ImageView(new Image(STRING.GAME_EDIT.TRASH_ICON));

		super.sizeMenuImageView(trashImage, DOUBLE.MENU_BAR_HEIGHT, DOUBLE.MENU_BAR_HEIGHT);
		
		Menu trashButton = new Menu("", trashImage);
		MenuItem delete = new MenuItem("Add new Level");
		delete.setOnAction(o -> controller.trashLevel(game, selectedIndex));
		
		return trashButton;

	}

}
