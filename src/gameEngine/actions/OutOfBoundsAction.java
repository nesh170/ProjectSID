package gameEngine.actions;

import sprite.Sprite;
import gameEngine.Action;

/**
 * This action is to prevent the sprite from going too far out of bounds
 * This could be used for bullets to prevent them from going to far causing RenderIssues.
 * @author Sivaneshwaran
 *
 */
public class OutOfBoundsAction extends Action {

    private double myInitialX;
    
    public OutOfBoundsAction (Sprite sprite) {
        super(sprite);
    }
    
    public OutOfBoundsAction (Sprite sprite, Double val) {
        super(sprite, val);
    }

    @Override
    public void prepare () {
        myInitialX = mySprite.transform().getPosX();
    }

    @Override
    protected void doAction () {
        if(mySprite.transform().getPosX()-myInitialX>value){
            mySprite.setIsActive(false);
        }
    }

    @Override
    public void stop () {
        // TODO Auto-generated method stub
        
    }

}
