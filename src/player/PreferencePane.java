package player;

import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.layout.GridPane;
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
	private static final double DEFAULT_BRIGHTNESS = 10;
	private static final String DEFAULT_UP_KEY = "up";
	private static final String DEFAULT_RIGHT_KEY = "right";
	private static final String DEFAULT_LEFT_KEY = "left";

	private PlayerViewController myController;
	private Stage myContainer;
	private Scene myScene;
	private TabPane myView;
	private AudioController myAudioController;
	private double myMusicVolume;
	private double myBrightness;
	private String myUpAction;
	private String myRightAction;
	private String myLeftAction;
	private Map<TextField, String> myActionMap;
	
	public PreferencePane(AudioController ac) {
		myAudioController = ac;
		setupDefaults();
		myView = new TabPane();
		myContainer = new Stage();
		Tab AV = makeAVTab();
		Tab controls = makeControlsTab();
		myView.getTabs().addAll(AV, controls);
		myScene = new Scene(myView, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		myContainer.setScene(myScene);
	}

	private Tab makeAVTab() {
		Tab AV = new Tab("Audiovisual");
		GridPane grid = new GridPane();
		grid.setVgap(50);
		grid.setHgap(40);
		grid.add(new Label("Audio"), 0, 0, 1, 2);
		grid.add(new Label("Visual"), 3, 0, 1, 2);
		grid.add(new Label("Game Volume"), 0, 1);
		grid.add(new Label("Music Volume"), 0, 2);
		grid.add(new Label("Game Brightness"), 3, 1);
		grid.add(makeSettingSlider(DEFAULT_SETTING), 1, 1);
		grid.add(makeMusicControl(myMusicVolume), 1, 2);
		grid.add(makeBrightnessControl(myBrightness), 4, 1);
		grid.add(makeCloseButton(), 2, 1);
		AV.setContent(grid);
		return AV;
	}

	private Tab makeControlsTab() {
		Tab controls = new Tab("Controls");	
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setVgap(50);
		grid.setHgap(40);
		grid.add(new Label("Action"), 0, 0, 1, 2);
		grid.add(new Label("Key/Control"), 3, 0, 1, 2);
		grid.add(new Label("Jump"), 0, 1);
		grid.add(new Label("Move Left"), 0, 2);
		grid.add(new Label("Move Right"), 0, 3);
		makeFieldDefaults(grid);
		grid.add(makeCloseButton(), 1, 4);
		grid.add(makeSaveButton(), 3, 4);
		controls.setContent(grid);
		return controls;
	}

	private void makeFieldDefaults(GridPane grid) {
		TextField up = makeKeyField(myUpAction);
		TextField right = makeKeyField(myRightAction);
		TextField left = makeKeyField(myLeftAction);
		grid.add(makeKeyField(myUpAction), 3, 1);
		grid.add(makeKeyField(myRightAction), 3, 2);
		grid.add(makeKeyField(myLeftAction), 3, 3);
		myActionMap = new HashMap<TextField, String>();
		myActionMap.put(up, "UpMotionAction");
		myActionMap.put(right, "RightMotionAction");
		myActionMap.put(left, "LeftMotionAction");
	}

	private Slider makeSettingSlider(double defaultVal) {
		Slider slider = new Slider(MIN_SETTING, MAX_SETTING, defaultVal);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(DEFAULT_SETTING);
		slider.setBlockIncrement(1);
		return slider;
	}

	private TextField makeKeyField(String item) {
		TextField container = new TextField(item);
		container.setOnKeyTyped(e -> container.clear());
		container.setOnKeyReleased(key -> handleKeyCode(container,
				key.getCode()));
		container.setAlignment(Pos.CENTER);
		return container;
	}

	private Button makeSaveButton() {
		Button save = new Button("Save Changes");
		save.setOnAction(e -> savePreferences());
		return save;
	}

	private void handleKeyCode(TextField keyInput, KeyCode code) {
		keyInput.clear();
		keyInput.setText(code.getName());
		myController.changeKeySetup(code, myActionMap.get(keyInput));
	}

	private Button makeCloseButton() {
		Button close = new Button("Close");
		close.setOnAction(e -> closePreferences());
		return close;
	}

	private void savePreferences() {

	}

	private Slider makeBrightnessControl(double brightness) {
		Slider slider = makeSettingSlider(brightness);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number oldVal, Number newVal) {
				myController.setBrightness((10 - newVal.doubleValue())
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