package player;

import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HUD {

	private ScrollPane myPane;
	private HBox myHUDBox;
	private String myFont;
	private double mySize;
	private Color myColor;
	/**
	 * Constructor for a HUD that moves with a Scrollpane
	 */
	public HUD(ScrollPane pane) {
		myPane = pane;
		myHUDBox = new HBox();
		mySize = 20;
		myFont = "Arial Black";
		myColor = Color.BLACK;
	}
	
	/**
	 * Allows HUD to be moved as the screen moves
	 */
	public void updateHUDLocation(Bounds bounds) {
		myHUDBox.setTranslateX(myPane.getHvalue() * (bounds.getMaxX() - myPane.getWidth()));
		myHUDBox.setTranslateY(myPane.getVvalue() * (bounds.getMaxY() - myPane.getHeight()));
	}
	
	public void addItem(String item) {
		Text text = new Text(item + ":");
		text.setFont(Font.font(myFont, mySize));
		text.setFill(myColor);
		myHUDBox.getChildren().add(text);
	}
	
	public HBox getHUDBox() {
		return myHUDBox;
	}
	
}
