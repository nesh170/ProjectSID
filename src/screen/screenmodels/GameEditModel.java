package screen.screenmodels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import javafx.scene.image.ImageView;
import levelPlatform.level.Level;
import game.Game;

public class GameEditModel {

	private Game game;
	private Level selectedLevel;
	private int selectedIndex;
	
	public GameEditModel(Game game) {
		this.game = game;
	}
	
	public boolean hasSplash() {
		return game.hasSplash();
	}
	
	public boolean hasLevel() {
		return game.hasLevel();
	}
	
	
	public void forEachLevel(Consumer<Level> consumer) {
		Collection<Level> levels = game.levels();
		levels.forEach(consumer);
	}
	
	public void configureSelection(int index){
		selectedIndex = index;
		selectedLevel = game.levels().get(selectedIndex);
	}
	
	public Game getGame() {
		return game;
	}

	public ImageView getLevelPlatformImageView() {		
		return game.splashScreen().getLevelPlatformImageView();
	}

	public Level getSelectedLevel() {
		return selectedLevel;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
}
