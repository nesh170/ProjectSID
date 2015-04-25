package screen.factories;

import screen.Screen;
import socCenter.User;
import socCenter.logIn.LogInScreen;
import socCenter.logIn.LogInScreenController;
import socCenter.mainPage.MainPageScreen;
import socCenter.mainPage.MainPageScreenController;

public class SocialCenterScreenFactory extends ScreenFactory {
	
	public SocialCenterScreenFactory(double width, double height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	//perhaps not permanently here...
	public Screen createLogInScreen(LogInScreenController logInScreenController){
		return new LogInScreen(logInScreenController, width, height);
	}
	
	public Screen createMainPageScreen(MainPageScreenController mainPageScreenController, User user){
		return new MainPageScreen(mainPageScreenController, width, height, user);
	}
}
