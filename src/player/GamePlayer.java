package player;

import gameEngine.GameEngineAbstract;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GamePlayer implements Player{

	private GameEngineAbstract myEngine;
	private Scene myScene;
	private BorderPane myBorderPane;
	private Timeline myTimeline;
	private int myFrameRate = 30;
	private String myGameFilePath;
	
	public GamePlayer(Stage stage) {
		myTimeline = new Timeline();
		myBorderPane = new BorderPane();
		MenuBar menuBar = new MenuBar();
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        menuBar.getMenus().add(buildFileMenu());
        menuBar.getMenus().add(menuEdit);
        menuBar.getMenus().add(menuView);
        
        myBorderPane.setTop(menuBar);
		myScene = new Scene(myBorderPane, 1200, 600);
        stage.setScene(myScene);
	}
	
	private Menu buildFileMenu() {
		Menu fileMenu = new Menu("File");
		
		MenuItem newGameItem = new MenuItem("New Game");
		newGameItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		newGameItem.setOnAction(event -> { System.out.println("HI"); });
		
		MenuItem loadItem = new MenuItem("Load Level");
		loadItem.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		loadItem.setOnAction(event -> { System.out.println("LOAD"); });
		
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
		quitItem.setOnAction(event -> { System.exit(0); });
		
		fileMenu.getItems().add(newGameItem);
		fileMenu.getItems().add(loadItem);
		fileMenu.getItems().add(quitItem);
		
		return fileMenu;
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