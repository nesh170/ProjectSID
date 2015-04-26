package screen.controllers;

import game.Game;
import javafx.scene.control.Tab;
import levelPlatform.splashScreen.SplashScreen;

public interface SplashEditScreenController {
	
	/*
	 * Goes back to the previous game editing screen  
	 */
	public void returnToGameEditScreen();
	
	public void saveSplashScreen(Game game, SplashScreen splashScreen);

}
