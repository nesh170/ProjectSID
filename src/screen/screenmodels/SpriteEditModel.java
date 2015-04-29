package screen.screenmodels;

import gameEngine.Action;
import gameEngine.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import screen.util.ImageAndFilePair;
import sprite.Sprite;

public class SpriteEditModel {
	
	private Sprite editableSprite;
	
	private ResourceBundle languageResources;
	private ResourceBundle actionResources;
	private ResourceBundle componentResources;
	private ResourceBundle behaviorLabels;

	
	private ObservableList<String> actionsToAdd;
	private ObservableList<String> actionsAdded;
	private ObservableList<String> componentsToAdd;
	private ObservableList<String> componentsAdded;
	private ObservableList<String> imagesAdded;
	
	private Map<String, String> classPathMap;
	private Map<String, Action> actionMap;
	private Map<String, Boolean> keyCodesAreVisibleMap;
	private Map<String, Component> componentMap;
	private Map<String, ImageAndFilePair> stringToImageMap;
	private Map<String, String> behaviorLabelsMap;
	private Map<String, String> createdBehaviorParameterMap;
	
	private KeyCode currentCode = KeyCode.UNDEFINED;


	public SpriteEditModel(ResourceBundle languageResources, ResourceBundle actionResources, ResourceBundle componentResources, ResourceBundle behaviorLabels) {
		this.languageResources = languageResources;
		this.actionResources = actionResources;
		this.componentResources = componentResources;
		this.behaviorLabels = behaviorLabels;
		
		actionMap = new HashMap<>();
		componentMap = new HashMap<>();
		stringToImageMap = new HashMap<>();
		createdBehaviorParameterMap = new HashMap<>();
		
		initializeKeyCodesAreVisibleMap();
		initializeClassPathMap();
		initializeBehaviorLabelsMap();
				
		editableSprite = new Sprite();

	}
	
	
	private void initializeKeyCodesAreVisibleMap() {

		keyCodesAreVisibleMap = new HashMap<>();
		keyCodesAreVisibleMap.put(languageResources.getString("AlwaysRun"),
				false);
		keyCodesAreVisibleMap.put(languageResources.getString("NeedCode"),
				true);
//		keyCodesAreVisibleMap.put(languageResources().getString("OnCollision"),
//				false);

	}

	private void initializeClassPathMap() {

		classPathMap = new HashMap<>();

		actionResources.keySet().forEach(
				e -> classPathMap.put(
						languageResources.getString(e),
						actionResources.getString(e)));

		componentResources.keySet().forEach(
				e -> classPathMap.put(
						languageResources.getString(e),
						componentResources.getString(e)));

	}
	
	private void initializeBehaviorLabelsMap() {

		behaviorLabelsMap = new HashMap<String, String>();

		behaviorLabels.keySet().forEach(
				e -> behaviorLabelsMap.put(
						languageResources.getString(e),
						behaviorLabels.getString(e)));

	}
	
	public ObservableList<String> setActionsToAdd(ObservableList<String> observableArrayList) {
		actionsToAdd = observableArrayList;
		return actionsToAdd;
	}
	
	public ObservableList<String> setActionsAdded() {
		actionsAdded = FXCollections.observableArrayList();
		return actionsAdded;
	}
	
	public ObservableList<String> setComponentsToAdd(ObservableList<String> observableArrayList) {
		componentsToAdd = observableArrayList;
		return componentsToAdd;
	}
	
	public ObservableList<String> setComponentsAdded() {
		componentsAdded = FXCollections.observableArrayList();
		return componentsAdded;
	}
	
	public ObservableList<String> setImagesAdded() {
		imagesAdded = FXCollections.observableArrayList();
		return imagesAdded;
	}

	public void onAddListener(String newSelect, TextField textField) {
		
		if (newSelect == null || newSelect.isEmpty()) {
			textField.setPromptText("");
		}
		else {
			textField.setPromptText(behaviorLabelsMap
					.get(newSelect));
		}
		
	}
	
	public String setDataText(String text) {
		return createdBehaviorParameterMap.get(text);
	}

	public String setDataText(ListView<String> listInFocus) {

		return createdBehaviorParameterMap.get(listInFocus
				.getSelectionModel().getSelectedItem());
	}
	
	public void setCurrentCode(KeyCode code) {
		currentCode = code;
	}


	public boolean getVisibilty(String select) {
		return keyCodesAreVisibleMap.get(select);
	}
	
	public ImageView getSelectedImage(String string) {
		return stringToImageMap.get(string).image();
	}


