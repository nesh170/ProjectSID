package screen.gamePlayScreen;

import javafx.scene.control.MenuBar;
import screen.Screen;

public class GamePlayScreen extends Screen {

	public GamePlayScreen(double width, double height) {
		
		super(width, height);
		
	}

	//GamePlayer specific menuBar
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
	}

}
