package levelPlatform.levelPlatformView;

import resources.constants.STRING;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;


/**
 * The perfect JavaFX class, subclassed for the task. Implements Styleable for bounds, EventTarget for mouse interactions
 * Contained in a LevelPlatformView -> TilePane 
 * 
 * @author Ruslan
 */
@Deprecated
public class SIDPixel extends Region {

	// Static Variables
	private static final boolean TESTING = false;
	
	
	// Getters & Setters
	private void setX(int x) {
	}
	
	private void setY(int y) {
	}
	
	
	// Constructor & Helpers
	public SIDPixel(int x, int y, double lengthSIDPixel) {

		setX(x);
		setY(y);
		
		configureSnapToPixelProperty();
		configureSIDPixelSize(lengthSIDPixel);
		configureHandlers();
		
		if (TESTING) {
			
			BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL);
			Border border = new Border(borderStroke);
			this.setBorder(border);
			
			this.setStyle(STRING.COLORS.FX_RED_BACKGROUND);
			
		}
		
	}

	private void configureSnapToPixelProperty() {

		// http://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/Region.html#snapToPixelProperty
		// This defaults to true, which is generally the expected behavior in order to have crisp user interfaces. 
		// A value of false will allow for fractional alignment, which may lead to "fuzzy" looking borders.
		this.snapToPixelProperty().set(false);

	}

	private void configureSIDPixelSize(double lengthSIDPixel) {

		this.setPrefWidth(lengthSIDPixel);
		this.setPrefHeight(lengthSIDPixel);

	}

	/**
	 * Reserved for mouseover events, maybe need a parent, etc.
	 * Proposal: Cast the Level object as a new interface that handles methods such as "move Sprite to x,y location." Is this clean design?
	 * 
	 * @author Ruslan
	 */
	private void configureHandlers() {
		
		// TODO homies & homettes
		
	}

}
