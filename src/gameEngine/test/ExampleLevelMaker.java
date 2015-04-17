package gameEngine.test;


import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.actions.AlterHealthAction;
import gameEngine.actions.BounceAction;
import gameEngine.actions.FallAction;
import gameEngine.actions.UpMotionAction;
import gameEngine.actions.KillAction;
import gameEngine.actions.LeftMotionAction;
import gameEngine.actions.NormalAction;
import gameEngine.actions.RightMotionAction;
import gameEngine.actions.ShootAction;
import gameEngine.components.HealthComponent;
import gameEngine.components.ProjectileMotionComponent;
import gameEngine.components.VelocityComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.DataHandler;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import resources.constants.DOUBLE;
import resources.constants.INT;
import sprite.Sprite;
import util.ImageToInt2DArray;
import levelPlatform.level.Level;

public class ExampleLevelMaker extends Application{

	private static final double GRAVITY = 50.0;
	private static final double SPEED = 8.0;
	private static final double JUMP_SPEED = -20.0;

	private Sprite myPlayer;
	private List<Sprite> mySpriteList = new ArrayList<>();
	private Action myJumpAction;
	private Action myBounceAction;
	private Action myNormalAction;
	private CollisionTable myCT;
	private Action myKillAction;
	private Sprite myProjectileTemplate;
	private List<Sprite> myPlatforms = new ArrayList<Sprite>();
	private List<Sprite> myTrampolines = new ArrayList<Sprite>();
	private List<Sprite> myEnemyTrampolines = new ArrayList<>();
	private List<Sprite> myEnemies = new ArrayList<>();
	private List<Sprite> mySuperTrampolines = new ArrayList<>();

	private Level makeLevel(){
		System.out.println("Oh yeah!!!");
		myCT = new CollisionTable();
		doPlatforms();
		//set up player
		myPlayer = new Sprite(new Point2D(0.0, 100.0), Point2D.ZERO, new Dimension2D(50.0, 50.0));
		myPlayer.setCollisionTag("player");
		addPlayerComponentsAndActions();
		mySpriteList.add(myPlayer);
		Level l = new Level(500, 500, myPlayer);
		//set up collisions
		l.setCollisionTable(myCT);
		//enemies:
		makeStationaryEnemies();
		makeBouncingEnemy(1700, 1500, 50, 50);
		//big one:
		Sprite tarHeel = makeEnemy(2150, 1450, 100, 100);
		Action gthc = new KillAction(tarHeel, 0.0, KeyCode.E);
		tarHeel.addAction(gthc);
		//stop from infinitely falling:
		makeEnemy(-200, 2000, 3000, 50);
		//Switch:
		doSwitch(gthc);
		/*//Star:
		Sprite star = new Sprite(new Point2D(2525.0, 50.0), Point2D.ZERO, new Dimension2D(50.0, 50.0));
		mySpriteList.add(star);
		setUpImage(star, "star.png", 8, 8);*/
		//Images
		doImages();
		//Goals:
		Map<Sprite, Integer> goalMap = new HashMap<>();
		goalMap.put(myPlayer, 0);

		l.setSprites(mySpriteList);
		l.setGoalMap(goalMap);
		return l;
	}

	private void doPlatforms() {
		//set up platforms:
		makeEnemyPlatform(1600, 1600, 300, 10);
		makeNormalPlatform(0, 400, 500, 10);
		makeTrampoline(600, 350, 300, 10);
		makeNormalPlatform(1500, 300, 300, 10);
		makeTrampoline(1000, 1700, 500, 30);
		makeNormalPlatform(1550, 1600, 50, 10);
		makeNormalPlatform(1600, 1600, 300, 10);
		makeNormalPlatform(1950, 1730, 200, 10);
		makeNormalPlatform(2050, 1550, 250, 10);
		//super trampoline:
		makePlatform(2400, 1600, 100, 10, mySuperTrampolines, "superTrampoline");
		//Finish:
		makeNormalPlatform(2500, 100, 100, 10);
	}

	private void makeStationaryEnemies() {
		//Enemy:
		makeEnemy(660, 160, 30, 30);
		makeEnemy(780, 160, 30, 30);
		makeEnemy(200, 300, 30, 100);
		//Falling part:
		makeEnemy(1000, 400, 20, 1300);
		makeEnemy(1350, 400, 20, 1000);
		makeEnemy(1200, 400, 40, 40);
		makeEnemy(1050, 600, 40, 40);
		makeEnemy(1300, 850, 40, 40);
		makeEnemy(1200, 1100, 40, 40);
		makeEnemy(1300, 1400, 40, 40);
		//after:
	}

	private void doSwitch(Action gthc) {
		Sprite killSwitch = new Sprite(new Point2D(2100.0, 1700.0), Point2D.ZERO, new Dimension2D(30.0, 30.0));
		killSwitch.setCollisionTag("switch");
		mySpriteList.add(killSwitch);
		setCollisionUp(myPlayer, killSwitch, gthc);
		setUpImage(killSwitch, "switch.png", 1, 1);
	}

	private void doImages() {
		setUpImage(myPlayer, "dukeBlue.png", 1, 1);
		for(Sprite p: myPlatforms){
			setUpImage(p, "platform.jpeg", 1, 1);
		}
		for(Sprite enemy: myEnemies){
			setUpImage(enemy, "carolinaBlue.png", 1, 1);
		}
	}

	private void setUpImage(Sprite sprite, String path, int width, int height){
		Image img = new Image(path);
		sprite.spriteImage().addImage(ImageToInt2DArray.convertImageTo2DIntArray(img, width, height));
	}

