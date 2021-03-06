package screen.screens;

import gameEngine.Action;
import gameEngine.CollisionTable;

import java.awt.Scrollbar;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.controllers.CollisionTableScreenController;
import screen.screenmodels.CollisionMap;
import sprite.Sprite;


/**
 * 
 * Resources:
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html
 * http://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/TilePane.html
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
 * http://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/TilePane.html
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html
 * http://stackoverflow.com/questions/13134983/liststring-to-LinkedListstring-conversion-issue
 * https://docs.oracle.com/javafx/2/api/javafx/scene/ImageCursor.html
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/LineTo.html
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
 * TODO (25 April 2015)
 * 
 * - create map of sprite pairs and actions
 * - pass map to level edit screen
 * - user clicks save level -> for each sprite tag, set action; pass list of Actions to 
 * 		CollisionTable.addActionToMap(String s1, String s2, int direction, List<Action> actions)
 * 
 * 
 * 
 *
 * @author Anika
 *
 */
public class CollisionTableScreen extends Screen{

	/******* PRIVATE VARIABLES *******
	 * 
	 * myController 	 : 	CollisionTableScreenController 	   	   || Interface class specific to the Collision Table Screen
	 * levelSpriteTags	 :	List<String>				           || List of String tags of sprites obtained from Level Edit Screen
	 * collTable		 :	CollisionTable					       || Collision Table class. CTS calls addActionToMap(s1, s2, dir, action)
	 * tablesDisplay	 :	StackPane						       || StackPane used to display VBox and HBoxes of comboboxes 
	 * 
	 * mapOfSpriteTypesToExistingSpriteStringNames : Map<String, ObservableList<String>>	|| Map obtained from Level Edit Screen
	 * 
	 * collisionTableMap :	Map<String, Map<String, List<String>>> || Map of Tag 1 to Map of Tag 2 to Action components (direction, action, value...)
	 * 			
	 * 			example: String1 = "player"
	 * 					 Inner Map: String2 = "enemy"
	 * 								List<String> = {"Left", "Alter Health", "-1"}
	 * 
	 * 			example: String1 = "player"
	 * 					 Inner Map: String2 = "power-up"
	 * 								List<String> = {"Above", "Switch-out", null, "fireMario"}
	 * 
	 * 
	 * DESIGN CHANGE: April 29
	 * 
	 * collisionTableMap :	Map<String, Map<String, List<List<String>>>> 
	 * 										|| Map of Tag 1 to Map of Tag 2 to Direction List of Action components (action, value...)
	 * 
	 *   			example: String1 = "player"
	 * 					 Inner Map: String2 = "enemy"
	 * 								List<String> 	= index: above: {"Alter Health", "-1"}
	 * 												= index: below: {"die"}
	 * 
	 * 			example: String1 = "player"
	 * 					 Inner Map: String2 = "power-up"
	 * 								List<String> 	= index: left: {"Switch-out", null, "fireMario"}
	 * 
	 */
	private CollisionTableScreenController myController;
	private List<String> levelSpriteTags;
	private StackPane tablesDisplay;	
	private Map<String, ObservableList<String>> mapOfSpriteTypesToExistingSpriteStringNames;

	private CollisionMap collisionTableMap;

	public CollisionTableScreen(double width, double height) {
		super(width, height);
	}
	
	
	

