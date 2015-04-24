package player;

import game.Game;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import levelPlatform.level.Level;
import media.AudioController;
import media.VideoPlayer;

public class PlayerViewController {

	public final static double FRAME_RATE = 60;
	public final static double UPDATE_RATE = 120;

	private Timeline myTimeline;
	private VideoPlayer myVideoPlayer;
	private AudioController myAudioController;
	private Media myVideo;
	private Media myAudio;
	private double myWidth;
	private double myHeight;
	private int myLives;
	private int myHealth;
	private int myScore;
	private File myGameFolder;
	private List<Level> myGameLevels;
	private ScrollPane myGameRoot;
	private Group myGameGroup;
	private GameEngine myEngine;
	private Game myGame;
	private Camera myCamera;
	private HUD myHUD;
	private StackPane myTop;
	
	public PlayerViewController(ScrollPane pane, HUD gameHUD) {
		myGameRoot = pane;
		myCamera = new Camera(pane);
		myHUD = gameHUD;
		loadNewChooser();
	}

	public PlayerViewController(Game game, ScrollPane pane, HUD gameHUD) {
		myGameRoot = pane;
		myCamera = new Camera(pane);
		myHUD = gameHUD;
		selectGame(game);
	}

	public void startView() {
		myEngine.play(myGameRoot);
		myTimeline.play();
	}

	public void stopView() {
		myTimeline.stop();
		myEngine.pause(myGameRoot);
		bringupPause();
	}

	private void update() {
		double[] cameraVals = myEngine.update();
		myCamera.updateCamera(cameraVals[0], cameraVals[1]);
	}

	private void display() {
		myGameGroup = myEngine.render();
		myGameRoot.setContent(myGameGroup);
		// sets focus automatically to root which is receiving key inputs
		myGameRoot.requestFocus();
		centerNodeInScrollPane();
	}

	public void centerNodeInScrollPane() {
		myCamera.focus();
	}

	private StackPane makePauseScreen() {
		StackPane pause = new StackPane();
		pause.setPrefSize(500, 500);
		pause.setAlignment(Pos.CENTER);
		Button startButton = new Button("Resume");
		startButton.setOnAction(event -> {
			removePause();
			startView();
		});
		pause.getChildren().addAll(startButton);
		pause.setStyle("-fx-background-color: rgba(184, 184, 184, 0.25); -fx-background-radius: 10;");
		return pause;
	}

	private void bringupPause() {
		StackPane pause = makePauseScreen();
		myTop.getChildren().add(pause);
		pause.requestFocus();
	}

	public void setPauseBase(StackPane pane) {
		myTop = pane;
	}
	
	private void removePause() {
		//top pane's only child will be pause menu
		myTop.getChildren().remove(0);
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

	public void initializeGameAttributes() {
		try {
			myGame = DataHandler.getGameFromDir(myGameFolder);
			myGameLevels = myGame.levels();
			myAudio = DataHandler.getAudioFromDir(myGameFolder);
			myVideo = DataHandler.getVideoFromDir(myGameFolder);
			myVideoPlayer = new VideoPlayer();
			myAudioController = new AudioController(new MediaPlayer(myAudio));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myEngine = new GameEngine(myGameLevels);
	}
	
	private void chooseGame(Stage gameChooser) {
		// find a way to set up a map so we can just have file paths
		// for games already established so no directory needs to be opened here
		myGameFolder = DataHandler.chooseDir(gameChooser);
		initializeGameAttributes();
		setupAnimation();
		startView();
	}

	public void selectGame(Game game) {
		myGame = game;
		myGameLevels = game.levels();
		myEngine = new GameEngine(myGameLevels);
		setupAnimation();
		startView();
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

	public void restart() {
		stopView();
		initializeGameAttributes();
		startView();
	}
	
	public void showTutorial() {
		// TODO Auto-generated method stub
		try {
			Stage videoStage = new Stage();
			videoStage.setOnCloseRequest(event -> playMusic());
			myVideoPlayer.init(videoStage, myVideo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playMusic() {
		myAudioController.play();
	}

	public void pauseMusic() {
		myAudioController.pause();
	}

	public void stopMusic() {
		myAudioController.stop();
	}
}
