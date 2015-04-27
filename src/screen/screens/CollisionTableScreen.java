package screen.screens;

import gameEngine.Action;
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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.controllers.CollisionTableScreenController;
import sprite.Sprite;


/**
 * 
 * Resources:
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html
 * http://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/TilePane.html
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
 * http://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/TilePane.html
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html
 * http://stackoverflow.com/questions/13134983/liststring-to-arrayliststring-conversion-issue
 * https://docs.oracle.com/javafx/2/api/javafx/scene/ImageCursor.html
 * 
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
	 * 
	 */
	private CollisionTableScreenController myController;
	private List<String> levelSpriteTags;
	private CollisionTable collTable; // TODO: how to get
	private StackPane tablesDisplay;	
	private Map<String, ObservableList<String>> mapOfSpriteTypesToExistingSpriteStringNames;
	private Map<String, Map<String, List<String>>> collisionTableMap;

	public CollisionTableScreen(double width, double height) {
		super(width, height);
	}
	
	/******* PRIVATE CLASS SPRITEPAIR *******
	 * 
	 * This inner private class SpritePair is used by the CollisionTableScreen 
	 * to aid with putting the necessary parameters to Collision Table's 
	 * addActionToMap(String s1, String s2, int dir, Action action) method
	 * 
	 * @author anika
	 *
	 */
