package data;

import game.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import levelPlatform.level.Level;

public class DataHandler {

	private static final XStream XSTREAM = new XStream(new DomDriver());

	public static void toXMLFile(Object obj, String filename, String filePath)
			throws IOException {
		Writer fWriter = new FileWriter(new File(filePath, filename));
		XSTREAM.toXML(obj, fWriter);
		fWriter.close();
	}

	public static Object fromXMLString(String xml) {
		return XSTREAM.fromXML(xml);
	}

	public static Object fromXMLFile(File xml) {
		return XSTREAM.fromXML(xml);
	}

	public static File chooseDir(Stage stage) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Directory");
		return directoryChooser.showDialog(stage);
	}

	public static File chooseFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		return fileChooser.showOpenDialog(stage);
	}

	public static File[] getFilesFromDir(File folder) {
		return folder.listFiles();
	}

	public static List<Image> getImagesFromDir(File folder) throws IOException {
		return Arrays
				.asList(folder.listFiles())
				.stream()
				.filter(file -> file.toString().endsWith(".jpg")
						|| file.toString().endsWith(".png")
						|| file.toString().endsWith(".tif")
						|| file.toString().endsWith(".tiff")
						|| file.toString().endsWith(".gif"))
				.map(file -> fileToImage(file)).collect(Collectors.toList());
	}

	public static List<Image> getImagesFromDir(File folder, double maxWidth,
			double maxHeight, boolean preserve) throws IOException {
		return Arrays
				.asList(folder.listFiles())
				.stream()
				.filter(file -> file.toString().endsWith(".jpg")
						|| file.toString().endsWith(".png")
						|| file.toString().endsWith(".tif")
						|| file.toString().endsWith(".tiff")
						|| file.toString().endsWith(".gif"))
				.map(file -> fileToImage(file, maxWidth, maxHeight, preserve))
				.collect(Collectors.toList());
	}

	public static List<Level> getLevelsFromDir(File folder) throws IOException {
		return Arrays.asList(folder.listFiles()).stream()
				.filter(file -> file.toString().endsWith(".xml"))
				.map(file -> fromXMLFile(file))
				.map(obj -> Level.class.cast(obj)).collect(Collectors.toList());
	}

	public static Image URLToImage(String url) {
		try {
			URL checkValidURL = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println(url + " is not valid.");
		}
		return new Image(url);
	}

	public static Image fileToImage(File file) {
		return new Image(file.toURI().toString());
	}

	/**
	 * Call this function to obtain a JavaFX Image object from an image file.
	 * This specific method allows you to specify the maximum width and height
	 * of the new Image object. If the preserve input is true, the file's image
	 * ratio is preserved, and kept within the bounds of the box. If preserve is
	 * false, then the image simply has a width of maxWidth, and a height of
	 * maxHeight.
	 * 
	 * @param file
	 *            The image file that represents the image you want to convert
	 *            to JavaFX.
	 * @param maxWidth
	 *            The maximum width of the JavaFX image.
	 * @param maxHeight
	 *            The maximum height of the JavaFX image.
	 * @param preserve
	 *            whether the original file's aspect ratio is fixed.
	 * @return a JavaFX Image object.
	 */
	public static Image fileToImage(File file, double maxWidth,
			double maxHeight, boolean preserve) {
		return new Image(file.toURI().toString(), maxWidth, maxHeight,
				preserve, true);
	}

}