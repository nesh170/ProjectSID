package player;

import game.Game;
import gameEngine.Action;
import gameEngine.GameEngine;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import resources.constants.INT;
import util.DialogUtil;
import util.ErrorHandler;
import voogasalad.util.network.Network;
import data.DataHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;
import levelPlatform.level.LevelView;
import media.AudioController;
import media.VideoPlayer;

public class PlayerViewController implements GamePlayerInterface {

	public final static double FRAME_RATE = 60;
	public final static double NETWORK_RATE = 15;
	public final static double UPDATE_RATE = 120;
	public final static int PORT_NUMBER = 60910;
	public static final String NETWORK_BROKE = "NETWORK BROKE";

	private static final String NETWORK = "Network";
	private static final String IPQUERY = "What is your IP address";

	private static final int POPUP_WINDOW_SIZE = 250;
	private static final String CONNECT_SERVER_STRING = "Connecting to server...";
	private static final String SERVER_CONNECTED_STRING = "Connected.";

	private Timeline myTimeline;
	private VideoPlayer myVideoPlayer;
	private AudioController myAudioController;
	private PreferencePane mySettings;

	private Media myVideo;
	private Media myAudio;

	private int myLives;
	private int myHealth;
	private int myScore;
	private Game myGame;
	private String myGameName;

	private File myGameFolder;
	private List<Level> myGameLevels;
	private Group myGameGroup;
	private GameEngine myEngine;
	private Camera myCamera;

	private File mySaveFolder;

	private PlayerView myView;
	private Network myNetwork;

	private Level myNetworkLevel;
	private ErrorHandler myErrorHandler;

	public PlayerViewController(PlayerView view) {
		myView = view;
		myCamera = new Camera(myView.getRoot());
		loadNewChooser();
		myNetwork = new Network();
	}

	@Override
	public void start() {
		resume();
		myAudioController.play();
	}

	public void resume() {
		myTimeline.play();
		myEngine.play(myView.getRoot());
		myView.playScreen();
	}

	public void pause() {
		myTimeline.stop();
		myEngine.pause(myView.getRoot());
		myView.pauseScreen();
	}

	private void update() {
		double[] cameraVals = myEngine.update();
		myCamera.updateCamera(cameraVals[0], cameraVals[1]);
		myView.updateHUD(myEngine.getDefaultHUDComponents());
	}

	private void display() {
		System.out.println("playerViewController.display");
		myGameGroup = myEngine.render();
		myView.display(myGameGroup);
		myCamera.focus();
	}

