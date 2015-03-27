package usecases;

import screen.levelEditScreen.LevelEditScreen;

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
		//TODO
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
