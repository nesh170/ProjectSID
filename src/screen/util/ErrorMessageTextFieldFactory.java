package screen.util;

import resources.constants.DOUBLE;
import resources.constants.STRING;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * A static factory class to create a Text Field to display errors. Animated disappearing on click.
 * 
 * @author Ruslan
 *
 */
public class ErrorMessageTextFieldFactory {

	public static TextField configureNewErrorMessageTextField(String error) {
		
		TextField returnErrorMessageTextField = new TextField(error + STRING.ERROR.CLICK_TO_DISMISS);
		
		configureNewMessage(returnErrorMessageTextField);

		return returnErrorMessageTextField;
		
	}
	
	private static void configureNewMessage(TextField messageBox) {
		
		double messageWidth = DOUBLE.ERROR_MESSAGE_WIDTH;
		double messageHeight = DOUBLE.ERROR_MESSAGE_HEIGHT;

		messageBox.setOnMouseClicked(e -> handleMessageBoxClicked(e, messageBox));

		messageBox.setEditable(false);

		messageBox.setStyle(STRING.COLORS.FX_RED_BACKGROUND);
		messageBox.setOpacity(DOUBLE.PERCENT.NINETY_PERCENT);

		messageBox.setMinWidth(messageWidth);
		messageBox.setMaxWidth(messageWidth);

		messageBox.setMinHeight(messageHeight);
		messageBox.setMaxHeight(messageHeight);
		
	}

	// On click, disappear message
	private static void handleMessageBoxClicked(MouseEvent click, TextField messageBox) {

		Timeline timeline = new Timeline();

		KeyValue opacityKeyValue = new KeyValue(messageBox.opacityProperty(), 0.0);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), opacityKeyValue);

		timeline.getKeyFrames().add(keyFrame);
		timeline.setAutoReverse(false);

		timeline.play();

	}
	
}
