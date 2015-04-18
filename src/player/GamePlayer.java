package player;

import game.Game;
import gameEngine.GameEngine;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import resources.constants.INT;
import data.DataHandler;
import voogasalad.util.network.Network;
import javafx.concurrent.Task;
import javafx.scene.Group;
import media.VideoController;
import media.VideoPlayer;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePlayer {

	public final static double FRAME_RATE = 30;
	public final static double UPDATE_RATE = 120;
	public final static int PORT_NUMBER = 52469;

	private ScrollPane myGameRoot;
	private Group myGameGroup;
	private GameEngine myEngine;
	private Scene myScene;
	private BorderPane myBorderPane;
	private double myWidth;
	private double myHeight;
	private int myLives;
	private int myHealth;
	private int myScore;
	private PlayerMenu myMenu;
	private PlayerViewController myView;

	private Network myNetwork = new Network();

	// constructor for testing
	public GamePlayer(Stage stage, MenuBar bar) {
		myGameRoot = new ScrollPane();
		myGameRoot.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		myGameRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		myGameRoot.setMaxSize(900, 450);
		myGameRoot.setMinSize(900,450);
		myBorderPane = new BorderPane();
		myBorderPane.setTop(bar);
		myView = new PlayerViewController(myGameRoot);
		myBorderPane.setCenter(myGameRoot);
		myScene = new Scene(myBorderPane, 1200, 600);
		stage.setScene(myScene);
	}

	public GamePlayer(Game game, ScrollPane pane, double width, double height) {
		myGameRoot = pane;
		myView = new PlayerViewController(game, myGameRoot);
	}

	public GamePlayer(ScrollPane pane, double width, double height) {
		myGameRoot = pane;
		myGameRoot.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		myGameRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		myGameRoot.setMaxSize(width, height);
		//myGameRoot.setMinSize(width, height);
		myView = new PlayerViewController(myGameRoot);
	}

	public void start() {
		myView.startView();
	}

	public void pause() {
		myView.stopView();
	}

	public void showTutorial() {
		myView.showTutorial();
	}

	public void loadNewGame() {
		myView.loadNewChooser();
	}

	public void save() {
		myView.save();
	}

	public void setupActions(PlayerMenu pMenu) {
		List<MenuItem> menuItems = pMenu.getCommandItems();
		menuItems.get(0).setOnAction(event -> {
			pause();
		});
		menuItems.get(1).setOnAction(event -> {
			start();
		});
		menuItems.get(2).setOnAction(event -> {
			loadNewGame();
		});
		menuItems.get(3).setOnAction(event -> {
			System.out.println("write code to load saved game");
		});
		//this may not be necessary any more
		menuItems.get(4).setOnAction(event -> {
			System.exit(0);
		});
		menuItems.get(5).setOnAction(event -> {
			playMusic();
		});
		menuItems.get(6).setOnAction(event -> {
			pauseMusic();
		});
		menuItems.get(7).setOnAction(event -> {
			stopMusic();
		});
	}

	public PlayerMenu getMenu() {
		return myMenu;
	}

	public int getLives() {
		// return myEngine.getLives();
		return 0;
	}

	public int getHealth() {
		// return myEngine.getHealth();
		return 0;
	}

	public int getScore() {
		// return myEngine.getScore();
		return 0;
	}

	public int getHighScore() {
		// load in high score?
		return 0;
	}

	public void setPreferences() {
		// TODO Auto-generated method stub

	}

	public List findGames() {
		// TODO Auto-generated method stub
		return null;
	}

	public void playMusic() {
		myView.playMusic();
	}

	public void pauseMusic() {
		myView.pauseMusic();
	}

	public void stopMusic() {
		myView.stopMusic();
	}


	public void startServer () {
		try {
			myNetwork.setUpServer(PORT_NUMBER);
			receiveFromClient();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendClientLevels(){
		Task<Void> sendTask = new Task<Void>() {
			@Override
			protected Void call () {

				while (true) {
					try {
						myNetwork.sendStringToClient(myView.getCurrentLevelinXML());
						Thread.sleep(100);
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		};

		Thread th = new Thread(sendTask);
		th.setDaemon(true);
		th.start();
	}




	private void receiveFromClient(){
		Task<Void> taskToReceive = new Task<Void>() {

			@Override
			protected Void call () throws Exception {
				while(true){
					try{
						String keyControl = myNetwork.getStringFromClient();
						List<String> keyString = (ArrayList<String>) DataHandler.fromXMLString(keyControl);
						myView.handleKeyEvent(keyString.get(0),keyString.get(1),INT.LOCAL_PLAYER);
					}
					catch(Exception e){
						System.out.println("Error detector");
						e.printStackTrace();
					}
				}
			}

		};
		Thread serverReceiveThread = new Thread(taskToReceive);
		serverReceiveThread.setDaemon(true);
		serverReceiveThread.start();
	}

	public void startClient () {
		try {
			myNetwork.setUpClient(PORT_NUMBER);
			myGameRoot.setOnKeyPressed(key -> sendEvent(key));
			myGameRoot.setOnKeyReleased(key -> sendEvent(key));
		}
		catch (Exception e) {
			System.err.println("Can't start Client");
		}
	}


	private void sendEvent (KeyEvent key) {
		List<String> keyData = new ArrayList<>();
		keyData.add(key.getEventType().getName());
		keyData.add(key.getCode().getName());
		try {
			myNetwork.sendStringToServer(DataHandler.toXMLString(keyData));
		}
		catch (Exception e) {
			System.err.println("Can't send key");
		}
	}

	private void receiveLevels(){
		Task<Void> sendTask = new Task<Void>() {
			@Override
			protected Void call () {

				while (true) {
					try {
						String level = myNetwork.getStringFromServer();

					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		};

		Thread th = new Thread(sendTask);
		th.setDaemon(true);
		th.start();
	}

}