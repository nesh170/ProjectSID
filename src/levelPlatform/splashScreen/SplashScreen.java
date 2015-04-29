package levelPlatform.splashScreen;

import java.util.ArrayList;
import java.util.HashMap;
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
	private Map<Sprite, Integer> goalMap;
	private IntConsumer nextLevelMethod;
	
	// Constructor & Helpers
	public SplashScreen(int width, int height) {
		
		super(width, height);
	    goalMap = new HashMap<>();
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
	
	public void setGoalMap(Map<Sprite,Integer> goalMap){
		this.goalMap = goalMap;
	}
	
	@Override
    public void update(){
        super.update();
        goalMap.keySet().forEach(sprite -> handleGoals(sprite));
    }
	
	 private void handleGoals (Sprite sprite) {
	        if(!sprite.isActive()){
	            nextLevelMethod.accept(goalMap.get(sprite));
	        }
	 }
	
	
	
}
