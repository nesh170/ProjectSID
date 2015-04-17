package screen.controllers;

import screen.levelPlatformCapableScreen.LevelEditScreen;
import sprite.Sprite;
import javafx.scene.control.Tab;

public interface SpriteEditScreenController {
	
	void returnToSelectedLevel(LevelEditScreen levelEditScreen, Tab tab, Sprite sprite);

}