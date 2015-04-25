package util;

import java.util.Optional;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;


public class DialogUtil {

    private static final String NETWORK = "Network";
    private static final String IPQUERY = "What is your IP address";

    /**
     * Sets up a dialog box and returns a string
     * @return
     */
    public static String setUpDialog () {
        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle(NETWORK);
        dialog.setHeaderText(IPQUERY);
        Optional<String> result = dialog.showAndWait();
        String entered = null;
        if (result.isPresent()) {
            entered = result.get();
        }
        return entered;
    }
}
