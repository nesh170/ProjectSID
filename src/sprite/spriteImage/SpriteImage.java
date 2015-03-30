package sprite.spriteImage;

import java.util.ArrayList;
import java.util.List;

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
	List<int[][]> images;

	
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
	
	// Constructor & Helpers
	public SpriteImage() {
		
		images = new ArrayList<int[][]>();
		
	}
	
	// All other instance methods
	
	
}
