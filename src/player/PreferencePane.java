package player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import media.AudioController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Creates preferences that change details of gameplay such as sound, volume, or
 * controls
 * 
 * @author James
 *
 */
public class PreferencePane {

	private static final double SLIDERVAL_TO_DOUBLE = .1;
	private static final double DEFAULT_WIDTH = 800;
	private static final double DEFAULT_HEIGHT = 500;
	private static final double MIN_SETTING = 0;
	private static final double MAX_SETTING = 10;
	private static final double DEFAULT_SETTING = (MIN_SETTING + MAX_SETTING) / 2;
	private static final double DEFAULT_MUSIC_VOL = 10;
	private static final double DEFAULT_BRIGHTNESS = 5;
	private static final double AVTAB_CENTER = 500;
	private static final double SLIDER_SIZE = 250;
	
	private PlayerViewController myController;
	private Stage myContainer;
	private Scene myScene;
	private TabPane myView;
	private Tab myAV;
	private Tab myControls;
	private AudioController myAudioController;
	private double myMusicVolume;
	private double myBrightness;
	private Map<String, Consumer<KeyCode>> myConsumerMap;
	private Map<String, KeyCode> myCodeMap;
	
	public PreferencePane(AudioController ac) {
		myAudioController = ac;
		setupDefaults();
		myView = new TabPane();
		myContainer = new Stage();
		makeAVTab();
		makeControlsTab();
		myView.getTabs().addAll(myAV, myControls);
		myScene = new Scene(myView, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		myContainer.setScene(myScene);
	}
	
	private void makeAVTab() {
		myAV = new Tab("Audiovisual");
		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		HBox titles = new HBox(AVTAB_CENTER);
		titles.getChildren().addAll(new Label("Audio"),
				new Label("Visual"));
		titles.setAlignment(Pos.TOP_CENTER);
		HBox music = new HBox(20);
		music.getChildren().addAll(new Label("Music Volume"),
				makeMusicControl(myMusicVolume));
		music.setAlignment(Pos.CENTER_LEFT);
		HBox brightness = new HBox(20);
		brightness.getChildren().addAll(new Label("Game Brightness"),
				makeBrightnessControl(myBrightness));
		brightness.setAlignment(Pos.CENTER_RIGHT);
		HBox row = new HBox(AVTAB_CENTER-SLIDER_SIZE);
		row.setAlignment(Pos.CENTER);
		row.getChildren().addAll(music, brightness);
		HBox buttons = makeButtonBox();
		buttons.setAlignment(Pos.BOTTOM_CENTER);
		container.getChildren().addAll(titles, row, buttons);
		myAV.setContent(container);
	}

	private void makeControlsTab() {
		myControls = new Tab("Controls");	
	}

	private Slider makeSettingSlider(double defaultVal) {
		Slider slider = new Slider(MIN_SETTING, MAX_SETTING, defaultVal);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(DEFAULT_SETTING);
		slider.setBlockIncrement(1);
		return slider;
	}

	private TextField makeKeyField(Consumer<KeyCode> item, String code) {
		TextField container = new TextField();
		container.setText(myCodeMap.get(code).getName());
		container.setOnKeyTyped(e -> container.clear());
		container.setOnKeyReleased(key -> handleKeyCode(container,
				key.getCode(), item));
		container.setAlignment(Pos.CENTER);
		return container;
	}

	private Button makeSaveButton() {
		Button save = new Button("Save Changes");
		save.setOnAction(e -> savePreferences());
		return save;
	}

	private Button makeCloseButton() {
		Button close = new Button("Close");
		close.setOnAction(e -> closePreferences());
		return close;
	}
	
	private void handleKeyCode(TextField keyInput, KeyCode code, Consumer<KeyCode> consumer) {
		keyInput.clear();
		keyInput.setText(code.getName());
		consumer.accept(code);
	}

	private HBox makeButtonBox() {
		HBox hbox = new HBox(50);
		hbox.getChildren().addAll(makeSaveButton(), makeCloseButton());
		return hbox;
	}

	private void regenerateControlFields(VBox container) {
		myCodeMap = myController.getKeyMap();
		myConsumerMap = myController.getConsumerSetup();
		HBox titles = new HBox(50);
		titles.getChildren().addAll(new Label("Action"), new Label("Key/Control"));
		titles.setAlignment(Pos.TOP_CENTER);
		container.getChildren().add(titles);
		for (String key : myConsumerMap.keySet()) {
			HBox hbox = new HBox(50);
			TextField field = makeKeyField(myConsumerMap.get(key), key);
			hbox.getChildren().addAll(new Label(key), field);
			container.getChildren().add(hbox);
			hbox.setAlignment(Pos.CENTER);
		}
		HBox buttons = makeButtonBox();
		buttons.setAlignment(Pos.BOTTOM_CENTER);
		container.getChildren().add(buttons);
		myControls.setContent(container);
	}
	
	private void savePreferences() {

	}

	private Slider makeBrightnessControl(double brightness) {
		Slider slider = makeSettingSlider(brightness);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number oldVal, Number newVal) {
				myController.setDim((newVal.doubleValue() - 5)
						* SLIDERVAL_TO_DOUBLE);
				myController.setBright((-1*newVal.doubleValue() + 5)
						* SLIDERVAL_TO_DOUBLE);
			}
		});
		return slider;
	}

	private Slider makeMusicControl(double volume) {
		Slider slider = makeSettingSlider(volume);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number oldVal, Number newVal) {
				myAudioController.setVol(newVal.doubleValue()
						* SLIDERVAL_TO_DOUBLE);
			}
		});

		return slider;
	}

	public void setController(PlayerViewController pvc) {
		myController = pvc;
	}

	public void bringUpPreferences() {
		VBox vbox = new VBox(20);
		regenerateControlFields(vbox);
		myControls.setContent(vbox);
		myContainer.show();
	}

	public void closePreferences() {
		myContainer.hide();
	}

	private void setupDefaults() {
		myMusicVolume = DEFAULT_MUSIC_VOL;
		myBrightness = DEFAULT_BRIGHTNESS;
	}
	
}