package data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import sprite.Sprite;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import levelPlatform.level.Level;

public class DataMain extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
				
		File f = DataHandler.chooseDir(stage);
		
		Level level = new Level(1200, 600, new Sprite());
		
		try {
			DataHandler.toXMLFile(level, "level.xml", f.toString());
		} catch (IOException e) {}
		
		File[] files = DataHandler.getFilesFromDir(f);
		
		for (File file : files) {
			System.out.println(file.toString());
		}
		
		List<Image> images = DataHandler.getImagesFromDir(f);
		Image im = new Image("http://mario.nintendo.com/img/mario_logo.png");
		ImageView iView = new ImageView(im);
		
		Stage s = new Stage();
		Group g = new Group();
		g.getChildren().add(iView);
		Scene scene = new Scene(g);
		s.setScene(scene);
		s.show();
	}
	
}