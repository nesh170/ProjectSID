package gameEngine;

import java.util.*;

public class Collision {
	
	private Map<Class, Map<Class, Behavior[]>> myBehaviorTable;
	
	public Collision(Map<Class, Map<Class, Behavior[]>> behaviors){
		myBehaviorTable = behaviors;
	}

    /**
     * Takes in two sprites, determines spatial relationship, and calls appropriate method to handle Collision
     * @param sprite1
     * @param sprite2
     **/
    public void handleCollide(Sprite sprite1, Sprite sprite2){
    	//get the difference in x from center to center. positive if sprite1 is to the right
    	double xDif = (sprite1.getCoordinate().getX() + sprite1.getDimensions().getWidth()/2)
    			   -  (sprite2.getCoordinate().getX() + sprite2.getDimensions().getWidth()/2);
    	//get the difference in y from center to center. positive if sprite1 is below
    	double yDif = (sprite1.getCoordinate().getY() + sprite1.getDimensions().getHeight()/2)
    			   -  (sprite2.getCoordinate().getY() + sprite2.getDimensions().getHeight()/2);
    	//TODO: Think of a better way to do this if possible
    	if(Math.abs(xDif)>Math.abs(yDif) && xDif<0) handleSprite1Left(sprite1, sprite2);
    	if(Math.abs(xDif)>Math.abs(yDif) && xDif>0) handleSprite1Right(sprite1, sprite2);
    	if(Math.abs(xDif)<Math.abs(yDif) && yDif<0) handleSprite1Up(sprite1, sprite2);
    	if(Math.abs(xDif)<Math.abs(yDif) && yDif>0) handleSprite1Down(sprite1, sprite2);
    }
    
    private void handleSprite1Left(Sprite sprite1, Sprite sprite2){
    	myBehaviorTable.get(sprite1.getClass()).get(sprite2.getClass())[0].execute();
    }
    
    private void handleSprite1Right(Sprite sprite1, Sprite sprite2){
    	myBehaviorTable.get(sprite1.getClass()).get(sprite2.getClass())[1].execute();
    }
    
    private void handleSprite1Up(Sprite sprite1, Sprite sprite2){
    	myBehaviorTable.get(sprite1.getClass()).get(sprite2.getClass())[2].execute();
    }
    
    private void handleSprite1Down(Sprite sprite1, Sprite sprite2){
    	myBehaviorTable.get(sprite1.getClass()).get(sprite2.getClass())[3].execute();
    }
}
