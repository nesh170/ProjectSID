package util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * A util designed to take in:
 *  	- an int[][]
 *  	- size per "pixel" (double)
 *  
 *  Assumes a rectangular 2D int array
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
		
		drawARGBValuesFromArrayIntoImageUsingPixelSideLength(sourceArray, returnImage, lengthSidePixel);
		
		return returnImage;
		
	}
	
	/**
	 * Possible slight rounding with ints to doubles and back. Images will be marginally imperfect due to JavaFX's WriteableImage using integer pixels
	 * 
	 * @param sourceArray
	 * @param destinationImage
	 * @param lengthSidePixel
	 */
	private static void drawARGBValuesFromArrayIntoImageUsingPixelSideLength(int[][] sourceArray, WritableImage destinationImage, double lengthSidePixel) {
		
		PixelWriter pixelWriter = destinationImage.getPixelWriter();
		
		for (int row=0; row < destinationImage.getHeight(); row++) {
			
			for (int column=0; column < destinationImage.getWidth(); column++) {
				
				pixelWriter.setArgb(column, row, 
						getIntARGBToDrawWithArrayWithImageXWithImageYWithLengthSidePixel(sourceArray, row, column, lengthSidePixel)
						);
				
			}
			
		}
		
	}

	private static int getIntARGBToDrawWithArrayWithImageXWithImageYWithLengthSidePixel(int[][] sourceArray, int x, int y, double lengthSidePixel) {
		
		int currentXIndexToUse = roundDownToIntAfterDividingAByB(x, lengthSidePixel);
		int currentYIndexToUse = roundDownToIntAfterDividingAByB(y, lengthSidePixel);
		
		return sourceArray[currentXIndexToUse][currentYIndexToUse];
		
	}

	private static int roundDownToIntAfterMultiplying(double a, double b) {
		return (int)Math.floor(a * b);
	}
	
	private static int roundDownToIntAfterDividingAByB(double a, double b) {
		return (int)Math.floor(a / b);
	}
	
}
