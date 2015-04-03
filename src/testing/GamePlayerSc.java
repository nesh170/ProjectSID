package testing;

import java.util.ArrayList;
import java.util.List;
import sprite.Sprite;
import gameEngine.GameEngine;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import level.Level;

public class GamePlayerSc extends Application {
    
    private Group myGroup;
    private ScrollPane myScrollPane;
    private GameEngine myGameEngine;
    private int myCounter = 0;

    @Override
    public void start (Stage s) throws Exception {
        Group groupView = new Group();
        setUpScrollPane();
        setUpEngine();
        groupView.getChildren().add(myScrollPane);
        s.setScene(new Scene(groupView, 1366, 768));
        start(60);
        s.show();
    }

    private void setUpEngine () {
        Sprite playerSprite = new Sprite(new Point2D(710, 710), "");
        List<Sprite> array = new ArrayList<Sprite>();
        Level firstLevel = new Level(array, playerSprite);
        List<Level> levelList = new ArrayList<>();
        levelList.add(firstLevel);
        myGameEngine = new GameEngine(levelList);
        
        
    }

    private void setUpScrollPane(){
        myScrollPane = new ScrollPane();
        myScrollPane.setMaxSize(1366, 768);
        myScrollPane.setContent(myGroup);
        
    }
    
    
    private KeyFrame start(int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate),
                        e -> callEngine(e));
}
    
    private void callEngine (ActionEvent e) {
        myGameEngine.update();
        if(myCounter==30){
            myGroup.getChildren().clear();
            myGroup.getChildren().add(myGameEngine.render());
            myCounter=0;
        }
        myCounter++;
    }

    
    public static void Main(String[] args){
        launch(args);
    }
}
