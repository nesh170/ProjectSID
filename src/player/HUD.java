package player;

import gameEngine.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private List<Component> components = new ArrayList<>();
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
	
	public void addComponent(Component c) {
		components.add(c);
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
