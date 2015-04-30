package levelPlatform.splashScreen;

import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.constants.STRING;
import sprite.Sprite;
import levelPlatform.level.Level;

public class SplashScreen extends Level {

	// Static Variables
	
	
	// Instance Variables
	//private List<Sprite> sprites = new ArrayList();
	@SuppressWarnings("unused")
    private IntConsumer nextLevelMethod;
	//private List<Sprite> playerSpriteList;
	
	// Constructor & Helpers
	public SplashScreen(int width, int height, List<Sprite> player) {
		super(width, height, player);
		//playerSpriteList = player;
		
	    //goalMap = new HashMap<>();
	}
	
	public SplashScreen(int width, int height) {
		super(width, height);
		//playerSpriteList = player;
		
	    //goalMap = new HashMap<>();
	}

	@Override
	public ImageView getLevelPlatformImageView(){
		//temporary image to represent each level
		ImageView img = new ImageView(new Image(STRING.GAME_EDIT.SPLASH_TMP));
		return img;
	}
	
//	public void addPlayerSprite(Sprite player){
//	    playerSpriteList.add(0, player);
//	}
	
	public void addSprites(List<Sprite> images) {
		sprites = images;
	}
	
	public void setGoalMap(Map<Sprite,Integer> goalMap){
		this.goalMap = goalMap;
	}	
	
}
