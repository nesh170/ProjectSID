package data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
						|| file.toString().endsWith(".tiff"))
				.map(file -> fileToImage(file))
				.collect(Collectors.toList());
	}
	
	public static List<Level> getLevelsFromDir(File folder) throws IOException {
		return Arrays
				.asList(folder.listFiles())
				.stream()
				.filter(file -> file.toString().endsWith(".xml"))
				.map(file -> fromXMLFile(file))
				.map(obj -> (Level) obj)
				.collect(Collectors.toList());
	}

	public static BufferedImage fileToImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}