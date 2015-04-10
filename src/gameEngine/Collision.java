package gameEngine;


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
        //get the difference in x from center to center. positive if sprite1 is to the right
        double xDif = (sprite1.transform().getPosX() + sprite1.dimensions().getWidth()/2)
                           -  (sprite2.transform().getPosX() + sprite2.dimensions().getWidth()/2);
        //get the difference in y from center to center. positive if sprite1 is below
        double yDif = (sprite1.transform().getPosY() + sprite1.dimensions().getHeight()/2)
                           -  (sprite2.transform().getPosY() + sprite2.dimensions().getHeight()/2);
        //TODO: Think of a better way to do this if possible
        if(Math.abs(xDif)>Math.abs(yDif) && xDif<0) handleSprite1Left(sprite1, sprite2);
        if(Math.abs(xDif)>Math.abs(yDif) && xDif>0) handleSprite1Right(sprite1, sprite2);
        if(Math.abs(xDif)<Math.abs(yDif) && yDif<0) handleSprite1Up(sprite1, sprite2);
        if(Math.abs(xDif)<Math.abs(yDif) && yDif>0) handleSprite1Down(sprite1, sprite2);
    }
    
    private void handleSprite1Left(Sprite sprite1, Sprite sprite2){
        System.out.println("Collision Left");
        collideTable.getActionForCollisionAndDirection(sprite1.collisonTag(), sprite2.collisonTag(), INT.COLLISION_LEFT).get().execute();
    }
    
    private void handleSprite1Right(Sprite sprite1, Sprite sprite2){
        System.out.println("Collision Right");
        collideTable.getActionForCollisionAndDirection(sprite1.collisonTag(), sprite2.collisonTag(), INT.COLLISION_RIGHT).get().execute();
    }
    
    private void handleSprite1Up(Sprite sprite1, Sprite sprite2){
        System.out.println("Collision Up");
        collideTable.getActionForCollisionAndDirection(sprite1.collisonTag(), sprite2.collisonTag(), INT.COLLISION_UP).get().execute();
    }
    
    private void handleSprite1Down(Sprite sprite1, Sprite sprite2){
        System.out.println("Collision Down");
        collideTable.getActionForCollisionAndDirection(sprite1.collisonTag(), sprite2.collisonTag(), INT.COLLISION_DOWN).get().execute();
    }
}
