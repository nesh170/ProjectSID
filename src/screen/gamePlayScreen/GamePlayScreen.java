package screen.gamePlayScreen;

import gameEngine.GameEngine;
import player.GamePlayer;
import javafx.scene.control.MenuBar;
import levelPlatform.level.Level;
import screen.Screen;
import screen.ScreenController;

public class GamePlayScreen extends Screen {

	// Instance Variables
	private GamePlayScreenController controller;
	private Level level;
	private GamePlayer myPlayer;
	private GamePlayScreenController gamePlayScreenController;
	
	
	// Constructor & Helpers
	public GamePlayScreen(ScreenController parent, double width, double height, Level level) {
		
		super(parent, width, height);
		
		configureLevel(level);
		//maybe change? adding creating GamePlayer here so screen can get MenuBar
		//also every GamePlayer must be containted within a gamePlayer screen
		myPlayer = new GamePlayer(width, height);		
		
	}
	
	@Override
	protected void createAppropriateControllerForParent(ScreenController parent) {
		this.controller = new GamePlayScreenManager(parent);
	}
	
	private void configureLevel(Level level) {
		this.level = level;
	}
	
	public GamePlayScreen(GamePlayScreenController gamePlayScreenController, double width, double height) {
		super(width, height);
		//maybe change? adding creating GamePlayer here so screen can get MenuBar
		//also every GamePlayer must be containted within a gamePlayer screen
		myPlayer = new GamePlayer(width, height);	
		this.gamePlayScreenController = gamePlayScreenController;
	}

	//GamePlayer specific menuBar
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
	}
	
	
	// All other instance methods

}
