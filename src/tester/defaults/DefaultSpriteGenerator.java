package tester.defaults;

import java.util.HashSet;
import java.util.Set;

import gameEngine.Action;
import gameEngine.actions.FallAction;
import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.RightMotionAction;
import gameEngine.actions.UpMotionAction;
import data.DataHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sprite.Sprite;
import tester.Tester;

public class DefaultSpriteGenerator extends Tester{
	
	private static final double DEFAULT_PLAYER_SIZE = 100.0;
	private static final double DEFAULT_ENEMY_SIZE = 100.0;
	private static final double DEFAULT_PLATFORM_SIZE = 100.0;
	private static final double DEFAULT_GRAVITY = 50.0;
	private static final double DEFAULT_SPEED = 8.0;
	private static final double DEFAULT_JUMP = -20.0;

	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	protected void test(Stage stage) {
		//TODO: refactor
		Sprite player = makeDefaultPlayer();
		Sprite enemy = makeDefaultEnemy();
		Sprite platform = makeDefaultPlatform();
		Set<Sprite> playerSet = new HashSet<Sprite>();
		Set<Sprite> enemySet = new HashSet<Sprite>();
		Set<Sprite> platformSet = new HashSet<Sprite>();
		playerSet.add(player);
		enemySet.add(enemy);
		platformSet.add(platform);
		try{
			DataHandler.toXMLFile(player, "defaultPlayer.xml", System.getProperty("user.dir")+"/defaults");
			DataHandler.toXMLFile(enemy, "defaultEnemy.xml", System.getProperty("user.dir")+"/defaults");
			DataHandler.toXMLFile(platform, "defaultPlatform.xml", System.getProperty("user.dir")+"/defaults");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
	}

	private Sprite makeDefaultPlayer() {
		Sprite player = new Sprite(Point2D.ZERO, Point2D.ZERO, new Dimension2D(DEFAULT_PLAYER_SIZE, DEFAULT_PLAYER_SIZE));
		player.setCollisionTag("player");
		player.setImagePath(System.getProperty("user.dir")+"/defaults/mario.png");
		makeFalling(player);
		player.addAction(new RightMotionAction(player, DEFAULT_SPEED, KeyCode.RIGHT));
		player.addAction(new LeftMotionAction(player, DEFAULT_SPEED, KeyCode.LEFT));
		player.addAction(new UpMotionAction(player, DEFAULT_JUMP, KeyCode.UP));
		return player;
	}

	private Sprite makeDefaultEnemy() {
		Sprite enemy = new Sprite(Point2D.ZERO, Point2D.ZERO, new Dimension2D(DEFAULT_ENEMY_SIZE, DEFAULT_ENEMY_SIZE));
		enemy.setImagePath(System.getProperty("user.dir")+"/defaults/Goomba.png");
		enemy.setCollisionTag("enemy");
		makeFalling(enemy);
		return enemy;
	}

	private Sprite makeDefaultPlatform() {
		Sprite platform = new Sprite(Point2D.ZERO, Point2D.ZERO, new Dimension2D(DEFAULT_PLATFORM_SIZE, DEFAULT_PLATFORM_SIZE));
		platform.setImagePath(System.getProperty("user.dir")+"/defaults/Brick_Block.png");
		platform.setCollisionTag("platform");
		return platform;
	}
	
	private void makeFalling(Sprite player) {
		Action fallAction = new FallAction(player, DEFAULT_GRAVITY, (KeyCode) null);
		fallAction.runEveryFrame();
		player.addAction(fallAction);
	}

}
