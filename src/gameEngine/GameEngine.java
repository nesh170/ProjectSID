package gameEngine;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;
import levelPlatform.level.LevelView;

public class GameEngine extends GameEngineAbstract {
    
    private Map<KeyCode,Action> myControlsMap;
    private List<Level> myLevelList;
    private Level myCurrentLevel;
    private LevelView myLevelRenderer;
    
    public GameEngine(List<Level> levelList) {
        myLevelList = levelList;
        initializeLevel(0);
    }
    
    public void initializeLevel(int index){
        myCurrentLevel = myLevelList.get(index);
        myControlsMap = myCurrentLevel.controlMap();
        //TODO ask Authoring env about this.....
        myLevelRenderer = new LevelView(myCurrentLevel,EditMode.EDIT_MODE_OFF);
        myCurrentLevel.prepareAllSprites();
    }
    
    @Override
    public double[] update () {
        myCurrentLevel.update();
        myLevelRenderer.updateCollisions();
        return myCurrentLevel.getNewCameraLocations();
    }

    @Override
    public Group render () {
        return myLevelRenderer.renderLevel();
    }

    /**
     * This pause method is called by the controller
     */
    @Override
    public void pause (Node scene) {
        scene.setOnKeyPressed(null);
    }

    /**
     * This method is called by the Game player when the game is played.
     * This sets up the eventhandler to the scene to call the handle method
     */
    @Override
    public void play (Node scene) {
        scene.setOnKeyPressed(keyPressed -> handle(keyPressed,action -> action.execute()));
        scene.setOnKeyReleased(keyReleased -> handle(keyReleased,action -> action.stop()));
    }
    
    /**
     * This method is a helper method for play where it takes in the keyPressed and gets a behavior from the control and executes it
     * @param keyPressed
     */
    private void handle(KeyEvent key,Consumer<Action> consumer) {
        if(myControlsMap.containsKey(key.getCode())){
            consumer.accept(myControlsMap.get(key.getCode()));
        }
    }




}
