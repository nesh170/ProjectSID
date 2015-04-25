package screen.screenmodels;

import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import sprite.Sprite;
import levelPlatform.level.Level;

public class LevelEditModel {
	
	private Level level;
	
	private Sprite spriteToAdd;
	private Sprite selectedSprite;
	
	private Image imageToAdd;

	private final ObservableList<String> listOfPlatforms = FXCollections.observableArrayList();
	private final ObservableList<String> listOfEnemies = FXCollections.observableArrayList();
	private final ObservableList<String> listOfPlayers = FXCollections.observableArrayList();
	private final ObservableList<String> listOfPowerups = FXCollections.observableArrayList();
	private final ObservableList<String> listOfOther = FXCollections.observableArrayList();
	
	private Map<String,ObservableList<String>> stringToListMap;
	private Map<String,Sprite> stringToSpriteMap;

	private Set<String> tags;
	
	private final static double UNSELECT = 1;
	private final static double SELECT = 0.4;



	public LevelEditModel() {
		// TODO Auto-generated constructor stub
	}

}
