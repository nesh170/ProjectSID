package player;

import game.Game;
import gameEngine.Action;
import gameEngine.GameEngine;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import resources.constants.INT;
import socCenter.Avatar;
import util.DialogUtil;
import voogasalad.util.network.Network;
import data.DataHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;
import levelPlatform.level.LevelView;
import media.AudioController;
import media.VideoPlayer;

/**
 * Controls the classes that affect the view of the player
 * 
 * @author James, Le
 */
public class PlayerViewController implements GamePlayerInterface {

	public final static double FRAME_RATE = 60;
	public final static double NETWORK_RATE = 30;
	public final static double UPDATE_RATE = 120;
	public final static int PORT_NUMBER = 60910;
	public final static String NETWORK_BROKE = "NETWORK BROKE";

	private static final String NETWORK = "Network";
	private static final String IPQUERY = "What is the IP address you would like to connect to?";

	private Timeline myTimeline;
	private VideoPlayer myVideoPlayer;
	private AudioController myAudioController;
	private PreferencePane mySettings;

	private Media myVideo;
	private Media myAudio;

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

	public PlayerViewController(PlayerView view) {
		myView = view;
		myCamera = new Camera(myView.getRoot());
		myNetwork = new Network();
	}

	public PlayerViewController(PlayerView view, Game game) {
		this(view);
		selectGame(game);
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
		myView.updateHUD(myEngine.getHUDMap());
	}

	private void display() {
		myGameGroup = myEngine.render();
		myView.display(myGameGroup);
		myCamera.focus();
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

	@Override
	public void setPreferences() {
		pause();
		mySettings.bringUpPreferences();
	}

	public void loadNewGame() {
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
			if (myAudio != null) {
				myAudioController = new AudioController(new MediaPlayer(myAudio));
				mySettings = new PreferencePane(myAudioController);
			}
			else {
				mySettings = new PreferencePane();
				myView.disableSoundMenu();
			}
			myVideo = DataHandler.getVideoFromDir(myGameFolder);
			if (myVideo != null)
				myVideoPlayer = new VideoPlayer();
			mySettings.setController(this);
		} catch (IOException | NullPointerException e) {
			DialogUtil.displayMessage("ERROR", "Invalid Game Folder ):");
			System.exit(0);
		}

		myEngine = new GameEngine(myGame.splashScreen(), myGameLevels);
	}

	private boolean isGame(File dir) {
		List<File> games = Arrays.asList(dir.listFiles())
				.stream()
				.filter(file -> file.toString().endsWith(".xml"))
				.collect(Collectors.toList());
		return games.size() > 0;
	}

	private void chooseGame(Stage gameChooser) {

		File dir = new File(System.getProperty(DataHandler.USER_DIR));
		List<File> children = null;

		try {
			children = DataHandler.getDirsFromDir(dir);
		} catch (IOException e) {
			DialogUtil.displayMessage("Load Game", "Problem with reading games.");
		}

		children = children.stream()
				.filter(folder -> isGame(folder))
				.collect(Collectors.toList());
		dir.listFiles();

		if (children.size() == 0) {
			DialogUtil.displayMessage("Load Game", "No games available to play.");
			return;
		}

		List<String> gameNames = children.stream().map(file -> file.getName())
				.collect(Collectors.toList());
		String choice = DialogUtil.choiceDialog("Load Game", "Select a game to play.", gameNames);

		if (choice == null) {
			return;
		}

		myGameFolder = children.stream()
				.filter(file -> file.getName().equals(choice))
				.collect(Collectors.toList()).get(0);

		myView.enableButtonItems();
		initializeGameAttributes();
		setupAnimation();

	}

	public void selectGame(Game game) {
		myGame = game;
		myGameLevels = game.levels();
		myEngine = new GameEngine(myGame.splashScreen(), myGameLevels);
		setupAnimation();
		start();
	}

