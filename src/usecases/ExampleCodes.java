package usecases;

import java.util.List;

import gameEngine.AttackBehavior;
import gameEngine.Behavior;
import gameEngine.JumpBehavior;
import gameEngine.Level;
import gameEngine.Sprite;
import gameEngine.SpriteExample;
import screen.ScreenController;
import screen.levelEditScreen.LevelEditScreen;
import screen.mainMenu.MainMenuScreenController;

import com.thoughtworks.xstream.XStream;

public class ExampleCodes {
	/**
	 * This Class contains the five example codes of five use cases
	 */
	
	/*
	 * 
	 */
	private void collisionBetweenPlayerAndEnemy() {
		//TODO
	}
	
	/*
	 * 
	 */
	private void placingAPlatform() {
		
		Level level = new Level();
		List<SpriteExample> boundaries = level.boundaries();
		
		// Once boundaries are implemented, the constructor to 
		// Boundary (now SpriteExample) will contain the corners of the boundary
		boundaries.add(new SpriteExample());
		
	}
	
	/*
	 * 
	 */
	private void choosingAnAttackType(Sprite s) {
		Behavior ab = new AttackBehavior();
		s.addBehavior(ab);
	}
	
	/*
	 * 
	 */
	private void savingAGameInTheGameAuthoringEnvironment() {
		LevelEditScreen screen = new LevelEditScreen();
		XStream xstream = new XStream();
		xstream.toXML(screen.getCurrentLevel());	
	}
	
	/*
	 * 
	 */
	private void implementingAJump(Sprite s) {
		Behavior jb = new JumpBehavior();
		s.addBehavior(jb);
	}
	
	/*
	 * 
	 */
	private void userClickedRecentGame(String recentGameName) {
		MainMenuScreenController parent = new ScreenController();
		parent.loadGameEditScreen(recentGameName);
	}

}
