package gameEngine.actions;

import java.io.File;
import java.io.IOException;
import data.DataHandler;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;

/**
 * Action to shoot projectiles.
 * Will always be mapped to key.
 */
public class ShootAction extends Action{
	
	private Sprite myProjectileTemplate;
	private String bulletString;
	
	public ShootAction(Sprite sprite,  Sprite projectile, KeyCode... keys) {
		super(sprite, keys);
		myProjectileTemplate = projectile;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare() {
            bulletString =  DataHandler.toXMLString(myProjectileTemplate);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Sprite newProjectile = generateClone();
		newProjectile.transform().setPosition(mySprite.transform().getPositionPoint());
		mySprite.addToEmissionList(newProjectile);
	}
	
	private Sprite generateClone(){
		return (Sprite) DataHandler.fromXMLString(bulletString);
	}
	
	private void addProjectileMotion(Sprite proj){
		//make MotionComponent subclass,
		//attach to sprite.
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
