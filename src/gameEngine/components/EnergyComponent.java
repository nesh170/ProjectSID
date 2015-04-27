package gameEngine.components;

import java.util.List;
import sprite.Sprite;
import gameEngine.Component;

public class EnergyComponent extends Component {
    
    private double initialEnergy;
    private double currentEnergy;

    public EnergyComponent (Sprite sprite, Double value) {
        super(sprite, value);
        initialEnergy = currentEnergy = value;
        myHUDName = "Energy";
    }

    @Override
    public void prepare () {
        
    }


    @Override
    protected void update () {
        if(currentEnergy<0){
            currentEnergy=0.0;
        }
    }
    
    public double getEnergy() {
        return currentEnergy;
    }
    
    /**     Subtract double from Energy */
    public void decrease(Double decreaseAmount){
            currentEnergy -= decreaseAmount;
    }
    
    /** Subtract int from Energy */
    public void decrease(int decreaseAmount){
            currentEnergy -= decreaseAmount;
    }
    
    /** Add double to Energy */
    public void increase(Double increaseAmount){
            currentEnergy += increaseAmount;
    }
    
    /** Add int to Energy */
    public void increase(int increaseAmount){
            currentEnergy += increaseAmount;
    }
    
    /** Increase the Energy to full */
    public void increaseToFull(){
        currentEnergy=initialEnergy;
    }

}
