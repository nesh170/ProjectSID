package socCenter;
import game.Game;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.parse4j.Parse;

import data.DataHandler;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.Stage;
import resources.constants.DOUBLE;
import resources.constants.INT;
import resources.constants.STRING;
import screen.factories.ScreenFactory;
import screen.factories.SocialCenterScreenFactory;
import screen.tab.TabManager;
import screen.util.ErrorMessageTextFieldFactory;
import socCenter.logIn.LogInScreenController;
import socCenter.mainPage.MainPageScreen;
import socCenter.mainPage.MainPageScreenController;
import socCenter.profileScreen.ProfileScreenController;
import sprite.Sprite;
import util.ErrorHandler;
import util.ParseHandler;


public class SocialCenterScreenController {
	
	// Static Variables
	public static final String APP_ID = "M82RHsJGWvuQQAjCgUms444ujpCWPuJISu5FzZO0";
	public static final String REST_KEY = "Q9ryEswqaCWXBGWMz6DaLwHJtZj49gg5lUU8sTxu";
	
	
	// Instance Variables
	// Sizing
	private double width, height;
	private double newScreenWidth, newScreenHeight;
	// JavaFX
	private Stage stage;
	private Group root;
	private Scene scene;
	private TabManager tabManager;
	// Assists in selecting the correct tab after opening / closing tabs
	private ErrorHandler errorHandler;
	// Screen Managers
	private LogInScreenManager logInScreenManager;
	private MainPageScreenManager mainPageScreenManager;
	private ProfileScreenManager profileScreenManager;

	//Factories
	private SocialCenterScreenFactory screenFactory;
	
	//Data
	private ParseHandler parseHandle;
	
	private MainPageScreen loggedInScr;
	private String imgToReturnURL;
	
	
	
	// Getters & Setters (static)
	
	
	// Getters & Setters (instance)
	public double width() {
		return this.width;
	}
	
	public double height() {
		return this.height;
	}
	
	public double newScreenWidth() {
		return this.newScreenWidth;
	}
	
	public double newScreenHeight() {
		return this.newScreenHeight;
	}
	
	public Stage stage() {
		return this.stage;
	}
	
	public Scene scene() {
		return scene;
	}
	
	public void setCursor(ImageCursor imageCursor) {
		stage.getScene().setCursor(imageCursor);
	}
	
	public String getAv(){
		if(loggedInScr != null) {
			return loggedInScr.userURL();
		} else {
			return null;
		}
	}
	
	public Avatar getAvatar(){
		if(loggedInScr != null){
			return loggedInScr.userAvatar();
		} else {
			return null;
		}
	}
	// Constructors & Helpers
	public SocialCenterScreenController(Stage stage, double width, double height) {
		
		this.root = new Group();
		this.scene = new Scene(root);
		this.scene.getStylesheets().add("resources/SID.css"); //possibly put this in a string class or properties file?
		parseHandle = new ParseHandler();
		Parse.initialize(APP_ID, REST_KEY);
		
		configureStageAndRoot(stage, root);
		configureWidthAndHeight(width, height);
		configureNewScreenWidthAndHeight(width, height);
		configureFactories(newScreenWidth, newScreenHeight - 40);
		configureErrorHandling(root);
		configureScreenManagers();
		
		configureTabPane();
		
		createInitialScreens();
	
	}

	private void configureScreenManagers() {
		logInScreenManager = new LogInScreenManager();
		mainPageScreenManager = new MainPageScreenManager();
		profileScreenManager = new ProfileScreenManager();
	}

	private void configureStageAndRoot(Stage stage, Group root) {
		
		this.stage = stage;
		this.root = root;
		
	}
	
