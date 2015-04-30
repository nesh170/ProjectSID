package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpriteGallery {

	private static final int DEFAULT_WIDTH = 350;
	private static final int DEFAULT_HEIGHT = 500;
	
	private ClickableImage mySelection;	
	@SuppressWarnings("unused")
    private String myFolderPath;
	
	public SpriteGallery(Stage primaryStage, String filePath) {
		myFolderPath = filePath;
		BorderPane bp = new BorderPane();
		ScrollPane scroll = new ScrollPane();
		TilePane tile = new TilePane();
		
		VBox vbox = new VBox(30);
		vbox.getChildren().add(createChooseButton(primaryStage));
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		
		tile.setPadding(new Insets(15, 15, 15, 15));
		tile.setHgap(15);
		
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); 
		scroll.setFitToWidth(true);
		scroll.setContent(tile);
		
		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			ClickableImage imageView = createImageView(file);
			tile.getChildren().addAll(imageView);
		}
		
		bp.setCenter(scroll);
		bp.setBottom(vbox);
		Scene scene = new Scene(bp, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		primaryStage.setScene(scene);
	}
	
	public SpriteGallery(String filePath) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		myFolderPath = filePath;
		ScrollPane scroll = new ScrollPane();
		TilePane tile = new TilePane();
		tile.setPadding(new Insets(15, 15, 15, 15));
		tile.setHgap(15);

		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); 
		scroll.setFitToWidth(true);
		scroll.setContent(tile);
		
		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			ClickableImage imageView = createImageView(file);
			tile.getChildren().addAll(imageView);
		}
		
		Scene scene = new Scene(scroll, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		stage.setScene(scene);
	}
	
	private Button createChooseButton(Stage stage) {
		Button choose = new Button("Choose");
		choose.setOnAction(event -> {			
			stage.close();
		});
		return choose;		
	}
	
	private ClickableImage createImageView(final File imageFile) {
		ImageView imageView = null;
		try {
			Image image = new Image(new FileInputStream(imageFile), 100, 0, true,
					true);
			imageView = new ImageView(image);
			imageView.setFitWidth(100);
			//add implement draggable mouseEventHandlers here
			//add ability to click image
		} catch (FileNotFoundException ex) {
			 DialogUtil.displayMessage("Error Image Creation", "Image Creation");
		}
		return new ClickableImage(imageView, this);
	}

	public void select(ClickableImage selected) {
		if (mySelection != null) {
			mySelection.deselect();
			mySelection = selected;
		}
		else {
			mySelection = selected;
		}
	}
	
	public ImageView getSelected() {
		return mySelection.getImage();
	}
}
