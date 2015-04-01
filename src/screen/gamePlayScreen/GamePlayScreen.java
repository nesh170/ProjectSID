package screen.gamePlayScreen;

import player.GamePlayer;
import javafx.scene.control.MenuBar;
import screen.Screen;

public class GamePlayScreen extends Screen {

	private GamePlayer myPlayer;
	
	public GamePlayScreen(double width, double height) {
		super(width, height);
		//maybe change? adding creating GamePlayer here so screen can get MenuBar
		//also every GamePlayer must be containted within a gamePlayer screen
		myPlayer = new GamePlayer();		
	}

	//GamePlayer specific menuBar
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		menuBar = myPlayer.createPlayerMenu();
		//throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
	}

}
