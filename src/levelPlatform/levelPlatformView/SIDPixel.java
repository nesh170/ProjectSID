package levelPlatform.levelPlatformView;

import javafx.scene.layout.Region;


/**
 * The perfect JavaFX class, subclassed for the task. Implements Styleable for bounds, EventTarget for mouse interactions
 * Contained in a LevelPlatformView -> TilePane 
 * 
 * @author Ruslan
 */
public class SIDPixel extends Region {

	public SIDPixel(double lengthSIDPixel) {

		configureSnapToPixelProperty();
		configureSIDPixelSize(lengthSIDPixel);



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

	

}
