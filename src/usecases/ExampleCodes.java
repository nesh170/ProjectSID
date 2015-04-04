package usecases;

import java.util.List;

import levelPlatform.level.Level;
import sprite.Sprite;

public class ExampleCodes {
	/**
	 * This Class contains the five example codes of five use cases
	 */
	
	/* 
	 * This example code shows how to create a platform and add it to a List of Sprites. Once 
	 * boundaries are implemented, the constructor to Boundary (now SpriteExample) will contain 
	 * the corners of the boundary
	 */
	private void placingAPlatform() {	
		Level level = new Level(0, 0);
		List<Sprite> sprites = level.sprites();
		sprites.add(new Sprite());
	}
	
	/*
	 * This example code shows how to add an attacking behavior to a sprite. This method would
	 * take in a Sprite. It would create an AttackBehavior, which implements Behavior. This 
	 * AttackBehavior would then be added to the Sprite using the addBehavior() method.
	 */
	private void choosingAnAttackType(Sprite s) {
//		Component ab = new AttackAction(s);
//		s.addComponent(ab);
	}
	
	/*
	 * This example code shows how to save a level to a pretty-printed XML String. This method
	 * would have a LevelEditScreen and an XStream. Then the XStream would use the toXML() 
	 * method using the current level. The current level is obtained through the LevelEditScreen 
	 * method getCurrentLevel().
	 */
	private void savingAGameInTheGameAuthoringEnvironment() {
//		LevelEditScreen screen = new LevelEditScreen(null, 100.0, 100.0, null);
//		XStream xstream = new XStream(new DomDriver());
//		String xmlString = xstream.toXML(screen.getCurrentLevel());	
	}
	
	 /*
	 * This example code shows how to add a jumping behavior to a sprite. This method would
	 * take in a Sprite. It would create a JumpBehavior, which implements Behavior. This 
	 * JumpBehavior would then be added to the Sprite using the addBehavior() method.
	 */
	private void implementingAJump(Sprite s) {
//		Component jb = new JumpAction(s);
//		s.addComponent(jb);
	}
	
	/*
	 * This example code shows how to save a game when in the game authoring environment. This 
	 * method would take a String of the game name. A ScreenController would be needed 
	 * in order to call the MainMenuScreenController API that has the loadGameEditScreen() 
	 * method.
	 */
	private void userClickedRecentGame(String recentGameName) {
//		MainMenuScreenController parent = new ScreenController(null, null, 0.0, 0.0);
//		parent.loadGameEditScreen(recentGameName);
	}

}
