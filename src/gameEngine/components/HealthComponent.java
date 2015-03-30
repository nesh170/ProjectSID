package gameEngine.components;

import gameEngine.Component;
import gameEngine.Sprite;

/**
 * Behavior containing health state,
 * and methods for increasing/decreasing
 * 
 */
public class HealthComponent extends Component {
	
	private static final Double DEFAULT_STARTING_HP = 50.0;
	private static final int DEFAULT_STARTING_LIVES = 5;
	private static final boolean DEFAULT_DEATH = true;
	
	private Double myHP;
	private int myLives;
	private boolean hasDeath;
	
	public HealthComponent(Sprite sprite, boolean death){
		super(sprite);
		myHP = DEFAULT_STARTING_HP;
		myLives = DEFAULT_STARTING_LIVES;
		hasDeath = DEFAULT_DEATH;
	}
	
	//3 setters below will be used in UI when adding this behavior
	//included defaults so that 'partially functional' health behaviors
	//won't ever be instantiated in the authoring environment
	public void setHP(Double startingHP){
		myHP = startingHP;
	}
	
	public void setLives(int startingLives){
		myLives = startingLives;
	}
	
	public void setHasDeath(boolean death){
		hasDeath = death;
	}
	
	/** get health points */
	public Double getHealth(){
		return myHP;
	}
	
	public int getLives(){
		return myLives;
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
	
	
	public void kill(){
		mySprite.setActive(false);
		myLives--;
	}
	
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		if(mySprite.isActive() && myHP <= 0.0 && hasDeath)	{
			kill();
		}

	}
        
   }

