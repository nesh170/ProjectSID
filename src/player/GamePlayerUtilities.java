package player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePlayerUtilities {

	private BorderPane myBorderPane;
	private Stage myGameChooser;
	private MenuBar myMenuBar;
	private StackPane myPause;
	private double myWidth;
	private double myHeight;
	private int myLives;
	private int myHealth;
	private int myScore;
	private Scene myScene;
	
	public GamePlayerUtilities() {
		myPause = makePauseScreen();
		myBorderPane = new BorderPane();
        myBorderPane.setMargin(myPause, new Insets(25, 25, 25, 25));
		myBorderPane.setTop(myMenuBar);
        myScene = new Scene(myBorderPane, 1200, 600);
	}

	
	// implementation still needed to connect to actual file chooser
	// add this once we have everything else working
	private Stage buildGameChooser() {
		Stage gameChooser = new Stage();
		gameChooser.initModality(Modality.APPLICATION_MODAL);
		Button mario = new Button("Mario");
		mario.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 3,2,2,2;");
		mario.setOnAction(event -> chooseGame(gameChooser));

		VBox vbox = new VBox(50);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().addAll(new Text("Your Games"), mario);
		Scene allGames = new Scene(vbox, 300, 200);
		gameChooser.setScene(allGames);
		return gameChooser;
	}

	private Stage chooserConfirmationDialog(Text text) {
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		VBox vbox = new VBox(25);
		vbox.setAlignment(Pos.TOP_CENTER);
		HBox buttonBox = new HBox(25);
		Button yes = new Button("Yes");
		yes.setOnAction(event -> loadNewGame());
		Button no = new Button("No");
		no.setOnAction(event -> dialogStage.close());
		buttonBox.getChildren().addAll(yes, no);
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.getChildren().addAll(text, buttonBox);
		Scene dialogScene = new Scene(vbox, 500, 100);
		dialogStage.setScene(dialogScene);
		return dialogStage;
	}
	

	private StackPane makePauseScreen() {
		StackPane pause = new StackPane();
		Button startButton = new Button("Resume");
		startButton.setOnAction(event -> {
			start();
		});
		pause.getChildren().add(startButton);
		pause.setStyle("-fx-background-color: rgba(184, 184, 184, 0.25); -fx-background-radius: 10;");
		pause.setPrefWidth(500);
		pause.setPrefHeight(500);
		return pause;
	}

	
}
