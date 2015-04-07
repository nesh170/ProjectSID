package player;

import gameEngine.GameEngine;

import java.io.File;
import java.io.IOException;
import java.util.List;

import data.DataHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import levelPlatform.level.Level;

public class GamePlayer implements GamePlayerInterface {

	public final static double FRAME_RATE = 60;
	public final static double UPDATE_RATE = 120;
	//private final static File MARIO_PATH 
	
	private GamePlayerView myView;
	private GameEngine myEngine;
	private Scene myScene;

	private File myGameFolder;
	private List<Level> myGameLevels;

	private BorderPane myBorderPane;
	private Timeline myTimeline;
	private String myGameFilePath;
	private Group myRoot;
	private Stage myGameChooser;
	private MenuBar myMenuBar;
	private StackPane myPause;
	private double myWidth;
	private double myHeight;
	private int myLives;
	private int myHealth;
	private int myScore;
	
	// constructor for testing
	public GamePlayer(Stage stage) {

		// TODO: transfer to GamePlayerView.java
		//myView = new GamePlayerView();

		myTimeline = new Timeline();
		myTimeline.setCycleCount(Animation.INDEFINITE);
		myRoot = new Group();
		//setupAnimation();
		Stage chooserStage = new Stage();
		chooserStage.initModality(Modality.APPLICATION_MODAL);
		chooserStage.initOwner(stage);
		chooseGame(chooserStage);
		//chooserStage.show();
		myPause = makePauseScreen();
		myBorderPane = new BorderPane();
		myMenuBar = createPlayerMenu();
        myBorderPane.setMargin(myPause, new Insets(25, 25, 25, 25));
		myBorderPane.setTop(myMenuBar);
		myBorderPane.setCenter(myRoot);
        myScene = new Scene(myBorderPane, 1200, 600);
        stage.setScene(myScene);
	}

	// currently using borderpane
	// will change to scrollpane when basic engine finished
	public GamePlayer(double width, double height) {
		// initialize(engine);
		myWidth = width;
		myHeight = height;
		setupAnimation();
		myPause = makePauseScreen();
		myBorderPane = new BorderPane();
		myMenuBar = createPlayerMenu();
		BorderPane.setMargin(myPause, new Insets(25, 25, 25, 25));
		myBorderPane.setTop(myMenuBar);
		myBorderPane.setCenter(myRoot);
		setupAnimation();
	}

	private Menu buildFileMenu() {
		Menu fileMenu = new Menu("File");

		MenuItem pauseItem = new MenuItem("Pause Game");
		pauseItem.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
		pauseItem.setOnAction(event -> {
			pause();
		});

		MenuItem playItem = new MenuItem("Resume Game");
		playItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		playItem.setOnAction(event -> {
			start();
		});

		MenuItem newGameItem = new MenuItem("New Game");
		newGameItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		newGameItem.setOnAction(event -> {
			myGameChooser.show();
		});

		MenuItem loadItem = new MenuItem("Load Level");
		loadItem.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		loadItem.setOnAction(event -> {
			loadNewGame();
		});

		MenuItem quitItem = new MenuItem("Quit");
		quitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
		quitItem.setOnAction(event -> {
			System.exit(0);
		});
		fileMenu.getItems().addAll(pauseItem, playItem, newGameItem, loadItem,
				quitItem);
		return fileMenu;
	}

	private Menu buildGamesMenu() {
		Menu gamesMenu = new Menu("Games");
		MenuItem marioItem = new MenuItem("Mario");
		marioItem.setOnAction(event -> {
			Text prompt = new Text("Are you sure you want to load a new Game? "
					+ "Save progress before proceeding.");
			Stage choose = chooserConfirmationDialog(prompt);
			choose.show();
		});
		gamesMenu.getItems().addAll(marioItem);
		return gamesMenu;
	}
	
	// implementation still needed to connect to actual file chooser
	// add this once we have everything else working
	private Stage buildGameChooser() {
		Stage gameChooser = new Stage();
		gameChooser.initModality(Modality.APPLICATION_MODAL);
		Button mario = new Button("Mario");
		mario.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 3,2,2,2;");
		mario.setOnAction(event -> chooseGame(gameChooser));

		VBox vbox = new VBox(50);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().addAll(new Text("Your Games"), mario);
		Scene allGames = new Scene(vbox, 300, 200);
		gameChooser.setScene(allGames);
		return gameChooser;
	}

