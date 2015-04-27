package socCenter;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

public class DefaultAvatarPack {
	
	private ResourceBundle defaultAvResources;
	private Map<String, Avatar> defaultAvatars;
	private ChoiceBox<String> defaultAvatarChoicesHolder;
	
	public DefaultAvatarPack(ResourceBundle resources)  {
		defaultAvResources = resources;
		initializeDefaultAvatarsList();
	}
	
	private void initializeDefaultAvatarsList(){
		defaultAvatars = new HashMap<String, Avatar>();
		for(String key: defaultAvResources.keySet()){
			Avatar newAvy = new Avatar(key, defaultAvResources.getString(key));
			defaultAvatars.put(key, newAvy);
		}
		
	}
	
	public ChoiceBox<String> getDefaultAvChoices(){
		defaultAvatarChoicesHolder = new ChoiceBox<String>();
		defaultAvatarChoicesHolder.setItems(FXCollections.observableArrayList(defaultAvatars.keySet()));
		return defaultAvatarChoicesHolder;
	}
	
	public Avatar getAvatar(String name){
		return defaultAvatars.get(name);
	}
}
