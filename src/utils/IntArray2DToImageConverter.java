package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
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
	 *  	- 0->7 A
	 *  	- 8->15 R
	 *  	- 16->23 G
	 *  	- 24->31 B
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
		
		/**
		 * Makes use of util "Int2DArraySizes", spaced out for readability
		 * 
		 * @author Ruslan
		 */
		int width = roundDownToIntAfterMultiplying(
				lengthSidePixel, 
				Int2DArraySizes.width2DIntArray(sourceArray)
				);
		
		int height = roundDownToIntAfterMultiplying(
				lengthSidePixel, 
				Int2DArraySizes.height2DIntArray(sourceArray)
				);
		
		WritableImage returnImage = new WritableImage(width, height);
		
		drawARGBValuesFromArrayIntoImage(sourceArray, returnImage);
		
		return returnImage;
		
	}
	
	private static void drawARGBValuesFromArrayIntoImage(int[][] sourceArray, WritableImage destinationImage) {
		
		PixelWriter pixelWriter = destinationImage.getPixelWriter();
		
		for (int x=0; x < destinationImage.getWidth(); x++) {
			
			for (int y=0; y < destinationImage.getHeight(); y++) {
				
				pixelWriter.setArgb(x, y, getIntARGBToDraw(sourceArray, x, y));
				
			}
			
		}
		
	}

	private static int getIntARGBToDraw(int[][] sourceArray, int x, int y) {
		
		throw new IllegalStateException("unimplemented getIntARGBToDraw in IntArray2DToImageConverter");
		
	}

	private static int roundDownToIntAfterMultiplying(double a, double b) {
		return (int)Math.floor(a * b);
	}
	
}
