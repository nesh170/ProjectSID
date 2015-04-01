package screen.levelEditScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import level.Level;
import screen.Screen;
import sprite.Sprite;

/**
 * 
 * @author Leo
 *
 */


public class LevelEditScreen extends Screen {
	
	private Level level;
	
	final ObservableList<Sprite> listOfPlatforms = FXCollections.observableArrayList();
	final ObservableList<Sprite> listOfEnemies = FXCollections.observableArrayList();
	final ObservableList<Sprite> listOfPlayers = FXCollections.observableArrayList();


	public LevelEditScreen(double width, double height) {
		
		super(width, height);
		makeSpritesInLevelTab();

		
	}
	
	public Level getCurrentLevel() {
		return level;
	}
	
	/*
	 * add a sprite to the level edit screen
	 */
	public void addSprite(Sprite sprite, Point2D location) {
		
	}

	@Override
	protected void addMenuItemsToMenuBar(MenuBar menuBar) {
		throw new IllegalStateException("unimplemented addMenuItemsToMenuBar in Screen");
	}
	
	private void makeSpritesInLevelTab() {
		VBox paneForSprites = new VBox();
		this.setLeft(paneForSprites);
		
		TitledPane platforms = makeTitledPane("Platforms",listOfPlatforms);
		TitledPane enemies = makeTitledPane("Enemies",listOfEnemies);
		TitledPane players = makeTitledPane("Players",listOfPlayers); 
		
		paneForSprites.getChildren().addAll(platforms,enemies,players);
	}
		
	private TitledPane makeTitledPane(String title, ObservableList<Sprite> content) {
		ListView<Sprite> platformListView = new ListView<>(content);
		return new TitledPane(title, platformListView);
	}

}
