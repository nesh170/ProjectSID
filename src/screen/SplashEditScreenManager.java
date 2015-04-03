package screen;

import java.io.File;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import screen.splashEditScreen.SplashEditScreenController;

// Inner class for handling SplashEditScreenController methods
public class SplashEditScreenManager extends UniversalManager implements SplashEditScreenController {


	Stage stage;

	public SplashEditScreenManager(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void returnToGameEditScreen() {
		throw new IllegalStateException("unimplemented returnToGameEditScreen in SplashEditScreenController");
	}

	@Override
	public void addStartButton() {
		File file = null;
		Image image = null;

		try {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

			file = fileChooser.showOpenDialog(null);
			image = new Image(file.toURI().toString(), 30.0, 30.0, false, false);	

		} catch (Exception ex) {	
			//LOAD STRING.DEFAULT_START_BUTTON
		}

		ImageCursor imageCursor = new ImageCursor(image);
		stage.getScene().setCursor(imageCursor);

	}

	@Override
	public void addImage() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addText() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addAnimation() {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveSplashScreen() {
		// TODO Auto-generated method stub
	}

	@Override
	public void trashSplashScreen() {
		// TODO Auto-generated method stub
	}

	@Override
	public void backSplashScreen() {
		// TODO Auto-generated method stub
	}
	
}
