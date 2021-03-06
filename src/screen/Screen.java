package screen;

import java.util.ResourceBundle;

import resources.constants.COLOR;
import resources.constants.DOUBLE;
import resources.constants.STRING;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

/**
 * The superclass to the MainMenuScreen, GameEditScreen, LevelEditScreen, etc.
 * 
 * @author Michael
 * @author Ruslan
 *
 */

public abstract class Screen extends BorderPane {

	// Instance variables
	// JavaFX (External)
	private Tab parentTab;
	// JavaFX (Internal)
	private MenuBar menuBar;
	private BorderPane viewableArea;
	// Resources
	private ResourceBundle languageResources;
	private ResourceBundle tagResources;
	// Sizing
	private double sideBarWidth;
	private double buttonWidth;

	// Getters & Setters
	/**
	 * Used in ScreenController addTabWithScreenWithStringIdentifier.
	 * If you'd like your Screen subclass to support closing itself, this is where to go
	 * 
	 * @param parentTab
	 */
	public void setParentTab(Tab parentTab) {
		this.parentTab = parentTab;
	}

	protected BorderPane viewableArea() {
		return this.viewableArea;
	}

	protected ResourceBundle languageResources() {
		return this.languageResources;
	}

	protected ResourceBundle tagResources() {
		return this.tagResources;
	}

	protected double sideBarWidth() {
		return this.sideBarWidth;
	}

	protected double buttonWidth() {
		return this.buttonWidth;
	}


	// Constructor & Helpers
	/**
	 * 
	 * @param width
	 * @param height of total height minus the tab pane
	 * 
	 */
	public Screen(double width, double height) {

		instantiateViewableArea();
		configureSizing(width, height);		// depends on an instance of viewableArea existing
		configureMenuBar(width);
		configureBackgroundColor();
		initializeRelevantResourceFiles();
		
	}
	
	protected ImageView makeImageView(String s, int height, int width) {
		
		ImageView img = new ImageView(s);
		img.setFitHeight(height);
		img.setFitWidth(width);
		return img;
		
	}
	
	private void configureSizing(double width, double height) {

		configureWidthAndHeight(width, height);
		configureSideBarAndButtonSizing(width);
		configureViewableAreaSizing(width, height);

	}

	private void configureWidthAndHeight(double width, double height) {

		setMinMaxWidthOnNode(this, width);
		setMinMaxHeightOnNode(this, height);

	}

	private void setMinMaxWidthOnNode(Node node, double width) {

		this.setMinWidth(width);
		this.setMaxWidth(width);

	}

	private void setMinMaxHeightOnNode(Node node, double height) {

		this.setMinHeight(height);
		this.setMaxHeight(height);

	}

	private void instantiateViewableArea() {

		this.viewableArea = new BorderPane();
		this.setCenter(viewableArea);

	}

	/**
	 * passes MenuBar to abstract subclass method
	 */
	private void configureMenuBar(double width) {

		VBox menuBarWrapper = instantiateMenuBar();
		addMenuItemsToMenuBar(menuBar);				// passes MenuBar to abstract method, never exposes MenuBar instance variable
		addMenuBarToThis(menuBarWrapper);

	}

	private VBox instantiateMenuBar() {

		VBox menuBarWrapper = new VBox();

		menuBar = new MenuBar();
		VBox.setVgrow(menuBar, Priority.NEVER);
		menuBarWrapper.getChildren().add(menuBar);

		menuBar.setPrefWidth(this.getWidth());
		menuBar.setPrefHeight(DOUBLE.MENU_BAR_HEIGHT);

		return menuBarWrapper;

	}
	
	public void rerender(){}

	protected abstract void addMenuItemsToMenuBar(MenuBar menuBar);

	@SafeVarargs
	protected final Menu makeFileMenu(EventHandler<ActionEvent>... fileMenuActions) {	

		//TODO more menu items for file
		MenuItem save = new MenuItem("Save");
		MenuItem exit = new MenuItem("Exit");
		MenuItem saveAndExit = new MenuItem("Save and Exit");

		save.setOnAction(fileMenuActions[0]);
		exit.setOnAction(fileMenuActions[1]);
		saveAndExit.setOnAction(fileMenuActions[2]);

		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(save, exit, saveAndExit);
		return fileMenu;

	}

	protected void initializeRelevantResourceFiles() {

		languageResources = ResourceBundle.getBundle("resources.stringResources");
		tagResources = ResourceBundle.getBundle("resources.TagChoices");

	}

	private void addMenuBarToThis(VBox wrapper) {
		this.setTop(wrapper);
	}

	private void configureBackgroundColor() {
		this.setStyle(STRING.COLORS.FX_BACKGROUND_COLOR_PREDICATE+STRING.COLORS.DEFAULT_FX_BACKGROUND_COLOR);
	}

	private void configureSideBarAndButtonSizing(double width) {

		this.sideBarWidth = width * DOUBLE.PERCENT.TEN_PERCENT;
		this.buttonWidth = sideBarWidth * DOUBLE.PERCENT.NINETY_PERCENT;

	}

	private void configureViewableAreaSizing(double width, double height) {

		this.viewableArea.setPrefSize(width - 2 * this.sideBarWidth, height - DOUBLE.MENU_BAR_HEIGHT);

		// Set margins for nice padding
		BorderPane.setMargin(viewableArea, new Insets(5, 5, 5, 5));

	}


	// All other instance methods
	protected void sizeMenuImageView(ImageView imageView, double width, double height) {

		imageView.setFitWidth(width);
		imageView.setFitHeight(height);

	}
	protected void setNodeScale( Node n, double f){
		n.setScaleX(f);
		n.setScaleY(f);
	}
	
	
	protected Button makeButtonForPane(String text, EventHandler<MouseEvent> lambda) {

		Button button = new Button(text);
		button.setOnMouseClicked(lambda);
		VBox.setVgrow(button, Priority.NEVER);
		button.setMinWidth(buttonWidth());
		
		return button;

	}
	
	protected Popup makeHideablePopup() {
		Popup popup = new Popup();
		popup.setHideOnEscape(true);
		popup.setAutoHide(true);
		
		return popup;
	}

}
