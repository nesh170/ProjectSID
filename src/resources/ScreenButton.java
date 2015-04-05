package resources;

import resources.constants.STRING;
import javafx.scene.control.Button;

/**
 * Button styled class
 * @author anika
 *
 */
public class ScreenButton extends Button {
	
	public ScreenButton(String name, String buttonStyle) {
		
		this.setText(name);
		this.setStyle(buttonStyle);
		this.setOnMousePressed(e -> mouseDown());
		this.setOnMouseReleased(e -> mouseUp());
		
	}
	
	/**
	 * added button styles
	 * @author Anika
	 */
	private void mouseDown() {
		this.setStyle(STRING.PRESSED_BUTTON_CSS);
	}
	
	private void mouseUp() {
		this.setStyle(STRING.RELEASED_BUTTON_CSS);
	}
	
}
