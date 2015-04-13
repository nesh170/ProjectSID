package player;

import game.Game;
import gameEngine.GameEngine;
import java.io.IOException;
import java.util.List;
import javafx.scene.Group;

import media.VideoController;
import media.VideoPlayer;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePlayer {

	public final static double FRAME_RATE = 60;
	public final static double UPDATE_RATE = 120;

	private ScrollPane myGameRoot;
	private Group myGameGroup;
	private GameEngine myEngine;
	private Scene myScene;
	private BorderPane myBorderPane;
	private double myWidth;
	private double myHeight;
	private int myLives;
	private int myHealth;
	private int myScore;
	private PlayerMenu myMenu;
	private PlayerViewController myView;
	
	// constructor for testing
	public GamePlayer(Stage stage, MenuBar bar) {
		myGameRoot = new ScrollPane();
		myGameRoot.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		myGameRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		myGameRoot.setMaxSize(900, 450);
		myGameRoot.setMinSize(900,450);
		myBorderPane = new BorderPane();
		myBorderPane.setTop(bar);
		myView = new PlayerViewController(myGameRoot);
		myBorderPane.setCenter(myGameRoot);
		myScene = new Scene(myBorderPane, 1200, 600);
		stage.setScene(myScene);
	}

	public GamePlayer(Game game, ScrollPane pane, double width, double height) {
		myGameRoot = pane;
		myGameRoot.setMaxSize(width, height);
		myGameRoot.setMinSize(width, height);
		myView = new PlayerViewController(myGameRoot);
	}

	public void start() {
		myView.startView();
	}

	public void pause() {
		myView.stopView();
	}

	public void showTutorial() {
		myView.showTutorial();
	}
	
	public void loadNewGame() {
		myView.loadNewChooser();
	}

	public void save() {
		myView.save();
	}
	
	public void setupActions(PlayerMenu pMenu) {
		List<MenuItem> menuItems = pMenu.getCommandItems();
		menuItems.get(0).setOnAction(event -> {
			pause();
		});
		menuItems.get(1).setOnAction(event -> {
			start();
		});
		menuItems.get(2).setOnAction(event -> {
			loadNewGame();
		});
		menuItems.get(3).setOnAction(event -> {
			System.out.println("write code to load saved game");
		});
		//this may not be necessary any more
		menuItems.get(4).setOnAction(event -> {
			System.exit(0);
		});
		
	}
	
	public PlayerMenu getMenu() {
		return myMenu;
	}
	
	public int getLives() {
		// return myEngine.getLives();
		return 0;
	}

	public int getHealth() {
		// return myEngine.getHealth();
		return 0;
	}

	public int getScore() {
		// return myEngine.getScore();
		return 0;
	}

	public int getHighScore() {
		// load in high score?
		return 0;
	}

	public void setPreferences() {
		// TODO Auto-generated method stub

	}

	public List findGames() {
		// TODO Auto-generated method stub
		return null;
	}

	public void playMusic() {
		myView.playMusic();
	}

	public void pauseMusic() {
		myView.pauseMusic();
	}

	public void stopMusic() {
		myView.stopMusic();
	}

}
