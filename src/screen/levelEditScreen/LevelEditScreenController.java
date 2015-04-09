package screen.levelEditScreen;

import sprite.Sprite;
import javafx.scene.control.Tab;

public interface LevelEditScreenController {
	
	void returnToGameEditScreen(Tab tab);
	
	void loadSpriteEditScreen(Sprite sprite);

}
