package tester.util.IntArray2DToImageConverter;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

import data.DataHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tester.Tester;

public class ImageToInt2DArrayAndBack extends Tester {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	protected void test(Stage stage) {

		welcome();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Please input the width of the int[][]: ");
		String widthString = scanner.next( );
		
		System.out.print("Please input the height of the int[][]: ");
		String heightString = scanner.next( );

		System.out.print("Please input the number of pixels per each entry in the int[][]: ");
		String pixelsString = scanner.next( );
		
		int width = Integer.parseInt(widthString);
		int height = Integer.parseInt(heightString);
		double realPixelsPerIntEntry = Double.parseDouble(pixelsString);

		getFileAndTest(stage, width, height, realPixelsPerIntEntry);

	}
	
	private void welcome() {
		
		System.out.println("------------------------------------------------");
		System.out.println("Welcome to ImageToInt2DArrayAndBack (extends Tester) 101.");
		System.out.println("------------------------------------------------");
		System.out.println("To begin, enter the desired dimensions of your int array, and then the desired");
		System.out.println("real pixels per a simulated pixel. Your resultant image's width and height are the");
		System.out.println("width(int array) * real pixels");
		System.out.println("and likewise for height.");
		System.out.println("**After you enter dimensions you will be able to select a file to use for testing**");
		System.out.println("------------------------------------------------");
		
	}

	private void getFileAndTest(Stage stage, int width, int height, double realPixelsPerIntEntry) {

		File file;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");

		file = fileChooser.showOpenDialog(stage);

		if (file != null) {

			Image image = new Image(file.toURI().toString());

			int[][] imageAs2DIntArray = util.ImageToInt2DArray.convertImageTo2DIntArray(image, height, width);

			Image readImage = util.IntArray2DToImageConverter.convert2DIntArrayToImage(imageAs2DIntArray, realPixelsPerIntEntry);

			ImageView imageView = new ImageView(readImage);

			addNode(imageView);

		}

	}

}
