package utils;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * A util designed to take in:
 *  	- an int[][]
 *  	- size per "pixel" (double)
 *  
 *  Returns:
 * 		- a pixelated JavaFX Image using the rgb values in the int
 *  
 * @author Ruslan
 */
public class IntArray2DToImageConverter {

	/**
	 *  RGB Breakdown Of Each Int:
	 *  	- 0->7 R
	 *  	- 8->15 G
	 *  	- 16->23 B
	 * 
	 * The int[][] is expected to be row-major
	 * 
	 * [0,0  0,1  0,2]
	 * [1,0  etc. etc]
	 * 
	 * 
	 * @param sourceArray (int[][])
	 * @param lengthSidePixel
	 * @return Image (JavaFX)
	 */
	public static Image convert2DIntArrayToImage(int[][] sourceArray, double lengthSidePixel) {
		return null;
	}
	
}