	private Sprite makeBouncingEnemy(double x, double y, double width, double height){
		Sprite enemy = makeEnemy(x, y, width, height);
		enemy.addComponent(new VelocityComponent(enemy, null));
		Action gravityAction = new FallAction(enemy, GRAVITY);
		gravityAction.runEveryFrame();
		enemy.addAction(gravityAction);
		makeBouncing(enemy, myEnemyTrampolines);
		return enemy;
	}

	private Sprite makeEnemy(double x, double y, double width, double height) {
		Sprite enemy = new Sprite(new Point2D(x, y),Point2D.ZERO,new Dimension2D(width, height));
		enemy.setCollisionTag("enemy");
		applyActionAll(myPlayer, enemy, myKillAction);
		mySpriteList.add(enemy);
		myEnemies.add(enemy);
		return enemy;
	}

	private void applyActionAll(Sprite sprite1, Sprite enemy, Action action) {
		setCollisionUp(sprite1, enemy, action);
		myCT.addActionToMap(sprite1.collisonTag(), enemy.collisonTag(), INT.COLLISION_DOWN, action);
		myCT.addActionToMap(enemy.collisonTag(), sprite1.collisonTag(), INT.COLLISION_UP, null);
		myCT.addActionToMap(sprite1.collisonTag(), enemy.collisonTag(), INT.COLLISION_RIGHT, action);
		myCT.addActionToMap(enemy.collisonTag(), sprite1.collisonTag(), INT.COLLISION_LEFT, null);
		myCT.addActionToMap(sprite1.collisonTag(), enemy.collisonTag(), INT.COLLISION_LEFT, action);
		myCT.addActionToMap(enemy.collisonTag(), sprite1.collisonTag(), INT.COLLISION_RIGHT, null);
	}
	private void addPlayerComponentsAndActions() {
		myPlayer.addComponent(new HealthComponent(myPlayer,null));
		myPlayer.addComponent(new VelocityComponent(myPlayer, null));
		myPlayer.addAction(new LeftMotionAction(myPlayer, SPEED, KeyCode.LEFT));
		Action rma = new RightMotionAction(myPlayer, SPEED, KeyCode.RIGHT);
		myPlayer.addAction(rma);
		makeJumping(myPlayer, KeyCode.UP, false);
		makeFallingLanding(myPlayer);
		myKillAction = new KillAction(myPlayer, 0.0, KeyCode.K);
		makeBouncing(myPlayer, myTrampolines);
	}

	private void makeBouncing(Sprite sprite, List<Sprite> trampolines){
		Action bounceAction = new BounceAction(sprite, JUMP_SPEED, (KeyCode) null);
		sprite.addAction(bounceAction);
		for(Sprite platform: trampolines){
			setCollisionUp(sprite, platform, bounceAction);
		}
		Action superBounceAction = new BounceAction(sprite, JUMP_SPEED*3.5, (KeyCode) null);
		sprite.addAction(superBounceAction);
		for(Sprite platform: mySuperTrampolines){
			setCollisionUp(sprite, platform, superBounceAction);
		}
	}

	private void setCollisionUp(Sprite sprite, Sprite platform, Action bounceAction) {
		myCT.addActionToMap(sprite.collisonTag(), platform.collisonTag(), INT.COLLISION_UP, bounceAction);
		myCT.addActionToMap(platform.collisonTag(), sprite.collisonTag(), INT.COLLISION_DOWN, null);
	}


	private Action makeJumping(Sprite sprite, KeyCode kc, boolean runsEveryFrame) {
		Action jumpAction = new UpMotionAction(sprite, JUMP_SPEED, kc);
		if(runsEveryFrame){
			jumpAction.runEveryFrame();
		}
		sprite.addAction(jumpAction);
		return jumpAction;
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

	private void addProjectile() {
		//set up projectile template, add to player, along with shoot actions
		myProjectileTemplate = new Sprite(new Point2D(0,0), Point2D.ZERO, new Dimension2D(10, 10));
		myProjectileTemplate.setCollisionTag("bullet");
		ProjectileMotionComponent projComp = new ProjectileMotionComponent(myProjectileTemplate,null, myPlayer);
		myProjectileTemplate.addComponent(projComp);
		Action shootAction = new ShootAction(myPlayer, myProjectileTemplate, KeyCode.SPACE);
		myPlayer.addAction(shootAction);


	}

	private Sprite makeNormalPlatform(double x, double y, double width, double height){
		return makePlatform(x, y, width, height, myPlatforms, "platform");
	}

	private Sprite makeTrampoline(double x, double y, double width, double height){
		return makePlatform(x, y, width, height, myTrampolines, "trampoline");
	}

	private Sprite makeEnemyPlatform(double x, double y, double width, double height){
		return makePlatform(x, y, width, height, myEnemyTrampolines, "enemyPlatform");
	}

	private Sprite makePlatform(double x, double y, double width, double height, List<Sprite> listToAdd, String tag) {
		Sprite platform = new Sprite(new Point2D(x, y),Point2D.ZERO,new Dimension2D(width, height));
		platform.setCollisionTag(tag);
		listToAdd.add(platform);
		mySpriteList.add(platform);
		return platform;
	}


	public static void main(String[] args){
		ExampleLevelMaker elm = new ExampleLevelMaker();
		Level l = elm.makeLevel();
		try{
			DataHandler.toXMLFile(l, "exampleLevel.xml", System.getProperty("user.dir")+"/mario");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}
		//launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}

