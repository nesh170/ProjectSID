package socCenter.logIn;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Popup;

/**
 * Interface for log-in screen
 * @author Emmanuel
 *
 */
public interface LogInScreenController {

	public void createNewProfile(Popup popup);
	
	public void loadUser(Popup popup);
	
	public void logIn(Popup popup, TextField username, PasswordField password);
	
	public void createProfile(Popup popup, TextField username, PasswordField password, PasswordField rePassWord, TextField imageURL, String defaultImageURL);
}
