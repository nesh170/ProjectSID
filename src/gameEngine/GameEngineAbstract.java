package gameEngine;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

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
    public abstract void pause(Node scene);
    
    /**
     * This method recouples all the bindings to the keys
     */
    public abstract void play(Node Scene);

}
