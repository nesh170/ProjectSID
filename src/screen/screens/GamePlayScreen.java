package screen.screens;

import game.Game;
import gameEngine.GameEngine;
import player.GamePlayer;
import player.HUD;
import player.PlayerMenu;
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
	private PlayerMenu myMenu;
	private GamePlayer myPlayer;
	private GamePlayScreenController gamePlayScreenController;
	private HUD myHUD;
	
	// Constructor & Helpers
	public GamePlayScreen(GamePlayScreenController parent, double width, double height, Level level) {
		
		super(width, height);
		
		this.controller = parent;
		
		configureLevel(level);
		//maybe change? adding creating GamePlayer here so screen can get MenuBar
		//also every GamePlayer must be containted within a gamePlayer screen
		//myMenu = new PlayerMenu(width, height);		
		
	}
		
	private void configureLevel(Level level) {
		this.level = level;
	}
	
	public GamePlayScreen(GamePlayScreenController gamePlayScreenController, double width, double height) {
		super(width, height);
		//maybe change? adding creating GamePlayer here so screen can get MenuBar
		//also every GamePlayer must be containted within a gamePlayer screen
		//myMenu = new PlayerMenu(width, height);
		this.gamePlayScreenController = gamePlayScreenController;
		ScrollPane scroll = new ScrollPane();
		StackPane base = new StackPane();
		StackPane top = new StackPane();
		myHUD = createHUD(scroll);
		top.getChildren().add(myHUD.getHUDBox());
		top.setAlignment(myHUD.getHUDBox(), Pos.TOP_LEFT);
		this.setCenter(base);
		base.getChildren().addAll(scroll, top);
		myPlayer = new GamePlayer(scroll, myHUD);
		myPlayer.setupActions(myMenu);
	}

	public GamePlayScreen(GamePlayScreenController sc, Game game, double width, double height) {
		super(width, height);
		ScrollPane scroll = new ScrollPane();
		viewableArea().setCenter(scroll);
		myPlayer = new GamePlayer(game, scroll, myHUD);
		myPlayer.setupActions(myMenu);
	}
	
	//GamePlayer specific menuBar
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		PlayerMenu menu = new PlayerMenu(menuBar);
		myMenu = menu;
	}
	
	public HUD createHUD(ScrollPane pane) {
		myHUD = new HUD(pane);
		myHUD.addItem("Lives", 0);
		myHUD.addItem("Health", 0);
		myHUD.addItem("Score", 0);
		return myHUD;
	}
	
	// All other instance methods

}
