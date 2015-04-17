package resources;

import resources.constants.STRING;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Button styled class for Image button
 * @author anika
 *
 */
public class ImageViewButton extends Button {
	
	private Image myImageDown;
	private Image myImageSelected;
	private Image myImage;
	
	public ImageViewButton(String name, String buttonStyle, Image image, Image imageDown, Image imageSelected) {
		
	
		//this.setText(name);
		//this.setStyle(buttonStyle);
		this.setGraphic(new ImageView(image));
		this.setOnMousePressed(e -> mouseDown());
		this.setOnMouseReleased(e -> mouseUp());
		myImageDown = imageDown;
		myImageSelected = imageSelected;
		myImage = image;
		
	}
	
	/**
	 * added button styles
	 * @author Anika
	 */
	private void mouseDown() {
		//this.setStyle(STRING.PRESSED_BUTTON_CSS);
		this.setGraphic(new ImageView(myImageDown));
		
	}
	
	private void mouseUp() {
		//this.setStyle(STRING.RELEASED_BUTTON_CSS);
		this.setGraphic(new ImageView(myImage));	
	}
	
	
	
}
