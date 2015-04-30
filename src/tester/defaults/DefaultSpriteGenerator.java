package tester.defaults;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Set;
import gameEngine.Action;
import gameEngine.actions.FallAction;
import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.RightMotionAction;
import gameEngine.actions.UpMotionAction;
import gameEngine.components.VelocityComponent;
import data.DataHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sprite.Sprite;
import tester.Tester;
import util.DialogUtil;

/**
 * This class exists to create xml files of default sprites (player, enemy, and platform)
 * These default sprites are used in the authoring environment
 */

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
		List<String> spriteTypes = Arrays.asList(new String[] {"Player", "Enemy", "Platform"});
		spriteTypes.forEach((String type) -> makeDefaultSpriteList(type));
	}
	
	private void makeDefaultSpriteList(String type){
		try {
			 Sprite sprite = null;
			 for(Method method: this.getClass().getDeclaredMethods()){
				 if(method.toString().endsWith(type + "()")) sprite = (Sprite) method.invoke(this);
			 }
			 Set<Sprite> spriteSet = new HashSet<Sprite>();
			 sprite.setTag(type);
			 sprite.setName("default "+type);
			 spriteSet.add(sprite);
			 sprite.setIsActive(true);
			 sprite.setIsGoal(false, -1);
			 DataHandler.toXMLFile(spriteSet, "default" + type +".xml", System.getProperty("user.dir")+"/defaults");
			} catch (Exception e){
				 DialogUtil.displayMessage("Error in Default Sprite Creation", "Sprite Creation");
			}
	}

	@SuppressWarnings("unused")
    private Sprite makeDefaultPlayer(){
		Sprite player = new Sprite(Point2D.ZERO, Point2D.ZERO, new Dimension2D(DEFAULT_PLAYER_SIZE, DEFAULT_PLAYER_SIZE));
		player.setCollisionTag("player");
		player.setImagePath("/defaults/mario.png");
		player.addComponent(new VelocityComponent(player, 50.0));
		makeFalling(player);
		player.addAction(new RightMotionAction(player, DEFAULT_SPEED, KeyCode.RIGHT));
		player.addAction(new LeftMotionAction(player, DEFAULT_SPEED, KeyCode.LEFT));
		player.addAction(new UpMotionAction(player, DEFAULT_JUMP, KeyCode.UP));
		return player;
	}

	@SuppressWarnings("unused")
    private Sprite makeDefaultEnemy() {
		Sprite enemy = new Sprite(Point2D.ZERO, Point2D.ZERO, new Dimension2D(DEFAULT_ENEMY_SIZE, DEFAULT_ENEMY_SIZE));
		enemy.setImagePath("/defaults/Goomba.png");
		enemy.setCollisionTag("enemy");
		enemy.addComponent(new VelocityComponent(enemy, 50.0));
		makeFalling(enemy);
		return enemy;
	}

	@SuppressWarnings("unused")
    private Sprite makeDefaultPlatform() {
		Sprite platform = new Sprite(Point2D.ZERO, Point2D.ZERO, new Dimension2D(DEFAULT_PLATFORM_SIZE, DEFAULT_PLATFORM_SIZE));
		platform.setImagePath("/defaults/Brick_Block.png");
		platform.setCollisionTag("platform");
		return platform;
	}
	
	private void makeFalling(Sprite player) {
		Action fallAction = new FallAction(player, DEFAULT_GRAVITY, (KeyCode) null);
		fallAction.runEveryFrame();
		player.addAction(fallAction);
	}

}
