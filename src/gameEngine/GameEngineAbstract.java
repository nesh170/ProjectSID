package gameEngine;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

/**
 * The GameEngine class is the main class of the game engine. It initializes all the sprites from the XML. It also has an
 * update and render method to update the pbjects and render them on screen. The pause and play method is called when the player
 * pauses and plays the game respectively.
 * 
 * GameEngine takes in the location of the file where all the XML are stored and the Group() to display the objects
 * @author Sivaneshwaran
 *
 */
public abstract class GameEngineAbstract {
    
    /**
     * This method sets up the new level.
     * @param index new level number
     */
    public abstract void initializeLevel(int index);
    
    /**
     * The update method runs through each sprite and update their position and any other values based on the state of the game.
     * It also checks for collisions. It also returns the X,Y Camera position.
     */
    public abstract double[] update();
    
    /**
     * The render method creates a new group then calls on the LevelViewScreen
     */
    public abstract Group render();
    
    /**
     * This method pauses the game by decoupling all the bindings to the keys
     */
    public abstract void pause(Node node);
    
    /**
     * This method recouples all the bindings to the keys
     */
    public abstract void play(Node node);

    /**
     * This method is a helper method for play where it takes in the keyPressed executes the appropriate behavior
     * @param localPlayer 
     * @param keyEventType
     * @param keyCode
     */
    public abstract void handleKeyEvent(String keyEventType, String keyCode, int playerNumber);
    
    /**
     * Uses The dataHandler class to convert the current level into a String
     * @return the output of XStream from the current level
     */
    public abstract String getCurrentLevelinXML();
    
    /**
     * Adds the groovy action to the level. The groovy action is user defined based on the sprite.
     * @param groovy
     */
    public abstract void addGroovyAction (String spriteTag, Action groovyAction);
    
    /**
     * Adds the groovy component to the level. The groovy component is user defined based on the sprite
     * @param spriteTag
     * @param groovyComponent
     */
    public abstract void addGroovyComponent (String spriteTag, Component groovyComponent);
    
    /**
     * Gets a list of sprite tags to be shown on the player
     * @return
     */
    public abstract List<String> getSpriteTagList();
    
    /**
     * Gives you a consumer so you can change the specific keycode of theat playerNumber
     * @param playerNumber
     * @return a Map of the action name and the consumer for you to change it
     */
    public abstract Map<String, Consumer<KeyCode>> getActionToChangeKeyCodeConsumerMap (int playerNumber);
    
    /**
     * 
     * @return a map of the components that needs to be displayed
     */
    public abstract Map<String, Double> getHUDMap();

    /**
     * Gets the map of string to keycode
     * @param playerNumber
     * @return
     */
    public abstract Map<String, KeyCode> getActionKeyCodeMap (int playerNumber);


    
}