	public void restart() {
		myAudioController.stop();
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
			DialogUtil.displayMessage("ERROR", "Cannot Open Video ):");
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

	public void setBright(double val) {
		myView.setBright(val);
	}

	public void setDim(double val) {
		myView.setDim(val);
	}

	public Map<String, KeyCode> getKeyMap() {
		return myEngine.getActionKeyCodeMap(INT.LOCAL_PLAYER);
	}

	public Map<String,Consumer<KeyCode>> getConsumerSetup() {
		return myEngine.getActionToChangeKeyCodeConsumerMap(INT.LOCAL_PLAYER);
	}

	public List<String> getSpriteTagList() {
		return myEngine.getSpriteTagList();
	}

	public void addRuntimeAction(String spriteTag, Object groovyAction) {
		myEngine.addGroovyAction(spriteTag, (Action) groovyAction);
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

	public void loadState() {
		pauseExecution();
		List<File> states;

		try {
			states = DataHandler.getDirsFromDir(myGameFolder);
		} catch (IOException e) {
			DialogUtil.displayMessage("Load File",
					"Error in loading save file.");
			resumeExecution();
			return;
		}

		if (states == null || states.size() == 0) {
			DialogUtil.displayMessage("Load File",
					"No save states available to load.");
			resumeExecution();
			return;
		}

		List<String> stateNames = states.stream().map(file -> file.getName())
				.collect(Collectors.toList());
		String chosenState = DialogUtil.choiceDialog("Load File",
				"Choose a save state.", stateNames);

		if (chosenState == null) {
			resumeExecution();
			return;
		}

		File stateFile = states.stream()
				.filter(file -> file.getName().equals(chosenState))
				.collect(Collectors.toList()).get(0);
		try {
			myGame = DataHandler.getGameFromDir(stateFile);
		} catch (IOException e) {
			DialogUtil.displayMessage("Load File", "Cannot load state.");
			resumeExecution();
			return;
		}

		myGameLevels = myGame.levels();
		myEngine = new GameEngine(myGame.splashScreen(), myGameLevels);
		setupAnimation();
		resumeExecution();
	}

	@Override
	public void saveGame() {
		if (mySaveFolder == null) {
			saveAs();
		} else {
			pauseExecution();
			try {
				DataHandler.toXMLFile(myGame, removeXMLExt(myGameName),
						mySaveFolder.toString());
			} catch (IOException e) {
				DialogUtil.displayMessage("Save File",
						"Error in creating save file.");
			}
			resumeExecution();
			return;
		}
	}

	public void saveAs() {
		pauseExecution();
		String saveName = DialogUtil.inputDialog("Save File",
				"Please enter the name of the log to save");
		if (saveName == null) {
			DialogUtil.displayMessage("Save File", "File not saved.");
			resumeExecution();
			return;
		}
		mySaveFolder = new File(myGameFolder.getAbsolutePath() + "/" + saveName);
		if (!mySaveFolder.mkdir()) {
			DialogUtil.displayMessage("Save File",
					"Error in creating save folder.");
			resumeExecution();
			return;
		}
		try {
			DataHandler.toXMLFile(myGame, removeXMLExt(myGameName),
					mySaveFolder.toString());
		} catch (IOException e) {
			DialogUtil.displayMessage("Save File",
					"Error in creating save file.");
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
		} catch (Exception e) {
			DialogUtil.displayMessage(NETWORK_BROKE, "Could not set up server.");
		}
	}

	private void sendClientLevels() {
		Task<Void> sendTask = new Task<Void>() {
			@Override
			protected Void call() {
				while (true) {
					try {
						myNetwork.sendObject(getCurrentLevelinXML());
						Thread.sleep(200);
					} catch (Exception e) {
						DialogUtil.displayMessage(NETWORK_BROKE, "Client disconnected.");
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
						@SuppressWarnings("unchecked")
						List<String> keyString = (ArrayList<String>) myNetwork.receiveObject();
						handleKeyEvent(keyString.get(0), keyString.get(1),
								INT.SECOND_PLAYER); 
					} catch (Exception e) {
						DialogUtil.displayMessage(NETWORK_BROKE, "Server disconnected.");
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
			String ipAddress = DialogUtil.inputDialog(NETWORK, IPQUERY);
			myNetwork.setUpClient(ipAddress, PORT_NUMBER);
			myView.closePopUp();
			myView.getRoot().setOnKeyPressed(key -> sendEvent(key));
			myView.getRoot().setOnKeyReleased(key -> sendEvent(key));
			myView.getRoot().requestFocus();
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
			DialogUtil.displayMessage(NETWORK_BROKE, "Could not start client.");
		}
	}

	private void sendEvent(KeyEvent key) {
		List<String> keyData = new ArrayList<>();
		keyData.add(key.getEventType().getName());
		keyData.add(key.getCode().getName());
		try {
			myNetwork.sendObject(keyData);
		} catch (Exception e) {
			DialogUtil.displayMessage(NETWORK_BROKE, "Could not send key events.");
		}
	}

	private void receiveLevels() {
		Task<Void> recvTask = new Task<Void>() {
			@Override
			protected Void call() {
				while (true) {
					try {
						String levelString = (String) myNetwork.receiveObject();
						myNetworkLevel = (Level) DataHandler.fromXMLString(levelString);
					} catch (IOException | ClassNotFoundException e) {
						DialogUtil.displayMessage(NETWORK_BROKE, "Processing levels failed.");
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

	public void openPreference() {
		mySettings.bringUpPreferences();
	}

	public void setSocialAvatar (Avatar av) {
		myView.addAvatarToPause(av);
	}

}
