package gameEngine.actions;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;

/**
 * Action consisting of other actions grouped.
 * Can be tied to key, or used as collision response
 *
 */
public class CompoundAction extends Action{
	
	/** List of sub-actions to be executed (in order) as compound action */
	private List<Action> mySubActions;
	
	public CompoundAction(Sprite sprite) {
		super(sprite);
		mySubActions = new ArrayList<Action>();
	}

	public CompoundAction(Sprite sprite,  KeyCode... keys){
		super(sprite, keys);
		mySubActions = new ArrayList<Action>();
	}
	
	public void addAction(Action act){
		mySubActions.add(act);
	}
	
	@Override
	public void prepare() {
	}

	@Override
	public void doAction() {
		// Using List version of .forEach() rather than
		// functional stream() version because we want users to be
		// able to establish an order. The list implementation
		// of forEach guarantees execution in order rather than
		// parallel
		mySubActions.forEach(subAction -> subAction.execute());
	}

	@Override
	public void stop() {
	}

}
