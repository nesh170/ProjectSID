package socCenter.profileScreen;

import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import socCenter.User;
import socCenter.mainPage.MainPageScreen;

public interface ProfileScreenController {

	public abstract void returnToMainPage(MainPageScreen mainPageScreen, Tab switchTab);
	
	public abstract void saveChanges(User toSave);
	
	public abstract void loadPlayerWithAvatar(Image avatarToUse);
	
}
