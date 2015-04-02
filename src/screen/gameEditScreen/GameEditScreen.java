package screen.gameEditScreen;

import game.Game;

import java.awt.SplashScreen;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import level.Level;
import screen.Screen;
/**
 * The screen where users edit a game
 * allows users to edit a level or edit a sprite.
 * @author Yongjiao
 *
 */
//Question:GameEditScreen do not need to save anything? only trash level or splashscreens? saving done by LevelEdit, SpriteEdit, SplashEdit?
public class GameEditScreen extends Screen {
	// Instance variables
	private GameEditScreenController parent;
	private Game myGame;
	private ArrayList<Level>	myLevels;  //may not need or change type
	private ArrayList<SplashScreen> mySplashScreen; //may not need
	
		// Constructor & Helpers
	
	/**
	 * Set up GameEdit screen from previously created game to re-edit game.
	 * @param controller, width, height, game
	 */
	public GameEditScreen(GameEditScreenController controller, double width, double height, Game game){
		super(width, height);
		this.myGame = game;
		//this.myGame.getLevels()
	}
	/**
	 * Creates new GameEditScreen
	 */
	public GameEditScreen( GameEditScreenController controller, double width, double height) {
		super(width, height);
		myGame = new Game(); //inatilize a game 
		configureParent(controller);
		configureButtons();
	}
	// Getters & Setters
	/**
	 * add to current game level
	 * @param level
	 * @return
	 */
	public void setMyLevel(int index, Level level){
		myLevels.add(index, level);
	}
	
	private void configureParent(GameEditScreenController controller) {
		this.parent = controller;	
	}	
	private void configureButtons() {
		
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
	
	}	
	private void configureAddLevelButton(){
		
	}
	
	private void configureTrashButton(){
		
	}
	private void configurePlayButton(){
		
	}
	private void configureBackButton(){
		
	}
	
	
	//MenuBar
	//TODO: Back, returns to main menu
	//TODO: Add, adds a level
	//TODO: Remove, removes a level
	//TODO: Play, starts the game
	//TODO: Add Splash Screen
	//TODO: Save, saves level and possible splash screen
	//TODO: Trash, trashes the level and possible splash screen

}
