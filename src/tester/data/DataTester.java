package tester.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import data.DataHandler;
import sprite.Sprite;
import tester.Tester;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import levelPlatform.level.Level;

public class DataTester extends Tester {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	protected void test(Stage stage) {

		File f = DataHandler.chooseDir(stage);

		Level level = new Level(1200, 600,new ArrayList<Sprite>());

		try {
			DataHandler.toXMLFile(level, "level.xml", f.toString());
		} catch (IOException e) {}

		File[] files = DataHandler.getFilesFromDir(f);

		for (File file : files) {
			System.out.println(file.toString());
		}

		try {
			List<Image> images = DataHandler.getImagesFromDir(f);
		} catch (IOException e) {}
		
		Image im = new Image("http://mario.nintendo.com/img/mario_logo.png");
		ImageView iView = new ImageView(im);

		this.addNode(iView);

	}

}