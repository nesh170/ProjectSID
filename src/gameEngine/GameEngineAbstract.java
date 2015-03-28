package gameEngine;
import javafx.scene.Group;

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
     * The update method runs through each sprite and update their position and any other values based on the state of the game.
     * It also checks for collisions. 
     */
    public abstract void update();
    
    /**
     * The render method creates a new group then goes through each sprite and calls the render method to add the new nodes to the group. It
     * then returns the groups
     */
    public abstract Group render();
    
    /**
     * This method pauses the game by decoupling all the bindings to the keys
     */
    public abstract void pause();
    
    /**
     * This method recouples all the bindings to the keys
     */
    public abstract void play();

}
