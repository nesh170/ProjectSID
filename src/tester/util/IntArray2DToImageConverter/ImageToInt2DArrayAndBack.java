package tester.util.IntArray2DToImageConverter;

import java.io.File;

import data.DataHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tester.Tester;

public class ImageToInt2DArrayAndBack extends Tester {

	public static void main(String[] args) {
        launch(args);
    }
	
	@Override
	protected void test(Stage stage) {
		
		File file = DataHandler.chooseFile(stage);
		Image image = DataHandler.fileToImage(file);
		
		int[][] imageAs2DIntArray = util.ImageToInt2DArray.convertImageTo2DIntArray(image, 25, 25);
		
		Image readImage = util.IntArray2DToImageConverter.convert2DIntArrayToImage(imageAs2DIntArray, 16.0);

		ImageView imageView = new ImageView(readImage);
		
		addNode(imageView);
		
	}

}
