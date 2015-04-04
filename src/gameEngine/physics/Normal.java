package gameEngine.physics;

import sprite.Sprite;
import gameEngine.EngineMathFunctions;
import gameEngine.Physics;

public class Normal extends Physics {
    
    public Normal (Sprite sprite, double normalValue) {
        super(sprite, EngineMathFunctions.accelerationValueFrame(normalValue));
    }

    @Override
    public void updateByPhysics () {}


    @Override
    public void setReactionValue (double reaction) {    }

    

}
