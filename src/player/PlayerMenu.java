package player;

import gameEngine.actions.GroovyAction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sprite.Sprite;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayerMenu {

	private MenuBar myMenuBar;
	private GamePlayer myPlayer;

	public PlayerMenu(Stage stage) {
		myMenuBar = new MenuBar();
		HUD hud = new HUD();
		ScrollPane myGameRoot = new ScrollPane();
		myGameRoot.setHbarPolicy(ScrollBarPolicy.NEVER);
		myGameRoot.setVbarPolicy(ScrollBarPolicy.NEVER);
		myGameRoot.setMaxSize(900, 450);
		myGameRoot.setMinSize(900, 450);
		PlayerViewController pvc = new PlayerViewController(myGameRoot, hud);
		createPlayerMenu(pvc);
		myPlayer = new GamePlayer(stage, getBar(), myGameRoot);
	}

	public PlayerMenu(MenuBar menuBar) {
		myMenuBar = menuBar;
	}

	public void createPlayerMenu(MenuBar menuBar, PlayerViewController view) {
		Menu menuView = new Menu("View");
		menuBar.getMenus().add(buildFileMenu(view));
		menuBar.getMenus().add(buildGamesMenu(view));
		menuBar.getMenus().add(menuView);
		menuBar.getMenus().add(buildSoundMenu(view));
		menuBar.getMenus().add(buildHelpMenu(view));
		menuBar.getMenus().add(buildGroovyMenu(view));
	}

	public void createPlayerMenu(PlayerViewController view) {
		createPlayerMenu(myMenuBar, view);
	}

	private MenuItem makeMenuItem(String name) {
		MenuItem item = new MenuItem(name);
		item.setAccelerator(KeyCombination.keyCombination("Ctrl+"
				+ name.substring(0, 1)));
		return item;
	}

	private Menu buildFileMenu(PlayerViewController view) {
		Menu fileMenu = new Menu("File");

		MenuItem pauseItem = makeMenuItem("Pause Game");
		pauseItem.setOnAction(event -> {
			view.stopView();
		});
		MenuItem playItem = makeMenuItem("Resume Game");
		playItem.setOnAction(event -> {
			view.startView();
			;
		});
		MenuItem loadItem = makeMenuItem("Load Game");
		loadItem.setOnAction(event -> {
			System.out.println("write code to load saved game");
		});
		MenuItem restartItem = makeMenuItem("Restart");
		restartItem.setOnAction(event -> {
			view.restart();
		});
		fileMenu.getItems().addAll(pauseItem, playItem, loadItem,
				restartItem);
		return fileMenu;
	}

	private Menu buildGamesMenu(PlayerViewController view) {
		Menu gamesMenu = new Menu("Games");
		MenuItem marioItem = new MenuItem("Mario");
		marioItem.setOnAction(event -> {
			Text prompt = new Text("Are you sure you want to load a new Game? "
					+ "Save progress before proceeding.");
			Stage choose = view.chooserConfirmationDialog(prompt);
			choose.show();
		});
		gamesMenu.getItems().addAll(marioItem);
		return gamesMenu;
	}

	private Menu buildHelpMenu(PlayerViewController view) {
		Menu helpMenu = new Menu("Help");
		MenuItem tutorialItem = new MenuItem("Tutorial");
		tutorialItem.setOnAction(event -> view.showTutorial());

		helpMenu.getItems().addAll(tutorialItem);
		return helpMenu;
	}
	
	private Menu buildGroovyMenu (PlayerViewController view) {
	    Menu groovyMenu = new Menu("Groovy");
	    MenuItem groovyAction = new MenuItem("Add GroovyAction");
	    GroovyActionMenu actionMenu = new GroovyActionMenu(new GroovyAction(new Sprite(), KeyCode.R));
	    groovyAction.setOnAction(event -> actionMenu.setUpGroovyDialog());
	    groovyMenu.getItems().addAll(groovyAction);
	    return groovyMenu;
	}

	private Menu buildSoundMenu(PlayerViewController view) {
		Menu soundMenu = new Menu("Sound");
		MenuItem playItem = makeMenuItem("Play");
		playItem.setOnAction(event -> view.playMusic());

		MenuItem pauseItem = new MenuItem("Pause");
		pauseItem.setOnAction(event -> view.pauseMusic());

		MenuItem stopItem = makeMenuItem("Stop");
		stopItem.setOnAction(event -> view.stopMusic());

		soundMenu.getItems().addAll(playItem, pauseItem, stopItem);
		return soundMenu;
	}

	private Stage buildGameChooser() {
		Stage gameChooser = new Stage();
		gameChooser.initModality(Modality.APPLICATION_MODAL);
		Button mario = new Button("Mario");
		mario.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00); -fx-background-radius: 3,2,2,2;");
		VBox vbox = new VBox(50);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().addAll(new Text("Your Games"), mario);
		Scene allGames = new Scene(vbox, 300, 200);
		gameChooser.setScene(allGames);
		return gameChooser;
	}


	public MenuBar getBar() {
		return myMenuBar;
	}

}
