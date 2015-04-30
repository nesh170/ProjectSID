package gameEngine.test;

import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.actions.FallAction;
import gameEngine.actions.UpMotionAction;
import gameEngine.actions.KillAction;
import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.NormalActionY;
import gameEngine.actions.RightMotionAction;
import gameEngine.actions.ShootAction;
import gameEngine.components.HealthComponent;
import gameEngine.components.ProjectileMotionComponent;
import gameEngine.components.VelocityComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import levelPlatform.level.Level;
import resources.constants.INT;
import sprite.Sprite;
import data.DataHandler;

public class ExampleLevelBullets {
	public static void main(String[] args){
		System.out.println("Oh yeah!!!");
		//set up player
		Sprite player = new Sprite();
		player.addComponent(new HealthComponent(player,null));
		player.addComponent(new VelocityComponent(player,null));
		player.addAction(new LeftMotionAction(player, 2.0, KeyCode.LEFT));
		Action rma = new RightMotionAction(player, 2.0, KeyCode.RIGHT);
		player.addAction(rma);
		Action jumpAction = new UpMotionAction(player, -6.0, KeyCode.UP);
		player.addAction(jumpAction);
		Action gravityAction = new FallAction(player, 10.0);
		gravityAction.runEveryFrame();
		player.addAction(gravityAction);
		Action normalAction = new NormalActionY(player);
		player.addAction(normalAction);
		//set up platform
		Sprite platform = new Sprite(new Point2D(0, 430),Point2D.ZERO,new Dimension2D(500, 10));
		
		Action killAction = new KillAction(player, 0.0, KeyCode.UP);
		
		List<Sprite> spriteList = new ArrayList<Sprite>();
		spriteList.add(platform);
		spriteList.add(player);
		spriteList.add(platform);
		List<Sprite> players = new ArrayList<>();
		players.add(player);
		Level l = new Level(500, 500, players);
		
		//set up projectile template
		Sprite projTemp = new Sprite(new Point2D(0,0), Point2D.ZERO, new Dimension2D(10, 10));
		ProjectileMotionComponent projComp = new ProjectileMotionComponent(projTemp,null, null);
		Action rma2 = new RightMotionAction(projTemp, 2.0, (KeyCode)null);
		
		projTemp.addComponent(projComp);
		projTemp.addAction(rma2);
		
		//shoot action
		Action shootAction = new ShootAction(player, projTemp, KeyCode.SPACE);
		
		player.addAction(shootAction);
		
		//set up collisions
		CollisionTable ct = new CollisionTable();
		platform.setCollisionTag("platform");
		player.setCollisionTag("player");
		ct.addActionToBigMap(player.collisionTag(), platform.collisionTag(), INT.COLLISION_UP, normalAction, player);
		ct.addActionToBigMap(platform.collisionTag(), player.collisionTag(), INT.COLLISION_DOWN, null, platform);
		l.setCollisionTable(ct);
		
		Map<Sprite, Integer> goalMap = new HashMap<>();
		goalMap.put(player, 0);
		
		l.setSprites(spriteList);
		l.setGoalMap(goalMap);
		try{
		DataHandler.toXMLFile(l, "exampleLevel.xml", System.getProperty(DataHandler.USER_DIR) + "/mario");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
	}

}
