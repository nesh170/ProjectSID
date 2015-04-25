package player;

import game.Game;
import gameEngine.Action;
import gameEngine.GameEngine;
import java.io.File;
import java.io.IOException;
import java.util.List;
import data.DataHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import levelPlatform.level.Level;
import media.AudioController;
import media.VideoPlayer;

public class PlayerViewController implements GamePlayerInterface {

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
	private Game myGame;

	private File myGameFolder;
	private List<Level> myGameLevels;
	private Group myGameGroup;
	private GameEngine myEngine;
	private Camera myCamera;

	private PlayerView myView;

	//	public PlayerViewController(ScrollPane pane, HUD gameHUD) {
	//		myGameRoot = pane;
	//		myCamera = new Camera(pane);
	//		myHUD = gameHUD;
	//		loadNewChooser();
	//	}
	//
	//	public PlayerViewController(Game game, ScrollPane pane, HUD gameHUD) {
	//		myGameRoot = pane;
	//		myCamera = new Camera(pane);
	//		myHUD = gameHUD;
	//		selectGame(game);
	//	}

	public PlayerViewController(PlayerView view) {
		myView = view;
		myCamera = new Camera(myView.getRoot());
		loadNewChooser();
	}

	public void play() {
		myTimeline.play();
		myEngine.play(myView.getRoot());
		myView.playScreen();
		myAudioController.play();
	}

	public void pause() {
		myTimeline.stop();
		myEngine.pause(myView.getRoot());
		myView.pauseScreen();
		myAudioController.pause();
	}

	private void update() {
		double[] cameraVals = myEngine.update();
		myCamera.updateCamera(cameraVals[0], cameraVals[1]);
	}

	private void display() {
		myGameGroup = myEngine.render();
		myView.display(myGameGroup);
		myCamera.focus();		
	}

	public void removePause() {
		//top pane's only child will be pause menu

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
		play();
	}

	public void selectGame(Game game) {
		myGame = game;
		myGameLevels = game.levels();
		myEngine = new GameEngine(myGameLevels);
		setupAnimation();
		play();
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
		pause();
		initializeGameAttributes();
		play();
	}

	public void showTutorial() {
		// TODO Auto-generated method stub
		myTimeline.pause();
		try {
			Stage videoStage = new Stage();
			videoStage.setOnCloseRequest(event -> {
				playMusic();
				myTimeline.play();
			});
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
	
	public List<String> getSpriteTagList(){
	    return myEngine.getSpriteTagList();
	}

    public void addRuntimeAction (String spriteTag, Object groovyAction) {
        myEngine.addGroovyAction(spriteTag, (Action) groovyAction);
    }


	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLives() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHighScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void loadNewGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPreferences() {
		// TODO Auto-generated method stub

	}

	@Override
	public List findGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveGame() {
		// TODO Auto-generated method stub

	}

}
