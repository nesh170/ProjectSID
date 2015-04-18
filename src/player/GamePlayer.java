package player;

import game.Game;
import gameEngine.GameEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
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
	private DatagramSocket mySocket;
	private DatagramSocket myClientSocket;

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

		try {
			//Keep a socket open to listen to all the UDP trafic that is destined for this port
			mySocket = new DatagramSocket(PORT_NUMBER, InetAddress.getByName("0.0.0.0"));
			mySocket.setBroadcast(true);

			while (true) {
				System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");

				//Receive a packet
				byte[] recvBuf = new byte[15000];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				mySocket.receive(packet);

				//Packet received
				System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
				System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

				//See if the packet holds the right command (message)
				String message = new String(packet.getData()).trim();
				if (message.equals("DISCOVER_FUIFSERVER_REQUEST")) {
					byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();

					//Send a response
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
					mySocket.send(sendPacket);

					System.out.println(getClass().getName() + ">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void startClient() {

		// Find the server using UDP broadcast
		try {
			//Open a random port to send the package
			myClientSocket = new DatagramSocket();
			myClientSocket.setBroadcast(true);

			byte[] sendData = "THE_DOPEST_PACKET".getBytes();

			//Try the 255.255.255.255 first
			try {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), PORT_NUMBER);
				myClientSocket.send(sendPacket);
				System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("YOU HAVE PROBLEMS");
			}

			// Broadcast the message over all the network interfaces
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();

				if (networkInterface.isLoopback() || !networkInterface.isUp()) {
					continue; // Don't want to broadcast to the loopback interface
				}

				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null) {
						continue;
					}

					// Send the broadcast package!
					try {
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, PORT_NUMBER);
						myClientSocket.send(sendPacket);
					} catch (Exception e) {

						System.out.println("ANOTHER FUCKING PROBLEM");
					}

					System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
				}
			}

			System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

			//Wait for a response
			byte[] recvBuf = new byte[15000];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			myClientSocket.receive(receivePacket);

			//We have a response
			System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

			//Check if the message is correct
			String message = new String(receivePacket.getData()).trim();
			if (message.equals("THE_DOPEST_PACKET")) {
				//DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
				System.out.println(receivePacket.getAddress());
			}

			//Close the port!
			myClientSocket.close();
		} catch (IOException ex) {

			System.out.println("PROBLEMS");
		}
	}
}
