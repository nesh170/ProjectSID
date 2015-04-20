package tester.engine;

import data.DataHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import levelPlatform.level.Level;
import sprite.Sprite;
import tester.Tester;

/**
 * Use this tester to test new features of the Engine without the messiness of ExampleLevelMaker
 */

public class EngineTester extends Tester {

	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	protected void test(Stage stage) {
		Sprite player = new Sprite(new Point2D(0.0, 100.0), Point2D.ZERO, new Dimension2D(50.0, 50.0));
		Level l = new Level(500, 500);
		try{
			DataHandler.toXMLFile(l, "testingLevel.xml", System.getProperty("user.dir")+"/engineTesting");
		}
		catch (Exception e){
			System.out.println("Oh no!!!");
		}

	}

}
