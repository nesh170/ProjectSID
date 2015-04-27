package socCenter.profileScreen;

import javafx.scene.control.Tab;
import socCenter.User;
import socCenter.mainPage.MainPageScreen;

public interface ProfileScreenController {

	public abstract void returnToMainPage(MainPageScreen mainPageScreen, Tab switchTab);
	
	public abstract void saveChanges(User toSave);
	
}
