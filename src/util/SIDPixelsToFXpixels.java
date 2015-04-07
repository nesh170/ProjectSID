package util;

import javafx.scene.Node;

public class SIDPixelsToFXpixels {
    private static final double X_CONVERT = 1;
    private static final double Y_CONVERT = 1;

    //TODO Maybe get the multiplier value from a resource bundle which the player changes
    
    /**
     * translates SID pixels to JavaFX allows resizability of the game.
     * @param node the sprite needed to render
     * @param X in SID pixels of the position of the node
     * @param Y in SID pixels of the position of the node
     */
    public static void translate(Node node,double X, double Y){
        node.setTranslateX(X_CONVERT);
        node.setTranslateY(Y_CONVERT);
    }

}


