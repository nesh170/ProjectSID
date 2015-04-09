package gameEngine;

public class EngineMathFunctions {

    //TODO update the update rate to get it from the game player class
    public static final double UPDATE_RATE =  60;
    public static final double METER_TO_PIXELS = 3779.527559055*Math.pow(10, -2);
    
    /**
     * Takes in the user defined acceleration in m/s^2 and returns the acceleration in our units
     * @param accelerationms in m/s^2
     * @return acceleration in pixels/(frame^2)
     */
    public static double accelerationValueFrame(double accelerationms){
        return (accelerationms/Math.pow(UPDATE_RATE, 2))*METER_TO_PIXELS;
    }
    
    /**
     * Takes in the user defined velocity in m/s and returns the velocity in our units
     * @param velocity in m/s^2
     * @return velocity in pixels/(frame^2)
     */
    public static double velocityValueFrame(double velocityms){
        return (velocityms/UPDATE_RATE)*METER_TO_PIXELS;
    }
    
    
   
}