	private Stage chooserConfirmationDialog(Text text) {
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		VBox vbox = new VBox(25);
		vbox.setAlignment(Pos.TOP_CENTER);
		HBox buttonBox = new HBox(25);
		Button yes = new Button("Yes");
		yes.setOnAction(event -> loadNewGame());
		Button no = new Button("No");
		no.setOnAction(event -> dialogStage.close());
		buttonBox.getChildren().addAll(yes, no);
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.getChildren().addAll(text, buttonBox);
		Scene dialogScene = new Scene(vbox, 500, 100);
		dialogStage.setScene(dialogScene);
		return dialogStage;
	}
	
	public MenuBar createPlayerMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menuView = new Menu("View");
		menuBar.getMenus().add(buildFileMenu());
		menuBar.getMenus().add(buildGamesMenu());
		menuBar.getMenus().add(menuView);
		return menuBar;
	}

	private StackPane makePauseScreen() {
		StackPane pause = new StackPane();
		Button startButton = new Button("Resume");
		startButton.setOnAction(event -> {
			start();
		});
		pause.getChildren().add(startButton);
		pause.setStyle("-fx-background-color: rgba(184, 184, 184, 0.25); -fx-background-radius: 10;");
		pause.setPrefWidth(myWidth);
		pause.setPrefHeight(myHeight);
		return pause;
	}

	private void bringupPause() {
		myRoot.getChildren().add(myPause);
	}

	private void removePause() {
		myRoot.getChildren().remove(myPause);
	}

	private void initialize(GameEngine engine) {
		myEngine = engine;
		myTimeline = new Timeline();
		myTimeline.setCycleCount(Animation.INDEFINITE);
		setupAnimation();
	}

	private void setupAnimation() {
		myTimeline = new Timeline();
		KeyFrame updateFrame = new KeyFrame(
				Duration.millis(1000 / UPDATE_RATE), e -> update());
		KeyFrame displayFrame = new KeyFrame(
				Duration.millis(1000 / FRAME_RATE), e -> display());
		myTimeline.getKeyFrames().add(updateFrame);
		myTimeline.getKeyFrames().add(displayFrame);
	}

	private void chooseGame(Stage gameChooser) {
		//find a way to set up a map so we can just have file paths
		//for games already established so no directory needs to be opened here
		myGameFolder = DataHandler.chooseDir(gameChooser);
		try {
			myGameLevels = DataHandler.getLevelsFromDir(myGameFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myEngine = new GameEngine(myGameLevels);
		setupAnimation();
	}

	public HBox createHUD() {
		HBox HUDbox = new HBox(myWidth);
		Text LivesText = new Text("Health:" + myHealth);
		LivesText.setFont(Font.font ("Arial Black", 20));
		LivesText.setFill(Color.WHITE);
		Text HealthText = new Text("Lives:" + myLives);
		HealthText.setFont(Font.font ("Arial Black", 20));
		HealthText.setFill(Color.WHITE);
		Text ScoreText = new Text("Score" + myScore);
		ScoreText.setFont(Font.font ("Arial Black", 20));
		ScoreText.setFill(Color.WHITE);
		HUDbox.getChildren().addAll(LivesText, HealthText, ScoreText);
		HUDbox.setAlignment(Pos.BOTTOM_CENTER);
		return HUDbox;
	}
	
	private void update() {
		myEngine.update();
	}

	private void display() {
		myRoot = myEngine.render();
		myRoot.getChildren().add(createHUD());
	}

	@Override
	public void start() {
		removePause();
		myTimeline.play();
	}

	@Override
	public void pause() {
		myTimeline.stop();
		//myEngine.pause(myBorderPane);
		bringupPause();
	}

	public boolean playerIsRunning() {
		return myTimeline.getStatus() == Animation.Status.RUNNING;
	}

	public MenuBar getBar() {
		return myMenuBar;
	}
	
	public int getLives() {
		//return myEngine.getLives();
		return 0;
	}
	
	public int getHealth() {
		//return myEngine.getHealth();
		return 0;
	}
	
	public int getScore() {
		//return myEngine.getScore();
		return 0;
	}
	
	@Override
	public int getHighScore() {
		// load in high score?
		return 0;
	}
	
	@Override
	public void loadNewGame() {
		System.out.println("LOAD");
	}

	@Override
	public void setPreferences() {
		// TODO Auto-generated method stub

	}

	@Override
	public List findGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveGame() {
		String[] names = new String[]{"mario1.xml", "mario2.xml", "mario3.xml"};
		for (int i = 0; i < myGameLevels.size(); i++) {
			try {
				DataHandler.toXMLFile(myGameLevels.get(i), names[i], myGameFolder.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

}
