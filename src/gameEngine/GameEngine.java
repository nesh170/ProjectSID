package gameEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
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
import levelPlatform.splashScreen.SplashScreen;

public class GameEngine extends GameEngineAbstract {
    
    private List<Map<KeyCode,Action>> myControlsMapList = new ArrayList<Map<KeyCode,Action>>();
    private List<Level> myLevelList;
    private Level myCurrentLevel;
    private LevelView myLevelRenderer;
    private static final Map<String, Consumer<Action>> KEY_EVENT_TO_ACTION_CONSUMER_MAP;
        static{
            KEY_EVENT_TO_ACTION_CONSUMER_MAP = new HashMap<>();
            KEY_EVENT_TO_ACTION_CONSUMER_MAP.put(KeyEvent.KEY_PRESSED.getName(), action -> action.execute());
            KEY_EVENT_TO_ACTION_CONSUMER_MAP.put(KeyEvent.KEY_RELEASED.getName(), action -> action.stop());
        }
        
    
    
    public GameEngine(SplashScreen splashLevel, List<Level> levelList) {
        myLevelList = levelList;
        if(splashLevel!=null){
            myLevelList.add(0, splashLevel);
        }
        initializeLevel(1);
    }

    @Override
    public void initializeLevel(int index){
        myCurrentLevel = myLevelList.get(index);
        myLevelRenderer = new LevelView(myCurrentLevel,EditMode.EDIT_MODE_OFF);
        myCurrentLevel.prepareAllSprites();
        myCurrentLevel.passInitializeLevelMethod(indexForLevel -> initializeLevel(indexForLevel));
        myCurrentLevel.playerSpriteList().forEach(player -> myControlsMapList.add(myCurrentLevel.controlMap(player)));
        //TODO: when switching levels, play new song. GameEngine will now have an instance of audio controller
    }
    
    @Override
    public double[] update () {
        myCurrentLevel.update();
        myLevelRenderer.updateCollisions();
        myControlsMapList.clear();
        myCurrentLevel.playerSpriteList().forEach(player -> myControlsMapList.add(myCurrentLevel.controlMap(player)));
        return myCurrentLevel.getNewCameraLocations();
    }

    @Override
    public Group render () {
    	System.out.println("GameEngine.render");
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
        node.setOnKeyPressed(keyPressed -> handleKeyEvent(keyPressed.getEventType().getName(),keyPressed.getCode().getName(),INT.LOCAL_PLAYER));
        node.setOnKeyReleased(keyReleased -> handleKeyEvent(keyReleased.getEventType().getName(),keyReleased.getCode().getName(),INT.LOCAL_PLAYER));
    }
    
    /**
     * This method is a helper method for play where it takes in the keyPressed executes the appropriate behavior
     * @param localPlayer 
     * @param keyEventType
     * @param keyCode
     */
    @Override
    public void handleKeyEvent(String keyEventType, String keyCode, int playerNumber) {
        KeyCode key = KeyCode.getKeyCode(keyCode);
        if(myControlsMapList.get(playerNumber).containsKey(key)){
            KEY_EVENT_TO_ACTION_CONSUMER_MAP.get(keyEventType).accept(myControlsMapList.get(playerNumber).get(key));
        }
    }

    @Override
    public String getCurrentLevelinXML () {
        return DataHandler.toXMLString(myCurrentLevel);
    }

    @Override
    public void addGroovyAction (String spriteTag, Action groovyAction) {
      myCurrentLevel.addGroovyAction(spriteTag,groovyAction);
    }

    @Override
    public void addGroovyComponent (String spriteTag, Component groovyComponent) {
        myCurrentLevel.addGroovyComponent(spriteTag,groovyComponent);
    }

    @Override
    public List<String> getSpriteTagList () {
        Set<String> spriteTagSet = new HashSet<>();
        myCurrentLevel.sprites().stream().forEach(sprite -> spriteTagSet.add(sprite.tag()));
        List<String> spriteTagList = spriteTagSet.stream().collect(Collectors.toList());
        return Collections.unmodifiableList(spriteTagList);
    }
    
    public List<String> actionWithKeyCode (int playerNum) {
        return myCurrentLevel.getActionListInStrings(playerNum);
    }

    @Override
    public void changeKeyCodeInAction (int playerNumber, String actionName, KeyCode key) {
        myCurrentLevel.setKeyCodeToPlayer(playerNumber, actionName, key);
    }

    public List<Component> getDefaultHUDComponents(){
    	List<Component> components = new ArrayList<Component>();
    	for(String type: new String[]{"Ammo","Energy","Health","Life","Time"}){
    		components.add(myCurrentLevel.playerSpriteList().get(0).getComponentOfType(type + "Component"));
    	}
    	return components;
    }


}
