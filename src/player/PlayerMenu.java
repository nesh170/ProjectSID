package player;

import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayerMenu {

	private MenuBar myMenuBar;
	private PlayerViewController myView;
	
	public PlayerMenu(ScrollPane pane) {
		myMenuBar = createPlayerMenu();
		myView = new PlayerViewController(pane);
	}
	
	public MenuBar createPlayerMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menuView = new Menu("View");
		menuBar.getMenus().add(buildFileMenu());
		menuBar.getMenus().add(buildGamesMenu());
		menuBar.getMenus().add(menuView);
		return menuBar;
	}
	
	private Menu buildFileMenu() {
		Menu fileMenu = new Menu("File");

		MenuItem pauseItem = makeMenuItem("Pause Game");
		pauseItem.setOnAction(event -> {
			pauseGame();
		});

		MenuItem playItem = makeMenuItem("Resume Game");
		playItem.setOnAction(event -> {
			startGame();
		});

		MenuItem newGameItem = makeMenuItem("New Game");
		newGameItem.setOnAction(event -> {
			myView.loadNewChooser();
		});

		MenuItem loadItem = makeMenuItem("Load Game");
		loadItem.setOnAction(event -> {
			System.out.println("write code to load saved game");
		});

		MenuItem quitItem = makeMenuItem("Quit");
		quitItem.setOnAction(event -> {
			System.exit(0);
		});
		fileMenu.getItems().addAll(pauseItem, playItem, newGameItem, loadItem,
				quitItem);
		return fileMenu;
	}

	private MenuItem makeMenuItem(String name) {
		MenuItem item = new MenuItem(name);
		item.setAccelerator(KeyCombination.keyCombination("Ctrl+"+name.substring(0, 1)));
		return item;
	}
	
	private Menu buildGamesMenu() {
		Menu gamesMenu = new Menu("Games");
		MenuItem marioItem = new MenuItem("Mario");
		marioItem.setOnAction(event -> {
			Text prompt = new Text("Are you sure you want to load a new Game? "
					+ "Save progress before proceeding.");
			Stage choose = myView.chooserConfirmationDialog(prompt);
			choose.show();
		});
		gamesMenu.getItems().addAll(marioItem);
		return gamesMenu;
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
	
	public void startGame() {
		myView.startView();
	}

	public void pauseGame() {
		myView.stopView();
	}
	
	public void loadNewGame() {
		Stage chooserStage = new Stage();
		chooserStage.initModality(Modality.APPLICATION_MODAL);
		myView.loadNewChooser();
	}
	
	public MenuBar getBar() {
		return myMenuBar;
	}
	
	public void saveGame() {
		myView.save();
	}	
}
