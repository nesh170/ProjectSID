package screen.screens;

import gameEngine.CollisionTable;

import java.awt.Scrollbar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.sun.glass.ui.Cursor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import levelPlatform.level.Level;
import resources.ScreenButton;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.controllers.CollisionTableScreenController;

/**
 * NOTE: STILL REFACTORING
 * DO NOT TOUCH YET
 */

/**
 * 
 * Resources:
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html
 * http://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/TilePane.html
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
 * http://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/TilePane.html
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html
 * http://stackoverflow.com/questions/13134983/liststring-to-arrayliststring-conversion-issue
 * 
 * 
 * 
 * Visual Design (discussion April 7)
 * 
 * Back-end layout:
 * 
 * Sprite 1		Sprite 2	Direction (acting on S1)	Action (applied to S1) (can have multiple)
 * 
 * 
 * Front-end layout (version 1):
 * 					Sprite acted upon
 * |----------------------------------------|
 * |   S1	|	S2	|	S3	|	S4	|	S5	|
 * |--------|-------|-------|-------|-------|
 * |   S2	|		|		|		|		|
 * |--------|-------|-------|-------|-------|
 * |   S3	|		|		|		|		|
 * |--------|-------|-------|-------|-------|
 * 				.	.	.	.	.	
 * 
 * 
 * Front-end layout (version 2): - by Anika
 * 
 * [ Edit Collisions]
 * 			|
 * 		   \./
 *  |-------------|
 *  |   {Select}  |
 * 	|    Player   |
 * 	|    Enemy    |
 * 	|  Platform   |
 * 	|  Power-up   |
 * 	---------------
 * 
 * [ Select ] -> [ Player ]
 * 
 * 						Interaction with:
 * |--------------------------------------------------------|
 * |       Enemy	  |	    Platform	 |	   Power-up		|	
 * |------------------|------------------|------------------|
 * | [Dir] [Action v] |	[Dir] [Action v] | [Dir] [Action v] |
 * |				  |				 	 |					|
 * |				  |				 	 |					|
 * |  (+) Add Action  |  (+) Add Action  |  (+) Add Action  |
 * |				  |				 	 |					|
 * |				  |				 	 |					|
 * |				  |				 	 |					|
 * 						.	.	.	.	.	
 * 
 * [Dir] = direction of player relative to the sprite
 * [Action v] = drop down menu of pre-written actions that happens to the player
 * 
 * For example: Sample portion of interactions:
 * 
 * 							[ Player ]
 * 						Interaction with:
 * |--------------------------------------------------------|
 * |       Enemy	  |	    Platform	 |	   Power-up		|	
 * |------------------|------------------|------------------|
 * | [ T ] [Points+1] |	[ B ][goThrough] | [ T ]  [Collect] |
 * | [ B ] [Health-1] |				 	 | [ B ]  [Collect]	|
 * | [ L ] [Health-1] |				 	 | [ L ]  [Collect]	|
 * | [ R ] [Health-1] |				 	 | [ R ]  [Collect] |
 * |--------------------------------------------------------|
 * 
 * 
 * 
 * 
 * Front-end layout (version 3): - by Anika (April 9)
 * 
 * [ Edit Collisions]
 * 			|
 * 		   \./
 * 
 * |------------------------------------------------------------------------------------------------------------|
 * |																						   					|
 * |											C O L L I S I O N S												|
 * |																										   	|
 * |													Interaction with:										|
 * |							|----------------------------------------------------------------------------|	|
 * |							|           Enemy	   	 |	        Platform		|	   	Power-up		 |	|
 * |							|------------------------|--------------------------|------------------------|	|
 * |							| [Dir] [Action v] [Val] |	[Dir] [Action v]  [Val] | [Dir] [Action v] [Val] |	|
 * | 		   PLAYER			|				   		 |				 	 		|						 |	|
 * |							|				   		 |				 	 		|						 |	|
 * |							|  	  (+) Add Action  	 |  	(+) Add Action      |  	 (+) Add Action  	 |	|
 * |							|				   		 |				 	 		|						 |	|
 * |							|----------------------------------------------------------------------------|	|
 * |																											|
 * |							|----------------------------------------------------------------------------|	|
 * |							|           Player	   	 |	        Platform		|	   	Power-up		 |	|
 * |							|------------------------|--------------------------|------------------------|	|
 * |							| [Dir] [Action v] [Val] |	[Dir] [Action v]  [Val] | [Dir] [Action v] [Val] |	|
 * | 		   ENEMY			|				   		 |				 	 		|						 |	|
 * |							|				   		 |				 	 		|						 |	|
 * |							|  	  (+) Add Action  	 |  	(+) Add Action      |  	 (+) Add Action  	 |	|
 * |							|				   		 |				 	 		|						 |	|
 * |							|----------------------------------------------------------------------------|	|
 * |																									        |
 * |								.	.	.	.	.						 									|
 * |------------------------------------------------------------------------------------------------------------|
 * 
 * 
 * 
 * ORGANIZATION:
 * 
 * |----------------------------------------------------|
 * |					    VBOX		 			    |
 * |								 			        | 
 * |	 |----------------------------------------|		|
 * |	 |					HBOX				  |     |  
 * |	 |										  |     |  
 * |	 |	TEXT			G R I D P A N E		  |     |  
 * |	 |										  |     |  
 * |	 |----------------------------------------|		|
 * |								 			        | 
 * |	 |----------------------------------------|		|
 * |	 |					HBOX				  |     |  
 * |	 |										  |     |  
 * |	 |	TEXT			G R I D P A N E		  |     |  
 * |	 |										  |     |  
 * |	 |----------------------------------------|		|
 * |								 			        | 
 * |----------------------------------------------------|
 * 
 * Menu and entering text similar to SpriteEditScreen ***
 * 
 * 
 * 
 * 
 * 
 * 
 * VERSION 2 - April 16 Group meeting
 * 
 * 
 * Collision table screen has only one text bar with add button
 * 
 * 
 * 
 * 	[Sprite 1 (active)]		[Sprite 2]		[Direction   v]		[Action  v]		[Value]		{Save}
 * 
 * 	[+]
 *  
 * 
 * 
 * 
 *
 * @author Anika
 *
 */
