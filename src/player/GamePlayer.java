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

	private PlayerView myView;
	private PlayerViewController myController;

	public GamePlayer(Stage stage) {
		myView = new PlayerView();
		myController = new PlayerViewController(myView);
		myView.setController(myController);
		stage.setScene(myView.getScene());
	}
	// constructor for testing
	//	public GamePlayer(Stage stage, MenuBar bar, ScrollPane pane) {
	//		myGameRoot = pane;
	//		
	//		stage.setScene(myScene);
	//	}
	//
	//	public GamePlayer(Game game, ScrollPane pane) {
	//		myGameRoot = pane;
	//		myGameRoot.setHbarPolicy(ScrollBarPolicy.NEVER);
	//		myGameRoot.setVbarPolicy(ScrollBarPolicy.NEVER);
	//		// myGameRoot.setMaxSize(width, height);
	//		myController = new PlayerViewController(game, myGameRoot, myHUD);
	//	}



	//	public GamePlayer(ScrollPane pane) {
	//		myController = new PlayerViewController();
	//		myView = new PlayerView(myController);
	//		myGameRoot = pane;
	//		myGameRoot.setHbarPolicy(ScrollBarPolicy.ALWAYS);
	//		myGameRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	//		// myGameRoot.setMaxSize(width, height);
	//		myController = new PlayerViewController(myGameRoot, myHUD);
	//	}


}
