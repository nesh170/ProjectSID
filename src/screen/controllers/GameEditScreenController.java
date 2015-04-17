package screen.controllers;

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
	
	public void returnToMainMenuScreen(Popup popup);
	
	/**
	 * Loads a new splash edit screen that edits the splash screen
	 * of the game parameter?
	 * @param game
	 */
	public void loadSplashEditScreen(Game game);
	/**
	 * Loads a new level edit screen and adds it to a game
	 * @param game
	 */
	public void loadLevelEditScreen(Game game);
	/**
	 * Loads a new level edit screen with an existing level
	 * @param level
	 */
	public void loadLevelEditScreen(Level level);
	/**
	 * allow users to test/demo while editing a game
	 * @param game
	 */
	public void playGame(Game game);
	/**
	 * remove the level from list of levels created 
	 * @param level
	 */
	public void trashLevel(Game game, int levelIndex);
	/**
	 * removes the splash screen from list of splash screen created
	 * pass in SplashScreen or Game?
	 */
	public void trashSplash(Game game);	
	
	/**
	 * Saves game to XML file
	 * @param game
	 */
	public void saveGame(Game game);
	/**
	 * Creates a popup menu when back button is clicked, asking user if save or not.
	 * @param game
	 * @param popup
	 */
	public void  showConfirmPopUpWithGame(Game game, Popup popup);
	/**
	 * saves the game before exiting the screen and returns to MainMenu
	 * @param game
	 * @param popup
	 * @return
	 */
	public void saveAndExit(Game game, Popup popup);



}