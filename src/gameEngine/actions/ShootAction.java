package gameEngine.actions;

import data.DataHandler;
import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.components.AmmoComponent;
import gameEngine.components.ProjectileMotionComponent;

/**
 * Action to shoot projectiles.
 * Will always be mapped to key.
 */

@ActionName(displayName = "Shoot")
public class ShootAction extends TwoSpriteAction{

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
			newProjectile.transform().setPosition(mySprite.transform().getPositionPoint());
			ProjectileMotionComponent projVelocity = (ProjectileMotionComponent) newProjectile.getComponentOfType("ProjectileMotionComponent");
			projVelocity.setShooter(mySprite);
			newProjectile.prepareAllActions();
			mySprite.addToEmissionList(newProjectile);
		}
	}
	
	private Sprite generateClone(){
		return (Sprite) DataHandler.fromXMLString(bulletString);
	}
	
	@SuppressWarnings("unused")
    private void addProjectileMotion(Sprite proj){
		//make MotionComponent subclass,
		//attach to sprite.
	}
	
	@Override
	public void stop() {}

}
