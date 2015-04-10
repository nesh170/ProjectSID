package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sprite.Sprite;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import levelPlatform.level.Level;

public class DataMain extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Dummy d1 = new Dummy("one", new Circle(5));
		Dummy d2 = new Dummy("two", new Circle(10));
		Dummy d3 = new Dummy("three", new Circle(15));
		
		List<Dummy> dList = new ArrayList<>();
		dList.add(d1);
		dList.add(d2);
		dList.add(d3);
				
		File f = DataHandler.chooseDir(stage);
		
		Level level = new Level(1200, 600, new Sprite());
		
		try {
			DataHandler.toXMLFile(level, "level.xml", f.toString());
		} catch (IOException e) {}
		
		File[] files = DataHandler.getFilesFromDir(f);
		
		for (File file : files) {
			System.out.println(file.toString());
		}
		
//		List<Image> images = DataHandler.getImagesFromDir(f, 800, 400, true);
		ImageView iView = new ImageView(new Image("http://mario.nintendo.com/img/mario_logo.png"));
		
		Stage s = new Stage();
		Group g = new Group();
		g.getChildren().add(iView);
		Scene scene = new Scene(g);
		s.setScene(scene);
		s.show();
	}
	
}