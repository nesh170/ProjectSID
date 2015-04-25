package player;

import javafx.stage.Stage;
import javafx.util.Duration;

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
