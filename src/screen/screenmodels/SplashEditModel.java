package screen.screenmodels;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import resources.constants.DOUBLE;
import resources.constants.INT;
import sprite.Sprite;
import levelPlatform.splashScreen.SplashScreen;

public class SplashEditModel {

	private SplashScreen splashScreen;

	private Sprite startButton = new Sprite();
	private List<Sprite> images = new ArrayList();
	private List<ImageView> imageViewArray = new ArrayList();

	private ImageView imageView;
	private ImageView startButtonImageView;

	private Text text;
	private List<Text> texts = new ArrayList();
	private int counter;

	public SplashEditModel(SplashScreen splashScreen) {
		this.splashScreen = splashScreen;
	}

	public void addImageView(ImageView imageView) {
		imageViewArray.add(imageView);
	}

	public void selectText(String textValue) {
		texts.stream()
		.filter(t -> t.getText().equals(textValue))
		.forEach(t -> text = t);		
	}

	public void selectImage(int index) {
		imageViewArray.stream()
		.filter(i -> !i.equals(null))
		.forEach(i -> imageView = i);
	}
	
	public void forEachText(Consumer<Text> consumer) {
		texts.forEach(consumer);
	}

	public void createStartButtonImageView(Image image) {
		startButtonImageView = new ImageView(image);
	}
	
	public void resizeAndRotateImage(KeyEvent e) {
		resizeAndRotate()
	}
	
	private void resizeAndRotate(Node node, KeyEvent e) {
		
		KeyCode keyCode = e.getCode();
		
		if(keyCode == KeyCode.UP) {
			
			node.setScaleX(DOUBLE.SPLASH_EDIT_SCALE_UP * node.getScaleX());
			node.setScaleY(DOUBLE.SPLASH_EDIT_SCALE_UP * node.getScaleY());
			
		}
		
		else if(keyCode == KeyCode.DOWN) {
			
			node.setScaleX(DOUBLE.SPLASH_EDIT_SCALE_DOWN * node.getScaleX());
			node.setScaleY(DOUBLE.SPLASH_EDIT_SCALE_DOWN * node.getScaleY());
			
		}
		
		else if(keyCode == KeyCode.RIGHT) {
			
			node.setRotate(node.getRotate() + INT.SPLASH_EDIT_ROTATE_FACTOR);
			
		}
		
		else if(keyCode == KeyCode.LEFT) {
			
			node.setRotate(node.getRotate() - INT.SPLASH_EDIT_ROTATE_FACTOR);
			
		}
		
	}
}
