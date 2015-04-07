package gameEngine.test;

import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.RightMotionAction;
import gameEngine.components.HealthComponent;
import gameEngine.components.VelocityComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.DataHandler;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import levelPlatform.level.Level;

public class ExampleLevelMaker {
	
	public static void main(String[] args){
		//set up player
		Sprite player = new Sprite();
		player.addComponent(new HealthComponent(player, true));
		player.addComponent(new VelocityComponent(player));
		player.addAction(new LeftMotionAction(player, 2.0, KeyCode.LEFT));
		player.addAction(new RightMotionAction(player, 2.0, KeyCode.RIGHT));
		//set up platform
		//Sprite platform = new Sprite();
		System.out.println("Oh yeah!!!");
		List<Sprite> spriteList = new ArrayList<Sprite>();
		spriteList.add(player);
		//spriteList.add(platform);
		Level l = new Level(500, 500, player);
		l.setSprites(spriteList);
		try{
		DataHandler.toXMLFile(l, "exampleLevel.xml", System.getProperty("user.dir"));
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
	}

}
