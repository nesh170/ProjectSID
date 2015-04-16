package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SpriteGallery {

	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 500;
	private ImageView mySelection;
	
	private String myFolderPath;
	
	public SpriteGallery(Stage primaryStage) {
		String path = "";
		myFolderPath = path;
		ScrollPane scroll = new ScrollPane();
		TilePane tile = new TilePane();
		tile.setPadding(new Insets(15, 15, 15, 15));
		tile.setHgap(15);

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (final File file : listOfFiles) {
			ImageView imageView;
			imageView = createImageView(file);
			tile.getChildren().addAll(imageView);
		}

		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); 
		scroll.setFitToWidth(true);
		scroll.setContent(tile);

		primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		primaryStage.setHeight(Screen.getPrimary().getVisualBounds()
				.getHeight());
		Scene scene = new Scene(scroll);
		primaryStage.setScene(scene);
	}

	public SpriteGallery(Stage primaryStage, String filePath) {
		myFolderPath = filePath;
		ScrollPane scroll = new ScrollPane();
		TilePane tile = new TilePane();
		tile.setPadding(new Insets(15, 15, 15, 15));
		tile.setHgap(15);

		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			ImageView imageView;
			imageView = createImageView(file);
			tile.getChildren().addAll(imageView);
		}

		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); 
		scroll.setFitToWidth(true);
		scroll.setContent(tile);

		primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		primaryStage.setHeight(Screen.getPrimary().getVisualBounds()
				.getHeight());
		Scene scene = new Scene(scroll);
		primaryStage.setScene(scene);
	}
	
	private ImageView createImageView(final File imageFile) {
		ImageView imageView = null;
		try {
			Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
					true);
			imageView = new ImageView(image);
			imageView.setFitWidth(150);
			//add implement draggable mouseEventHandlers here
			//add ability to click image
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		return imageView;
	}

}
