package gameEngine.actions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import data.DataHandler;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.components.AmmoComponent;

/**
 * Action to shoot projectiles.
 * Will always be mapped to key.
 */
public class ShootAction extends TwoSpriteAction{
	
	private static final Double OUT_OF_BOUNDS = null;
    private String bulletString;
	
	public ShootAction(Sprite sprite,  Sprite projectile, KeyCode... keys) {
		super(sprite, projectile, keys);
	}

	@Override
	public void prepare() {
            bulletString =  DataHandler.toXMLString(mySecondSprite);
	}

	@Override
	public void doAction() {
		AmmoComponent myAmmo = (AmmoComponent) mySprite.getComponentOfType("AmmoComponent");
		if (myAmmo == null || myAmmo.getAmmoCount() > 0) {
			Sprite newProjectile = generateClone();
			newProjectile.addAction(new OutOfBoundsAction(newProjectile, OUT_OF_BOUNDS));
			newProjectile.transform().setPosition(mySprite.transform().getPositionPoint());
			newProjectile.prepareAllActions();
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
