package screen.screens;

import javafx.scene.image.ImageView;

public class ImageAndFilePair {
	
	private ImageView image;
	private String filePath;
	

	public ImageAndFilePair(ImageView image, String filePath) {
		this.image = image;
		this.filePath = filePath;
	}
	
	public ImageView image() {
		return image;
	}
	
	public String filePath() {
		return filePath;
	}

}
