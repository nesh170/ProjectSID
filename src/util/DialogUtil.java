package util;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;


public class DialogUtil {

	/**
	 * Sets up a dialog box and returns a string
	 * @return
	 */
	public static String setUpDialog (String title, String header)
	{
		Dialog<String> dialog = new TextInputDialog();
		setTitleAndHeader(dialog, title, header);
		Optional<String> result = dialog.showAndWait();
		String entered = null;
		if (result.isPresent()) {
			entered = result.get();
		}
		return entered;
	}

	public static void displayMessage(String title, String header)
	{
		ButtonType confirmButtonType = ButtonType.OK;
		Dialog<String> dialog = new Dialog<>();
		setTitleAndHeader(dialog, title, header);
		dialog.getDialogPane().getButtonTypes().add(confirmButtonType);
		dialog.getDialogPane().lookupButton(confirmButtonType).setDisable(false);
		dialog.showAndWait();
	}
	
	private static void setTitleAndHeader(Dialog<?> dialog, String title, String header) {
		dialog.setTitle(title);
		dialog.setHeaderText(header);
	}
}
