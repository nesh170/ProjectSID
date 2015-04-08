package player;


import java.io.IOException;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GamePlayer {

	public final static double FRAME_RATE = 60;
	public final static double UPDATE_RATE = 120;	

	private ScrollPane myGameRoot;	
	private Scene myScene;
	private BorderPane myBorderPane;
	private double myWidth;
	private double myHeight;
	private int myLives;
	private int myHealth;
	private int myScore;
	private PlayerMenu myMenu;
	
	// constructor for testing
	public GamePlayer(Stage stage) {
		myGameRoot = new ScrollPane();
		myMenu = new PlayerMenu(myGameRoot);
		myMenu.loadNewGame();
		myBorderPane = new BorderPane();
		myBorderPane.setTop(myMenu.getBar());
		myBorderPane.setCenter(myGameRoot);
        myScene = new Scene(myBorderPane, 1200, 600);
        stage.setScene(myScene);
	}

	public GamePlayer(double width, double height) {
		myWidth = width;
		myHeight = height;
		myGameRoot = new ScrollPane();
		myMenu = new PlayerMenu(myGameRoot);
		myMenu.loadNewGame();
		myBorderPane = new BorderPane();
		myBorderPane.setTop(myMenu.getBar());
		myBorderPane.setCenter(myGameRoot);
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
	
	public int getHighScore() {
		// load in high score?
		return 0;
	}
	
	public void loadNewGame() {
		System.out.println("LOAD");
	}

	public void setPreferences() {
		// TODO Auto-generated method stub

	}

	public List findGames() {
		// TODO Auto-generated method stub
		return null;
	}



}
