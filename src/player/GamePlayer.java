package player;

import game.Game;
import gameEngine.GameEngine;

import java.io.IOException;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Group;
import media.VideoController;
import media.VideoPlayer;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePlayer {

	public final static double FRAME_RATE = 30;
	public final static double UPDATE_RATE = 120;

	private ScrollPane myGameRoot;
	private Scene myScene;
	private BorderPane myBorderPane;
	private int myLives;
	private int myHealth;
	private int myScore;
	private PlayerMenu myMenu;
	private PlayerViewController myView;
	private HUD myHUD;

	// constructor for testing
	public GamePlayer(Stage stage, MenuBar bar, ScrollPane pane) {
		myGameRoot = pane;
		myBorderPane = new BorderPane();
		myBorderPane.setCenter(myGameRoot);
		myBorderPane.setTop(bar);
		myScene = new Scene(myBorderPane, 1200, 600);
		stage.setScene(myScene);
	}

	public GamePlayer(Game game, ScrollPane pane) {
		myGameRoot = pane;
		myGameRoot.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		myGameRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		// myGameRoot.setMaxSize(width, height);
		myView = new PlayerViewController(game, myGameRoot, myHUD);
	}

	public GamePlayer(ScrollPane pane) {
		myGameRoot = pane;
		myGameRoot.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		myGameRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		// myGameRoot.setMaxSize(width, height);
		myView = new PlayerViewController(myGameRoot, myHUD);
	}

	public StackPane createHUD(ScrollPane pane) {
		StackPane stack = new StackPane();
		myHUD = new HUD(pane);
		myHUD.addItem("Lives", 0);
		myHUD.addItem("Health", 0);
		myHUD.addItem("Score", 0);
		stack.getChildren().add(myHUD.getHUDBox());
		stack.setAlignment(myHUD.getHUDBox(), Pos.TOP_LEFT);
		myView.setPauseBase(stack);
		return stack;
	}

	public void setupActions(PlayerMenu pMenu) {
		pMenu.createPlayerMenu(myView);
	}

	public PlayerMenu getMenu() {
		return myMenu;
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
