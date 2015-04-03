package gameEngine.components;

import java.util.List;

import sprite.Sprite;
import gameEngine.Action;
import gameEngine.Component;

public class CollisionComponent extends Component {

	/** Identifier used in global collisions map to choose correct reaction*/
	private String collisionIdentifier;
	
	
	public CollisionComponent(Sprite sprite, String identifier) {
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
	protected void update() {
		// TODO Auto-generated method stub
		
	}


}
