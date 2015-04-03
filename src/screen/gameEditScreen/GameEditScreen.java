package screen.gameEditScreen;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import resources.ScreenButton;
import screen.Screen;

/**
 * Buttons - anika
 * @author anika
 *
 */
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
	private String[] myScreenButtonsNames;
	private ResourceBundle myButtonLabels;
	private Button[] myScreenButtons;
	
	public GameEditScreen(double width, double height) {
		
		super(width, height);
		myButtonLabels = ResourceBundle.getBundle("gameEditScreenButtons");		
		myScreenButtonsNames = new String[] {"Save", "Play", "Remove Level", 
				"Remove Splash Screen", "Add Level", "Add Splash Screen"};
		createScreenButtons();
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
//		throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
	}
	
	private void createScreenButtons() {
		// Creates buttons that are put on the screen
		for (int i = 0; i < NUM_BUTTONS; i++){
			ScreenButton myB = new ScreenButton(myButtonLabels.getString(myScreenButtonsNames[i]), BUTTON_STYLE);
			myScreenButtons[i] = myB;
		//	hbox.getChildren().add(myB); TODO: add to screen
		}	
		myScreenButtons[SAVE_BUTTON].setOnMouseClicked(e -> saveLevel());
		myScreenButtons[PLAY_BUTTON].setOnMouseClicked(e -> playGame());
		myScreenButtons[REMOVE_SPLASH_BUTTON].setOnMouseClicked(e -> trashSplashScreen());
		myScreenButtons[REMOVE_LEVEL_BUTTON].setOnMouseClicked(e -> removeLevel());

		myScreenButtons[ADD_LEVEL_BUTTON].setOnMouseClicked(e -> addLevel());
		myScreenButtons[ADD_SPLASH_BUTTON].setOnMouseClicked(e -> addSplash());
		
		
	}
	
	private int addLevel()
	{
		int levelNum = 0;
		// TODO
		return levelNum;
	}
	
	private int addSplash()
	{
		int splashNum = 0;
		// TODO
		return splashNum;
	}
	
	private boolean saveLevel()
	{
		return true;
	}
	
	private boolean removeLevel()
	{
		return true;
	}
	
	private boolean trashSplashScreen()
	{
		return true;
	}
	
	private void playGame()
	{
		
	}
	
	
	//MenuBar
	//TODO: Back, returns to main menu	

}
