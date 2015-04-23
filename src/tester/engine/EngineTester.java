package tester.engine;

import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.actions.FallAction;
import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.MotionPathAction;
import gameEngine.actions.NormalAction;
import gameEngine.actions.RightMotionAction;
import gameEngine.actions.SwitchOutAction;
import gameEngine.actions.UpMotionAction;
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
import resources.constants.INT;
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
	private CollisionTable myCT = new CollisionTable();
	private List<Sprite> myPlatforms = new ArrayList<>();
	private static final double GRAVITY = 50.0;
	private static final double JUMP_SPEED = -20.0;
	private static final double SPEED = 8.0;

	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	protected void test(Stage stage) {
		makePlatform(0, 300, 200, 30);
		makePlatform(-20, 0, 20, 200);
		
		Sprite player = makePlayer();
		Sprite fireMario = makeSpecialPlayer();
		SwitchOutAction switchOut = new SwitchOutAction(new Sprite[] {player, fireMario}, KeyCode.S);
		fireMario.addAction(switchOut);
		player.addAction(switchOut);
		
		Level l = new Level(500, 500, myPlayerList);
		l.setSprites(mySpriteList);
		l.setCollisionTable(myCT);
		try{
			DataHandler.toXMLFile(l, "testingLevel.xml", System.getProperty("user.dir")+"/engineTesting");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
	}

	private Sprite makeSpecialPlayer() {
		Sprite fireMario = new Sprite(new Point2D(180.0, 100.0), Point2D.ZERO, new Dimension2D(50.0, 50.0));
		fireMario.setImagePath("star.png");
		fireMario.setCollisionTag("fireMario");
		fireMario.addComponent(new VelocityComponent(fireMario, null));
		makeFallingLanding(fireMario);
		makeJumping(fireMario, KeyCode.UP, false);
		makeLeftRighting(fireMario);
		mySpriteList.add(fireMario);
		myPlayerList.add(fireMario);
		return fireMario;
	}

	private Sprite makePlayer() {
		Sprite player = new Sprite(new Point2D(100.0, 100.0), Point2D.ZERO, new Dimension2D(50.0, 50.0));
		player.addComponent(new VelocityComponent(player, null));
		player.setImagePath("mario.jpg");
		player.setCollisionTag("player");
		myPlayerList.add(player);
		mySpriteList.add(player);
		makeFallingLanding(player);
		makeJumping(player, KeyCode.UP, false);
		makeLeftRighting(player);
		return player;
	}

	private void setCollisionUp(Sprite sprite, Sprite platform, Action action) {
		myCT.addActionToMap(sprite.collisionTag(), platform.collisionTag(), INT.COLLISION_UP, action);
		myCT.addActionToMap(platform.collisionTag(), sprite.collisionTag(), INT.COLLISION_DOWN, action);
	}
	
	private void makeFallingLanding(Sprite sprite) {
		Action gravityAction = new FallAction(sprite, GRAVITY);
		gravityAction.runEveryFrame();
		sprite.addAction(gravityAction);
		Action normalAction = new NormalAction(sprite);
		sprite.addAction(normalAction);
		for(Sprite platform: myPlatforms){
			setCollisionUp(sprite, platform, normalAction);
		}
	}
	private void makeLeftRighting(Sprite sprite) {
		sprite.addAction(new LeftMotionAction(sprite, SPEED, KeyCode.LEFT));
		sprite.addAction(new RightMotionAction(sprite, SPEED, KeyCode.RIGHT));
	}
	private Action makeJumping(Sprite sprite, KeyCode kc, boolean runsEveryFrame) {
		Action jumpAction = new UpMotionAction(sprite, JUMP_SPEED, kc);
		sprite.addAction(jumpAction);
		return jumpAction;
	}
	
	private Sprite makePlatform(double x, double y, double width, double height) {
		Sprite platform = new Sprite(new Point2D(x, y),Point2D.ZERO,new Dimension2D(width, height));
		platform.setCollisionTag("platform");
		myPlatforms.add(platform);
		mySpriteList.add(platform);
		return platform;
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
