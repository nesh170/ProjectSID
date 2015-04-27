package util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;


public class DialogUtil {

	/**
	 * Sets up a dialog box and returns a string
	 * @return
	 */
	public static String setUpDialog (String title, String header) {
		Dialog<String> dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		Optional<String> result = dialog.showAndWait();
		String entered = null;
		if (result.isPresent()) {
			entered = result.get();
		}
		return entered;
	}

	public static void display() {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to format your system?");
	}
}
