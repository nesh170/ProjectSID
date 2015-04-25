package sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.constants.DOUBLE;
import resources.constants.INT;
import util.Int2DArraySizes;
import util.IntArray2DToImageConverter;
import util.SilentFailArrayList;

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
	private transient Optional<List<ImageView>> imageViews = Optional.empty();
	
	private List<int[][]> images;			// Stores all 2D, pixelated images
	private int imageFrameRate;
	
	// Counters
	private int framesSinceLastUpdate;
	private int currentImageIndex;
	
	
	// Getters & Setters
	// # columns = width, assumes evenly laid-out array (which it should be)
	/**
	 * @author Ruslan
	 * 
	 * One of the design decisions here was to either surround these in "if (hasImages()) return, else return 0"
	 * or to subclass a List and have it silently fail. We write a util that handles a null int[][] being passed in,
	 * and avoid duplicated code. :) Same spiel for height()
	 */
	public int width() {
		return Int2DArraySizes.width2DIntArray(images.get(0));
	}

	// # rows = height
	public int height() {
		return Int2DArraySizes.height2DIntArray(images.get(0));
	}
	
	public void setImageFrameRate(int imageFrameRate) {
		
		this.imageFrameRate = imageFrameRate;
		this.framesSinceLastUpdate = 0;
		
	}
	
	
	// Constructor & Helpers
	public SpriteImage() {
		
		setImageFrameRate(INT.DEFAULT_IMAGE_FRAMERATE);
		instantiateImagesList();
		instantiateImageViewsFromImagesList();
		
	}
	
	public SpriteImage(SpriteImage spriteImage) {
		this();
		spriteImage.images.forEach(image -> this.addImage(image));
	}
	
	private void instantiateImagesList() {
		this.images = new SilentFailArrayList<int[][]>();
	}
	
	private void instantiateImageViewsFromImagesList() {
		
		this.imageViews = Optional.of(new SilentFailArrayList<ImageView>());
		
		for (int[][] image2DArray : images) {
			
			Image image = IntArray2DToImageConverter.convert2DIntArrayToImage(image2DArray, DOUBLE.DEFAULT_LENGTH_SIDE_PIXEL);
			ImageView imageView = new ImageView(image);
			
			imageViews.get().add(imageView);
			
		}
		
	}
	
	// All other instance methods
	/**
	 * Cover the case where we have an Empty Optional for transient imageViews list.. Just read from XML
	 * 
	 * @author Ruslan
	 */
	public void checkForJustReadFromXML() {
		
		if (!imageViews.isPresent()) {
			instantiateImageViewsFromImagesList();
		}
		
	}
	
	 /** No need for if(images == null), "images" is always instantiated in the constructor. 
	 * 
	 * @return true if SpriteImage contains at least one int[][]
	 */
	public boolean hasImages() {
		return !images.isEmpty();
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
			instantiateImageViewsFromImagesList();
			return true;
			
		}
		
		else {					// Already has images. Check width & height before adding
			
			if (checkDimensions(image)) {
				
				images.add(image);
				instantiateImageViewsFromImagesList();
				return true;
				
			}
			
			else {
				return false;
			}
			
		}
		
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
	public int removeImage(int indexToRemove) {
		
		images.remove(indexToRemove);
		instantiateImageViewsFromImagesList();
		
		return images.size();
		
	}
	
	/**
	* @deprecated
	* 
	* The GameEngine is expected to simply call this method at whatever framerate it runs at. 
	* SpriteImage takes care of the rest.
	* 
	* @param (double) length of a side of each real java pixel
	* @return Image if non-empty images, null if no images
	* 
	*/
	public Image getImageToDisplay(double lengthSidePixel) {
		
		Callable getImageToDisplay = () -> {
			
			int[][] sourceArray = images.get(currentImageIndex);

			return IntArray2DToImageConverter.convert2DIntArrayToImage(sourceArray, lengthSidePixel); 
			
		};
		
		return (Image)getImageOrImageViewToDisplayWrapper(getImageToDisplay);
		
	}
	
	/**
	* The GameEngine is expected to simply call this method at whatever framerate it runs at. 
	* SpriteImage takes care of the rest. Note, Sprite should be instantiated with the size of each SID Pixel length
	* 
	* @return ImageView if non-empty images, null if no images
	* 
	*/
	public ImageView getImageViewToDisplay() {
		
		// Would normally need to add for checks if just read from XML, but the SpriteImage getter already called it here. Assume non-null imageViews
		Callable getImageViewToDisplay = () -> {
		
			return imageViews.get().get(currentImageIndex);
			
		};
		
		return (ImageView)getImageOrImageViewToDisplayWrapper(getImageViewToDisplay);
		
	}
	
	/**
	 * Method designed to eliminate duplicated code of checking for an empty list using callables
	 * 
	 * @param Callable
	 * @return Object castable to specific Callable you pass in
	 */
	private Object getImageOrImageViewToDisplayWrapper(Callable callable) {

		Object returnObject = null;
		
		if (hasImages()) {
			
			adjustCounters();
			
			try {
				returnObject = callable.call();
			} catch (Exception e) {}
			
		} 

		return returnObject;

	}
	
	private void adjustCounters() {
		
		if (framesSinceLastUpdate > imageFrameRate) {
			currentImageIndex = (currentImageIndex+1) % images.size();			
		}
		
		framesSinceLastUpdate++;
		
	}

	
}
