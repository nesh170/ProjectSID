package screen.gamePlayScreen;

import gameEngine.GameEngine;
import player.GamePlayer;
import player.PlayerMenu;
import javafx.scene.control.MenuBar;
import levelPlatform.level.Level;
import screen.Screen;
import screen.ScreenController;

public class GamePlayScreen extends Screen {

	// Instance Variables
	private GamePlayScreenController controller;
	private Level level;
	private PlayerMenu myMenu;
	private GamePlayScreenController gamePlayScreenController;
	
	
	// Constructor & Helpers
	public GamePlayScreen(GamePlayScreenController parent, double width, double height, Level level) {
		
		super(width, height);
		
		this.controller = parent;
		
		configureLevel(level);
		//maybe change? adding creating GamePlayer here so screen can get MenuBar
		//also every GamePlayer must be containted within a gamePlayer screen
		myMenu = new PlayerMenu(width, height);		
		
	}
		
	private void configureLevel(Level level) {
		this.level = level;
	}
	
	public GamePlayScreen(GamePlayScreenController gamePlayScreenController, double width, double height) {
		super(width, height);
		//maybe change? adding creating GamePlayer here so screen can get MenuBar
		//also every GamePlayer must be containted within a gamePlayer screen
		myMenu = new PlayerMenu(width, height);	
		this.gamePlayScreenController = gamePlayScreenController;
	}

	//GamePlayer specific menuBar
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
	}
	
	
	// All other instance methods

}
