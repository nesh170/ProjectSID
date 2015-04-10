package player;

import java.io.IOException;
import java.util.List;

import media.VideoController;
import media.VideoPlayer;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePlayer {

	public final static double FRAME_RATE = 60;
	public final static double UPDATE_RATE = 120;
	private final static String TUTORIAL_URI =
			"file:///home/leqi/Projects/workspace/COMPSCI308/voogasalad_ScrollingDeep/mario/tutorial.mp4";

	private ScrollPane myGameRoot;
	private Scene myScene;
	private BorderPane myBorderPane;
	private double myWidth;
	private double myHeight;
	private int myLives;
	private int myHealth;
	private int myScore;
	private PlayerMenu myMenu;
	private PlayerViewController myView;
	private VideoPlayer myVideoPlayer;
	
	// constructor for testing
	public GamePlayer(Stage stage, MenuBar bar) {
		myGameRoot = new ScrollPane();
		myBorderPane = new BorderPane();
		myBorderPane.setTop(bar);
		myView = new PlayerViewController(myGameRoot);
		myVideoPlayer = new VideoPlayer();
		myBorderPane.setCenter(myGameRoot);
		myScene = new Scene(myBorderPane, 1200, 600);
		stage.setScene(myScene);
	}

	public GamePlayer(double width, double height) {
		myWidth = width;
		myHeight = height;
		myGameRoot = new ScrollPane();
		myView = new PlayerViewController(myGameRoot);
		myVideoPlayer = new VideoPlayer();
		myBorderPane = new BorderPane();
		myBorderPane.setCenter(myGameRoot);
	}

	public void start() {
		myView.startView();
	}

	public void pause() {
		myView.stopView();
	}

	public void showTutorial() {
		try {
			myVideoPlayer.init(new Stage(), TUTORIAL_URI);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadNewGame() {
		myView.loadNewChooser();
	}

	public void save() {
		myView.save();
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

}
