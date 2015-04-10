package gameEngine.test;


import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.actions.AlterHealthAction;
import gameEngine.actions.FallAction;
import gameEngine.actions.JumpAction;
import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.NormalAction;
import gameEngine.actions.RightMotionAction;
import gameEngine.components.HealthComponent;
import gameEngine.components.VelocityComponent;

import java.util.ArrayList;
import java.util.List;

import data.DataHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import resources.constants.INT;
import sprite.Sprite;
import levelPlatform.level.Level;

public class ExampleLevelMaker {
	
	public static void main(String[] args){
		System.out.println("Oh yeah!!!");
		//set up player
		Sprite player = new Sprite();
		player.addComponent(new HealthComponent(player));
		player.addComponent(new VelocityComponent(player));
		player.addAction(new LeftMotionAction(player, 20.0, KeyCode.LEFT));
		Action rma = new RightMotionAction(player, 20.0, KeyCode.RIGHT);
		player.addAction(rma);
		Action jumpAction = new JumpAction(player, -6.0, KeyCode.UP);
		player.addAction(jumpAction);
		Action gravityAction = new FallAction(player, 10.0);
		gravityAction.runEveryFrame();
		player.addAction(gravityAction);
		Action normalAction = new NormalAction(player);
		player.addAction(normalAction);
		//set up platform
		Sprite platform = new Sprite(new Point2D(0, 430),Point2D.ZERO,new Dimension2D(5000, 10));
		List<Sprite> spriteList = new ArrayList<Sprite>();
		spriteList.add(platform);
		spriteList.add(player);
		spriteList.add(platform);
		Level l = new Level(5000, 500, player);
		
		//set up collisions
		CollisionTable ct = new CollisionTable();
		platform.setCollisionTag("platform");
		player.setCollisionTag("player");
		ct.addActionToMap(player.collisonTag(), platform.collisonTag(), INT.COLLISION_UP, normalAction);
		ct.addActionToMap(platform.collisonTag(), player.collisonTag(), INT.COLLISION_DOWN, null);
		l.setCollisionTable(ct);
		l.setSprites(spriteList);
		try{
		DataHandler.toXMLFile(l, "exampleLevel.xml", System.getProperty("user.dir")+"/mario");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
	}

}
