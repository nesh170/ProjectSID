package levelPlatform.splashScreen;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.constants.STRING;
import sprite.Sprite;
import levelPlatform.level.Level;

public class SplashScreen extends Level {

	// Static Variables
	
	
	// Instance Variables
	//private List<Sprite> sprites = new ArrayList();
	
	// Constructor & Helpers
	public SplashScreen(int width, int height) {
		
		super(width, height);
		
	}

	@Override
	public ImageView getLevelPlatformImageView(){
		//temporary image to represent each level
		ImageView img = new ImageView(new Image(STRING.GAME_EDIT.SPLASH_TMP));
		return img;
	}
	
	public void addSprites(List<Sprite> images) {
		sprites = images;
	}
}
