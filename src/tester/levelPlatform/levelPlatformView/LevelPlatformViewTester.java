package tester.levelPlatform.levelPlatformView;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import levelPlatform.level.EditMode;
import levelPlatform.level.Level;
import levelPlatform.levelPlatformView.LevelPlatformView;
import resources.constants.DOUBLE;
import resources.constants.INT;
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
		
		double width = DOUBLE.DEFAULT_LENGTH_SIDE_PIXEL * INT.DEFAULT_LEVEL_WIDTH;
		double height = DOUBLE.DEFAULT_LENGTH_SIDE_PIXEL * INT.DEFAULT_BUTTON_HEIGHT;
		
		LevelPlatformView levelPlatformView = new LevelPlatformView(level, EditMode.EDIT_MODE_ON, width, height);
		
		borderPane.setCenter(levelPlatformView);
		
		System.out.println(levelPlatformView.getWidth());
		System.out.println(levelPlatformView.getHeight());

		// At this point verified that the TilePane needs to have a height set on it
			// --> LevelEditScreen can dynamically find, let's say 50% of the width and 40% of the height, and
			// allocate that towards the TilePane
		
	}
	
}
