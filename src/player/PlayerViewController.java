package player;

import gameEngine.GameEngine;

import java.io.File;
import java.io.IOException;
import java.util.List;

import data.DataHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import levelPlatform.level.Level;

public class PlayerViewController {

	public final static double FRAME_RATE = 60;
	public final static double UPDATE_RATE = 120;

	private Timeline myTimeline;
	private Stage myGameChooser;
	private StackPane myPause;
	private double myWidth;
	private double myHeight;
	private int myLives;
	private int myHealth;
	private int myScore;
	private Scene myScene;
	private File myGameFolder;
	private List<Level> myGameLevels;
	private ScrollPane myGameRoot;
	private Group myGameGroup;
	private GameEngine myEngine;

	public PlayerViewController(ScrollPane pane) {
		myGameRoot = pane;
		loadNewChooser();
		myPause = makePauseScreen();
	}

	public PlayerViewController(ScrollPane pane, double width, double height) {
		myGameRoot = pane;
		loadNewChooser();
		myPause = makePauseScreen();
	}

	public void startView() {
		removePause();
		myEngine.play(myGameRoot);
		myTimeline.play();
	}

	public void stopView() {
		myTimeline.stop();
		myEngine.pause(myGameRoot);
		bringupPause();
	}

	private void update() {
		myEngine.update();
	}

	private void display() {

		myGameGroup = myEngine.render();
		myGameGroup.getChildren().add(createHUD());
		myGameRoot.setContent(myGameGroup);
	}

	private StackPane makePauseScreen() {
		StackPane pause = new StackPane();
		Button startButton = new Button("Resume");
		startButton.setOnAction(event -> {
			startView();
		});
		pause.getChildren().add(startButton);
		pause.setStyle("-fx-background-color: rgba(184, 184, 184, 0.25); -fx-background-radius: 10;");
		pause.setPrefWidth(500);
		pause.setPrefHeight(500);
		return pause;
	}

	private void bringupPause() {
		myGameRoot.setContent(myPause);
	}

	private void removePause() {
		myGameRoot.setContent(myGameGroup);
	}

	private void setupAnimation() {
		myTimeline = new Timeline();
		myTimeline.setCycleCount(Animation.INDEFINITE);
		KeyFrame updateFrame = new KeyFrame(
				Duration.millis(1000 / UPDATE_RATE), e -> update());
		KeyFrame displayFrame = new KeyFrame(
				Duration.millis(1000 / FRAME_RATE), e -> display());
		myTimeline.getKeyFrames().add(updateFrame);
		myTimeline.getKeyFrames().add(displayFrame);
	}

	public Stage chooserConfirmationDialog(Text text) {
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		VBox vbox = new VBox(25);
		vbox.setAlignment(Pos.TOP_CENTER);
		HBox buttonBox = new HBox(25);
		Button yes = new Button("Yes");
		yes.setOnAction(event -> loadNewChooser());
		Button no = new Button("No");
		no.setOnAction(event -> dialogStage.close());
		buttonBox.getChildren().addAll(yes, no);
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.getChildren().addAll(text, buttonBox);
		Scene dialogScene = new Scene(vbox, 500, 100);
		dialogStage.setScene(dialogScene);
		return dialogStage;
	}

	public void loadNewChooser() {
		Stage chooserStage = new Stage();
		chooserStage.initModality(Modality.APPLICATION_MODAL);
		chooseGame(chooserStage);
	}

	private void chooseGame(Stage gameChooser) {
		// find a way to set up a map so we can just have file paths
		// for games already established so no directory needs to be opened here
		myGameFolder = DataHandler.chooseDir(gameChooser);
		try {
			myGameLevels = DataHandler.getLevelsFromDir(myGameFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myEngine = new GameEngine(myGameLevels);
		setupAnimation();
	}

	public HBox createHUD() {
		HBox HUDbox = new HBox(myWidth);
		Text LivesText = new Text("Health:" + myHealth);
		LivesText.setFont(Font.font("Arial Black", 20));
		LivesText.setFill(Color.WHITE);
		Text HealthText = new Text("Lives:" + myLives);
		HealthText.setFont(Font.font("Arial Black", 20));
		HealthText.setFill(Color.WHITE);
		Text ScoreText = new Text("Score" + myScore);
		ScoreText.setFont(Font.font("Arial Black", 20));
		ScoreText.setFill(Color.WHITE);
		HUDbox.getChildren().addAll(LivesText, HealthText, ScoreText);
		HUDbox.setAlignment(Pos.BOTTOM_CENTER);
		return HUDbox;
	}

	public void save() {
		String[] names = new String[] { "mario1.xml", "mario2.xml",
				"mario3.xml" };
		for (int i = 0; i < myGameLevels.size(); i++) {
			try {
				DataHandler.toXMLFile(myGameLevels.get(i), names[i],
						myGameFolder.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
