package util;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ClickableImage extends Button{

    private static final String SELECTED = "-fx-background-color: blue ,-fx-box-border,-fx-control-inner-background;";
    private static final String UNPRESSED_STYLE = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    
    private ImageView myImage;
    
    public ClickableImage(ImageView image, SpriteGallery parent) {
        setGraphic(image);
        myImage = image;
        this.setOnMousePressed(event -> {			
			setStyle(SELECTED);
			parent.select(this);
		});
    }
    
    public ImageView getImage() {
    	return myImage;
    }

    public void deselect() {
    	setStyle(UNPRESSED_STYLE);
    }
    
}
