package gameEngine.components;

import java.util.List;
import sprite.Sprite;
import gameEngine.Component;

/**
 * Behavior containing health state,
 * and methods for increasing/decreasing
 * 
 */
public class HealthComponent extends Component {
	
	private static final Double DEFAULT_STARTING_HP = 50.0;
	private static final boolean DEFAULT_DEATH = true;
	
	private Double myHP;
	private boolean hasDeath;
	
	public HealthComponent(Sprite sprite, Double value){
		super(sprite, value);
		try{
		    myHP = value;
		}
		catch(Exception e){
		    myHP = DEFAULT_STARTING_HP;
		}		    
		hasDeath = DEFAULT_DEATH;
	}
	
	//3 setters below will be used in UI when adding this behavior
	//included defaults so that 'partially functional' health behaviors
	//won't ever be instantiated in the authoring environment
	public void setHP(Double startingHP){
		myHP = startingHP;
	}
	
	public void setHasDeath(boolean death){
		hasDeath = death;
	}
	
	/** get health points */
	public Double getHealth(){
		return myHP;
	}
	
	/**	Subtract double from HP */
	public void decrease(Double decreaseAmount){
		myHP -= decreaseAmount;
	}
	
	/** Subtract int from HP */
	public void decrease(int decreaseAmount){
		myHP -= decreaseAmount;
	}
	
	/** Add double to HP */
	public void increase(Double increaseAmount){
		myHP += increaseAmount;
	}
	
	/** Add int to HP */
	public void increase(int increaseAmount){
		myHP += increaseAmount;
	}
	
	@Override
	public void prepare() {
	
	}

	@Override
	public void update() {
		LifeComponent lifeComponent = (LifeComponent) mySprite.getComponentOfType("LifeComponent");
		
		if(mySprite.isActive() && myHP <= 0.0 && hasDeath)	{
			lifeComponent.kill();
		}

	}
        
   }

