package screen.controllers;

import java.util.List;
import java.util.Map;

import screen.screenmodels.CollisionMap;
import javafx.scene.control.Tab;
import gameEngine.CollisionTable;

/**
 * 
 * @author Anika
 *
 */
public interface CollisionTableScreenController {

	// returns to current level
	public void returnToLevel(CollisionMap collisionMap, Tab switchTo);
	
}