public class CollisionTableScreen extends Screen{

	private CollisionTableScreenController myController;
	private ArrayList<String> levelSpriteTags;
	private CollisionTable collTable; // TODO: how to get
	private StackPane tablesDisplay;	

	public CollisionTableScreen(double width, double height) {
		super(width, height);
	}

	/**
	 * CollisionTableScreen(collisionTableScreenController, width, height, level);
	 * 
	 * TODO: need to get passed Sprites from LES so that create action and set action to sprite
	 * then need to pass collision table the ACTION (not string of action)
	 */
	public CollisionTableScreen(CollisionTableScreenController controller, double width, double height, Set<String> spriteTags) {
		super(width, height);
		myController = controller;
		levelSpriteTags = new ArrayList<String>(spriteTags);
		initialize();
	}
	

	

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		// TODO Auto-generated method stub

	}
	
	private void initialize(){
		createVBoxOfCollisionRows();
		this.setCenter(tablesDisplay);
	}
	

	private void createVBoxOfCollisionRows(){
		tablesDisplay = new StackPane();
		VBox verticalBox = new VBox();
		ScreenButton addRowButton = new ScreenButton("Add2", STRING.BUTTONS.BUTTON_STYLE);
		addRowButton.setAlignment(Pos.BOTTOM_CENTER);
		verticalBox.setStyle(STRING.COLLISION_EDIT.BOTTOM_ROW_STYLE);
		ScrollPane levelSP = configureScrollPane(addRowButton);
		levelSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
		levelSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // Vertical scroll bar
		verticalBox.getChildren().addAll(levelSP);
		verticalBox.setVgrow(levelSP, Priority.ALWAYS);
       

			verticalBox.getChildren().add(addRowButton);
			
		
		tablesDisplay.getChildren().add(verticalBox);
	}

	private ComboBox createComboBoxFromList(ArrayList<String> list, String id, String style, String promptText)
	{
		ObservableList<String> options = FXCollections.observableArrayList();
		ComboBox comboBox = new ComboBox(options);
		
		for (String element : list)
		{
			comboBox.getItems().add(element);
		}
		comboBox.setId(id);
		comboBox.setStyle(style);
		comboBox.setStyle(STRING.COLLISION_EDIT.TEXT_BOX_STYLE);
		
		comboBox.setPromptText(promptText);
		return comboBox;
	}
	
	private VBox addTableRow()
	{
		VBox collisionTable = new VBox(800);
		collisionTable.setAlignment(Pos.CENTER);
		collisionTable.setTranslateY(50);
		collisionTable.setTranslateX(100);
		

		GridPane collisionSet = new GridPane();
		collisionSet.setHgap(30);
		collisionSet.setVgap(30);
		collisionSet.setPadding(new Insets(10, 50, 10, 50));
		collisionSet.setMaxHeight(50);

	
		ComboBox activeSpriteList = this.createComboBoxFromList(levelSpriteTags, STRING.COLLISION_EDIT.COMBO_SPRITE1_TAG, STRING.COLLISION_EDIT.FONT_STYLE, STRING.COLLISION_EDIT.COMBO_SPRITE1_NAME);
	
		collisionSet.add(activeSpriteList, 1, 0); 
		
		ComboBox inactiveSpriteList = this.createComboBoxFromList(levelSpriteTags, STRING.COLLISION_EDIT.COMBO_SPRITE2_TAG, STRING.COLLISION_EDIT.FONT_STYLE, STRING.COLLISION_EDIT.COMBO_SPRITE2_NAME);
	
		collisionSet.add(inactiveSpriteList, 2, 0); 
		
		ArrayList<String> third = new ArrayList<>(STRING.DIRECTION_TO_INTEGER_MAP.keySet());

		ComboBox direction = this.createComboBoxFromList(third, STRING.COLLISION_EDIT.COMBO_DIRECTION_NAME_AND_TAG,STRING.COLLISION_EDIT.FONT_STYLE, STRING.COLLISION_EDIT.COMBO_DIRECTION_NAME_AND_TAG);
		
		collisionSet.add(direction, 3, 0); 
		
		ObservableList<String> actionsToAdd = FXCollections.observableArrayList(ResourceBundle
				.getBundle("resources.spritePartProperties.action")
				.keySet().stream().map(e -> languageResources().getString(e))
				.collect(Collectors.toList()));
		
		ArrayList<String> fourth = new ArrayList<String>(actionsToAdd);
		ArrayList<String> nicerNamedActions = new ArrayList<String>();
		
		for (String each : fourth)
		{
			String better = each.substring(0, each.length()-INT.ACTION_STRING_LENGTH);
			nicerNamedActions.add(better);
		}
		
		ComboBox action = this.createComboBoxFromList(nicerNamedActions, STRING.COLLISION_EDIT.COMBO_ACTION_NAME_AND_TAG, STRING.COLLISION_EDIT.FONT_STYLE, STRING.COLLISION_EDIT.COMBO_ACTION_NAME_AND_TAG);

		collisionSet.add(action, 4, 0); 
		
		
		TextField text = new TextField();
		text.setStyle(STRING.COLLISION_EDIT.TEXT_BOX_STYLE);
		text.setPromptText(STRING.COLLISION_EDIT.TEXT_PROMPT);
		text.setId(STRING.COLLISION_EDIT.TEXT_PROMPT);
		collisionSet.add(text, 5, 0); 
		
		
	
		action.valueProperty().addListener(new ChangeListener<String>() {
	           
	            public void changed(ObservableValue ov, String t, String t1) {                
	              if (t1.equals("die"))
	              {
	            	  text.setDisable(true);
	              }
	              else
	              {
	            	  text.setDisable(false);
	              }
	            }    
	        });

		
		Image saveButtonImg = new Image(STRING.COLLISION_EDIT.SAVE_BUTTON_IMG);
		
		ImageView saveButton = new ImageView(saveButtonImg);
		saveButton.setFitWidth(100);
		saveButton.setPreserveRatio(true);
		
		setButtonStyle(saveButton, saveButtonImg, new Image(STRING.COLLISION_EDIT.SAVE_BUTTON_PRESSED_IMG));
		saveButton.setOnMouseClicked(e-> this.saveRow((String)activeSpriteList.getValue(), (String)inactiveSpriteList.getValue(), (String)direction.getValue(), (String)action.getValue(), (String)(text.getText())));
		collisionSet.add(saveButton, 6, 0); 
		
		collisionTable.getChildren().add(collisionSet);
		return collisionTable;
	}

	private void setButtonStyle(ImageView button, Image natural, Image pressed)
	{
		button.setCursor(ImageCursor.HAND);
		button.setOnMousePressed(e -> button.setImage(pressed));
		button.setOnMouseReleased(e -> button.setImage(natural));
	}
	
	
	
	private ScrollPane configureScrollPane(ScreenButton addCollisionRowButton){
		ScrollPane sp = new ScrollPane();
		sp.setPannable(true);
	
	
	//	TilePane tile = new TilePane(Orientation.VERTICAL);
		VBox tile = new VBox(50);
		
		HBox description = new HBox(800);
		description.setAlignment(Pos.TOP_CENTER);
		
		ImageView titleImage = new ImageView(new Image(STRING.COLLISION_EDIT.COLLISION_SCREEN_TITLE));
		titleImage.setFitWidth(600);
		titleImage.setPreserveRatio(true);
	//	titleImage.setTranslateX(200);
		
		description.getChildren().add(titleImage);

		tile.setPadding(new Insets(45, 45, 75, 45));
	//	tile.setVgap(20);
	//	tile.setHgap(20);
	//	tile.setStyle("-fx-background-color: DAE6F3;");
		
		tile.setStyle(STRING.COLLISION_EDIT.BACKGROUND_STYLE);
			
		tile.getChildren().add(description);

		for (int i = 0; i < 3; i++)
		{		
			VBox eachRow = this.addTableRow();	
			tile.getChildren().add(eachRow);
		}
		
		addCollisionRowButton.setOnMouseClicked(e -> {VBox row = this.addTableRow(); tile.getChildren().add(row);});
		
//		addButton.setOnMouseClicked(e -> {VBox row = this.addTableRow(); tile.getChildren().add(row);});

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // Vertical scroll bar
	//	sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		
		sp.setContent(tile);  
		
		
		
		return sp;
	}
	
	
	private boolean isDouble(String val)
	{
		try { 
			Double.parseDouble(val);
	    } catch(NumberFormatException e) { 
	       
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
		return true;
		
	}
	
	private void saveRow(String activeSp, String inactiveSp, String dir, String action, String value)
	{
		double valDouble = 0;
		if (isDouble(value))
		{
			valDouble = Double.parseDouble(value);
		}
		
		System.out.println(activeSp);
		System.out.println(inactiveSp);
		System.out.println(dir);
		System.out.println(action);
		System.out.println(value);
		
		
	
		
	//	collTable.addActionToMap(activeSp, inactiveSp, STRING.DIRECTION_TO_INTEGER_MAP.get(dir), toAdd);
		
		
	
	}
	
}
