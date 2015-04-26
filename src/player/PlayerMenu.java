package player;

import gameEngine.actions.GroovyAction;
import java.util.ArrayList;
import java.util.List;
import sprite.Sprite;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Dialog;

public class PlayerMenu extends MenuBar{


	public PlayerMenu(PlayerViewController pvc) {
		createPlayerMenu(pvc);
	}

    public void createPlayerMenu (PlayerViewController view) {
        List<Method> methodList =
                Stream.of(PlayerMenu.class.getDeclaredMethods())
                        .filter(method -> method.getAnnotations().length > 0)
                        .collect(Collectors.toList());
        Collections.sort(methodList, (method1, method2) -> ((Integer) ((AddMenuItem) method1
                .getAnnotation(AddMenuItem.class)).order()).compareTo(((AddMenuItem) method2
                .getAnnotation(AddMenuItem.class)).order())); //This method sorts the object based on the order given by the annotations
        methodList.forEach(method -> getMenus().add(handleMenuAddition(method, view)));
    }

    private Menu handleMenuAddition (Method method, PlayerViewController view) {
        Menu menuToAdd = new Menu();
        try {
            menuToAdd = (Menu) method.invoke(this, view);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return menuToAdd;
    }

    private MenuItem makeMenuItem(String name) {
		MenuItem item = new MenuItem(name);
		item.setAccelerator(KeyCombination.keyCombination("Ctrl+"
				+ name.substring(0, 1)));
		return item;
	}

	@AddMenuItem(order = 0)
	private Menu buildFileMenu(PlayerViewController controller) {
		Menu fileMenu = new Menu("File");

		MenuItem pauseItem = makeMenuItem("Pause Game");
		pauseItem.setOnAction(event -> {
			controller.pause();
		});
		MenuItem playItem = makeMenuItem("Resume Game");
		playItem.setOnAction(event -> {
			controller.play();
			;
		});
		MenuItem loadItem = makeMenuItem("Load Game");
		loadItem.setOnAction(event -> {
			controller.loadNewChooser();
		});
		MenuItem restartItem = makeMenuItem("Restart");
		restartItem.setOnAction(event -> {
			controller.restart();
		});
		fileMenu.getItems().addAll(pauseItem, playItem, loadItem,
				restartItem);
		return fileMenu;
	}

	@AddMenuItem(order = 1)
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

	@AddMenuItem(order = 2)
	private Menu buildHelpMenu(PlayerViewController view) {
		Menu helpMenu = new Menu("Help");
		MenuItem tutorialItem = new MenuItem("Tutorial");
		tutorialItem.setOnAction(event -> view.showTutorial());

		helpMenu.getItems().addAll(tutorialItem);
		return helpMenu;
	}
	

	@AddMenuItem(order = 3)
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
	
	@AddMenuItem(order = 4)
	private Menu buildGroovyMenu (PlayerViewController view) {
	    Menu groovyMenu = new Menu("Groovy");
	    MenuItem groovyActionItem = new MenuItem("Add GroovyAction");
	    GroovyMenu actionMenu =
	            new GroovyActionMenu(new GroovyAction(new Sprite(),0.0, KeyCode.R));
	    groovyActionItem
	    .setOnAction(event -> actionMenu.setUpGroovyDialog(view.getSpriteTagList(),
	                                                       (spriteTag, groovyAction) -> view
	                                                       .addRuntimeAction(spriteTag,
	                                                                         groovyAction)));
	    groovyMenu.getItems().addAll(groovyActionItem);
	    return groovyMenu;
	}

	@AddMenuItem(order = 5)
	private Menu buildNetworksMenu(PlayerViewController view) {
		Menu networksMenu = new Menu("Multiplayer");
		MenuItem hostItem = new MenuItem("Host Game");
		MenuItem joinItem = new MenuItem("Join Game");
		
		hostItem.setOnAction(event -> {
//			Dialog<String> dialog = new Dialog<>();
//			dialog.setContentText("You opened port 10000");
//			dialog.showAndWait();
			view.startServer();
			
		});
		
		joinItem.setOnAction(event -> {
//			Dialog<String> dialog = new Dialog<>();
//			dialog.setContentText("You joined port 10000");
//			dialog.showAndWait();
			view.startClient();
		});
		
		networksMenu.getItems().addAll(hostItem, joinItem);
		return networksMenu;
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

}
