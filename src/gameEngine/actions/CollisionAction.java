package gameEngine.actions;

import java.util.List;

import sprite.Sprite;
import gameEngine.Action;

public class CollisionAction extends Action {

	/** Identifier used in global collisions map to choose correct reaction*/
	private String collisionIdentifier;
	
	/** List of sub-actions to be executed (in order) during collision */
	private List<Action> mySubActions;
	
	public CollisionAction(Sprite sprite, String identifier) {
		super(sprite);
		collisionIdentifier = identifier;
		// TODO Auto-generated constructor stub
	}
	
	public String getID(){
		return collisionIdentifier;
	}
	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		// IMPORTANT!!
		// Using List version of .forEach() rather than
		// stream() version because we want users to be
		// able to establish an order. The list implementation
		// of forEach guarantees execution in order rather than
		// parallel
		mySubActions.forEach(subAction -> subAction.execute());
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
