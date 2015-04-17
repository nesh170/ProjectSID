package screen.screens;

import gameEngine.CollisionTable;

import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import levelPlatform.level.Level;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import screen.controllers.CollisionTableScreenController;

/**
 * NOTE: I NEED TO REFACTOR THIS CODE 
 * DO NOT TOUCH YET
 */

/**
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
 * 	[Sprite 1 (active)]		[Sprite 2]		[Direction   v]		[Action  v]		[Value]
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
	private List<String> levelSprites;
	private CollisionTable collTable; // TODO: how to get
	private StackPane tablesDisplay;

	public CollisionTableScreen(double width, double height) {
		super(width, height);
	}

	/**
	 * CollisionTableScreen(collisionTableScreenController, width, height, level);
	 */
	public CollisionTableScreen(CollisionTableScreenController controller, double width, double height, List<String> sprites) {
		super(width, height);
		myController = controller;
		levelSprites = sprites;
		initialize();
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		// TODO Auto-generated method stub

	}


	// HASHMAP of s1 s2 and action and direction
	private void initialize(){
		configureButtons();
		//makeSpriteTableDisplay();
		//	this.setCenter(tablesDisplay);
	}


	private void configureButtons() {	
		configureLevelDisplay();
		this.setCenter(tablesDisplay);

	}


	private void configureLevelDisplay(){
		//, DisplayLevels(myLevels)
		tablesDisplay = new StackPane();
		ScrollPane levelSP = this.displayLevels(this.levelSprites);


		tablesDisplay.getChildren().addAll(levelSP);
	}

	/**
	 * display list of levels that are represented by images in parallel 
	 * @param ObservableList<Level>
	 */
	private ScrollPane displayLevels(List<String> levels) { 		

		//TableView<ObservableList> levelTable = new TableView();	
		ScrollPane sp = configureScrollPane();



		return sp;

	}
	private HBox configureHBox(){
		HBox hb = new HBox(INT.GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}
	private ScrollPane configureScrollPane(){
		ScrollPane sp = new ScrollPane();

		sp.setPannable(true);

		
		
		TilePane tile = new TilePane(Orientation.VERTICAL);
		HBox description = new HBox(800);
		description.setAlignment(Pos.TOP_CENTER);
		Text category = new Text("Sales:");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		description.getChildren().add(category);

		tile.setPadding(new Insets(25, 25, 25, 25));
		tile.setVgap(20);
		tile.setHgap(20);
		tile.setStyle("-fx-background-color: DAE6F3;");
		tile.getChildren().add(description);

		for (int i = 0; i < 3; i++)
		{
			
			
			HBox collisionTable = new HBox(800);
			collisionTable.setAlignment(Pos.CENTER);
			collisionTable.setTranslateY(100);
			collisionTable.setTranslateX(100);


			
			
			

			GridPane collisionSet = new GridPane();
			collisionSet.setHgap(30);
			collisionSet.setVgap(30);
			collisionSet.setPadding(new Insets(0, 50, 0, 50));

			// Category in column 2, row 1
			ObservableList<String> options = 
					FXCollections.observableArrayList(
							"Option 1",
							"Option 2",
							"Option 3"
							);
			ComboBox comboBox = new ComboBox(options);

			ArrayList<String> testOptions = new ArrayList<String>();
			testOptions.add("Option44");
			comboBox.getItems().addAll(testOptions);
			
			comboBox.setId("SpriteActive");
			comboBox.setStyle("-fx-font: 20px \"Serif\";");
			comboBox.setPromptText("Active Sprite");
			collisionSet.add(comboBox, 1, 0); 
			
			

			
			// Title in column 3, row 1
			ObservableList<String> nextOptions = 
					FXCollections.observableArrayList(
							"Option 1",
							"Option 2",
							"Option 3"
							);
			ComboBox comboBox2 = new ComboBox(nextOptions);

			comboBox2.getItems().addAll(
				    "Option 4",
				    "Option 5",
				    "Option 6"
				);
			
			comboBox2.setId("SpriteInActive");
			comboBox2.setStyle("-fx-font: 20px \"Serif\";");
			comboBox2.setPromptText("Inactive Sprite");

			collisionSet.add(comboBox2, 2, 0); 
			
			
			
			
			
			ObservableList<String> directionOptions = 
					FXCollections.observableArrayList(
							"Above",
							"Below",
							"Left",
							"Right"
							);
			ComboBox comboBox3 = new ComboBox(directionOptions);
			comboBox3.setPromptText("Direction");

			
			
			comboBox3.setId("Direction");
			comboBox3.setStyle("-fx-font: 20px \"Serif\";");
			collisionSet.add(comboBox3, 3, 0); 

			
			
			

			ObservableList<String> actionOptions = 
					FXCollections.observableArrayList(
							"die",
							"move",
							"sigh",
							"groove"
							);
			ComboBox comboBox4 = new ComboBox(actionOptions);
			comboBox4.setPromptText("Action");

			
			
			comboBox4.setId("Action");
			comboBox4.setStyle("-fx-font: 20px \"Serif\";");
			collisionSet.add(comboBox4, 4, 0); 
			
			
			
			
			TextField text = new TextField();
			text.setPromptText("Value");
			text.setId("Value");
			collisionSet.add(text, 5, 0); 
			
			comboBox4.valueProperty().addListener(new ChangeListener<String>() {
		           
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
			
			
			

			collisionTable.getChildren().add(collisionSet);


			tile.getChildren().add(collisionTable);


		}

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		sp.setContent(tile);     
		return sp;
	}
	private void displayLevelsWhenEmpty(HBox hb) {

	}


	private ImageView makeButton(String location, EventHandler<MouseEvent> lamda){
		ImageView b = new ImageView(new Image(location));
		b.setFitHeight(80);
		b.setFitWidth(80);
		b.setOnMouseClicked(lamda);
		return b;
	}	





}
