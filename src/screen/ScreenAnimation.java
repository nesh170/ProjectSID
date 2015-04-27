package screen;

import resources.constants.INT;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 * 
 * @author Yongjiao
 *
 */
public class ScreenAnimation{
	
	/**
	 * animates popup shaking when no game info has been entered.
	 * @param args
	 */
	public static void shakePopUpWhenError(Window popup){
		Timeline t = new Timeline();
		double naturalLocation = popup.getX();
		
		System.out.println("location is "+ naturalLocation);	
		DoubleProperty x = new SimpleDoubleProperty(naturalLocation);	
		x.addListener((obs, oldX, newX) -> popup.setX(newX.doubleValue()));
		t.getKeyFrames().addAll(				
				new KeyFrame(Duration.millis(0), new KeyValue(x, naturalLocation)),
				new KeyFrame(Duration.millis(50), new KeyValue(x, naturalLocation-10)),
				new KeyFrame(Duration.millis(100), new KeyValue(x, naturalLocation+10)),
				new KeyFrame(Duration.millis(150), new KeyValue(x, naturalLocation-10)),
		    	new KeyFrame(Duration.millis(200), new KeyValue(x, naturalLocation+10)),
		    	new KeyFrame(Duration.millis(250), new KeyValue(x, naturalLocation-10)),
		    	new KeyFrame(Duration.millis(300), new KeyValue(x, naturalLocation+ 10 )),
		    	new KeyFrame(Duration.millis(350), new KeyValue(x, naturalLocation-10)),
		    	new KeyFrame(Duration.millis(400), new KeyValue(x, naturalLocation))
		);
        t.setDelay(Duration.seconds(0.2));
		t.play();
	}

	
}
