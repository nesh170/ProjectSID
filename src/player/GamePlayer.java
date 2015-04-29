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

}
