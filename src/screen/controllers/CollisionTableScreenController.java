package screen.controllers;

import java.util.List;
import java.util.Map;

import javafx.scene.control.Tab;
import gameEngine.CollisionTable;

/**
 * 
 * @author Anika
 *
 */
public interface CollisionTableScreenController {

	// returns to current level
	public void returnToLevel(Map<List<String>, List<String>> collisionTable, Tab switchTo);
	
}
