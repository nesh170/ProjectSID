package player;

import media.AudioController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PreferencePane {

	private static final double DEFAULT_WIDTH = 800;
	private static final double DEFAULT_HEIGHT = 500;
	private static final double MIN_SETTING = 0;
	private static final double MAX_SETTING = 10;
	private static final double DEFAULT_SETTING = (MIN_SETTING + MAX_SETTING)/2;
	private static final double DEFAULT_MUSIC_VOL = 5;
	private static final double DEFAULT_BRIGHTNESS = 5;
	private static final Color TEXT_COLOR = Color.WHITE;
	
	private PlayerViewController myController;
	private Stage myContainer;
	private Scene myScene;
	private TabPane myView;
	private AudioController myAudioController;
	private double myVolume;
	private double myBrightness;
	
	public PreferencePane(AudioController ac) {
		myAudioController = ac;
		myVolume = DEFAULT_MUSIC_VOL;
		myBrightness = DEFAULT_BRIGHTNESS;
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
		Label audioTitle = new Label("Audio");
		Label visualTitle = new Label("Visual");
		Label gameVol = new Label("Game Volume");
		Label musicVol = new Label("Music Volume");
		Label brightness = new Label("Game Brightness");
		grid.setVgap(50);
        grid.setHgap(40);
		grid.add(audioTitle, 0, 0, 1, 2);
		grid.add(visualTitle, 2, 0, 1, 2);
		grid.add(gameVol, 0, 1);
		grid.add(musicVol, 0, 2);
		grid.add(brightness, 2, 1);
		grid.add(makeSettingSlider(DEFAULT_SETTING), 1, 1);
		grid.add(makeMusicControl(myVolume), 1, 2);
		grid.add(makeSettingSlider(myBrightness), 3, 1);
		AV.setContent(grid);
		return AV;
	}
	
	private Tab makeControlsTab() {
		Tab controls = new Tab("Controls");
		return controls;
	}
	
	private Slider makeSettingSlider(double defaultVal) {
		Slider slider = new Slider(MIN_SETTING, MAX_SETTING, defaultVal);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(DEFAULT_SETTING);
		slider.setBlockIncrement(1);
		return slider;
	}
	
	private Slider makeBrightnessSlider(double defaultVal) {
		Slider slider = new Slider(MIN_SETTING, MAX_SETTING, defaultVal); 
		ColorAdjust ca = new ColorAdjust();
		slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number oldVal, Number newVal) {
                    ca.setBrightness(newVal.doubleValue());
                    myController.setBrightness(ca);
            }
        });
		return slider;
	}
	
	private Slider makeMusicControl(double volume) {
		Slider slider = makeSettingSlider(volume);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number oldVal, Number newVal) {
                    myAudioController.setVol(newVal.doubleValue());
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
	
}