package util;


import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import resources.constants.DOUBLE;
import screen.util.ErrorMessageTextFieldFactory;

/**
 * 
 * @author Michael
 *
 */

public class ErrorHandler {
	TextField errorMessageTextField;
	Group root;
	
	public ErrorHandler(Group root) {
		this.root = root;
	}
	
	public void displayError(String error) {
		
		cleanUpOldErrorMesssage();
		instantiateErrorMessage(error);
		configureErrorMessageOffsets();
		addErrorMessage();
		
	}
	
	/**
	 * Methods below are helpers for Error Messages
	 * 
	 * @author Ruslan
	 */
	private void cleanUpOldErrorMesssage() {
		
		if (errorMessageTextField != null) {
			root.getChildren().remove(errorMessageTextField);
		}		
		
	}
	
	private void instantiateErrorMessage(String error) {
		errorMessageTextField = ErrorMessageTextFieldFactory.configureNewErrorMessageTextField(error);
		errorMessageTextField.setOnMouseClicked(e -> hideErrorMessage());
	}
	
	private void hideErrorMessage() {
		errorMessageTextField.setVisible(false);
	}

	private void configureErrorMessageOffsets() {
		errorMessageTextField.setTranslateY(DOUBLE.MENU_BAR_HEIGHT);
	}
	
	private void addErrorMessage() {
		root.getChildren().add(errorMessageTextField);
	}
}
