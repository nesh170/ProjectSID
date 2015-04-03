package utils;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import resources.constants.INT;

/**
* 
* @author Ruslan
* 
* Images are stored like so:
* images.length = # rows = height
* images[0].length = # columns = width
* 
* Please adhere to only setting widths to **odd** values -- centering Sprites will be much easier
* 
* A (3 X 2) image would be stored as:
* [0,0] [1,0] 
* [0,1] [1,1] 
* [0,2] [1,2] 
* 
*/

public class SpriteImage {

	// Static variables
	
	
	// Instance Variables
	private List<int[][]> images;			// Stores all 2D, pixelated images
	private int imageFrameRate;
	
	// Counters
	private int framesSinceLastUpdate;
	private int currentImageIndex;
	
	
	// Getters & Setters
	// # columns = width, assumes evenly laid-out array (which it should be)
	public int width() {
		
		if (!hasImages()) {
			return 0;
		} 
		
		else {

			int[][] firstImage = images.get(0);
			int[] firstRow = firstImage[0];
			return firstRow.length;					
			
		}
		
	}

	// # rows = height
	public int height() {
		
		if (!hasImages()) {
			return 0;
		} 
		
		else {

			int[][] firstImage = images.get(0);
			return firstImage.length;				
			
		}
		
	}
	
	public void setImageFrameRate(int imageFrameRate) {
		
		this.imageFrameRate = imageFrameRate;
		this.framesSinceLastUpdate = 0;
		
	}
	
	// Constructor & Helpers
	public SpriteImage() {
		
		imageFrameRate = INT.DEFAULT_IMAGE_FRAMERATE;
		images = new ArrayList<int[][]>();
		
	}
	
	
	// All other instance methods
	/**
	 * 
	 * @return true if SpriteImage contains at least one int[][]
	 * 
	 */
	public boolean hasImages() {
		return images.isEmpty();
	}
	
	/**
	 * 
	 * @param (int[][]) image 
	 * @return true if success adding image, false if not
	 * 
	 */
	public boolean addImage(int[][] image) {
		
		if (!hasImages()) {		// Go ahead
			
			images.add(image);
			return true;
			
		}
		
		else {					// Already has images. Check width & height before adding
			
			if (checkDimensions(image)) {
				images.add(image);
				return true;
			}
			
			else {
				return false;
			}
			
		}
		
	}
	
	public Image convertArrayToImage(int[][] image) {
		
		//TODO unimplemented
		return null;
		
	}
	
	private boolean checkDimensions(int[][] image) {
		
		int width = image[0].length;
		int height = image.length;
		
		return (width == width() && height == height());
		
	}
	
	/**
	 * 
	 * @param indexToRemove
	 * @return count of images remaining
	 * 
	 * Will always be called from SpriteEditorScreen, which will only be able to use 
	 * "indexToRemove" after checking the length. Will never exceed our length.
	 * --> no null check necessary
	 * 
	 */
	private int removeImage(int indexToRemove) {
		
		images.remove(indexToRemove);
		return images.size();
		
	}
	
	/**
	* 
	* The GameEngine is expected to simply call this method at whatever framerate it runs at. 
	* SpriteImage takes care of the rest.
	* 
	* @return int[][] if non-empty images, null if no images
	* 
	*/
	public int[][] getIntArrayToDisplay() {
		
		if (hasImages()) {
			
			adjustCounters();
			return images.get(currentImageIndex).clone();			// clone of the array to avoid modification
			
		} 
		
		else {
			return null;
		}
		
	}
	
	private void adjustCounters() {
		
		if (framesSinceLastUpdate > imageFrameRate) {
			currentImageIndex = (currentImageIndex+1) % images.size();			
		}
		
		framesSinceLastUpdate++;
		
	}
	
}