	/**
	 * CollisionTableScreen(collisionTableScreenController, width, height, level);
	 * 
	 */
	public CollisionTableScreen(CollisionTableScreenController controller, double width, double height, Set<String> spriteTags,
			CollisionMap collisionMap, Map<String,ObservableList<String>> spriteMap) {
		
		super(width, height);
		
		myController = controller;
		
		levelSpriteTags = new LinkedList<String>(spriteTags);
		
		mapOfSpriteTypesToExistingSpriteStringNames = spriteMap;

		collisionTableMap = collisionMap;


		createVBoxOfCollisionRows(); 
		setCenter(tablesDisplay);
		
		
	}
	

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		// TODO Auto-generated method stub

	}
	
	
	private void createVBoxOfCollisionRows(){
		tablesDisplay = new StackPane();
		VBox verticalBox = new VBox();
		verticalBox.setStyle(STRING.COLLISION_EDIT.BOTTOM_ROW_STYLE);
		
		Image addRowButtonImg = new Image(STRING.COLLISION_EDIT.ADD_BUTTON_IMG);
		
		ImageView addRowButton = new ImageView(addRowButtonImg);
		addRowButton.setPreserveRatio(true);
		
		setButtonStyle(addRowButton, addRowButtonImg, new Image(STRING.COLLISION_EDIT.ADD_BUTTON_PRESSED_IMG), INT.ADD_ROW_BUTTON_SIZE);
		
		ScrollPane levelSP = configureScrollPane(addRowButton);
		levelSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
		levelSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // Vertical scroll bar
		verticalBox.getChildren().addAll(levelSP);
		verticalBox.setVgrow(levelSP, Priority.ALWAYS);
       
		addRowButton.setTranslateX(INT.ADD_ROW_BUTTON_OFFSET);
		verticalBox.getChildren().add(addRowButton);
			
	
		
		tablesDisplay.getChildren().add(verticalBox);
	}

	private ComboBox<String> createComboBoxFromList(List<String> list, String id, String style, String promptText)
	{
		ObservableList<String> options = FXCollections.observableList(list);
		ComboBox<String> comboBox = new ComboBox<>(options);

		comboBox.setId(id);
		comboBox.setStyle(style);
		comboBox.setStyle(STRING.COLLISION_EDIT.TEXT_BOX_STYLE);
		
		comboBox.setPromptText(promptText);
		return comboBox;
	}
	
	private VBox addTableRow()
	{
		VBox collisionTable = new VBox(INT.EACH_ROW_VBOX_SIZE);
		collisionTable.setAlignment(Pos.CENTER);
		collisionTable.setTranslateY(INT.EACH_ROW_VBOX_OFFSET_Y);
		collisionTable.setTranslateX(INT.EACH_ROW_VBOX_OFFSET_X);
		

		GridPane collisionSet = new GridPane();
		collisionSet.setHgap(INT.EACH_ROW_GRIDPANE_HGAP);
		collisionSet.setVgap(INT.EACH_ROW_GRIDPANE_VGAP);
		collisionSet.setPadding(new Insets(INT.EACH_ROW_GRIDPANE_INSETS_TOPBOTTOM,
				INT.EACH_ROW_GRIDPANE_INSETS_LEFTRIGHT, 
				INT.EACH_ROW_GRIDPANE_INSETS_TOPBOTTOM,
				INT.EACH_ROW_GRIDPANE_INSETS_LEFTRIGHT));
		collisionSet.setMaxHeight(INT.EACH_ROW_GRIDPANE_MAX_HEIGHT);

	
		ComboBox<String> activeSpriteList = createComboBoxFromList(levelSpriteTags, 
				STRING.COLLISION_EDIT.COMBO_SPRITE1_TAG, 
				STRING.COLLISION_EDIT.FONT_STYLE, 
				STRING.COLLISION_EDIT.COMBO_SPRITE1_NAME);
	
		collisionSet.add(activeSpriteList, INT.ACTIVESPRITE_COLUMN, INT.TOP_ROW); 
		
		ComboBox<String> inactiveSpriteList = createComboBoxFromList(levelSpriteTags, 
				STRING.COLLISION_EDIT.COMBO_SPRITE2_TAG, 
				STRING.COLLISION_EDIT.FONT_STYLE, 
				STRING.COLLISION_EDIT.COMBO_SPRITE2_NAME);
	
		collisionSet.add(inactiveSpriteList, INT.INACTIVESPRITE_COLUMN, INT.TOP_ROW); 
		
		List<String> third = new LinkedList<>(STRING.DIRECTION_TO_INTEGER_MAP.keySet());

		ComboBox<String> direction = createComboBoxFromList(third, 
				STRING.COLLISION_EDIT.COMBO_DIRECTION_NAME_AND_TAG,
				STRING.COLLISION_EDIT.FONT_STYLE, 
				STRING.COLLISION_EDIT.COMBO_DIRECTION_NAME_AND_TAG);
		
		collisionSet.add(direction, INT.DIRECTION_COLUMN, INT.TOP_ROW); 
		
		ObservableList<String> actionsToAdd = FXCollections.observableList(ResourceBundle
				.getBundle("resources.spritePartProperties.collisionAction")
				.keySet().stream().map(e -> languageResources().getString(e))
				.collect(Collectors.toList()));
		
		List<String> fourth = new LinkedList<String>(actionsToAdd);
		List<String> nicerNamedActions = new LinkedList<String>();
		
		for (String each : fourth)
		{
			nicerNamedActions.add(each);
		}
		
		ComboBox<String> action = createComboBoxFromList(nicerNamedActions, 
				STRING.COLLISION_EDIT.COMBO_ACTION_NAME_AND_TAG, 
				STRING.COLLISION_EDIT.FONT_STYLE, 
				STRING.COLLISION_EDIT.COMBO_ACTION_NAME_AND_TAG);

		collisionSet.add(action, INT.ACTION_COLUMN, INT.TOP_ROW); 
		

		TextField text = new TextField();
		text.setStyle(STRING.COLLISION_EDIT.TEXT_BOX_STYLE);
		text.setPromptText(STRING.COLLISION_EDIT.TEXT_PROMPT);
		text.setId(STRING.COLLISION_EDIT.TEXT_PROMPT);
		collisionSet.add(text, INT.TEXTVAL_COLUMN, INT.TOP_ROW); 


		
		List<String> optionalSprites = new LinkedList<String>();
		optionalSprites.add(null);
		
		action.valueProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue ov, String t, String t1) {     
				
				text.setDisable(STRING.NO_VALUE_NEEDED_ACTIONS.contains(t1));
				
				if (t1.equals(STRING.COLLISION_EDIT.SWITCH_ACTION))
				{
					List<String> possibleSpritesToSwitchWith = new LinkedList<String>();
					if (activeSpriteList.valueProperty() != null)
					{
						String activeSprite = (String)activeSpriteList.getValue();
						possibleSpritesToSwitchWith = new LinkedList<String>(mapOfSpriteTypesToExistingSpriteStringNames.get(activeSprite));
					}

					ComboBox<String> spritesToChooseFrom = createComboBoxFromList(possibleSpritesToSwitchWith, 
							STRING.COLLISION_EDIT.COMBO_SPRITE_SELECT_TAG, 
							STRING.COLLISION_EDIT.FONT_STYLE, 
							STRING.COLLISION_EDIT.COMBO_SPRITE_SELECT_NAME);

					collisionSet.add(spritesToChooseFrom, INT.ACTION_COLUMN, INT.SPRITELIST_ROW); 

					spritesToChooseFrom.valueProperty().addListener(new ChangeListener<String>() {


						public void changed(ObservableValue ov, String t, String t1) {                
							optionalSprites.set(0, t1);

						}
					});

				}

			}  



		});
		
		
		Image saveButtonImg = new Image(STRING.COLLISION_EDIT.SAVE_BUTTON_IMG);
		
		ImageView saveButton = new ImageView(saveButtonImg);
		saveButton.setPreserveRatio(true);
		
		setButtonStyle(saveButton, saveButtonImg, new Image(STRING.COLLISION_EDIT.SAVE_BUTTON_PRESSED_IMG), INT.SAVE_BUTTON_SIZE);
		saveButton.setOnMouseClicked(e-> saveRowAndAddToCollisionTableMap(activeSpriteList.getValue(), inactiveSpriteList.getValue(), 
				direction.getValue(), action.getValue(), optionalSprites.get(0), text.getText()));
		collisionSet.add(saveButton, INT.SAVE_BUTTON_COLUMN, INT.TOP_ROW); 
		
		Image trashButtonImg = new Image(STRING.COLLISION_EDIT.TRASH_BUTTON_IMG);
		
		ImageView trashButton = new ImageView(trashButtonImg);
		trashButton.setPreserveRatio(true);
		setButtonStyle(trashButton, trashButtonImg, new Image(STRING.COLLISION_EDIT.TRASH_BUTTON_PRESSED_IMG), INT.TRASH_BUTTON_SIZE);
	//	trashButton.setOnMouseClicked(e -> {activeSpriteList.setValue(activeSpriteList.getPromptText());});
		trashButton.setOnMouseClicked(e -> {collisionTable.getChildren().remove(collisionSet);});
		collisionSet.add(trashButton, INT.TRASH_BUTTON_COLUMN, INT.TOP_ROW); 
		
		collisionTable.getChildren().add(collisionSet);
		
		return collisionTable;
	}

	private void setButtonStyle(ImageView button, Image natural, Image pressed, int size)
	{
		button.setFitWidth(size);
		button.setCursor(ImageCursor.HAND);
		button.setOnMousePressed(e -> button.setImage(pressed));
		button.setOnMouseReleased(e -> button.setImage(natural));
	}
	
	private void setPathAnimationtoTitleImage(ImageView title)
	{
		Path path = new Path();
		path.getElements().add(new MoveTo(INT.TITLE_ANI_MOVE_START,INT.TITLE_ANI_MOVE_END));
		path.getElements().add(new LineTo(INT.TITLE_ANI_LINE_START, INT.TITLE_ANI_LINE_END));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(INT.TITLE_ANI_DURATION));
		pathTransition.setPath(path);
		pathTransition.setNode(title);
		pathTransition.play();
	}
	

	
	private ScrollPane configureScrollPane(ImageView addCollisionRowButton){ 
		ScrollPane sp = new ScrollPane();
		sp.setPannable(true);
	
		VBox tile = new VBox(INT.SCROLLPANE_VBOX_SIZE);
		
		HBox titleBox = new HBox(INT.SCROLLPANE_TITLEBOX_SIZE);
		titleBox.setAlignment(Pos.TOP_CENTER);
		
		ImageView titleImage = new ImageView(new Image(STRING.COLLISION_EDIT.COLLISION_SCREEN_TITLE));
		titleImage.setFitWidth(INT.TITLE_IMAGE_SIZE);
		titleImage.setPreserveRatio(true);
		
		setPathAnimationtoTitleImage(titleImage);
		
		titleBox.getChildren().add(titleImage);

		tile.setPadding(new Insets(INT.SCROLLPANE_INSETS_TOP, 
				INT.SCROLLPANE_INSETS_RIGHT, 
				INT.SCROLLPANE_INSETS_BOTTOM, 
				INT.SCROLLPANE_INSETS_LEFT));

		
		tile.setStyle(STRING.COLLISION_EDIT.BACKGROUND_STYLE);
			
		tile.getChildren().add(titleBox);

		for (int i = 0; i < INT.SAMPLE_DISPLAY_NUMBER; i++)
		{		
			VBox eachRow = addTableRow();	
			tile.getChildren().add(eachRow);
		}
		
		addCollisionRowButton.setOnMouseClicked(e -> {VBox row = addTableRow(); tile.getChildren().add(row);});
		

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // Vertical scroll bar
		sp.setFitToWidth(true);
		
		sp.setContent(tile);  
		
		
		
		return sp;
	}
	

	private void saveRowAndAddToCollisionTableMap(String activeSp, String inactiveSp, String dir, String action, String switchOption, String value)
	{
	
		List<String> actionParameters = new ArrayList<String>();
		actionParameters.add(action);
		actionParameters.add(value);
	
		collisionTableMap.put(activeSp, inactiveSp, STRING.DIRECTION_TO_INTEGER_MAP.get(dir), actionParameters);
		
	}
	
}