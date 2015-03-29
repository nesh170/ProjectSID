package gameEngine;

import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameEngine extends GameEngineAbstract {
    
    private Map<KeyCode,Action> myControlsMap;
    private Level myCurrentLevel;
    
    
    private void initializeLevel(Level num){
        //TODO fill up when parser is done
//        myCurrentLevel = Parser.getLevel();
        myControlsMap = myCurrentLevel.getControlMap();
        
        
    }
    
    @Override
    public void update () {
        myCurrentLevel.update();
    }

    @Override
    public Group render () {
        return myCurrentLevel.render();
    }

    /**
     * This pause method is called by the controller
     */
    @Override
    public void pause (Node node) {
        node.setOnKeyPressed(null);
    }

    /**
     * This method is called by the Game player when the game is played.
     * This sets up the eventhandler to the node to call the handle method
     */
    @Override
    public void play (Node node) {
        node.setOnKeyPressed(keyPressed -> handle(keyPressed,action -> action.execute()));
        node.setOnKeyReleased(keyReleased -> handle(keyReleased,action -> action.stop()));
    }
    
    /**
     * This method is a helper method for play where it takes in the keyPressed and gets a behavior from the control and executes it
     * @param keyPressed
     */
    private void handle(KeyEvent key,Consumer<Action> consumer) {
        if(myControlsMap.containsKey(key)){
            consumer.accept(myControlsMap.get(key));
        }
    }
}
