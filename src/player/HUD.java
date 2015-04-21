package player;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	private Map<String, Double> myHUDMap;
	/**
	 * Constructor for a HUD that moves with a Scrollpane
	 */
	public HUD(ScrollPane pane, HashMap<String, Double> map) {
		myPane = pane;
		myHUDMap = map;
		myHUDBox = new HBox();
		mySize = 20;
		myFont = "Arial Black";
		myColor = Color.BLACK;
	}
	
	public HUD(ScrollPane pane) {
		this(pane, new HashMap<String, Double>());
	}
	
	public HUD() {
		myHUDMap = new HashMap<String, Double>();
		myHUDBox = new HBox();
		mySize = 20;
		myFont = "Arial Black";
		myColor = Color.BLACK;
	}
	
	/**
	 * Allows HUD to be moved as the screen moves
	 */
	public void updateHUDLocation(Bounds bounds) {
//		myHUDBox.setTranslateX(bounds.getMinX() + (myPane.getHvalue() * (bounds.getMaxX() - myPane.getWidth())));
//		myHUDBox.setTranslateY(bounds.getMinY() + (myPane.getVvalue() * (bounds.getMaxY() - myPane.getHeight())));
		myHUDBox.setTranslateX(-myPane.getViewportBounds().getMinX());
		myHUDBox.setTranslateY(-myPane.getViewportBounds().getMinY());
		System.out.println("boundsMinX: " + bounds.getMinX());
		System.out.println("boundsMinY: " + bounds.getMinY());
		System.out.println("ViewportMinY: " + myPane.getViewportBounds().getMinY());
		System.out.println("ViewportMinX: " + myPane.getViewportBounds().getMinX());
	}
	
	public void addItem(String item, double val) {
		Text text = new Text(item + ": " + val);
		text.setFont(Font.font(myFont, mySize));
		text.setFill(myColor);
		myHUDBox.getChildren().add(text);
		myHUDMap.put(item, val);
	}
	
	public HBox getHUDBox() {
		return myHUDBox;
	}
	
	public void changeFont(String font) {
		myFont = font;		
	}
	
	public void changeSize(double size) {
		mySize = size;
	}
	
	public void changeMap(HashMap<String, Double> map) {
		myHUDMap = map;
	}
	
	public void updateHUDDisplay() {
		myHUDBox = new HBox();
		for (Entry<String, Double> entry : myHUDMap.entrySet()) {
			addItem(entry.getKey(), entry.getValue());
		}
	}
	
}
