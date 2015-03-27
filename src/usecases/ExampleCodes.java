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
	private void choosingAnAttackType() {
		//TODO
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
	private void implementingAJump() {
		//TODO
	}

}
