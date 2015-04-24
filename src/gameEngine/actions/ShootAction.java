package gameEngine.actions;

import java.io.File;
import java.io.IOException;

import data.DataHandler;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.components.AmmoComponent;

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
	}

	@Override
	public void prepare() {
        bulletString =  DataHandler.toXMLString(myProjectileTemplate);
	}

	@Override
	public void execute() {
		AmmoComponent myAmmo = (AmmoComponent) mySprite.getComponentOfType("AmmoComponent");
		if (myAmmo == null || myAmmo.getAmmoCount() > 0) {
			Sprite newProjectile = generateClone();
			newProjectile.transform().setPosition(mySprite.transform().getPositionPoint());
			mySprite.addToEmissionList(newProjectile);
		}
	}
	
	private Sprite generateClone(){
		return (Sprite) DataHandler.fromXMLString(bulletString);
	}
	
	private void addProjectileMotion(Sprite proj){
		//make MotionComponent subclass,
		//attach to sprite.
	}
	
	@Override
	public void stop() {}

}
