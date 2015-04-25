package screen.controllers;

import screen.screens.LevelEditScreen;
import sprite.Sprite;
import javafx.scene.control.Tab;

public interface SpriteEditScreenController {
	
	public void returnToSelectedLevel(LevelEditScreen levelEditScreen, Tab tab, Sprite sprite);

	public void returnToSelectedLevel(LevelEditScreen levelEditScreen, Tab tab);

}
