package player;

import gameEngine.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HUD {

	private HBox myHUDBox;
	private String myFont;
	private double mySize;
	private Color myColor;
	private Map<String, Component> myComponentMap;
	private Map<String, Double> myHUDMap;
	/**
	 * Constructor for a HUD that moves with a Scrollpane
	 */
	public HUD(ScrollPane pane, HashMap<String, Double> map) {
		myHUDMap = map;
		myHUDBox = new HBox();
		mySize = 20;
		myFont = "Arial Black";
		myColor = Color.BLACK;
		Label random = new Label("HELLO ALL lul");
		myHUDBox.getChildren().add(random);
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
	
	public void addItem(String item, Component component) {
		double val = component.getValue();
		Text text = new Text(item + ": " + val);
		text.setFont(Font.font(myFont, mySize));
		text.setFill(myColor);
		myHUDBox.getChildren().add(text);
		myComponentMap.put(item, component);
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
	
	public void updateHUDBox() {
		myHUDBox = new HBox();
		for (Entry<String, Component> entry : myComponentMap.entrySet()) {
			addItem(entry.getKey(), entry.getValue());
		}
	}

	public void updateHUDValues(List<Component> defaultHUDComponents) {
		myComponentMap = new HashMap<String, Component>();
		for (Component c : defaultHUDComponents) {
			if (c != null) {
				myComponentMap.put(c.getHudName(), c);
			}
		}
		updateHUDBox();
	}
	
}
