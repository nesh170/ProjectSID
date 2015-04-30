package player;


import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Displays relevant game data (Lives, Health, etc.) on the screen
 * @author James
 *
 */
public class HUD {

	private HBox myHUDBox;
	private String myFont;
	private double mySize;
	private Color myColor;
	private Map<String, Double> myHUDMap;
	/**
	 * Constructor for a HUD that moves with a Scrollpane
	 */
	public HUD(ScrollPane pane, HashMap<String, Double> map) {
	        this();
		myHUDMap = map;
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
	
	private void addItem(String item, Double value) {
		Text text = new Text(item + ": " + value);
		text.setFont(Font.font(myFont, mySize));
		text.setFill(myColor);
		myHUDBox.getChildren().add(text);
	}
	
	public void renderHUD(){
	    myHUDBox.getChildren().clear();
	    myHUDMap.forEach((name,value) -> addItem(name, value));
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

	public void updateHUDValues(Map<String, Double> map) {
		myHUDMap = map;
	}
	
}
