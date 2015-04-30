package screen.screens;

import game.Game;
import gameEngine.GameEngine;
import player.GamePlayer;
import player.HUD;
import player.PlayerMenu;
import player.PlayerView;
import player.PlayerViewController;
import javafx.geometry.Pos;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import levelPlatform.level.Level;
import screen.controllers.GamePlayScreenController;
import screen.controllers.ScreenController;

public class GamePlayScreen extends LevelPlatformCapableScreen {

	// Instance Variables
	private GamePlayScreenController controller;
	private Level level;
	private MenuBar myMenu;
	private GamePlayer myPlayer;

	// Constructor & Helpers
	public GamePlayScreen(GamePlayScreenController parent, double width,
			double height, Level level) {

		super(width, height);

		this.controller = parent;

		configureLevel(level);

	}

	private void configureLevel(Level level) {
		this.level = level;
	}

	public GamePlayScreen(GamePlayScreenController gamePlayScreenController,
			double width, double height) {
		
		this(gamePlayScreenController, null, width, height);
		
	}

	public GamePlayScreen(GamePlayScreenController sc, Game game, double width,
			double height) {
		
		super(width, height);
		
		this.controller = sc;
		StackPane base = new StackPane();
		
		if (!game.levels().isEmpty()) {
		
			PlayerView view = new PlayerView(this, base);
			PlayerViewController pvc = new PlayerViewController(view, game);
			view.setController(pvc, myMenu);
			
		} else {
			controller.createLevelsError();
		}
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		myMenu = menuBar;
	}

	// All other instance methods

}
