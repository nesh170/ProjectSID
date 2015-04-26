package player;

import game.Game;
import gameEngine.Action;
import gameEngine.GameEngine;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
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
	private Network myNetwork = new Network();
	
	private Level myNetworkLevel;
	private ErrorHandler myErrorHandler;

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
		myEngine = new GameEngine(myGame.splashScreen(),myGameLevels);
	}

	private void chooseGame(Stage gameChooser) {
		// find a way to set up a map so we can just have file paths
		// for games already established so no directory needs to be opened here
		myGameFolder = DataHandler.chooseDir(gameChooser);
		initializeGameAttributes();
		setupAnimation();
		//play();
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
	
	public String getCurrentLevelinXML() {
		return myEngine.getCurrentLevelinXML();
	}

	public void handleKeyEvent(String keyEventType, String keyCode, int playerNumber) {
		myEngine.handleKeyEvent(keyEventType, keyCode, playerNumber);
	}
	
	public void startServer () {
		try {
		    myEngine.pause(myGameGroup);
		    myTimeline.pause();
		    myNetwork.setUpServer(PORT_NUMBER);
		    myView.getRoot().setOnKeyPressed(key -> sendEvent(key, event-> handleServerSend(event,key)));
                    myView.getRoot().setOnKeyReleased(key -> sendEvent(key, event-> handleServerSend(event,key)));
		    myTimeline.play();
		    receive( () -> myNetwork.getStringFromClient(), INT.SECOND_PLAYER);
		}
		catch (IOException e) {
			myErrorHandler.displayError(NETWORK_BROKE);
		}
	}
	
	public void setErrorHandler (ErrorHandler errorHandler) {
	    myErrorHandler = errorHandler;
	}
	
	    private void handleServerSend (String event, KeyEvent key) {
	        try {
	            myNetwork.sendStringToClient(event);
	            myEngine.handleKeyEvent(key.getEventType().getName(), key.getCode().getName(),
	                                    INT.LOCAL_PLAYER);
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	public void startClient () {
		try {
		        myEngine.pause(myGameGroup);
		        myTimeline.pause();
			myNetwork.setUpClient(DialogUtil.setUpDialog(),PORT_NUMBER);
			myView.getRoot().setOnKeyPressed(key -> sendEvent(key, event-> handleClientSend(event,key)));
			myView.getRoot().setOnKeyReleased(key -> sendEvent(key, event-> handleClientSend(event,key)));
			myTimeline.play();
			receive( () -> myNetwork.getStringFromServer(), INT.LOCAL_PLAYER);
			
		}
		catch (Exception e) {
			System.err.println("Can't start Client");
		}
	}

    private void handleClientSend (String event, KeyEvent key) {
        try {
            myNetwork.sendStringToServer(event);
            myEngine.handleKeyEvent(key.getEventType().getName(), key.getCode().getName(),
                                    INT.SECOND_PLAYER);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEvent (KeyEvent key, Consumer<String> networkMethod) {
		List<String> keyData = new ArrayList<>();
		keyData.add(key.getEventType().getName());
		keyData.add(key.getCode().getName());
		try {
			networkMethod.accept(DataHandler.toXMLString(keyData));
		}
		catch (Exception e) {
		    myErrorHandler.displayError(NETWORK_BROKE);
		}
	}
	
	       private void receive(Callable<String> receive, int playerNum){
	                Task<Void> taskToReceive = new Task<Void>() {

	                        @Override
	                        protected Void call () throws Exception {
	                                while(true){
	                                        try{
	                                                String keyControl = receive.call();
	                                                @SuppressWarnings("unchecked")
	                                                List<String> keyString = (ArrayList<String>) DataHandler.fromXMLString(keyControl);
	                                                handleKeyEvent(keyString.get(0),keyString.get(1), playerNum); //Add code to make another player play
	                                        }
	                                        catch(Exception e){
	                                            myErrorHandler.displayError(NETWORK_BROKE);
	                                        }
	                                }
	                        }

	                };
	                Thread serverReceiveThread = new Thread(taskToReceive);
	                serverReceiveThread.setDaemon(true);
	                serverReceiveThread.start();
	        }

	

	private void display(Level level, LevelView renderer, Camera camera){
	    if(level==null){
	        return;
	    }
		renderer.setLevel(level);
		myView.getRoot().setContent(renderer.renderLevel());
		double[] coordinates = level.getNewCameraLocations();
		camera.focusOn(coordinates[INT.X], coordinates[INT.Y]);
	}

 

}
