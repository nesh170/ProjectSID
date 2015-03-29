package data;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;

public class FileParser {
	
	private XStream myXStream;
	
	public FileParser() {
		myXStream = new XStream();
	}
	
	public void toXMLFile(Object obj, String filename) throws IOException {
		Writer fWriter = new FileWriter(filename);
		myXStream.toXML(obj, fWriter);
		fWriter.close();
	}
	
	public Object fromXMLString(String xml) {
		return myXStream.fromXML(xml);
	}
	
	public Object fromXMLFile(File xml) {
		return myXStream.fromXML(xml);
	}

}