/*	private class SpritePair {
		
		private String myFirstSprite;
		private String mySecondSprite;
		
		public void initialize(String sprite1, String sprite2)
		{
			myFirstSprite = sprite1;
			mySecondSprite = sprite2;
		}
		
		public String getFirstSprite()
		{
			return myFirstSprite;
		}
		
		public String getSecondSprite()
		{
			return mySecondSprite;
		}
	
	}*/
	

	/**
	 * CollisionTableScreen(collisionTableScreenController, width, height, level);
	 * 
	 */
	public CollisionTableScreen(CollisionTableScreenController controller, double width, double height, Set<String> spriteTags,
			Map<String, ObservableList<String>> spriteMap) {
		
		super(width, height);
		
		myController = controller;
		
		levelSpriteTags = new ArrayList<String>(spriteTags);
		mapOfSpriteTypesToExistingSpriteStringNames = spriteMap; // map obtained from Level Edit Screen
		
		collisionTableMap = new HashMap<String, Map<String, List<String>>>(); // map populated by Collision Table Screen
		createVBoxOfCollisionRows(); 
		this.setCenter(tablesDisplay);
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
		
		setButtonStyle(addRowButton, addRowButtonImg, new Image(STRING.COLLISION_EDIT.ADD_BUTTON_PRESSED_IMG), 50);
		
		ScrollPane levelSP = configureScrollPane(addRowButton);
		levelSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
		levelSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // Vertical scroll bar
		verticalBox.getChildren().addAll(levelSP);
		verticalBox.setVgrow(levelSP, Priority.ALWAYS);
       
		addRowButton.setTranslateX(INT.ADD_ROW_BUTTON_OFFSET);
		verticalBox.getChildren().add(addRowButton);
			
		
		tablesDisplay.getChildren().add(verticalBox);
	}

	private ComboBox createComboBoxFromList(List<String> list, String id, String style, String promptText)
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
		VBox collisionTable = new VBox(INT.EACH_ROW_VBOX_SIZE);
		collisionTable.setAlignment(Pos.CENTER);
		collisionTable.setTranslateY(INT.EACH_ROW_VBOX_OFFSET_Y);
		collisionTable.setTranslateX(INT.EACH_ROW_VBOX_OFFSET_X);
		

		GridPane collisionSet = new GridPane();
		collisionSet.setHgap(INT.EACH_ROW_GRIDPANE_HGAP);
		collisionSet.setVgap(INT.EACH_ROW_GRIDPANE_VGAP);
		collisionSet.setPadding(new Insets(INT.EACH_ROW_GRIDPANE_INSETS_TOPBOTTOM, INT.EACH_ROW_GRIDPANE_INSETS_LEFTRIGHT, 
				INT.EACH_ROW_GRIDPANE_INSETS_TOPBOTTOM, INT.EACH_ROW_GRIDPANE_INSETS_LEFTRIGHT));
		collisionSet.setMaxHeight(INT.EACH_ROW_GRIDPANE_MAX_HEIGHT);

	
		ComboBox activeSpriteList = this.createComboBoxFromList(levelSpriteTags, STRING.COLLISION_EDIT.COMBO_SPRITE1_TAG, 
				STRING.COLLISION_EDIT.FONT_STYLE, STRING.COLLISION_EDIT.COMBO_SPRITE1_NAME);
	
		collisionSet.add(activeSpriteList, INT.ACTIVESPRITE_COLUMN, INT.TOP_ROW); 
		
		ComboBox inactiveSpriteList = this.createComboBoxFromList(levelSpriteTags, STRING.COLLISION_EDIT.COMBO_SPRITE2_TAG, 
				STRING.COLLISION_EDIT.FONT_STYLE, STRING.COLLISION_EDIT.COMBO_SPRITE2_NAME);
	
		collisionSet.add(inactiveSpriteList, INT.INACTIVESPRITE_COLUMN, INT.TOP_ROW); 
		
		ArrayList<String> third = new ArrayList<>(STRING.DIRECTION_TO_INTEGER_MAP.keySet());

		ComboBox direction = this.createComboBoxFromList(third, STRING.COLLISION_EDIT.COMBO_DIRECTION_NAME_AND_TAG,
				STRING.COLLISION_EDIT.FONT_STYLE, STRING.COLLISION_EDIT.COMBO_DIRECTION_NAME_AND_TAG);
		
		collisionSet.add(direction, INT.DIRECTION_COLUMN, INT.TOP_ROW); 
		
		ObservableList<String> actionsToAdd = FXCollections.observableArrayList(ResourceBundle
				.getBundle("resources.spritePartProperties.collisionAction")
				.keySet().stream().map(e -> languageResources().getString(e))
				.collect(Collectors.toList()));
		
		ArrayList<String> fourth = new ArrayList<String>(actionsToAdd);
		ArrayList<String> nicerNamedActions = new ArrayList<String>();
		
		for (String each : fourth)
		{
			String better = each.substring(0, each.length()-INT.ACTION_STRING_LENGTH);
			nicerNamedActions.add(better);
		}
		
		ComboBox action = this.createComboBoxFromList(nicerNamedActions, STRING.COLLISION_EDIT.COMBO_ACTION_NAME_AND_TAG, 
				STRING.COLLISION_EDIT.FONT_STYLE, STRING.COLLISION_EDIT.COMBO_ACTION_NAME_AND_TAG);

		collisionSet.add(action, INT.ACTION_COLUMN, INT.TOP_ROW); 
		

		TextField text = new TextField();
		text.setStyle(STRING.COLLISION_EDIT.TEXT_BOX_STYLE);
		text.setPromptText(STRING.COLLISION_EDIT.TEXT_PROMPT);
		text.setId(STRING.COLLISION_EDIT.TEXT_PROMPT);
		collisionSet.add(text, INT.TEXTVAL_COLUMN, INT.TOP_ROW); 


		
		ArrayList<String> optionalSprites = new ArrayList<String>();
		optionalSprites.add(null);
		
		action.valueProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1) {                
				if (STRING.NO_VALUE_NEEDED_ACTIONS.contains(t1))
				{
					text.setDisable(true);
				}
				else
				{
					text.setDisable(false);
				}


				if (t1.equals(STRING.COLLISION_EDIT.SWITCH_ACTION))
				{
					ArrayList<String> possibleSpritesToSwitchWith = new ArrayList<String>();
					if (activeSpriteList.valueProperty() != null)
					{
						String activeSprite = (String)activeSpriteList.getValue();
						possibleSpritesToSwitchWith = new ArrayList<String>(mapOfSpriteTypesToExistingSpriteStringNames.get(activeSprite));
					}

					ComboBox spritesToChooseFrom = createComboBoxFromList(possibleSpritesToSwitchWith, 
							STRING.COLLISION_EDIT.COMBO_SPRITE_SELECT_TAG, STRING.COLLISION_EDIT.FONT_STYLE, 
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
		saveButton.setOnMouseClicked(e-> this.saveRow((String)activeSpriteList.getValue(), (String)inactiveSpriteList.getValue(), 
				(String)direction.getValue(), (String)action.getValue(), optionalSprites.get(0), (String)(text.getText())));
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
	
	
	
	private ScrollPane configureScrollPane(ImageView addCollisionRowButton){ // TODO: magic numbers fix
		ScrollPane sp = new ScrollPane();
		sp.setPannable(true);
	
	
	//	TilePane tile = new TilePane(Orientation.VERTICAL);
		VBox tile = new VBox(INT.SCROLLPANE_VBOX_SIZE);
		
		HBox titleBox = new HBox(INT.SCROLLPANE_TITLEBOX_SIZE);
		titleBox.setAlignment(Pos.TOP_CENTER);
		
		ImageView titleImage = new ImageView(new Image(STRING.COLLISION_EDIT.COLLISION_SCREEN_TITLE));
		titleImage.setFitWidth(600);
		titleImage.setPreserveRatio(true);
	//	titleImage.setTranslateX(200);
		
		titleBox.getChildren().add(titleImage);

		tile.setPadding(new Insets(45, 45, 75, 45));
	//	tile.setVgap(20);
	//	tile.setHgap(20);
	//	tile.setStyle("-fx-background-color: DAE6F3;");
		
		tile.setStyle(STRING.COLLISION_EDIT.BACKGROUND_STYLE);
			
		tile.getChildren().add(titleBox);

		for (int i = 0; i < INT.SAMPLE_DISPLAY_NUMBER; i++)
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
	
	
	private void saveRow(String activeSp, String inactiveSp, String dir, String action, String switchOption, String value)
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
		System.out.println(switchOption); // TODO check is switchOption is 'null'
		System.out.println(value);
		
	/*	SpritePair thisSpritePair = new SpritePair();
		thisSpritePair.initialize(activeSp, inactiveSp);
		*/
		
		
	
		ArrayList<String> actionParameters = new ArrayList<String>();
		actionParameters.add(dir);
		actionParameters.add(action.replaceAll("\\s", "") + STRING.COLLISION_EDIT.ACTION_NAME);
		actionParameters.add(value);
		actionParameters.add(switchOption);
		
		
		Map<String, List<String>> activeSpriteMap;
		
		if (!(this.collisionTableMap.containsKey(activeSp)))
		{
			activeSpriteMap = new HashMap<String, List<String>>();
		}
		else
		{
			activeSpriteMap = collisionTableMap.get(activeSp);
		}
		
		activeSpriteMap.put(inactiveSp, actionParameters);
		
		
	
		collisionTableMap.put(activeSp, activeSpriteMap);
	
		
	//	collTable.addActionToMap(activeSp, inactiveSp, STRING.DIRECTION_TO_INTEGER_MAP.get(dir), toAdd); 
		// -> TO BE DONE FROM LEVEL EDIT SCREEN ON SAVE
		
		
	}
	
	
}
