package sprite.spriteImage;

import java.util.ArrayList;
import java.util.List;

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
		
		if (images.get(0)  == null) {
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
		
		if (images.get(0)  == null) {
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
	public boolean hasImages() {
		return images.isEmpty();
	}
	
	/**
	* 
	* The GameEngine is expected to simply call this method at whatever framerate it runs at. 
	* SpriteImage takes care of the rest.
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
