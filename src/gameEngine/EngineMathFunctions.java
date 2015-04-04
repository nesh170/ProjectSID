package gameEngine;

public class EngineMathFunctions {

    public static final double UPDATE_RATE =  60;
    public static final double METER_TO_PIXELS = 3779.527559055;
    
    /**
     * Takes in the user defined gravity in m/s^2 and returns the gravity in our units
     * @param gravityms
     * @return gravity in pixels/(frame^2)
     */
    public static double gravityValueFrame(double gravityms){
        return (gravityms/Math.pow(UPDATE_RATE, 2))*METER_TO_PIXELS;
    }
    
    public static double velocityValueFrame(double velocityms){
        return (velocityms/UPDATE_RATE)*METER_TO_PIXELS;
    }
    
    
   
}
