package gameEngine.actions;

import javafx.scene.input.KeyCode;
import sprite.Sprite;
import gameEngine.Action;
import gameEngine.components.DistanceComponent;
import gameEngine.components.VelocityComponent;

public class BackAndForthAction extends Action {
    
    private double mySpeed;
    private double myCenterPos;
    private double myMaxDistance;
    private VelocityComponent myVelocityComponent;

    public BackAndForthAction (Sprite sprite, Double val, KeyCode... keys) {
        super(sprite, val);
    }

    @Override
    public void prepare () {
        DistanceComponent distance = (DistanceComponent) mySprite.getComponentOfType("DistanceComponent");
        myMaxDistance = distance.getValue();
        myVelocityComponent  = (VelocityComponent) mySprite.getComponentOfType(VEL_COMP);
    }

    @Override
    protected void doAction () {
        if(mySprite.transform().getPosX()>=myCenterPos+myMaxDistance){
            myVelocityComponent.setVelocityX(-mySpeed);
        }
        if(mySprite.transform().getPosX()>myCenterPos){
            myVelocityComponent.setVelocityX(mySpeed);
        }
        if(mySprite.transform().getPosX()<=myCenterPos-myMaxDistance){
            myVelocityComponent.setVelocityX(mySpeed);
        }
        if(mySprite.transform().getPosX()<=myCenterPos){
            myVelocityComponent.setVelocityX(-mySpeed);
        }
    }

    @Override
    public void stop () {
        // TODO Auto-generated method stub
        
    }

}
