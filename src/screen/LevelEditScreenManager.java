package screen;

import javafx.scene.control.Tab;
import screen.levelEditScreen.LevelEditScreenController;
import screen.spriteEditScreen.SpriteEditScreen;
import sprite.Sprite;

// Inner class for handling LevelEditScreenController methods
public class LevelEditScreenManager extends UniversalManager implements LevelEditScreenController {

	private ScreenController parent;
	
	public LevelEditScreenManager(ScreenController screenController) {
		this.parent = screenController;
	}

	@Override
	public void loadSpriteEditScreen(Sprite sprite) {
		parent.addTabWithScreenWithStringIdentifier(new SpriteEditScreen(parent.spriteEditScreenManager,
			 									sprite,
			 									parent.getTabSelectionModel().getSelectedItem(),
			 									parent.newScreenWidth(),
			 									parent.newScreenHeight()),
				"Sprite Edit Screen");
	}

	@Override
	public void returnToGameEditScreen(Tab tab) {
		Tab levelEditTab = parent.getTabSelectionModel().getSelectedItem();
		parent.getTabSelectionModel().select(tab);
		parent.removeTab(levelEditTab);
	}
}