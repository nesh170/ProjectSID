package socCenter.logIn;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import resources.constants.INT;
import resources.constants.STRING;
import screen.Screen;
import socCenter.DefaultAvatarPack;

/** Screen with options for logging in
 * to social center, creating profile.
 * 
 * Some duplicate code from main menu screen, etc for now.
 * Will think about that refactor after an actual
 * model/database is built so we can test
 * @author Emmanuel
 *
 */
public class LogInScreen extends Screen {

	//Instance variables
	private LogInScreenController controller;
	private Popup logInPopup;
	private Popup createProfilePopup;
	private ResourceBundle socialTextProps;
	private ResourceBundle defaultAvResources;
	private DefaultAvatarPack avPack;
	private ChoiceBox<String> defaultAvChoices;
	
	//Getters & Setters
	
	//Constructor & Helpers
	public LogInScreen(LogInScreenController controller, double width, double height){
		super(width, height);
		initalizeRelevantResourceFiles();
		initializeDefaultAvatarPack();
		this.controller = controller;
		configureButtons(width, height);
		this.setStyle(STRING.COLORS.FX_GAME_EDIT_BACKGROUND);
		this.getStyleClass().add("pane");
	
	}
	
	private void initalizeRelevantResourceFiles(){
		super.initializeRelevantResourceFiles();
		socialTextProps = ResourceBundle
				.getBundle("resources.socialCenterProperties.socialButtons");
		defaultAvResources = ResourceBundle
				.getBundle("resources.socialCenterProperties.defaultAvatars");
	}
	
