package sprite;

import java.util.*;
import javafx.scene.image.*;

public class ImageManager {

	private Map<String, Image> imageMap = new HashMap<>();
	
	public Image getImageForString(String path){
		if(path == null){
			return null;
		}
		if(imageMap.containsKey(path)){
			return imageMap.get(path);
		}
		else{
			Image img = new Image(path);
			imageMap.put(path, img);
			return img;
		}
	}
	
}
