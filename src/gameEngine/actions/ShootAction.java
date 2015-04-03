package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;

/**
 * Action to shoot projectiles.
 * Will always be mapped to key.
 */
public class ShootAction extends Action{
	
	private Sprite myProjectileTemplate;
	
	public ShootAction(Sprite sprite, KeyCode[] keys, Sprite projectile) {
		super(sprite, keys);
		myProjectileTemplate = projectile;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Sprite newProjectile = generateClone();
	}
	
	private Sprite generateClone(){
		Sprite clone = new Sprite(myProjectileTemplate);
		return clone;
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