	private void initializeDefaultAvatarPack(){
		avPack = new DefaultAvatarPack(defaultAvResources);
	}
	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		
	}
	
	private void configureButtons(double width, double height) {
		
		StackPane menu = new StackPane();	
		menu.getChildren().addAll(makeImageView("images/Blue_Devils.png", 300, 540), makeButtons(), makeText("Welcome to SID Social Center"));
		this.viewableArea().setCenter(menu);
		
	}
	
	protected ImageView makeImageView(String s, int height, int width) {
		
		ImageView img = new ImageView(s);
		img.setFitHeight(height);
		img.setFitWidth(width);
		return img;
		
	}
	
	private VBox makeButtons() {
		
		Button newProfile = makeButton("Create Profile");
		Button logIn = makeButton("Log In");
		Button exit = makeButton("Exit Application ");
		configurePopups();
		newProfile.setOnMouseClicked(e -> controller.createNewProfile(createProfilePopup));
		logIn.setOnMouseClicked(e -> controller.loadUser(logInPopup));
		exit.setOnMouseClicked(e -> System.exit(0));
		VBox vbox = new VBox(INT.DEFAULT_BUTTON_SPREAD);
		vbox.getChildren().addAll(newProfile, logIn, exit);
		vbox.setAlignment(Pos.CENTER);
		return vbox;
		
	}
	
	private Button makeButton(String s) {	
		
		Button b = new Button(s);
		b.setStyle("-fx-background-color: lightgray;");
		b.setMinSize(INT.DEFAULT_BUTTON_WIDTH, INT.DEFAULT_BUTTON_HEIGHT);
		return b;
		
	}
	
	private Text makeText(String s) {
		
		Text text = new Text(s);
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		text.setEffect(ds);
		text.setCache(true);
		text.setX(10.0f);
		text.setY(270.0f);
		text.setFill(Color.BLACK);
		text.setFont(Font.font("SERIF", FontWeight.BOLD, 48));
		text.setTranslateY(-250);  
		return text;
		
	}

	private void configurePopups() {
		
		//popup menu for game name		
		 makePopups();
		 
		 GridPane grid = configureLogInGridPane();
	     ImageView img = makeImageView(STRING.MAIN_MENU_SCREEN.POPUP, 350, INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
	     logInPopup.getContent().addAll(img, grid);
	     
	     GridPane createGrid = configureCreateProfileGridPane();
	     ImageView createImg = makeImageView(STRING.MAIN_MENU_SCREEN.POPUP, 350, INT.DEFAULT_LEVEL_DISPLAY_WIDTH);
	     createProfilePopup.getContent().addAll(createImg, createGrid);
	}
	
	private void makePopups(){
		logInPopup = makePopup();
		createProfilePopup = makePopup();
	}
	
	private Popup makePopup() {   
		 Popup pop = new Popup();
	     pop.setAutoFix(false);
	     pop.setHideOnEscape(true);
	     pop.setX(500);
	     pop.setY(250);
	     return pop;
	}

	private GridPane configureLogInGridPane() {
		
		 GridPane grid = makeLogInGridPane();
	     TextField userNameInput = new TextField();
	     userNameInput.setPromptText("Enter User Name: ");
	     grid.add(userNameInput, 2, 2 );
	     PasswordField passwordInput = new PasswordField();
	     passwordInput.setPromptText("Enter Password");
	     grid.add(passwordInput, 2, 3 );
	     HBox popUpHBox = new HBox(100);
	     grid.add(popUpHBox, 2, 5);
	     Button ok = new Button("log in");
	     Button cancel = new Button("cancel");
	     popUpHBox.getChildren().addAll(cancel, ok);
	     ok.setOnAction(e -> controller.logIn(logInPopup, userNameInput, passwordInput));
	     cancel.setOnAction(e -> logInPopup.hide());
	     return grid;
	     
	}
	
	private GridPane configureCreateProfileGridPane(){
		
		 GridPane grid = makeCreateProfileGridPane();
	     TextField newUserName = new TextField();
	     newUserName.setPromptText("Enter User Name: ");
	     grid.add(newUserName, 2, 2 );
	     PasswordField newPassWord = new PasswordField();
	     newPassWord.setPromptText("Enter Password");
	     grid.add(newPassWord, 2, 3 );
	     PasswordField reNewPassWord = new PasswordField();
	     reNewPassWord.setPromptText("Re-Enter Password");
	     grid.add(reNewPassWord, 2, 4);
	     TextField imageURL = new TextField();
	     imageURL.setPromptText("Enter profile image URL");
	     grid.add(imageURL, 2, 5);
	     defaultAvChoices = avPack.getDefaultAvChoices();
	     grid.add(defaultAvChoices, 2, 6);
	     HBox popUpHBox = new HBox(100);
	     grid.add(popUpHBox, 2, 7);
	     Button ok = new Button("create profile");
	     Button cancel = new Button("cancel");
	     popUpHBox.getChildren().addAll(cancel, ok);
	     ok.setOnAction(e -> controller.createProfile(createProfilePopup, newUserName, newPassWord, reNewPassWord, imageURL, defaultAvChoices.getSelectionModel().getSelectedItem()));
	     cancel.setOnAction(e -> createProfilePopup.hide());
	     return grid;
		
	}
	
	private GridPane makeLogInGridPane() {
		
		 GridPane grid = new GridPane();
	     grid.setAlignment(Pos.CENTER);
	     grid.setHgap(10);
	     grid.setVgap(20);	
	     grid.addRow(0, new Text(""));
	     Label name = new Label( socialTextProps.getString("User"));
	     grid.add(name, 0, 2, 2, 1);
	     Label password = new Label(socialTextProps.getString("Password"));
	     grid.add(password, 0, 3, 2, 1);
	     return grid;
	     
	}
	
	private GridPane makeCreateProfileGridPane() {
		 GridPane grid = new GridPane();
	     grid.setAlignment(Pos.CENTER);
	     grid.setHgap(10);
	     grid.setVgap(20);	
	     grid.addRow(0, new Text(""));
	     Label name = new Label( socialTextProps.getString("User"));
	     grid.add(name, 0, 2, 2, 1);
	     Label password = new Label(socialTextProps.getString("Password"));
	     grid.add(password, 0, 3, 2, 1);
	     Label reEnterPassword = new Label( socialTextProps.getString("rePassword"));
	     grid.add(reEnterPassword, 0, 4, 2, 1);
	     return grid;
	}
	
}
