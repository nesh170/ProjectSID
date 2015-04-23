package screen;

import resources.constants.INT;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Yongjiao
 *
 */
public class ScreenAnimation{
	/**
	 * 
	 */
	public static void expandButtonWhenMouseOver(Node n){
		//move methods to here
	}
	/**
	 * animates it a node needs to be removed from screen
	 * @param n
	 * @param destination
	 */
	public static void disposeNode(Node n, Point2D destination){
		
		
	}

	
	/**
	 * shakes popup when user did not enter game info or inapproporiate input and ask user to enter again.
	 * @param args
	 */
	public static void shakePopUpWhenError(Node popup){
		
	}
	/**
	 * This shows an visual effect of a button (ex: jumps slightly backwards) when it is pressed 
	 * when built in java.button class is not the best option or 
	 * different visual effect of mouse click is wanted
	 * @param Node 
	 */
	public static void buttonPressed(Node button){
		//move up button.setTranslateY()?
	}

	  /*Testing
		@Override
	    public void start(final Stage primaryStage) {
	        primaryStage.setTitle("Animation");
	        Group root = new Group();
	        Scene scene = new Scene(root, 400, 300, Color.WHITE);       
	        primaryStage.setScene(scene);
	        
	        Rectangle rect = new Rectangle (100, 40, 100, 100);
	        rect.setArcHeight(50);
	        rect.setArcWidth(50);
	        rect.setFill(Color.VIOLET);
	    
	        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), rect);
	        tt.setByX(200f);
	        tt.setCycleCount((int) 4f);
	        
	        root.getChildren().add(rect);
	        tt.play();
	        primaryStage.show();
	    }

	    public static void main(String[] args) {
	        Application.launch(args);
	    }
	    */
	
}
