package screen.gamePlayScreen;

import gameEngine.GameEngine;
import player.GamePlayer;
import javafx.scene.control.MenuBar;
import levelPlatform.level.Level;
import screen.Screen;

public class GamePlayScreen extends Screen {

	// Instance Variables
	private GamePlayScreenController parent;
	private Level level;
	private GamePlayer myPlayer;
	
	public GamePlayScreen(GamePlayScreenController parent, double width, double height, Level level) {
		
		super(width, height);
		
		configureParent(parent);
		configureLevel(level);
		//maybe change? adding creating GamePlayer here so screen can get MenuBar
		//also every GamePlayer must be containted within a gamePlayer screen
		myPlayer = new GamePlayer(width, height);		
		
	}
	
	private void configureParent(GamePlayScreenController parent) {
		this.parent = parent;
	}
	
	private void configureLevel(Level level) {
		this.level = level;
	}

	//GamePlayer specific menuBar
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
	}

}
