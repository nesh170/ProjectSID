package application;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class SuperMarioPlatforms {

    private Scene myScene;
    private Group myRoot;
    private Group myRectangles;
    private Mario myMario;
    
    //Array of lower-bound x coordinates of platforms:
    private double[] lowX = {200.0, 100.0};
    //Array of upper-bound x coordinates of platforms:
    private double[] upX = {200.0, 300.0};
    //Array of lower-bound y coordinates of platforms:
    private double[] lowY = {200.0, 350.0};
    //Array of upper-bound y coordinates of platforms:
    private double[] upY = {300.0, 350.0};
    //Array of platform widths:
    private double[] width = {50.0, 100.0};
    //Array of booleans specifying which direction the platform is moving in:
    private boolean[] dir = {true, true};
    private int myNumPlats;
    
    
	
	public Scene init(Stage s, int width, int height){
		myRoot = new Group();
		//Generate platforms:
		//myPlatformSet = createPlatformSet();
		myNumPlats = lowX.length;
		myRectangles = makePlatforms();
		myRoot.getChildren().add(myRectangles);
		//Create mario:
		myMario = new Mario(100.0, 100.0, new Image("mario.png"));
		myRoot.getChildren().add(myMario);
		//Generate and return myScene:
		myScene = new Scene(myRoot, width, height,Color.SKYBLUE);
		myScene.setOnKeyPressed(e -> handleKeyInput(e));
		return myScene;
	}

	private Group makePlatforms() {
		Group rectangles = new Group(); 
		for(int i = 0; i<myNumPlats; i++){
			Rectangle r = new Rectangle(lowX[i], lowY[i], width[i], 20.0);
			r.setFill(Color.BEIGE);
			r.setStroke(Color.BLACK);
			rectangles.getChildren().add(r);
		}
		return rectangles;
	}
	
	public KeyFrame start(int frameRate){
		return new KeyFrame(Duration.millis(1000/frameRate), e -> updateSprites());
	}
	
	private void updateSprites(){
		//animate mario and platforms each keyframe
		movePlatforms();
		
	}
	
	private void movePlatforms(){
		int i = 0;
		for(Node n: myRectangles.getChildren()){
		Rectangle r = (Rectangle) n;	
			if(lowY[i]==upY[i]){
				//We have a side-to-side moving platform
				moveSideToSide(i, r);
		}
			else{
				moveUpDown(i, r);
			}
			i++;
		}
	}

	private void moveSideToSide(int i, Rectangle r) {
		if(r.getX()>upX[i] || r.getX()<lowX[i]){
			dir[i] = !dir[i];
}
		//make it move in the appropriate direction:
		if(dir[i]){
			r.setX(r.getX()+1.0);
		}
		else{
			r.setX(r.getX() - 1.0);
		}
	}
	
	private void moveUpDown(int i, Rectangle r) {
		if(r.getY()>upY[i] || r.getY()<lowY[i]){
			dir[i] = !dir[i];
}
		//make it move in the appropriate direction:
		if(dir[i]){
			r.setY(r.getY()+1.0);
		}
		else{
			r.setY(r.getY() - 1.0);
		}
	}
	
    private void handleKeyInput (KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.RIGHT) {
            myMario.moveRight();
        }
        else if (keyCode == KeyCode.LEFT) {
            myMario.moveLeft();
        }
        else if (keyCode == KeyCode.SPACE) {
           //jump
        }

    }

	
}
