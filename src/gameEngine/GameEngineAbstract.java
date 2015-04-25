package gameEngine;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
     * Returns a string of the action names with KeyCodes to be modified by the player
     * @return
     */
    public abstract List<String> actionWithKeyCode(int playerNum);
    
    /**
     * Lets you change the keycode of a specific action in the playerNumber.
     * @param playerNumber
     * @param actionName
     * @param key
     */
    public abstract void changeKeyCodeInAction(int playerNumber, String actionName, KeyCode key);
    
}