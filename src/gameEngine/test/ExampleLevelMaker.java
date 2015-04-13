package gameEngine.test;


import gameEngine.Action;
import gameEngine.CollisionTable;
import gameEngine.actions.AlterHealthAction;
import gameEngine.actions.BounceAction;
import gameEngine.actions.FallAction;
import gameEngine.actions.JumpAction;
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
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import resources.constants.INT;
import sprite.Sprite;
import levelPlatform.level.Level;

public class ExampleLevelMaker {
	
	private static final double GRAVITY = 50.0;
	private static final double SPEED = 8.0;
	private static final double JUMP_SPEED = -20.0;
	
	private Sprite myPlayer;
	private List<Sprite> mySpriteList;
	private Action myJumpAction;
	private Action myBounceAction;
	private Action myNormalAction;
	private CollisionTable myCT;
	private Action myKillAction;
	private Sprite myProjectileTemplate;
	
	private Level makeLevel(){
	System.out.println("Oh yeah!!!");
	//set up player
	myPlayer = new Sprite(new Point2D(0.0, 100.0), Point2D.ZERO, new Dimension2D(50.0, 50.0));
	addPlayerComponentsAndActions();
	mySpriteList = new ArrayList<Sprite>();
	mySpriteList.add(myPlayer);
	Level l = new Level(500, 500, myPlayer);
	
	

	
	
	//set up collisions
	myCT = new CollisionTable();
	myPlayer.setCollisionTag("player");
	l.setCollisionTable(myCT);
	//make Platforms here:
	makePlatform(0, 400, 500, 10, myNormalAction, "platform");
	makePlatform(600, 350, 300, 10, myBounceAction, "trampoline");
	makePlatform(1000, 300, 500, 10, myNormalAction, "platform");
	//Enemy:
	makeEnemy(660, 160, 30, 30);
	makeEnemy(780, 160, 30, 30);
	makeEnemy(200, 300, 30, 100);
	addProjectile();
	

	
	
	//Goals:
	Map<Sprite, Integer> goalMap = new HashMap<>();
	goalMap.put(myPlayer, 0);
	
	l.setSprites(mySpriteList);
	l.setGoalMap(goalMap);
	return l;
}
	private void makeEnemy(double x, double y, double width, double height) {
		Sprite enemy = new Sprite(new Point2D(x, y),Point2D.ZERO,new Dimension2D(width, height));
		enemy.setCollisionTag("enemy");
		myCT.addActionToMap(myPlayer.collisonTag(), enemy.collisonTag(), INT.COLLISION_UP, myKillAction);
		myCT.addActionToMap(enemy.collisonTag(), myPlayer.collisonTag(), INT.COLLISION_DOWN, null);
		myCT.addActionToMap(myPlayer.collisonTag(), enemy.collisonTag(), INT.COLLISION_DOWN, myKillAction);
		myCT.addActionToMap(enemy.collisonTag(), myPlayer.collisonTag(), INT.COLLISION_UP, null);
		myCT.addActionToMap(myPlayer.collisonTag(), enemy.collisonTag(), INT.COLLISION_RIGHT, myKillAction);
		myCT.addActionToMap(enemy.collisonTag(), myPlayer.collisonTag(), INT.COLLISION_LEFT, null);
		myCT.addActionToMap(myPlayer.collisonTag(), enemy.collisonTag(), INT.COLLISION_LEFT, myKillAction);
		myCT.addActionToMap(enemy.collisonTag(), myPlayer.collisonTag(), INT.COLLISION_RIGHT, null);
		mySpriteList.add(enemy);
	}
	private void addPlayerComponentsAndActions() {
		myPlayer.addComponent(new HealthComponent(myPlayer,null));
		myPlayer.addComponent(new VelocityComponent(myPlayer, null));
		myPlayer.addAction(new LeftMotionAction(myPlayer, SPEED, KeyCode.LEFT));
		Action rma = new RightMotionAction(myPlayer, SPEED, KeyCode.RIGHT);
		myPlayer.addAction(rma);
		myJumpAction = new JumpAction(myPlayer, JUMP_SPEED, KeyCode.UP);
		myPlayer.addAction(myJumpAction);
		Action gravityAction = new FallAction(myPlayer, GRAVITY);
		gravityAction.runEveryFrame();
		myPlayer.addAction(gravityAction);
		myNormalAction = new NormalAction(myPlayer);
		myPlayer.addAction(myNormalAction);
		myKillAction = new KillAction(myPlayer, 0.0, KeyCode.K);
		
		myBounceAction = new BounceAction(myPlayer, JUMP_SPEED, (KeyCode) null);
		myPlayer.addAction(myBounceAction);
		

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
	
	private void makePlatform(double x, double y, double width, double height, Action action, String tag) {
		Sprite platform = new Sprite(new Point2D(x, y),Point2D.ZERO,new Dimension2D(width, height));
		platform.setCollisionTag(tag);
		myCT.addActionToMap(myPlayer.collisonTag(), platform.collisonTag(), INT.COLLISION_UP, action);
		myCT.addActionToMap(platform.collisonTag(), myPlayer.collisonTag(), INT.COLLISION_DOWN, null);
		mySpriteList.add(platform);
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
	}

}