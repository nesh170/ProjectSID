package player;

import gameEngine.GameEngineAbstract;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePlayer implements GamePlayerInterface{


	private GameEngineAbstract myEngine;
	private Scene myScene;
	private BorderPane myBorderPane;
	private Timeline myTimeline;
	private int myFrameRate = 30;
	private String myGameFilePath;
	private Group myRoot;
	private Stage myGameChooser;
	private StackPane myPause;
	
	//constructor for testing
	public GamePlayer(Stage stage) {
		myTimeline = new Timeline();
		myRoot = new Group();
		myPause = makePauseMenu();
	    myBorderPane = new BorderPane();
		MenuBar menuBar = createPlayerMenu();
        myBorderPane.setTop(menuBar);
		myBorderPane.setCenter(myRoot);
        myScene = new Scene(myBorderPane, 1200, 600);
        stage.setScene(myScene);
        myGameChooser = buildGameChooser(stage);
	}
	
	public GamePlayer() {
		myTimeline = new Timeline();
	}
	
	private Menu buildFileMenu() {
		Menu fileMenu = new Menu("File");
		
		MenuItem pauseItem = new MenuItem("Pause Game");
		pauseItem.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
		pauseItem.setOnAction(event -> { pause(); });
		pauseItem.setOnAction(event -> { myRoot.getChildren().add(myPause); });
		
		MenuItem playItem = new MenuItem("Resume Game");
		playItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		playItem.setOnAction(event -> { start(); });
		playItem.setOnAction(event -> { System.out.println("PLAY"); });
		
		MenuItem newGameItem = new MenuItem("New Game");
		newGameItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		newGameItem.setOnAction(event -> { myGameChooser.show(); });
		
		MenuItem loadItem = new MenuItem("Load Level");
		loadItem.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		loadItem.setOnAction(event -> { System.out.println("LOAD"); });
		
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
		quitItem.setOnAction(event -> { System.exit(0); });
		fileMenu.getItems().addAll(pauseItem, playItem, newGameItem, loadItem, 
				quitItem);		
		return fileMenu;
	}
	
	//implementation still needed to connect to actual file chooser 
	private Stage buildGameChooser(Stage s) {
		//TextField textField = new TextField("Your Games"); 
		Stage gameChooser = new Stage();
        gameChooser.initModality(Modality.APPLICATION_MODAL);
        gameChooser.initOwner(s);
	    Button mario = new Button("Mario");
        mario.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 3,2,2,2;");
        mario.setOnAction(event -> { System.exit(0); });
        VBox vbox = new VBox(50);
        vbox.getChildren().addAll(new Text("Your Games"), mario);
        Scene allGames = new Scene(vbox, 300, 200);
        gameChooser.setScene(allGames);
		return gameChooser;
	}
	
	public MenuBar createPlayerMenu() {
		MenuBar menuBar = new MenuBar();
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        menuBar.getMenus().add(buildFileMenu());
        menuBar.getMenus().add(menuEdit);
        menuBar.getMenus().add(menuView);
        return menuBar;
	}
	
	private StackPane makePauseMenu() {
		Button startButton = new Button("Resume");
	    startButton.setOnAction(event -> { start(); });
		StackPane pause = new StackPane();
	    //StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
	    pause.getChildren().addAll(startButton);
	    pause.setStyle("-fx-background-color: rgba(192, 192, 192, 0.25); -fx-background-radius: 10;");
	    pause.setPrefWidth(500);
	    pause.setPrefHeight(500);
	    //glass.setPadding(new Insets(25));
//	    glass.setMaxWidth(rect.getWidth() - 40);
//	    glass.setMaxHeight(rect.getHeight() - 40);
	    return pause;
	}
	
	private void initialize(GameEngineAbstract engine) {
		myEngine = engine;
		myTimeline = new Timeline();
		myTimeline.setCycleCount(Animation.INDEFINITE);
		//myTimeline.getKeyFrames().add(frame);
	}
	
	private void setupAnimation() {
		//new KeyFrame(Duration.millis(1000 / myFrameRate), e -> updateGrid());
	}
	
	private void update() {
		Group group = myEngine.render();
	}
	
	@Override
	public void start() {
		myTimeline.play();
	}

	@Override
	public void pause() {
		myTimeline.stop();
	}

	public boolean playerIsRunning() {
		return myTimeline.getStatus() == Animation.Status.RUNNING;
	}
	
	@Override
	public int getHighScore() {
		// get high score from engine
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
