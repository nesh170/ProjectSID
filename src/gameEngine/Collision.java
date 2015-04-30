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
    
    private static final double DEFAULT_TOLERANCE = 5.0;
    
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
    	
    	double[] tolerances = calculateTolerances(sprite1, sprite2);
    	
        if(transform1.getRightEdge() <= transform2.getPosX() + tolerances[0]) executeAction(sprite1, sprite2, INT.COLLISION_LEFT);
        if(transform1.getPosX() >= transform2.getRightEdge() - tolerances[0]) executeAction(sprite1, sprite2, INT.COLLISION_RIGHT);
        if(transform1.getBottomEdge() <= transform2.getPosY() + tolerances[1]) executeAction(sprite1, sprite2, INT.COLLISION_UP);
        if(transform1.getPosY() >= transform2.getBottomEdge() - tolerances[1]) executeAction(sprite1, sprite2, INT.COLLISION_DOWN);
    }
    
    private void executeAction(Sprite sprite1, Sprite sprite2, int direction){
    	Action toRun = collideTable.getActionForCollisionDirectionAndSprite(sprite1.tag(), sprite2.tag(), direction, sprite1);
    	if(toRun != null) toRun.execute();
    }
    
    private double[] calculateTolerances(Sprite sprite1, Sprite sprite2){
       	VelocityComponent velocity1 = (VelocityComponent) sprite1.getComponentOfType("VelocityComponent");
    	VelocityComponent velocity2 = (VelocityComponent) sprite2.getComponentOfType("VelocityComponent");
    	double edgeToleranceX1, edgeToleranceY1, edgeToleranceX2, edgeToleranceY2;
    	edgeToleranceX1 = edgeToleranceY1 = edgeToleranceX2 = edgeToleranceY2 = DEFAULT_TOLERANCE;
    	if(velocity1 != null){
    		edgeToleranceX1 = velocity1.getVelocity().getX();
    		edgeToleranceY1 = velocity1.getVelocity().getY();
    	}
    	if(velocity2 != null){
    		edgeToleranceX2 = velocity2.getVelocity().getX();
    		edgeToleranceY2 = velocity2.getVelocity().getY();
    	}
    	double edgeToleranceX = Double.max(edgeToleranceX1, edgeToleranceX2);
    	double edgeToleranceY = Double.max(edgeToleranceY1, edgeToleranceY2);
    	return new double[] {edgeToleranceX, edgeToleranceY};
    }
    
}
