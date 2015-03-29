package authoringEnvironment;

import java.io.File;

import com.sun.prism.paint.Color;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The scene that contains pops up when the Authoring Environment 
 * application is opened.
 * Allows user to select whether they want to create a new game
 * or edit an existing one.
 * 
 * @author Michael
 *
 */
public class MainMenuScene extends Scene {

	Group myRoot;
	
	public MainMenuScene(Group arg0) {
		super(arg0);
		myRoot = arg0;
		addContents();
	}
	
	private void addContents() {
		File f = new File("/Users/Michael/Documents/workspace2/voogasalad_ScrollingDeep/DESIGN/data/MainPage.png");
		Image i = new Image(f.toURI().toString());
		add(new ImageView(i));
	}
	
	public void add(Node node) {
		myRoot.getChildren().add(node);
	}

}
