package player;

import util.ErrorHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PlayerView {

	private Scene myScene;
	private PlayerMenu myMenuBar;
	private ScrollPane myGameRoot;
	private StackPane myPauseScreen;
	private HUD myHUD;
	private PlayerViewController myController;
	private BorderPane myBorderPane;
	private StackPane myStackPane;

	public PlayerView() {

		myGameRoot = new ScrollPane();
		myHUD = new HUD();

		myGameRoot.setHbarPolicy(ScrollBarPolicy.NEVER);
		myGameRoot.setVbarPolicy(ScrollBarPolicy.NEVER);
		myGameRoot.setMaxSize(900, 450);
		myGameRoot.setMinSize(900, 450);

		myStackPane = new StackPane();
		
		myBorderPane = new BorderPane();
		myBorderPane.setCenter(myGameRoot);
		
		myStackPane.getChildren().add(myBorderPane); 
		myScene = new Scene(myStackPane, 1200, 600);

	}

	public void setController(PlayerViewController playerController) {
		myMenuBar = new PlayerMenu(playerController);
		myController = playerController;
		Group errorGroup =new Group();
		myBorderPane.setLeft(errorGroup);
		myController.setErrorHandler(new ErrorHandler(errorGroup));
		myBorderPane.setTop(myMenuBar);
		myPauseScreen = makePauseScreen(playerController);
	}
	
//	public StackPane createHUD(ScrollPane pane) {
//		StackPane stack = new StackPane();
//		myHUD = new HUD(pane);
//		myHUD.addItem("Lives", 0);
//		myHUD.addItem("Health", 0);
//		myHUD.addItem("Score", 0);
//		stack.getChildren().add(myHUD.getHUDBox());
//		StackPane.setAlignment(myHUD.getHUDBox(), Pos.TOP_LEFT);
//		return stack;
//	}

	private StackPane makePauseScreen(PlayerViewController playerController) {
		StackPane pause = new StackPane();
		pause.setPrefSize(500, 500);
		pause.setAlignment(Pos.CENTER);
		Button startButton = new Button("Resume");
		startButton.setOnAction(event -> {
			playerController.removePause();
			playerController.play();
		});
		pause.getChildren().add(startButton);
		pause.setStyle("-fx-background-color: rgba(184, 184, 184, 0.25); -fx-background-radius: 10;");
		return pause;
	}
	
	public void pauseScreen() {
		myStackPane.getChildren().add(myPauseScreen);
		myPauseScreen.requestFocus();
	}
	
	public void playScreen() {
		myStackPane.getChildren().remove(myPauseScreen);
	}
	
	public Scene getScene() {
		return myScene;
	}

	public ScrollPane getRoot() {
		return myGameRoot;
	}

	public void display(Group group) {
		myGameRoot.setContent(group);
		myGameRoot.requestFocus();
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
