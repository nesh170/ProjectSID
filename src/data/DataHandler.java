package data;

import game.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import resources.constants.STRING;
import util.DialogUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import levelPlatform.level.Level;

public class DataHandler {

	public static final String USER_DIR = "user.dir";
	private static final String DATA_ERRTITLE = "Data Handler";
	private static final String XML_ERRMSG = "Not exactly one xml file.";
	
	private static final XStream XSTREAM = new XStream(new DomDriver());

	public static void toXMLFile(Object obj, String filename, String filePath)
			throws IOException {
		Writer fWriter = new FileWriter(new File(filePath, filename + STRING.EXT.XML));
		XSTREAM.toXML(obj, fWriter);
		fWriter.close();
	}

	public static String toXMLString(Object obj) {
		return XSTREAM.toXML(obj);
	}

	public static Object fromXMLString(String xml) {
		return XSTREAM.fromXML(xml);
	}

	public static Object fromXMLFile(File xml) {
		return XSTREAM.fromXML(xml);
	}
	public static File chooseDir(Stage stage) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty(USER_DIR)));
		directoryChooser.setTitle("Open Directory");
		return directoryChooser.showDialog(stage);
	}

	public static File chooseFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty(USER_DIR)));
		fileChooser.setTitle("Select File");
		return fileChooser.showOpenDialog(stage);
	}

	public static File[] getFilesFromDir(File folder) {
		return folder.listFiles();
	}

	public static void saveFileToDir(File folder, File file) throws IOException {
		FileWriter fWriter = new FileWriter(file);
		fWriter.close();
	}

	public static Game getTestFromFolder(File folder) throws IOException {
		List<Game> games = Arrays.asList(folder.listFiles()).stream()
				.filter(file -> file.getName().startsWith("test"))
				.map(file -> fromXMLFile(file))
				.map(obj -> Game.class.cast(obj)).collect(Collectors.toList());
		System.out.println(games.size());
		if (games.size() != 1) {
			DialogUtil.displayMessage(DATA_ERRTITLE, XML_ERRMSG);
			return null;
		} else {
			return games.get(0);
		}
	}

	public static Game getGameFromDir(File folder) throws IOException {
		List<Game> games = Arrays.asList(folder.listFiles()).stream()
				.filter(file -> file.toString().endsWith(".xml"))
				.map(file -> fromXMLFile(file))
				.map(obj -> Game.class.cast(obj)).collect(Collectors.toList());
		if (games.size() != 1) {
			DialogUtil.displayMessage(DATA_ERRTITLE, XML_ERRMSG);
			return null;
		} else {
			return games.get(0);
		}
	}

	public static List<File> getDirsFromDir(File folder) throws IOException {
		return Arrays.asList(folder.listFiles()).stream()
				.filter(file -> file.isDirectory())
				.collect(Collectors.toList());
	}

	public static String getGameName(File folder) throws IOException {
		List<File> gameFiles = (List<File>) Arrays.asList(folder.listFiles()).stream()
				.filter(file -> file.toString().endsWith(".xml"))
				.collect(Collectors.toList());
		if (gameFiles.size() != 1) {
			DialogUtil.displayMessage(DATA_ERRTITLE, XML_ERRMSG);
			return null;
		} else {
			return gameFiles.get(0).getName();
		}
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
						.map(file -> fileToImage(file))
						.collect(Collectors.toList());
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

	public static Media getVideoFromDir(File folder) {
		List<Media> videoFiles = Arrays.asList(folder.listFiles()).stream()
				.filter(file -> file.toString().endsWith(".flv")
						|| file.toString().endsWith(".mp4"))
						.map(file -> new Media(file.toURI().toString()))
						.collect(Collectors.toList());

		if (videoFiles.size() == 0) {
			return null;
		} else {
			return videoFiles.get(0);
		}
	}

	public static Media getAudioFromDir(File folder) {
		List<Media> videoFiles = Arrays.asList(folder.listFiles()).stream()
				.filter(file -> file.toString().endsWith(".mp3")
						|| file.toString().endsWith(".m4a"))
						.map(file -> new Media(file.toURI().toString()))
						.collect(Collectors.toList());

		if (videoFiles.size() == 0) {
			return null;
		} else {
			return videoFiles.get(0);
		}
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