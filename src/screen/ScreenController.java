package screen;

import screen.gameEditScreen.GameEditScreenController;
import screen.levelEditScreen.LevelEditScreenController;
import screen.mainMenu.MainMenuScreenController;
import screen.splashEditScreen.SplashEditScreenController;
import screen.spriteEditScreen.SpriteEditScreenController;
import gameEngine.Level;
import screen.Screen;

public class ScreenController implements MainMenuScreenController, 
										GameEditScreenController, 
										SplashEditScreenController,
										LevelEditScreenController,
										SpriteEditScreenController {
	
	private Screen currentScreen;
	

	public ScreenController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void returnToSelectedLevel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSpriteEditScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnToGameEditScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSplashEditScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createNewGame() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void returnToMainMenuScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadLevelEditScreen(Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGame(String gameFile) {
		// TODO Auto-generated method stub
		
	}

}
