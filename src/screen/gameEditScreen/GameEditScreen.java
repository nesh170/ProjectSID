package screen.gameEditScreen;

import java.awt.SplashScreen;
import java.util.ArrayList;
import javafx.scene.control.MenuBar;
import level.Level;
import screen.Screen;
/**
 * The screen where users edit a game
 * allows users to edit a level or edit a sprite.
 * @author Yongjiao
 *
 */

public class GameEditScreen extends Screen {
	// Static variables
		// Instance variables
	private GameEditScreenController parent;
	private ArrayList<Level>	myLevels;
	private ArrayList<SplashScreen> mySplashScreen;
	
		// Getters & Setters


		// Constructor & Helpers
	
	public GameEditScreen( GameEditScreenController controller, double width, double height) {
		super(width, height);
		configureParent(controller);
		configureButtons();
	}

	private void configureParent(GameEditScreenController controller) {
		this.parent = controller;	
	}	
	private void configureButtons() {
		
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
	
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
