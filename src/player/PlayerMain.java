package player;

import javafx.application.Application;
import javafx.stage.Stage;

public class PlayerMain extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Game Player");
		GamePlayer myGamePlayer = new GamePlayer(stage);
		stage.show();
	}

	/**
	 * Main executable function for running examples.
	 * 
	 * @param arguments Command-line arguments: none expected.
	 */
	public static void main(String[] args) {
		launch(args);
	}

}