package screen.controllers;

import screen.screens.GameEditScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Popup;
import levelPlatform.level.Level;
import levelPlatform.splashScreen.SplashScreen;
import game.Game;

/**
 * 
 * @author Yongjiao
 * @author Michael
 *
 */
public interface GameEditScreenController {
	
	/**
	 * Loads a new level edit screen and adds it to a game and the ObservableList in GameEditScreen
	 * @param game
	 */
	void loadLevelEditScreen(Game game, GameEditScreen gameEditScreen);
	/**
	 * Loads a new splash edit screen that edits the splash screen
	 * updates gameEditScreen display
	 * @param game
	 */
	void loadSplashEditScreen(Game game, GameEditScreen gameEditScreen);
	/**
	 * Loads a new level edit screen with an existing level
	 * @param level
	 */
	void loadLevelEditScreen(Game game, Level level);
	/**
	 * allow users to test/demo while editing a game
	 * @param game
	 */
	public void playGame(Game game);
	/**
	 * remove the level from list of levels created and updates gameEditScreen
	 * @param level
	 */
	public void trashLevel(Game game, int levelIndex, GameEditScreen gameEditScreen);
	/**
	 * removes the splash screen the game and updates gameEditScreen
	 */
	public void trashSplash(Game game, GameEditScreen gameEditScreen);	
	
	/**
	 * Saves game to XML file
	 * @param game
	 */
	public void saveGame(Game game);

	/**
	 * shows a popup
	 * @param popup
	 */

	public void returnToMainMenuScreen(Popup popup);
	
	/**
	 * Shows a pop asking user to save game before return
	 * @param game
	 * @param popup
	 */
	public void showConfirmPopUpWithGame(Game game, Popup popup);
	/**
	 * saves the game and returns back to main menu screen
	 * @param game
	 * @param popup
	 */
	public void saveAndExit(Game game, Popup popup);
	

}