	private void setupAnimation() {
		System.out.println("pvc.setUpAnimation");
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

	@Override
	public void setPreferences() {
		pause();
		mySettings.bringUpPreferences();
	}

	public void loadNewChooser() {
		Stage chooserStage = new Stage();
		chooserStage.initModality(Modality.APPLICATION_MODAL);
		chooseGame(chooserStage);
	}

	public void initializeGameAttributes() {
		try {
			myGame = DataHandler.getGameFromDir(myGameFolder);
			myGameName = DataHandler.getGameName(myGameFolder);
			myGameLevels = myGame.levels();
			myAudio = DataHandler.getAudioFromDir(myGameFolder);
			myVideo = DataHandler.getVideoFromDir(myGameFolder);
			myVideoPlayer = new VideoPlayer();
			myAudioController = new AudioController(new MediaPlayer(myAudio));
			mySettings = new PreferencePane(myAudioController);
			mySettings.setController(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		myEngine = new GameEngine(myGame.splashScreen(), myGameLevels);
	}

	private void chooseGame(Stage gameChooser) {
		// find a way to set up a map so we can just have file paths
		// for games already established so no directory needs to be opened here
		myGameFolder = DataHandler.chooseDir(gameChooser);
		initializeGameAttributes();
		setupAnimation();
		// play();
	}

	public void selectGame(Game game) {
		myGame = game;
		myGameLevels = game.levels();
		myEngine = new GameEngine(myGame.splashScreen(), myGameLevels);
		setupAnimation();
		start();
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
		start();
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

	public void setBrightness(ColorAdjust ca) {
		myGameGroup.setEffect(ca);
	}

	public List<String> getSpriteTagList() {
		return myEngine.getSpriteTagList();
	}

	public void addRuntimeAction(String spriteTag, Object groovyAction) {
		myEngine.addGroovyAction(spriteTag, (Action) groovyAction);
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
		// Implemented with chooseGame method
	}

	@Override
	public List findGames() {
		// TODO Auto-generated method stub
		return null;
	}

	private void pauseExecution() {		
		myTimeline.stop();		
		myEngine.pause(myView.getRoot());		
		myAudioController.pause();		
	}		

	private void resumeExecution() {		
		myTimeline.play();		
		myEngine.play(myView.getRoot());		
		myAudioController.play();		
	}
	
	public void loadState()
	{	
		pauseExecution();
		List<File> states;

		try {
			states = DataHandler.getDirsFromDir(myGameFolder);
		} catch (IOException e) {
			DialogUtil.displayMessage("Load File", "Error in loading save file.");
			resumeExecution();
			return;
		}

		if (states == null || states.size() == 0) {
			DialogUtil.displayMessage("Load File", "No save states available to load.");
			resumeExecution();
			return;
		}

		List<String> stateNames = states.stream().map(file -> file.getName()).collect(Collectors.toList());
		String chosenState = DialogUtil.choiceDialog("Load File", "Choose a save state.", stateNames);
		File stateFile = states.stream().filter(file -> file.getName().equals(chosenState)).collect(Collectors.toList()).get(0);
		try {
			myGame = DataHandler.getGameFromDir(stateFile);
		} catch (IOException e) {
			DialogUtil.displayMessage("Load File", "Cannot load state.");
			resumeExecution();
			return;
		}

		myGameLevels = myGame.levels();
		myEngine = new GameEngine(myGame.splashScreen(),myGameLevels);
		setupAnimation();
		resumeExecution();
	}

	@Override
	public void saveGame() {

		if (mySaveFolder == null) {
			saveAs();
		}
		else {
			pauseExecution();
			try {
				DataHandler.toXMLFile(myGame, removeXMLExt(myGameName), mySaveFolder.toString());
			} catch (IOException e) {
				DialogUtil.displayMessage("Save File", "Error in creating save file.");
			}
			resumeExecution();
			return;
		}
	}

	public void saveAs() {
		pauseExecution();
		String saveName = DialogUtil.setUpDialog("Save File", "Please enter the name of the log to save");
		if (saveName == null) {
			DialogUtil.displayMessage("Save File", "File not saved.");
			resumeExecution();
			return;
		}
		mySaveFolder = new File(myGameFolder.getAbsolutePath() + "/" + saveName);
		if (!mySaveFolder.mkdir()) {
			DialogUtil.displayMessage("Save File", "Error in creating save folder.");
			resumeExecution();
			return;
		}
		try {
			DataHandler.toXMLFile(myGame, removeXMLExt(myGameName), mySaveFolder.toString());
		} catch (IOException e) {
			DialogUtil.displayMessage("Save File", "Error in creating save file.");
			resumeExecution();
			return;
		}
		resumeExecution();
		return;
	}

	private String removeXMLExt(String str) {
		if (str.endsWith(".xml")) {
			int index = str.indexOf(".xml");
			return str.substring(0, index);
		}
		return str;
	}

	public String getCurrentLevelinXML() {
		return myEngine.getCurrentLevelinXML();
	}

	public void handleKeyEvent(String keyEventType, String keyCode,
			int playerNumber) {
		myEngine.handleKeyEvent(keyEventType, keyCode, playerNumber);
	}

	public void startServer() {
		try {
			myTimeline.pause();
			myNetwork.setUpServer(PORT_NUMBER);
			myTimeline.play();
			sendClientLevels();
			receiveFromClient();
		} catch (IOException e) {
			myErrorHandler.displayError(NETWORK_BROKE);
		}
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		myErrorHandler = errorHandler;
	}

	private void sendClientLevels() {
		Task<Void> sendTask = new Task<Void>() {
			@Override
			protected Void call() {
				while (true) {
					try {
						myNetwork.sendStringToClient(getCurrentLevelinXML());
						Thread.sleep(500);
					} catch (Exception e) {
						myErrorHandler.displayError(NETWORK_BROKE);
					}
				}
			}
		};

		Thread th = new Thread(sendTask);
		th.setDaemon(true);
		th.start();
	}

	private void receiveFromClient() {
		Task<Void> taskToReceive = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				while (true) {
					try {
						String keyControl = myNetwork.getStringFromClient();
						@SuppressWarnings("unchecked")
						List<String> keyString = (ArrayList<String>) DataHandler
						.fromXMLString(keyControl);
						handleKeyEvent(keyString.get(0), keyString.get(1),
								INT.SECOND_PLAYER); // Add code to make another
						// player play
					} catch (Exception e) {
						myErrorHandler.displayError(NETWORK_BROKE);
					}
				}
			}

		};
		Thread serverReceiveThread = new Thread(taskToReceive);
		serverReceiveThread.setDaemon(true);
		serverReceiveThread.start();
	}

	public void startClient() {
		try {
			myTimeline.stop();
			// myView.displayPopUp(CONNECT_SERVER_STRING, POPUP_WINDOW_SIZE);
			// myView.changePopUpText(SERVER_CONNECTED_STRING);
			// Thread.sleep(2000);
			// myView.closePopUp();
			myNetwork.setUpClient(DialogUtil.setUpDialog(NETWORK, IPQUERY),
					PORT_NUMBER);
			myView.getRoot().setOnKeyPressed(key -> sendEvent(key));
			myView.getRoot().setOnKeyReleased(key -> sendEvent(key));
			receiveLevels();
			LevelView renderer = new LevelView(null, EditMode.EDIT_MODE_OFF);
			Camera camera = new Camera(myView.getRoot());
			KeyFrame displayFrame = new KeyFrame(
					Duration.millis(1000 / NETWORK_RATE), e -> display(
							myNetworkLevel, renderer, camera));
			Timeline networkTimeline = new Timeline();
			networkTimeline.setCycleCount(Animation.INDEFINITE);
			networkTimeline.getKeyFrames().add(displayFrame);
			networkTimeline.play();
		} catch (Exception e) {
			System.err.println("Can't start Client");
		}
	}

	private void sendEvent(KeyEvent key) {
		List<String> keyData = new ArrayList<>();
		keyData.add(key.getEventType().getName());
		keyData.add(key.getCode().getName());
		try {
			myNetwork.sendStringToServer(DataHandler.toXMLString(keyData));
		} catch (Exception e) {
			myErrorHandler.displayError(NETWORK_BROKE);
		}
	}

	private void receiveLevels() {
		Task<Void> recvTask = new Task<Void>() {
			@Override
			protected Void call() {
				while (true) {
					try {
						String levelString = myNetwork.getStringFromServer();
						myNetworkLevel = (Level) DataHandler
								.fromXMLString(levelString);
					} catch (Exception e) {
						myErrorHandler.displayError(NETWORK_BROKE);
					}
				}

			}
		};

		Thread th = new Thread(recvTask);
		th.setDaemon(true);
		th.start();

	}

	private void display(Level level, LevelView renderer, Camera camera) {
		if (level == null) {
			return;
		}
		renderer.setLevel(level);
		myView.getRoot().setContent(renderer.renderLevel());
		double[] coordinates = level.getNewCameraLocations();
		camera.focusOn(coordinates[INT.X], coordinates[INT.Y]);
	}

}
