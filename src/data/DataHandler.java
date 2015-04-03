package data;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DataHandler {
	
	private XStream myXStream;
	
	public DataHandler() {
		myXStream = new XStream(new DomDriver());
	}
	
	public void toXMLFile(Object obj, String filename, String filePath) throws IOException {
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

}