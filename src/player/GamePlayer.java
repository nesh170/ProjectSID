package player;

import gameEngine.GameEngine;

import java.io.File;
import java.util.List;

import data.DataHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GamePlayer implements GamePlayerInterface{

	public final static double FRAME_RATE = 60;
	public final static double UPDATE_RATE = 120;

	private GamePlayerView myView;
	private GameEngine myEngine;
	private Scene myScene;
	
	private File myGameFolder;
	
	private BorderPane myBorderPane;
	private Timeline myTimeline;
	private String myGameFilePath;
	private Group myRoot;
	private Stage myGameChooser;
	private MenuBar myMenuBar;
	private StackPane myPause;
	private int currentSize = 4;
	
	//constructor for testing
	public GamePlayer(Stage stage) {
		
		//TODO: transfer to GamePlayerView.java
		myView = new GamePlayerView();
		
		myBorderPane = new BorderPane();
		myRoot = new Group();
		myPause = makePauseScreen();
		myMenuBar = createPlayerMenu();
        BorderPane.setMargin(myPause, new Insets(25, 25, 25, 25));
		myBorderPane.setTop(myMenuBar);
		myBorderPane.setCenter(myRoot);
        myScene = new Scene(myBorderPane, 1200, 600);
        stage.setScene(myScene);
        myGameChooser = buildGameChooser(stage);
	}
	
	//currently using borderpane
	//will change to scrollpane when basic engine finished
	public GamePlayer(double width, double height) {
		//initialize(engine);
		myPause = makePauseScreen();
		myBorderPane = new BorderPane();
		myMenuBar = createPlayerMenu();
        BorderPane.setMargin(myPause, new Insets(25, 25, 25, 25));
		myBorderPane.setTop(myMenuBar);
		myBorderPane.setCenter(myRoot);
	}
	
	private Menu buildFileMenu() {
		Menu fileMenu = new Menu("File");
		
		MenuItem pauseItem = new MenuItem("Pause Game");
		pauseItem.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
		pauseItem.setOnAction(event -> { pause(); });
		
		MenuItem playItem = new MenuItem("Resume Game");
		playItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		playItem.setOnAction(event -> { start(); });
		
		MenuItem newGameItem = new MenuItem("New Game");
		newGameItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		newGameItem.setOnAction(event -> { myGameChooser.show(); });
		
		MenuItem loadItem = new MenuItem("Load Level");
		loadItem.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		loadItem.setOnAction(event -> { loadNewGame(); });
		
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
		quitItem.setOnAction(event -> { System.exit(0); });
		fileMenu.getItems().addAll(pauseItem, playItem, newGameItem, loadItem, 
				quitItem);		
		return fileMenu;
	}
	
	//implementation still needed to connect to actual file chooser 
	private Stage buildGameChooser(Stage s) {
		Stage gameChooser = new Stage();
        gameChooser.initModality(Modality.APPLICATION_MODAL);
        gameChooser.initOwner(s);
	    Button mario = new Button("Mario");
        mario.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 3,2,2,2;");
        mario.setOnAction(event -> chooseGame(gameChooser));
        
        VBox vbox = new VBox(50);
        vbox.getChildren().addAll(new Text("Your Games"), mario);
        Scene allGames = new Scene(vbox, 300, 200);
        gameChooser.setScene(allGames);
		return gameChooser;
	}
	
	public MenuBar createPlayerMenu() {
		MenuBar menuBar = new MenuBar();
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        menuBar.getMenus().add(buildFileMenu());
        menuBar.getMenus().add(menuEdit);
        menuBar.getMenus().add(menuView);
        return menuBar;
	}
	
	private StackPane makePauseScreen() {
		StackPane pause = new StackPane();
		Button startButton = new Button("Resume");
	    startButton.setOnAction(event -> { start(); });
	    pause.getChildren().add(startButton);
	    pause.setStyle("-fx-background-color: rgba(184, 184, 184, 0.25); -fx-background-radius: 10;");
	    pause.setPrefWidth(800);
	    pause.setPrefHeight(500);
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
		KeyFrame updateFrame = new KeyFrame(Duration.millis(1000 / UPDATE_RATE), e -> update());
		KeyFrame displayFrame = new KeyFrame(Duration.millis(1000 / FRAME_RATE), e -> display());
		myTimeline.getKeyFrames().add(updateFrame);
		myTimeline.getKeyFrames().add(displayFrame);
	}
	
	private void chooseGame(Stage gameChooser) {
		myGameFolder = DataHandler.chooseDir(gameChooser);
		File[] levels = DataHandler.getFilesFromDir(myGameFolder);
	}
	
	private void update() {
		myEngine.update();
	}
	
	private void display() {
		myRoot = myEngine.render();
	}
	
	@Override
	public void start() {
		removePause();
		myTimeline.play();
	}

	@Override
	public void pause() {
		myTimeline.stop();
		myEngine.pause(myBorderPane);
		bringupPause();
	}

	public boolean playerIsRunning() {
		return myTimeline.getStatus() == Animation.Status.RUNNING;
	}
	
	@Override
	public int getHighScore() {
		// get high score from engine
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
		// TODO Auto-generated method stub
		
	}

}