	private void configureWidthAndHeight(double width, double height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	private void configureNewScreenWidthAndHeight(double width, double height) {
					
		double newScreenHeight = height - DOUBLE.TAB_HEIGHT;
		
		this.newScreenWidth = width;
		this.newScreenHeight = newScreenHeight;
		
	}
	
	private void configureFactories(double width, double height) {
		this.screenFactory = new SocialCenterScreenFactory(width, height);
	}
	
	private void configureErrorHandling(Group root) {
		errorHandler = new ErrorHandler(root);
	}
	
	private void configureTabPane() {
		
		TabPane tabPane = new TabPane();
		createTabManager(tabPane);
		setTabPaneAlignmentAndSize(tabPane);
		addTabPane(tabPane);
		
	}
	
	private void createTabManager(TabPane tabPane) {
		tabManager = new TabManager(tabPane);
	}

	private void setTabPaneAlignmentAndSize(TabPane tabPane) {
		
		tabPane.sideProperty().set(Side.BOTTOM);
		
		tabPane.minHeightProperty().set(newScreenHeight);
		tabPane.maxHeightProperty().set(newScreenHeight);
		
		tabPane.minWidthProperty().set(newScreenWidth);
		tabPane.maxWidthProperty().set(newScreenWidth);
		
		tabPane.tabMinHeightProperty().set(DOUBLE.TAB_HEIGHT);
		tabPane.tabMaxHeightProperty().set(DOUBLE.TAB_HEIGHT);
		
	}
	
	private void addTabPane(TabPane tabPane) {
		root.getChildren().add(tabPane);
	}

	private void createInitialScreens() {
		
		tabManager.setDefaultTab(createLogInScreen());

	}
	
	
	// All other instance methods
	/**
	 * Closes the Application.
	 */
	public void close() {
		stage.close();
	}

	private Tab createLogInScreen() {
		
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createLogInScreen(logInScreenManager),
				STRING.MAIN_MENU_SCREEN.MAIN_MENU
				);		
	}
	

	private Tab createMainPageScreen(User loggedIn){
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createMainPageScreen(mainPageScreenManager, loggedIn),
				STRING.MAIN_MENU_SCREEN.MAIN_MENU
				);	
	}

	private Tab createProfileScreen(Tab mainPageScreenTab, User profileUser, User loggedIn, boolean self){
		return tabManager.addTabWithScreenWithStringIdentifier(
				screenFactory.createProfileScreen(profileScreenManager, mainPageScreenTab, profileUser, loggedIn, self),
				STRING.MAIN_MENU_SCREEN.MAIN_MENU
				);
	}

	


	/**
	 * A series of private, nested classes that implement ScreenController interfaces. 
	 * These classes conveniently use any necessary ScreenController instance variables
	 * while protecting us from entirely passing in the ScreenController into Screen objects.
	 * Screen objects use the provided methods in the ScreenController interfaces to 
	 * perform transitioning, closing, opening, etc. actions
	 *
	 * Emulating the ScreenController used for the authoring environment
	 */
	private class LogInScreenManager implements LogInScreenController {

		@Override
		public void createNewProfile(Popup popup) {

			popup.show(stage);
		}

		@Override
		public void loadUser(Popup popup) {

			popup.show(stage);
		}

		@Override
		public void logIn(Popup popup, TextField username, PasswordField password) {
			User loggedIn = parseHandle.loadUser(username.getText(), password.getText());
			if( loggedIn != null ) {
				loggedInScr = (MainPageScreen) createMainPageScreen(loggedIn).getContent();
				//createMainPageScreen(loggedIn);
			} else {
				errorHandler.displayError("No such user!");
			}
			popup.hide();
		}

		@Override
		public void createProfile(Popup popup, TextField username,
				PasswordField password, PasswordField rePassWord, TextField imageURL,
				String defaultImageURL) {
			String urlToUse = (imageURL.getText().length() < 2) ? (defaultImageURL) : imageURL.getText();
			User newUser = new User("", username.getText(), password.getText(), urlToUse);
			parseHandle.saveUser(newUser);
			loggedInScr = (MainPageScreen) createMainPageScreen(newUser).getContent();
			popup.hide();
			
		}
	}
	
	private class MainPageScreenManager implements MainPageScreenController {

		@Override
		public void loadUserPopup(Popup popup) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void nop() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadUserScreen(User loggedIn, User toDisplay) {
			// TODO Auto-generated method stub
			Tab currTab = tabManager.getTabSelectionModel().getSelectedItem();
			createProfileScreen(currTab, toDisplay, loggedIn, loggedIn.equals(toDisplay));
			System.out.println("loading: " + loggedIn.getName());
		}

		@Override
		public void loadMyUserScreen(User loggedIn) {
	
			Tab currTab = tabManager.getTabSelectionModel().getSelectedItem();
			createProfileScreen(currTab, loggedIn, loggedIn, true);
			System.out.println("loading: " + loggedIn.getName());
		}
		
	}
	
	private class ProfileScreenManager implements ProfileScreenController {

		@Override
		public void returnToMainPage(MainPageScreen mainPageScreen, Tab switchTab) {
			tabManager.removeTabAndChangeSelected(switchTab);
			
			
		}

		@Override
		public void saveChanges(User toSave) {
			
			parseHandle.saveUser(toSave);
			
		}

		@Override
		public void loadPlayerWithAvatar(Image avatarToUse) {
			//load instance of Player with avatar of profile
		}
		
		
		
	}
}
