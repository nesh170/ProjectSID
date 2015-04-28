package player;

import gameEngine.Component;
import java.util.List;
import java.util.Map;
import util.ErrorHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayerView {

	private Scene myScene;
	private PlayerMenu myMenuBar;
	private ScrollPane myGameRoot;
	private StackPane myPauseScreen;
	private HUD myHUD;
	private PlayerViewController myController;
	private BorderPane myBorderPane;
	private StackPane myBase;
	private StackPane myTop;
	private Stage myPopUp;

	public PlayerView() {

		myGameRoot = new ScrollPane();
		myHUD = new HUD();

		myGameRoot.setHbarPolicy(ScrollBarPolicy.NEVER);
		myGameRoot.setVbarPolicy(ScrollBarPolicy.NEVER);

		myBase = new StackPane();
		myTop = new StackPane();
		myTop.getChildren().add(myHUD.getHUDBox());
		myTop.setAlignment(myHUD.getHUDBox(), Pos.TOP_LEFT);
		myBorderPane = new BorderPane();
		myBorderPane.setCenter(myBase);

		myBase.getChildren().addAll(myGameRoot, myTop);

		myScene = new Scene(myBorderPane, 1200, 600);
		myPopUp = new Stage();

	}

	public void setController(PlayerViewController playerController) {
		myMenuBar = new PlayerMenu(playerController);
		myController = playerController;
		Group errorGroup = new Group();
		myBorderPane.setLeft(errorGroup);
		myController.setErrorHandler(new ErrorHandler(errorGroup));
		myBorderPane.setTop(myMenuBar.getBar());
		myPauseScreen = makePauseScreen(playerController);
	}

	private StackPane makePauseScreen(PlayerViewController playerController) {
		StackPane pause = new StackPane();
		pause.setAlignment(Pos.CENTER);
		Button startButton = new Button("Resume");
		startButton.setOnAction(event -> {
			playScreen();
			playerController.resume();
		});
		pause.getChildren().add(startButton);
		pause.setStyle("-fx-background-color: rgba(184, 184, 184, 0.25); -fx-background-radius: 10;");
		return pause;
	}

	public void pauseScreen() {
		myTop.getChildren().add(myPauseScreen);
		myPauseScreen.requestFocus();
	}

	public void playScreen() {
		myTop.getChildren().remove(myPauseScreen);
	}

	public Scene getScene() {
		return myScene;
	}

	public ScrollPane getRoot() {
		return myGameRoot;
	}

	public void display(Group group) {
	        myHUD.renderHUD();
		myGameRoot.setContent(group);
		myGameRoot.requestFocus();
	}

	public void displayPopUp(String text, int size) {
		StackPane waitPane = new StackPane();
		waitPane.getChildren().add(new Text(text));
		Scene waitScene = new Scene(waitPane, size, size);
		myPopUp.setScene(waitScene);
		myPopUp.show();

	}

	public void changePopUpText(String text) {
		if (myPopUp.isShowing()) {
			StackPane pane = (StackPane) myPopUp.getScene().getRoot();
			Text paneText = (Text) pane.getChildren().get(0);
			paneText.setText(text);
		}
	}

	public void closePopUp() {
		if (myPopUp.isShowing())
			myPopUp.close();
	}

	public double findPrefWidth() {
		return myGameRoot.getWidth();
	}

	public double findPrefHeight() {
		return myGameRoot.getHeight();
	}

	public void updateHUD(Map<String, Double> hudMap) {
		myHUD.updateHUDValues(hudMap);
		
	}
}
