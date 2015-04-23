package screen;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.*;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransitionTest extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

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
        tt.setAutoReverse(true);
    
        tt.play();
        
        root.getChildren().add(rect);
        tt.play();
        primaryStage.show();
    }
}
