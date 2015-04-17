package gameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import data.DataHandler;
import resources.constants.INT;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;
import levelPlatform.level.LevelView;

public class GameEngine extends GameEngineAbstract {
    
    private List<Map<KeyCode,Action>> myControlsMapList = new ArrayList<Map<KeyCode,Action>>();
    private List<Level> myLevelList;
    private Level myCurrentLevel;
    private LevelView myLevelRenderer;
    private static final Map<EventType<KeyEvent>, Consumer<Action>> KEY_EVENT_TO_ACTION_CONSUMER_MAP;
        static{
            KEY_EVENT_TO_ACTION_CONSUMER_MAP = new HashMap<>();
            KEY_EVENT_TO_ACTION_CONSUMER_MAP.put(KeyEvent.KEY_PRESSED, action -> action.execute());
            KEY_EVENT_TO_ACTION_CONSUMER_MAP.put(KeyEvent.KEY_RELEASED, action -> action.stop());
        }
        
    
    
    public GameEngine(List<Level> levelList) {
        myLevelList = levelList;
        initializeLevel(0);
    }
    
    @Override
    public void initializeLevel(int index){
        myCurrentLevel = myLevelList.get(index);
        myCurrentLevel.playerSpriteList().forEach(player -> myControlsMapList.add(myCurrentLevel.controlMap(player)));
        myLevelRenderer = new LevelView(myCurrentLevel,EditMode.EDIT_MODE_OFF);
        myCurrentLevel.prepareAllSprites();
        myCurrentLevel.passInitializeLevelMethod(indexForLevel -> initializeLevel(indexForLevel));
        //TODO: when switching levels, play new song. GameEngine will now have an instance of audio controller
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
    public void pause (Node node) {
        node.setOnKeyPressed(null);
    }

    /**
     * This method is called by the Game player when the game is played.
     * This sets up the eventhandler to the scene to call the handle method
     */
    @Override
    public void play (Node node) {
        node.setOnKeyPressed(keyPressed -> handleKeyEvent(keyPressed,INT.LOCAL_PLAYER));
        node.setOnKeyReleased(keyReleased -> handleKeyEvent(keyReleased,INT.LOCAL_PLAYER));
    }
    
    /**
     * This method is a helper method for play where it takes in the keyPressed executes the appropriate behavior
     * @param localPlayer 
     * @param keyPressed
     */
    @Override
    public void handleKeyEvent(KeyEvent key, int playerNumber) {
        if(myControlsMapList.get(playerNumber).containsKey(key.getCode())){
            KEY_EVENT_TO_ACTION_CONSUMER_MAP.get(key.getEventType()).accept(myControlsMapList.get(playerNumber).get(key.getCode()));
        }
    }

    @Override
    public String getCurrentLevelinXML () {
        return DataHandler.toXMLString(myCurrentLevel);
    }




}
