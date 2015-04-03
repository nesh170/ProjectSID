package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DataHandler {

	private XStream myXStream;

	public DataHandler() {
		myXStream = new XStream(new DomDriver());
	}

	public void toXMLFile(Object obj, String filename, String filePath)
			throws IOException {
		Writer fWriter = new FileWriter(new File(filePath, filename));
		myXStream.toXML(obj, fWriter);
		fWriter.close();
	}

	public Object fromXMLString(String xml) {
		return myXStream.fromXML(xml);
	}

	public Object fromXMLFile(File xml) {
		return myXStream.fromXML(xml);
	}

	public File chooseDir(Stage stage) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Directory");
		return directoryChooser.showDialog(stage);
	}

	public File[] getFilesFromDir(File folder) {
		return folder.listFiles();
	}

	public BufferedImage[] getImagesFromDir(File folder) throws IOException {
		return (BufferedImage[]) Arrays
				.asList(folder.listFiles())
				.stream()
				.filter(file -> file.toString().endsWith(".jpg")
						|| file.toString().endsWith(".png")
						|| file.toString().endsWith(".tif")
						|| file.toString().endsWith(".tiff"))
				.map(file -> fileToImage(file))
				.toArray();
	}

	private BufferedImage fileToImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}