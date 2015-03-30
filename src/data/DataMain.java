package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Circle;

public class DataMain {

	public static void main(String[] args) {
		FileParser data = new FileParser();
		
		Dummy d1 = new Dummy("one", new Circle(5));
		Dummy d2 = new Dummy("two", new Circle(10));
		Dummy d3 = new Dummy("three", new Circle(15));
		
		List<Dummy> dList = new ArrayList<>();
		dList.add(d1);
		dList.add(d2);
		dList.add(d3);
		
		DummyHolder dummyHolder = new DummyHolder("THELIST", dList);
		
		try {
			data.toXMLFile(dummyHolder, "level.xml");
		} catch (IOException e) {}
		
		DummyHolder dummyHolder = data.fromXML(xml, DummyHolder.class);
	}
	
}
