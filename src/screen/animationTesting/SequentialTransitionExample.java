package screen.animationTesting;
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

public class SequentialTransitionExample extends Application {

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
        Rectangle rect2 = new Rectangle (100, 40, 100, 100);
        rect.setArcHeight(50);
        rect.setArcWidth(50);
        rect.setFill(Color.VIOLET);
        final Duration SEC_2 = Duration.millis(2000);
        final Duration SEC_3 = Duration.millis(3000);
    
        PauseTransition pt = new PauseTransition(Duration.millis(1000));
        FadeTransition ft = new FadeTransition(SEC_3, rect2);
        ft.setFromValue(1.0f);
        ft.setToValue(0.3f);
        //ft.setCycleCount((int) 2f);
        ft.setAutoReverse(true);
        ft.setOnFinished( new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				System.out.println("First Action");
				
			}
        	
        }
        		);
        TranslateTransition tt = new TranslateTransition(SEC_2, rect);
        tt.setFromX(-100f);
        tt.setToX(100f);
       // tt.setCycleCount((int) 2f);
        tt.setAutoReverse(true);
        RotateTransition rt = new RotateTransition(SEC_3, rect);
        rt.setByAngle(180f);
       // rt.setCycleCount((int) 4f);
        rt.setAutoReverse(true);     
        ScaleTransition st = new ScaleTransition(SEC_2, rect);
        st.setByX(1.5f);
        st.setByY(1.5f);
        //st.setCycleCount((int) 2f);
        st.setAutoReverse(true);
        ParallelTransition ppt = new ParallelTransition(tt, rt, st);
        SequentialTransition seqT = new SequentialTransition ( ft, ppt);
        seqT.setOnFinished(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				System.out.println("What is wrong here???");
				
			}
        });
        root.getChildren().addAll(rect, rect2);
        seqT.play();
        primaryStage.show();
    }
}