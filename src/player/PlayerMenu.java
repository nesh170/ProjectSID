package player;

import gameEngine.actions.GroovyAction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sid.SIDSocial;
import sprite.Sprite;
import util.DialogUtil;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Sets up menu at top of Player
 *
 * @author James, Le
 */
public class PlayerMenu {

	private MenuBar myMenu;
	private Menu mySoundMenu;

	public PlayerMenu(PlayerViewController pvc) {
		myMenu = new MenuBar();
		createPlayerMenu(pvc);
	}

	public PlayerMenu(PlayerViewController pvc, MenuBar bar) {
		myMenu = bar;
		createPlayerMenu(pvc);
	}

	/**
	 * Order and add MenuItems to the TopBar menu of the BorderPane
	 * 
	 * @param view
	 */
	public void createPlayerMenu(PlayerViewController view) {
		List<Method> methodList = Stream
				.of(PlayerMenu.class.getDeclaredMethods())
				.filter(method -> method.getAnnotations().length > 0)
				.collect(Collectors.toList());
		Collections.sort(methodList,
				(method1, method2) -> ((Integer) ((AddMenuItem) method1
						.getAnnotation(AddMenuItem.class)).order())
						.compareTo(((AddMenuItem) method2
								.getAnnotation(AddMenuItem.class)).order()));
		methodList.forEach(method -> myMenu.getMenus().add(
				handleMenuAddition(method, view)));
	}

	private Menu handleMenuAddition(Method method, PlayerViewController view) {
		Menu menuToAdd = new Menu();
		try {
			menuToAdd = (Menu) method.invoke(this, view);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			 DialogUtil.displayMessage("Error Menu Creation", "Menu Creation");
		}
		return menuToAdd;
	}

	private MenuItem makeMenuItem(String name) {
		MenuItem item = new MenuItem(name);
		return item;
	}

	@AddMenuItem(order = 0)
	private Menu buildFileMenu(PlayerViewController controller) {
		Menu fileMenu = new Menu("File");
		MenuItem openItem = makeMenuItem("Open Game");
		openItem.setOnAction(event -> {
			controller.loadNewGame();
			controller.resume();
		});
		MenuItem pauseItem = makeMenuItem("Pause Game");
		pauseItem.setOnAction(event -> {
			controller.pause();
		});

		MenuItem playItem = makeMenuItem("Resume Game");
		playItem.setOnAction(event -> {
			controller.resume();
		});

		MenuItem restartItem = makeMenuItem("Restart");
		restartItem.setOnAction(event -> {
			controller.restart();
		});

		MenuItem saveItem = makeMenuItem("Save State");
		saveItem.setOnAction(event -> {
			controller.saveGame();
		});

		MenuItem saveAsItem = makeMenuItem("Save State As");
		saveAsItem.setOnAction(event -> {
			controller.saveAs();
		});

		MenuItem loadItem = makeMenuItem("Load State");
		loadItem.setOnAction(event -> {
			controller.loadState();
		});

		fileMenu.getItems().addAll(openItem, pauseItem, playItem, restartItem,
				saveItem, saveAsItem, loadItem);

		toggleMenuItems(fileMenu, true, "Open Game");

		return fileMenu;
	}

	@AddMenuItem(order = 1)
	private Menu buildPreferencesMenu(PlayerViewController view) {
		Menu prefMenu = new Menu("Preferences");

		MenuItem prefItem = makeMenuItem("Set Preferences");
		prefItem.setOnAction(event -> {
			view.openPreference();
		});
		prefMenu.getItems().add(prefItem);
		toggleMenuItems(prefMenu, true, "Join Game");
		return prefMenu;
	}

	@AddMenuItem(order = 2)
	private Menu buildSoundMenu(PlayerViewController view) {
		Menu soundMenu = new Menu("Sound");
		MenuItem playItem = makeMenuItem("Play");
		playItem.setOnAction(event -> view.playMusic());

		MenuItem pauseItem = new MenuItem("Pause");
		pauseItem.setOnAction(event -> view.pauseMusic());

		MenuItem stopItem = makeMenuItem("Stop");
		stopItem.setOnAction(event -> view.stopMusic());

		soundMenu.getItems().addAll(playItem, pauseItem, stopItem);
		toggleMenuItems(soundMenu, true, "Join Game");
		mySoundMenu = soundMenu;
		return soundMenu;
	}

	@AddMenuItem(order = 3)
	private Menu buildGroovyMenu(PlayerViewController view) {
		Menu groovyMenu = new Menu("Groovy");
		MenuItem groovyActionItem = new MenuItem("Add GroovyAction");
		GroovyMenu actionMenu = new GroovyActionMenu(new GroovyAction(
				new Sprite(), 0.0, KeyCode.R));
		groovyActionItem.setOnAction(event -> actionMenu.setUpGroovyDialog(view
				.getSpriteTagList(), (spriteTag, groovyAction) -> view
				.addRuntimeAction(spriteTag, groovyAction)));
		groovyMenu.getItems().addAll(groovyActionItem);
		toggleMenuItems(groovyMenu, true);
		return groovyMenu;
	}

	@AddMenuItem(order = 4)
	private Menu buildNetworksMenu(PlayerViewController view) {
		Menu networksMenu = new Menu("Multiplayer");
		MenuItem hostItem = new MenuItem("Host Game");
		MenuItem joinItem = new MenuItem("Join Game");

		hostItem.setOnAction(event -> {
			view.startServer();

		});

		joinItem.setOnAction(event -> {
			view.startClient();
		});

		networksMenu.getItems().addAll(hostItem, joinItem);
		toggleMenuItems(networksMenu, true, "Join Game");

		return networksMenu;
	}

	@AddMenuItem(order = 5)
	private Menu buildSocialMenu(PlayerViewController view){
		Menu socialMenu = new Menu("Social Center");
		MenuItem openSocial = new MenuItem("Open");
		SIDSocial socialCenter = new SIDSocial();
		openSocial.setOnAction(event -> {try {
			view.pause();
			Stage socialStage = new Stage();
			socialCenter.start(socialStage);
			socialStage.setOnCloseRequest(close -> {
				view.setSocialAvatar(socialCenter.getAvatar()); //TODO: uncomment this to when the social center returns an avatar
				socialStage.close();
				view.resume();
			});
		}
		catch (Exception e) {
			DialogUtil.displayMessage("ERROR",  "FAILED TO INITIALIZE STAGE");
		}});
		socialMenu.getItems().add(openSocial);
		toggleMenuItems(socialMenu, true);
		return socialMenu;
	}

	protected MenuBar getBar() {
		return myMenu;
	}

	public void toggleMenuItems(Menu fileMenu, boolean disable, String... exceptions) {
		fileMenu.getItems().stream().filter(item -> !Arrays.asList(exceptions).contains(item.getText()))
		.forEach(item -> item.setDisable(disable));
	}

	public void enableAll() {
		myMenu.getMenus().stream()
		.forEach(item -> toggleMenuItems(item, false));
	}

	public void disableSoundItems() {
		toggleMenuItems(mySoundMenu, true);
	}
}
