package level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import gameEngine.Transform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class LevelViewScreen {
    
    private Level myLevel;
    //private SpriteImage mySpriteImageManager;
    
    public LevelViewScreen(Level level) {
        myLevel=level;
    }
    
    public void setLevel(Level levelToSet) {
        myLevel=levelToSet;
        //TODO clear the rest of the instance
    }
    
    public Group renderLevel() {
        Group levelGroup = new Group();
        myLevel.getAllSprites().stream().forEach(sprite -> levelGroup.getChildren().add(renderSprite(sprite.path(), sprite.transform())));
        return levelGroup;
    }
    
    private Node renderSprite(String path, Transform transform) {
        Rectangle spriteNode = new Rectangle(transform.getPosX(),transform.getPosY(),transform.getWidth(),transform.getHeight());
        Paint spriteColor;
        try {
            spriteColor = Color.web(path);
        } 
        catch(IllegalArgumentException e) {
            //TODO add Ruslan's sprite image methodology
            //spriteColor = new ImagePattern(resourceManager.getImage(myColorPath));
            spriteColor = Color.BEIGE;
            
        }
        spriteNode.setFill(spriteColor);
        return spriteNode;
    }
}
