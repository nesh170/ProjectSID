package tester.levelPlatform.levelPlatformView;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;
import levelPlatform.levelPlatformView.LevelPlatformView;
import tester.Tester;


/**
 * Test displaying a LevelPlatformView with SIDPixels
 * @author Ruslan
 *
 */
public class LevelPlatformViewTester extends Tester {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	protected void test(Stage stage) {

		BorderPane borderPane = new BorderPane();
		this.addNode(borderPane);
		
		Level level = new Level(100, 50);
		LevelPlatformView levelPlatformView = new LevelPlatformView(level, EditMode.EDIT_MODE_ON, 10.0);
		
		borderPane.setCenter(levelPlatformView);
		
		System.out.println(levelPlatformView.getWidth());
		System.out.println(levelPlatformView.getHeight());

		// At this point verified that the TilePane needs to have a height set on it
			// --> LevelEditScreen can dynamically find, let's say 50% of the width and 40% of the height, and
			// allocate that towards the TilePane
		
	}
	
}
