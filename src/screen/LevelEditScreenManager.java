package screen;

import javafx.scene.control.Tab;
import resources.constants.STRING;
import screen.levelEditScreen.LevelEditScreenController;
import screen.manager.Manager;
import screen.spriteEditScreen.SpriteEditScreen;
import sprite.Sprite;

// Inner class for handling LevelEditScreenController methods
public class LevelEditScreenManager extends Manager implements LevelEditScreenController {

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
		parent().addTabWithScreenWithStringIdentifier(new SpriteEditScreen(parent().spriteEditScreenManager,
			 									sprite,
			 									parent().getTabSelectionModel().getSelectedItem(),
			 									parent().newScreenWidth(),
			 									parent().newScreenHeight()),
				STRING.SPRITE_EDIT);
	}

	@Override
	public void returnToGameEditScreen(Tab tab) {
		Tab levelEditTab = parent().getTabSelectionModel().getSelectedItem();
		parent().getTabSelectionModel().select(tab);
		parent().removeTab(levelEditTab);
	}
	
}