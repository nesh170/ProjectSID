package gameEngine.test;


import gameEngine.actions.AlterHealthAction;
import gameEngine.actions.GravityAction;

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
import sprite.Sprite;
import levelPlatform.level.Level;

public class ExampleLevelMaker {
	
	public static void main(String[] args){
		//set up player
		Sprite player = new Sprite();
		Sprite block = new Sprite();
		player.addComponent(new HealthComponent(player, true));
		player.addComponent(new VelocityComponent(player));
		player.addAction(new LeftMotionAction(player, 50.0, KeyCode.LEFT));
		player.addAction(new RightMotionAction(player, 50.0, KeyCode.RIGHT));
		player.addAction(new JumpAction(player, 20.0, KeyCode.UP));
		player.addAction(new FallAction(player, 9.81));
		//set up platform
		Sprite platform = new Sprite(new Point2D(0, 400),Point2D.ZERO,new Dimension2D(5000, 10));
		platform.addAction(new NormalAction(platform, -9.81));
		System.out.println("Oh yeah!!!");
		List<Sprite> spriteList = new ArrayList<Sprite>();
		spriteList.add(player);
		//spriteList.add(block);
		spriteList.add(platform);
		Level l = new Level(5000, 800, player);
		l.setSprites(spriteList);
		try{
		DataHandler.toXMLFile(l, "exampleLevel.xml", System.getProperty("user.dir")+"/mario");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
	}

}
