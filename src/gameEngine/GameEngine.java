package gameEngine;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import platform.level.Level;
import platform.level.LevelViewScreen;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameEngine extends GameEngineAbstract {
    
    private Map<KeyCode,Action> myControlsMap;
    private List<Level> myLevelList;
    private Level myCurrentLevel;
    private LevelViewScreen myLevelRenderer;
    
    public GameEngine(List<Level> levelList) {
        myLevelList = levelList;
    }
    
    private void initializeLevel(int index){
        myCurrentLevel = myLevelList.get(index);
        myControlsMap = myCurrentLevel.getControlMap();
        myLevelRenderer.setLevel(myCurrentLevel);    
    }
    
    @Override
    public void update () {
        myCurrentLevel.update();
        myLevelRenderer.updateCollisions();
    }

    @Override
    public Group render () {
        return myLevelRenderer.renderLevel();
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
