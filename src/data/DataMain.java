package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DataMain extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		DataHandler data = new DataHandler();
		
		Dummy d1 = new Dummy("one", new Circle(5));
		Dummy d2 = new Dummy("two", new Circle(10));
		Dummy d3 = new Dummy("three", new Circle(15));
		
		List<Dummy> dList = new ArrayList<>();
		dList.add(d1);
		dList.add(d2);
		dList.add(d3);
		
		DummyHolder dummyHolder = new DummyHolder("THELIST", dList);
		
		File f = data.chooseDir(stage);
		
		try {
			data.toXMLFile(dummyHolder, "level.xml", f.toString());
		} catch (IOException e) {}
		
		File[] files = data.getFilesFromDir(f);
		
		for (File file : files) {
			System.out.println(file.toString());
		}
		
	}
	
}