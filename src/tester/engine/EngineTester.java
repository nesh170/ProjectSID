package tester.engine;

import gameEngine.Action;
import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.MotionPathAction;
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
		//test MotionPathAction
		makeTextBoxSprite(":This sprite wraps around:", 150, 220);
		makeMotionPathSprite(new Point2D[] {new Point2D(150.0, 300.0), new Point2D(200.0, 350.0), 
							 new Point2D(250.0, 300.0), new Point2D(200.0, 250.0)}, 2.0, true);
		makeTextBoxSprite(":This sprite does not:", 180, 40);
		Point2D[] zigZagPath = makeZigZagPath(30, 20, 40, 160, 80);
		makeMotionPathSprite(zigZagPath, 5.0, false);
		
		Level l = new Level(500, 500, myPlayerList);
		l.setSprites(mySpriteList);
		try{
			DataHandler.toXMLFile(l, "testingLevel.xml", System.getProperty("user.dir")+"/engineTesting");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
	}

	private void makeTextBoxSprite(String text, int x, int y){
		Sprite textSprite = new Sprite(new Point2D(x, y), Point2D.ZERO, new Dimension2D(text.length()*12, 150.0));
		textSprite.setImagePath(text);
		textSprite.setCollisionTag("textBox");
		mySpriteList.add(textSprite);
	}
	
	private Point2D[] makeZigZagPath(int numLines, int horSpace, int verSpace, int xStart, int yStart) {
		Point2D[] zigZagPath = new Point2D[numLines];
		for(int i = 0; i<zigZagPath.length; i++){
			zigZagPath[i] = new Point2D(xStart + horSpace*i, yStart + (i%2)*verSpace);
		}
		return zigZagPath;
	}

	private void makeMotionPathSprite(Point2D[] points, double speed, boolean wrapsAround) {
		Sprite mps = new Sprite(points[0], Point2D.ZERO, new Dimension2D(50.0, 50.0));
		mps.addComponent(new VelocityComponent(mps, null));
		MotionPathAction mpa = new MotionPathAction(mps, speed, points, (KeyCode) null);
		if(wrapsAround) mpa.wrapAround();
		mpa.runEveryFrame();
		mps.setCollisionTag("motionPathSprite");
		mps.addAction(mpa);
		mps.setImagePath("duke.png");
		mySpriteList.add(mps);
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
