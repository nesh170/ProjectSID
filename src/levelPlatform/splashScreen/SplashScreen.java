package levelPlatform.splashScreen;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.constants.STRING;
import levelPlatform.level.Level;

public class SplashScreen extends Level {

	// Static Variables
	
	
	// Instance Variables
	
	
	// Constructor & Helpers
	public SplashScreen(int width, int height) {
		
		super(width, height);
		
	}
	// All other instance methods
	/**
	 * get an ImageView representation for splash screen to display on GameEditScreen.
	 * @return ImageView or Image
	 */
	public ImageView getSplashImageView(){
		//TODO: implement this method
		//temporary img to reprsent each level
		ImageView img = new ImageView(new Image(STRING.GAME_EDIT.SPLASH_TMP));
		return img;		
	}
	
}
