package tester.engine;

import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.RightMotionAction;
import gameEngine.components.VelocityComponent;

import java.util.ArrayList;
import java.util.List;

import data.DataHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import levelPlatform.level.Level;
import sprite.Sprite;
import sprite.SpriteImage;
import tester.Tester;
import util.ImageToInt2DArray;

/**
 * Use this tester to test new features of the Engine without the messiness of ExampleLevelMaker
 */

public class EngineTester extends Tester {
	
	private List<Sprite> mySpriteList = new ArrayList<>();
	private List<Sprite> myPlayerList = new ArrayList<>();

	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	protected void test(Stage stage) {
		Sprite player = new Sprite(new Point2D(0.0, 100.0), Point2D.ZERO, new Dimension2D(50.0, 50.0));
		player.setImagePath("mario.jpg");
		myPlayerList.add(player);
		mySpriteList.add(player);
		//Test to make sure images run smoothly:
		for(int i = 200; i<400; i+= 20){
		makeMovingThing(i);
		}
		Level l = new Level(500, 500, myPlayerList);
		l.setSprites(mySpriteList);
		try{
			DataHandler.toXMLFile(l, "testingLevel.xml", System.getProperty("user.dir")+"/engineTesting");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
	}

	private void makeMovingThing(int i) {
		Sprite movingThing = new Sprite(new Point2D(0.0, i), Point2D.ZERO, new Dimension2D(15.0, 15.0));
		movingThing.addComponent(new VelocityComponent(movingThing, null));
		movingThing.addAction(new RightMotionAction(movingThing, 2.0, KeyCode.RIGHT));
		movingThing.addAction(new LeftMotionAction(movingThing, 2.0, KeyCode.LEFT));
		movingThing.setImagePath("duke.png");
		myPlayerList.add(movingThing);
		mySpriteList.add(movingThing);
	}

}
