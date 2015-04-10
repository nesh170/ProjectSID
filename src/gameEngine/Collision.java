package gameEngine;


import gameEngine.components.VelocityComponent;
import resources.constants.INT;
import sprite.Sprite;

public class Collision {
    
    /**
     * The Collision Class needs to be given a map which, for each combination of Sprites, gives
     * an array of four behaviors, representing what happens as a result of the first sprite colliding
     * with the second sprite from all four sides
     */
    
    private CollisionTable collideTable;
    
    public Collision(CollisionTable table){
            this.collideTable = table;
    }

    /**
     * Takes in two sprites, determines spatial relationship, and calls appropriate method to handle Collision
     * @param sprite1
     * @param sprite2
     **/
    public void handleCollide(Sprite sprite1, Sprite sprite2){
    	Transform transform1 = sprite1.transform();
    	Transform transform2 = sprite2.transform();
    	
    	VelocityComponent velocity1 = (VelocityComponent) sprite1.getComponentOfType("VelocityComponent");
    	VelocityComponent velocity2 = (VelocityComponent) sprite2.getComponentOfType("VelocityComponent");
    	
    	Double edgeToleranceX1 = 5.0;
    	Double edgeToleranceY1 = 5.0;
    	Double edgeToleranceX2 = 5.0;
    	Double edgeToleranceY2 = 5.0;
    	
    	Double edgeToleranceX = 5.0;
    	Double edgeToleranceY = 5.0;
    	
    	if(velocity1 != null){
    		edgeToleranceX1 = velocity1.getVelocity().getX();
    		edgeToleranceY1 = velocity1.getVelocity().getY();
    	}
    	if(velocity2 != null){
    		edgeToleranceX2 = velocity2.getVelocity().getX();
    		edgeToleranceX2 = velocity2.getVelocity().getY();
    	}
    	
    	edgeToleranceX = Double.max(edgeToleranceX1, edgeToleranceX2);
    	edgeToleranceY = Double.max(edgeToleranceY1, edgeToleranceY2);
    	
        //TODO: Think of a better way to do this if possible
        if(transform1.getRightEdge() <= transform2.getPosX() + edgeToleranceX) handleSprite1(sprite1, sprite2, INT.COLLISION_LEFT);
        if(transform1.getPosX() >= transform2.getRightEdge() - edgeToleranceX) handleSprite1(sprite1, sprite2, INT.COLLISION_RIGHT);
        if(transform1.getBottomEdge() <= transform2.getPosY() + edgeToleranceY) handleSprite1(sprite1, sprite2, INT.COLLISION_UP);
        if(transform1.getPosY() >= transform2.getBottomEdge() - edgeToleranceY) handleSprite1(sprite1, sprite2, INT.COLLISION_DOWN);
    }
    
    private void handleSprite1(Sprite sprite1, Sprite sprite2, int direction) {
    	Action a = collideTable.getActionForCollisionAndDirection(sprite1.collisonTag(), sprite2.collisonTag(), direction);
    	if(a != null) a.execute();
    	System.out.println(direction);
    }
}
