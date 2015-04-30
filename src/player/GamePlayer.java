package player;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GamePlayer {


	private PlayerView myView;
	private PlayerViewController myController;

	public GamePlayer(Stage stage) {
		myView = new PlayerView(new BorderPane(), new StackPane());
		myController = new PlayerViewController(myView);
		myView.setController(myController);
		stage.setScene(myView.getScene());
	}

}
