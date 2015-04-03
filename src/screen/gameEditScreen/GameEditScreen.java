package screen.gameEditScreen;

import java.util.ResourceBundle;
import javafx.scene.control.MenuBar;
import screen.Screen;

public class GameEditScreen extends Screen {

	private static final int NUM_BUTTONS = 6;
	private static final int SAVE_BUTTON = 0;
	private static final int PLAY_BUTTON = 1;
	private static final int REMOVE_LEVEL_BUTTON = 2;
	private static final int REMOVE_SPLASH_BUTTON = 3;
	private static final int ADD_LEVEL_BUTTON = 4;
	private static final int ADD_SPLASH_BUTTON = 5;
	private static final String BUTTON_STYLE = 
			"-fx-font: 14 georgia; -fx-text-fill: black;  "
			+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); "
			+ "-fx-border-width: 2 2 2 2; -fx-border-color: #006652; -fx-background-color: white;";
	public GameEditScreen(double width, double height) {
		
		super(width, height);
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
//		throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
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
