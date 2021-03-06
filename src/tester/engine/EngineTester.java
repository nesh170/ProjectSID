package tester.engine;

import game.Game;
import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.actions.FallAction;
import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.MotionPathAction;
import gameEngine.actions.NormalActionY;
import gameEngine.actions.RightMotionAction;
import gameEngine.actions.ShootAction;
import gameEngine.actions.SwitchOutAction;
import gameEngine.actions.UpMotionAction;
import gameEngine.components.ProjectileMotionComponent;
import gameEngine.components.VelocityComponent;
import java.util.ArrayList;
import java.util.List;
import data.DataHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import levelPlatform.level.Level;
import resources.constants.INT;
import sprite.Sprite;
import tester.Tester;

/**
 * Use this tester to test new features of the Engine without the messiness of ExampleLevelMaker
 */

public class EngineTester extends Tester {
	
	private List<Sprite> mySpriteList = new ArrayList<>();
	private List<Sprite> myPlayerList = new ArrayList<>();
	private CollisionTable myCT = new CollisionTable();
	private List<Sprite> myPlatforms = new ArrayList<>();
	private static final double GRAVITY = 50.0;
	private static final double JUMP_SPEED = 20.0;
	private static final double SPEED = 8.0;

	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	protected void test(Stage stage) {
		makePlatform(0, 300, 200, 30);
		makePlatform(-20, 0, 20, 200);
		makePlatform(250, 250, 200, 30);
		makePlatform(500, 300, 200, 30);
		Sprite player = makePlayer();
		Sprite fireMario = makeSpecialPlayer();
//		SwitchOutAction switchOut = new SwitchOutAction(new Sprite[] {player, fireMario}, myPlayerList, KeyCode.S);
//		fireMario.addAction(switchOut);
//		player.addAction(switchOut);
//		
		addProjectile(fireMario);
		@SuppressWarnings("unused")
        Sprite goomba = makeGoomba();
		

		//Sprite fireFlower = new Sprite(new Point2D(0.0, 0.0), Point2D.ZERO, new Dimension2D(300.0, 300.0));
		/*fireFlower.setCollisionTag("flower");*/
//		fireFlower.setImagePath("engineTesting/fireFlower.png");
//		mySpriteList.add(fireFlower);
//		myPlayerList.add(fireFlower);
		//setCollisionAll(player, fireFlower, switchOut);
		
		Level l = new Level(1200, 600, myPlayerList);
		l.setBackground("engineTesting/background.png");
		l.setSprites(mySpriteList);
		l.setCollisionTable(myCT);

		Game testGame = new Game("test");
		testGame.addLevel(l);

		try{
			DataHandler.toXMLFile(testGame, "simpleGame.xml", System.getProperty(DataHandler.USER_DIR)+"/simple");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
	}

	private Sprite makeGoomba() {
		Sprite goomba = new Sprite(new Point2D(600.0, 150.0), Point2D.ZERO, new Dimension2D(40.0, 40.0));
		goomba.addComponent(new VelocityComponent(goomba, null));
		goomba.setTag("goomba");
		goomba.setTag("enemy");
		makeFallingLanding(goomba);
		goomba.setImagePath("engineTesting/Goomba.png");
		Action goombaPath = new MotionPathAction(goomba, 2.0, new Point2D[]{new Point2D(510, 260), new Point2D(650, 260)}, (KeyCode) null);
		goombaPath.runEveryFrame();
		goomba.addAction(goombaPath);
		mySpriteList.add(goomba);
		return goomba;
	}


	private Sprite makeSpecialPlayer() {
		Sprite fireMario = new Sprite(new Point2D(180.0, 100.0), Point2D.ZERO, new Dimension2D(40.0, 50.0));
		fireMario.setImagePath("engineTesting/FireMario.png");
		fireMario.setTag("fireMario");
                @SuppressWarnings("unused")
                Sprite projTemp = new Sprite(new Point2D(0,0), Point2D.ZERO, new Dimension2D(10, 10));
                
		fireMario.addComponent(new VelocityComponent(fireMario, null));
		makeFallingLanding(fireMario);
		makeJumping(fireMario, KeyCode.UP, false);
		makeLeftRighting(fireMario);
		mySpriteList.add(fireMario);
		myPlayerList.add(fireMario);
		return fireMario;
	}

	private Sprite makePlayer() {
		Sprite player = new Sprite(new Point2D(100.0, 100.0), Point2D.ZERO, new Dimension2D(40.0, 50.0));
		player.addComponent(new VelocityComponent(player, null));
		player.setImagePath("engineTesting/mario.png" );
		player.setTag("player");
		myPlayerList.add(player);
		mySpriteList.add(player);
		makeFallingLanding(player);
		makeJumping(player, KeyCode.UP, false);
		makeLeftRighting(player);
		return player;
	}

	private void setCollisionUp(Sprite sprite, Sprite platform, Action action) {
		myCT.addActionToBigMap(sprite.tag(), platform.tag(), INT.COLLISION_UP, action, sprite);
		myCT.addActionToBigMap(platform.tag(), sprite.tag(), INT.COLLISION_DOWN, action, platform);
	}
	private void setCollisionLeftRightDown(Sprite sprite1, Sprite enemy, Action action){
		myCT.addActionToBigMap(sprite1.tag(), enemy.tag(), INT.COLLISION_DOWN, action, sprite1);
		myCT.addActionToBigMap(enemy.tag(), sprite1.tag(), INT.COLLISION_UP, null, enemy);
		myCT.addActionToBigMap(sprite1.tag(), enemy.tag(), INT.COLLISION_RIGHT, action, sprite1);
		myCT.addActionToBigMap(enemy.tag(), sprite1.tag(), INT.COLLISION_LEFT, null, enemy);
		myCT.addActionToBigMap(sprite1.tag(), enemy.tag(), INT.COLLISION_LEFT, action, sprite1);
		myCT.addActionToBigMap(enemy.tag(), sprite1.tag(), INT.COLLISION_RIGHT, null, enemy);
	}
	@SuppressWarnings("unused")
    private void setCollisionAll(Sprite sprite1, Sprite enemy, Action action) {
		setCollisionUp(sprite1, enemy, action);
		setCollisionLeftRightDown(sprite1, enemy, action);
	}
	
	private void makeFallingLanding(Sprite sprite) {
		Action gravityAction = new FallAction(sprite, GRAVITY);
		gravityAction.runEveryFrame();
		sprite.addAction(gravityAction);
		Action normalAction = new NormalActionY(sprite, 0.0);
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
		jumpAction.setSound("engineTesting/sounds/jump.mp3");
		sprite.addAction(jumpAction);
		return jumpAction;
	}
	private void addProjectile(Sprite myPlayer) {
		//set up projectile template, add to player, along with shoot actions
		Sprite myProjectileTemplate = new Sprite(new Point2D(0,0), Point2D.ZERO, new Dimension2D(20, 20));
		myProjectileTemplate.setTag("bullet");
		myProjectileTemplate.setImagePath("engineTesting/mario.png");
		ProjectileMotionComponent projComp = new ProjectileMotionComponent(myProjectileTemplate,0.5);
		myProjectileTemplate.addComponent(projComp);
		Action shootAction = new ShootAction(myPlayer, myProjectileTemplate, KeyCode.SPACE);
		myPlayer.addAction(shootAction);
	}
	
	private Sprite makePlatform(double x, double y, double width, double height) {
		Sprite platform = new Sprite(new Point2D(x, y),Point2D.ZERO,new Dimension2D(width, height));
		platform.setTag("platform");
		platform.setImagePath("engineTesting/mushroomPlatform.png");
		myPlatforms.add(platform);
		mySpriteList.add(platform);
		return platform;
	}
	
	@SuppressWarnings("unused")
    private void makeTextBoxSprite(String text, int x, int y){
		Sprite textSprite = new Sprite(new Point2D(x, y), Point2D.ZERO, new Dimension2D(text.length()*12, 150.0));
		textSprite.setImagePath(text);
		textSprite.setTag("textBox");
		textSprite.setTag("text");
		mySpriteList.add(textSprite);
	}
	
	@SuppressWarnings("unused")
    private Point2D[] makeZigZagPath(int numLines, int horSpace, int verSpace, int xStart, int yStart) {
		Point2D[] zigZagPath = new Point2D[numLines];
		for(int i = 0; i<zigZagPath.length; i++){
			zigZagPath[i] = new Point2D(xStart + horSpace*i, yStart + (i%2)*verSpace);
		}
		return zigZagPath;
	}

	@SuppressWarnings("unused")
    private void makeMotionPathSprite(Point2D[] points, double speed, boolean wrapsAround) {
		Sprite mps = new Sprite(points[0], Point2D.ZERO, new Dimension2D(50.0, 50.0));
		mps.addComponent(new VelocityComponent(mps, null));
		MotionPathAction mpa = new MotionPathAction(mps, speed, points, (KeyCode) null);
		if(wrapsAround) mpa.wrapAround();
		mpa.runEveryFrame();
		mps.setTag("motionPathSprite");
		mps.addAction(mpa);
		mps.setImagePath("duke.png");
		mps.setTag("motion");
		mySpriteList.add(mps);
	}
	

	@SuppressWarnings("unused")
    private void makeMovingThing(int i) {
		Sprite movingThing = new Sprite(new Point2D(0.0, i), Point2D.ZERO, new Dimension2D(15.0, 15.0));
		movingThing.addComponent(new VelocityComponent(movingThing, null));
		movingThing.addAction(new RightMotionAction(movingThing, 2.0, KeyCode.RIGHT));
		movingThing.addAction(new LeftMotionAction(movingThing, 2.0, KeyCode.LEFT));
		movingThing.setImagePath("duke.png");
		movingThing.setTag("movingThing");
		myPlayerList.add(movingThing);
		mySpriteList.add(movingThing);
	}

}
