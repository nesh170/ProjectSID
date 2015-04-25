package screen.screenmodels;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import resources.constants.DOUBLE;
import resources.constants.INT;
import sprite.Sprite;
import levelPlatform.splashScreen.SplashScreen;

public class SplashEditModel {

	private SplashScreen splashScreen;

	//private Sprite startButton = new Sprite();
	private List<Sprite> images = new ArrayList();
	private List<ImageView> imageViewArray = new ArrayList();

	private ImageView imageView;
	//private ImageView startButtonImageView;

	private Text text;
	private List<Text> texts = new ArrayList();


	public SplashEditModel(SplashScreen splashScreen) {
		this.splashScreen = splashScreen;
	}

	public void addImageView(ImageView imageView) {
		imageViewArray.add(imageView);
	}

	public void createImageView(Image image) {
		imageView = new ImageView(image);
	}
	
	public ImageView getImageView() {
		return imageView;
	}
	
	public void removeImageViewFromImageViewArray() {
		imageViewArray.remove(imageView);
	}
	
	public void imageViewMove(MouseEvent e) {
		imageView.setOnMouseReleased(f -> placeImageView(f));
	}
	
	private void placeImageView(MouseEvent e) {
		imageView.setX(e.getX());
		imageView.setY(e.getY());
	}
	
	public void placeImageViewAtXY(MouseEvent e) {
		imageView.setX(e.getX());
		imageView.setY(e.getY());
	}
	
	public void textMove(MouseEvent e) {
		text.setOnMouseReleased(f -> placeText(f));
	}
	
	private void placeText(MouseEvent e) {
		text.setX(e.getX());
		text.setY(e.getY());
	}
	
	public void placeTextAtXY(int index, MouseEvent e) {
		texts.get(index).setX(e.getX());
		texts.get(index).setY(e.getY());
	}
	
	public void createText(String string) {
		text = new Text(string);
	}
	
	public void colorText(Color color) {
		text.fillProperty().setValue(color);
	}
	
	public void fontText(ComboBox fontPicker) {
		text.setFont(Font.font(fontPicker.getValue().toString()));
	}
	
	public void addText() {
		texts.add(text);
	}
	
	public Text getText() {
		return text;
	}
	
	public void removeTextFromTextArray() {
		texts.remove(text);
	}
	
	public void addSpriteImageToSpriteList(Sprite sprite) {
		images.add(sprite);
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

//	public void createStartButtonImageView(Image image) {
//		startButtonImageView = new ImageView(image);
//	}
	
	public void resizeAndRotateImage(KeyEvent e) {
		resizeAndRotate(imageView, e);
	}
	
	public void resizeAndRotateText(int index, KeyEvent e) {
		resizeAndRotate(texts.get(index), e);
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
