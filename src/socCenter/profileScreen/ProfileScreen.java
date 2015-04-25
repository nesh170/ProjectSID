package socCenter.profileScreen;

import javafx.scene.control.MenuBar;
import screen.Screen;
import socCenter.User;

public class ProfileScreen extends Screen {

	private User loggedIn;
	private User toDisplay;
	
	public ProfileScreen(double width, double height, User toDisplay, User loggedIn) {
		super(width, height);
		this.loggedIn = loggedIn;
		this.toDisplay = toDisplay;
	
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		// TODO Auto-generated method stub

	}

}
