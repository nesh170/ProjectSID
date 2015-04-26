package screen.screenmodels;

import javafx.geometry.Point2D;
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

/**
 * Model to Call Methods for Splash Edit Screen
 * 
 * @author Kyle Milden
 * @author Michael Mendelsohn
 *
 */
public class SplashEditModel {

	private SplashScreen splashScreen;
	private List<Sprite> images = new ArrayList();
	private List<ImageView> imageViewArray = new ArrayList();
	private List<Sprite> spriteList = new ArrayList();
	private ImageView imageView;
	private Text text;
	private List<Text> texts = new ArrayList();

	/**
	 * Constructor for Splash Edit Model
	 * 
	 * @param splashScreen
	 */
	public SplashEditModel(SplashScreen splashScreen) {
		this.splashScreen = splashScreen;
	}
	
	/**
	 * get method to obtain the Splash Screen
	 * 
	 * @return SplashScreen
	 */
	public SplashScreen getSplashScreen() {
		return splashScreen;
	}
	
	/**
	 * save the Splash Screen
	 */
	public void saveSplashScreen() {
		splashScreen.addSprites(spriteList);
	}

	/**
	 * add an imageview to the array of images
	 */
	public void addImageView() {
		imageViewArray.add(imageView);
		spriteList.add(new Sprite(new Point2D(imageView.getX(), imageView.getY())));
	}

	/**
	 * creates an imageview from an image
	 * 
	 * @param image
	 */
	public void createImageView(Image image) {
		imageView = new ImageView(image);
	}
	
	/**
	 * get the active imageview
	 * 
	 * @return ImageView
	 */
	public ImageView getImageView() {
		return imageView;
	}
	
	/**
	 * removes an imageview from the list
	 */
	public void removeImageViewFromImageViewArray() {
		imageViewArray.remove(imageView);
	}
	
	/**
	 * used to move an image
	 * 
	 * @param e
	 */
	public void imageViewMove(MouseEvent e) {
		imageView.setOnMouseReleased(f -> placeImageView(f));
	}
	
	/**
	 * used to place an image
	 * 
	 * @param e
	 */
	public void placeImageView(MouseEvent e) {
		imageView.setX(e.getSceneX());
		imageView.setY(e.getSceneY());
	}

	/**
	 * used to move text
	 * 
	 * @param e
	 */
	public void textMove(MouseEvent e) {
		text.setOnMouseReleased(f -> placeText(f));
	}
	
	private void placeText(MouseEvent e) {
		text.setX(e.getSceneX());
		text.setY(e.getSceneY());
	}
	
	/**
	 * used to place text
	 * 
	 * @param index
	 * @param e
	 */
	public void placeTextAtXY(int index, MouseEvent e) {
		texts.get(index).setX(e.getSceneX());
		texts.get(index).setY(e.getSceneY());
	}
	
	/**
	 * used to create a new text object from a string
	 * 
	 * @param string
	 */
	public void createText(String string) {
		text = new Text(string);
	}
	
	/**
	 * used to add color to text
	 * 
	 * @param color
	 */
	public void colorText(Color color) {
		text.fillProperty().setValue(color);
	}
	
	/**
	 * used to add font to a text
	 * 
	 * @param fontPicker, ComboBox
	 */
	public void fontText(ComboBox fontPicker) {
		text.setFont(Font.font(fontPicker.getValue().toString()));
	}
	
	/**
	 * used to add text
	 */
	public void addText() {
		texts.add(text);
		Sprite sprite = new Sprite(new Point2D(text.getX(), text.getY()));
		sprite.setName(text.getText());
		sprite.setIsText(true);
		spriteList.add(sprite);
	}
	
	/**
	 * used to get the active text
	 * 
	 * @return Text
	 */
	public Text getText() {
		return text;
	}
	
	/**
	 * used to get the list of texts
	 * 
	 * @return List<Text>
	 */
	public List<Text> getTexts() {
		return texts;
	}
	
	/**
	 * used to remove text from the list of text
	 */
	public void removeTextFromTextArray() {
		texts.remove(text);
	}
	
	/**
	 * used to add a sprite to the list of sprites
	 * 
	 * @param sprite
	 */
	public void addSpriteImageToSpriteList(Sprite sprite) {
		images.add(sprite);
	}
	
	/**
	 * used to select a certain text
	 * 
	 * @param textValue
	 */
	public void selectText(String textValue) {
		texts.stream().filter(t -> t.getText().equals(textValue)).forEach(t -> text = t);	
	}

	/**
	 * used to select a certain image
	 * 
	 * @param index
	 * @param counter
	 */
	public void selectImage(int index, int counter) {	
		imageView = imageViewArray.get(index);
	}
	
	/**
	 * used to resize and rotate an image
	 * 
	 * @param e
	 */
	public void resizeAndRotateImage(KeyEvent e) {
		resizeAndRotate(imageView, e);
	}
	
	/**
	 * used to resize and rotate text
	 * 
	 * @param index
	 * @param e
	 */
	public void resizeAndRotateText(int index, KeyEvent e) {
		resizeAndRotate(texts.get(index), e);
	}
	
	/**
	 * used to get the entire list of images
	 * 
	 * @return
	 */
	public List<ImageView> getImageViewArray() {
		return imageViewArray;
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