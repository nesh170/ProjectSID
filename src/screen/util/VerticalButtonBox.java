package screen.util;

import resources.constants.DOUBLE;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * wrapper for a series of buttons with standardized:
 *  - style 
 *  - button offsets from each other
 * 
 * @author Ruslan
 */
public class VerticalButtonBox extends VBox {

	public VerticalButtonBox() {
		
		this.setAlignment(Pos.BASELINE_CENTER);
		this.setFillWidth(false);
		this.setSpacing(DOUBLE.BUTTON_SPACING);
		this.getStyleClass().add("pane");
		
	}
	
}
