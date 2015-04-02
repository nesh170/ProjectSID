package player;

import java.util.List;
import javafx.animation.Timeline;
import javafx.scene.Group;
import gameEngine.GameEngineAbstract;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GamePlayer implements GamePlayerInterface{


	private GameEngineAbstract myEngine;
	private Scene myScene;
	private BorderPane myBorderPane;
	private Timeline myTimeline;
	private int myFrameRate = 30;
	private String myGameFilePath;
	private Group myRoot;
	
	//constructor for testing
	public GamePlayer(Stage stage) {
		myTimeline = new Timeline();
		myRoot = new Group();
		StackPane pause = makePauseMenu();
	    myRoot.getChildren().add(pause);
	    myBorderPane = new BorderPane();
		MenuBar menuBar = createPlayerMenu();
        myBorderPane.setTop(menuBar);
		myBorderPane.setCenter(myRoot);
        myScene = new Scene(myBorderPane, 1200, 600);
        stage.setScene(myScene);
	}
	
	public GamePlayer() {
		myTimeline = new Timeline();
	}
	
	private Menu buildFileMenu() {
		Menu fileMenu = new Menu("File");
		
		MenuItem pauseItem = new MenuItem("Pause Game");
		pauseItem.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
		pauseItem.setOnAction(event -> { pause(); });
		pauseItem.setOnAction(event -> { System.out.println("PAUSE"); });
		
		MenuItem playItem = new MenuItem("Resume Game");
		playItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		playItem.setOnAction(event -> { start(); });
		playItem.setOnAction(event -> { System.out.println("PLAY"); });
		
		MenuItem newGameItem = new MenuItem("New Game");
		newGameItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		newGameItem.setOnAction(event -> { System.out.println("HI"); });
		
		MenuItem loadItem = new MenuItem("Load Level");
		loadItem.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		loadItem.setOnAction(event -> { System.out.println("LOAD"); });
		
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
		quitItem.setOnAction(event -> { System.exit(0); });
		fileMenu.getItems().addAll(pauseItem, playItem, newGameItem, loadItem, 
				quitItem);		
		return fileMenu;
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
	
	private StackPane makePauseMenu() {
		Button startButton = new Button("Resume");
	    startButton.setOnAction(event -> { start(); });
		StackPane pause = new StackPane();
	    //StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
	    pause.getChildren().addAll(startButton);
	    pause.setStyle("-fx-background-color: rgba(0, 100, 100, 0.25); -fx-background-radius: 10;");
	    pause.setPrefWidth(500);
	    pause.setPrefHeight(500);
	    //glass.setPadding(new Insets(25));
//	    glass.setMaxWidth(rect.getWidth() - 40);
//	    glass.setMaxHeight(rect.getHeight() - 40);
	    return pause;
	}
	
	private void initialize(GameEngineAbstract engine) {
		myEngine = engine;
		myTimeline = new Timeline();
		myTimeline.setCycleCount(Animation.INDEFINITE);
		//myTimeline.getKeyFrames().add(frame);
	}
	
	private void setupAnimation() {
		//new KeyFrame(Duration.millis(1000 / myFrameRate), e -> updateGrid());
	}
	
	private void update() {
		Group group = myEngine.render();
	}
	
	@Override
	public void start() {
		myTimeline.play();
	}

	@Override
	public void pause() {
		myTimeline.stop();
	}

	public boolean playerIsRunning() {
		return myTimeline.getStatus() == Animation.Status.RUNNING;
	}
	
	@Override
	public int getHighScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void loadNewGame() {
		// TODO Auto-generated method stub
		
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