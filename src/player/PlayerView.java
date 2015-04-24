package player;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;

public class PlayerView {

	private Scene myScene;
	private PlayerMenu myMenuBar;
	private ScrollPane myGameRoot;
	private HUD myHUD;
	private Camera myCamera;
	private PlayerViewController myController;
	private BorderPane myBorderPane;

	public PlayerView() {

		myGameRoot = new ScrollPane();
		myHUD = new HUD();

		myGameRoot.setHbarPolicy(ScrollBarPolicy.NEVER);
		myGameRoot.setVbarPolicy(ScrollBarPolicy.NEVER);
		myGameRoot.setMaxSize(900, 450);
		myGameRoot.setMinSize(900, 450);

		myCamera = new Camera(myGameRoot);

		myBorderPane = new BorderPane();
		myBorderPane.setCenter(myGameRoot);

		myScene = new Scene(myBorderPane, 1200, 600);

	}

	public void setController(PlayerViewController playerController) {
		myMenuBar = new PlayerMenu(playerController);
		myController = playerController;
		myBorderPane.setTop(myMenuBar);
	}

	public Scene getScene() {
		return myScene;
	}

	public ScrollPane getRoot() {
		return myGameRoot;
	}

	public Camera getCamera() {
		return myCamera;
	}

	//	public PlayerView(ScrollPane scrollPane, PlayerViewController playerController) {
	//		
	//		myMenuBar = new PlayerMenu(playerController);
	//		myGameRoot = scrollPane;
	//		myHUD = new HUD();
	//		myController = playerController;
	//		
	//		myGameRoot.setHbarPolicy(ScrollBarPolicy.ALWAYS);
	//		myGameRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	//		myGameRoot.setMaxSize(900, 450);
	//		myGameRoot.setMinSize(900, 450);
	//	}

}
