package player;

import media.AudioController;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PreferencePane {

	private static final double MIN_SETTING = 0;
	private static final double MAX_SETTING = 10;
	private static final double DEFAULT_SETTING = MIN_SETTING*MAX_SETTING*.5;
	private static final Color TEXT_COLOR = Color.WHITE;
	
	private TabPane myView;
	private AudioController myAudioController;
	
	public PreferencePane(AudioController ac) {
		myAudioController = ac;
		myView = new TabPane();
		Tab AV = makeAVTab();
		myView.getTabs().addAll(AV);
	}
	
	private Tab makeAVTab() {
		Tab AV = new Tab("Audiovisual");
		GridPane grid = new GridPane();
		Label audioTitle = new Label("Audio");
		Label visualTitle = new Label("Visual");
		Label gameVol = new Label("Game Volume");
		Label musicVol = new Label("Music Volume");
		grid.add(audioTitle, 0, 0, 1, 2);
		grid.add(visualTitle, 2, 0, 1, 2);
		grid.add(gameVol, 0, 1);
		grid.add(musicVol, 2, 1);
		grid.add(makeSettingSlider(DEFAULT_SETTING), 1, 1);
		grid.add(makeSettingSlider(DEFAULT_SETTING), 1, 1);
		return AV;
	}
	
	private Slider makeSettingSlider(double defaultVal) {
		Slider slider = new Slider(MIN_SETTING, MAX_SETTING, defaultVal);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(DEFAULT_SETTING);
		slider.setBlockIncrement(1);
		return slider;
	}
	
}