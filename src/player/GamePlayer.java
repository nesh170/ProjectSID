package player;

import game.Game;
import gameEngine.GameEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import util.Network;
import javafx.concurrent.Task;
import javafx.scene.Group;
import media.VideoController;
import media.VideoPlayer;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePlayer {

	public final static double FRAME_RATE = 30;
	public final static double UPDATE_RATE = 120;
	public final static int PORT_NUMBER = 10000;

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
	
	private Network myNetwork;
	
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
	
	public void startServer() {
		myNetwork = new Network();
		String hostName = myNetwork.setUpServer(PORT_NUMBER);
		System.out.println(hostName);
		
		Task<Void> sendTask = new Task<Void>() {
			@Override
			protected Void call() {
				
				while (true) {
					myNetwork.sendStringToServer("HI");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
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
	
	public void startClient() {
		myNetwork = new Network();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String hostName = null;
		try {
			hostName = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myNetwork.setUpClient(hostName, PORT_NUMBER);
		
		Task<Void> recvTask = new Task<Void>() {
			@Override
			protected Void call() {
				
				while (true) {
					try {
						System.out.println(myNetwork.getStringFromServer());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
								
			}
		};
		
		Thread th = new Thread(recvTask);
		th.setDaemon(true);
		th.start();
		
	}

}
