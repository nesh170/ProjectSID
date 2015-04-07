package screen.levelEditScreen;

import javafx.scene.control.Tab;
import resources.constants.STRING;
import screen.ScreenController;
import screen.manager.ScreenManager;
import screen.spriteEditScreen.SpriteEditScreen;
import sprite.Sprite;

// Inner class for handling LevelEditScreenController methods
public class LevelEditScreenManager extends ScreenManager implements LevelEditScreenController {

	// Static Variables
	
	
	// Instance Variables
	
	
	// Getters & Setters
	
	
	// Static Methods
	
	
	// Constructor & Helpers
	public LevelEditScreenManager(ScreenController parent) {
		
		super(parent);
		
	}

	
	// All other instance methods
	@Override
	public void loadSpriteEditScreen(Sprite sprite) {
		Tab levelEditTab = parent().getTabSelectionModel().getSelectedItem();
		parent().createSpriteEditScreen((LevelEditScreen) levelEditTab.getContent(), sprite);
	}

	@Override
	public void returnToGameEditScreen(Tab tab) {
		Tab levelEditTab = parent().getTabSelectionModel().getSelectedItem();
		parent().getTabSelectionModel().select(tab);
		parent().removeTab(levelEditTab);
	}
	
}