	public void addAction(String selected, String actionValueText, String actionType, String soundPath) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		KeyCode[] keylist = new KeyCode[1];
		if (currentCode != null
				&& !currentCode.equals(KeyCode.UNDEFINED)) {
			keylist[0] = currentCode;
		}
		Action action = (Action) Class
				.forName(classPathMap.get(selected))
				.getConstructor(Sprite.class, Double.class,
						KeyCode[].class)
						.newInstance(editableSprite,
								Double.parseDouble(actionValueText),
								keylist);
		if(!keyCodesAreVisibleMap.get(actionType)) {
			action.runEveryFrame();
		}
		action.setSound(soundPath);
		String parameterMapValue = selected + "-> "
				+ languageResources.getString("Keycode") + " "
				+ currentCode.toString() + ", "
				+ languageResources.getString("Value") + " "
				+ actionValueText;
		addBehaviorToListView(action, parameterMapValue, actionsToAdd, actionsAdded, actionMap, true);

	}
	
	/**
	 * 
	 * @param behavior - Must be an action or component unless a new type of behavior is added 
	 * @param valueString - This is the string that displays when selecting an added behavior
	 * @param start - (behavior)ToAdd observable list
	 * @param end - (behavior)Added obserable list
	 * @param behaviorMap - Map from a string to a behavior, <String,Behavior> *DO NOT USE ANY OTHER TYPES OF MAPS*
	 * @param adding - boolean that signifies whether the behavior is being added or removed
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addBehaviorToListView(Object behavior, String valueString, ObservableList<String> start, ObservableList<String> end, Map behaviorMap, boolean adding) {
		String stringToSwitch = languageResources.getString(trimClassName(behavior.getClass().getName()));
		if(adding) {
			createdBehaviorParameterMap.put(stringToSwitch, valueString);
			start.remove(stringToSwitch);
			end.add(stringToSwitch);
			behaviorMap.put(stringToSwitch, behavior);
		}
		else {
			createdBehaviorParameterMap.remove(stringToSwitch);
			start.add(stringToSwitch);
			end.remove(stringToSwitch);
			behaviorMap.remove(stringToSwitch);
		}
	}
	
	private String trimClassName(String classPath) {
		String[] split = classPath.split("\\.");
		return split[split.length - 1];
	}


	public void removeAction(String selected) {
		if (selected != null) {
			
			addBehaviorToListView(actionMap.get(selected), "", actionsToAdd, actionsAdded, actionMap, false);

		}		
	}


	public void addComponent(String selected, String componentValueText) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Double values = Double.parseDouble(componentValueText);
		Component component = (Component) Class
				.forName(classPathMap.get(selected))
				.getConstructor(Sprite.class, Double.class)
				.newInstance(editableSprite, values);
		String parameterMapValue = selected + "-> "
				+ languageResources.getString("Value") + " "
				+ componentValueText;
		addBehaviorToListView(component, parameterMapValue, componentsToAdd, componentsAdded, componentMap, true);
		
	}
	
	public void removeComponent(String selected) {
		if (selected != null) {

			addBehaviorToListView(componentMap.get(selected),"",componentsToAdd,componentsAdded,componentMap,false);

		}
	}


	public Consumer<? super Action> getActionConsumer() {
		return action -> addBehaviorToListView(action, "", actionsToAdd, actionsAdded, actionMap, true);
	}

	public Consumer<? super Component> getComponentConsumer() {
		return component -> addBehaviorToListView(component, "", componentsToAdd, componentsAdded, componentMap, true);
	}


	public int getNumImages() {
		return imagesAdded.size();
	}


	public void addImageToList(String path, ImageView spriteImageRep) {
		
		String imageName = languageResources.getString("ImageName") + imagesAdded.size();
		stringToImageMap.put(imageName, new ImageAndFilePair(spriteImageRep,path));
		imagesAdded.add(imageName);	
	
	}
	
	public void saveSprite(String spriteName, String tag, boolean isGoal, int levelNum) {
		
		//TODO don't allow save if name isn't written 

		stringToImageMap.keySet().forEach(
				e -> {
					//TODO - need this to take in multiple image paths
					editableSprite.setImagePath(stringToImageMap.get(e).filePath());
					editableSprite.setDimensions(new Dimension2D((stringToImageMap).get(e).image().getImage().getWidth(),
														(stringToImageMap).get(e).image().getImage().getHeight()));
				});

		componentMap.keySet().forEach(e -> {
			editableSprite.addComponent(componentMap.get(e));
		});

		actionMap.keySet().forEach(e -> {
			editableSprite.addAction(actionMap.get(e));
		});

		editableSprite.setName(spriteName);
		editableSprite.setTag(tag);
		editableSprite.setIsGoal(isGoal, levelNum);
		
		
	}


	public Sprite retrieveEditedSprite() {
		return editableSprite;
	}

}
