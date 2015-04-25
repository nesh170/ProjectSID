package player;

import javafx.scene.control.ScrollPane;

public class Camera {

	private ScrollPane myView;
	private double focusX;
	private double focusY;
	
	public Camera(ScrollPane pane) {
		myView = pane;
	}
	
	public void updateCamera(double xTarget, double yTarget) {
		focusX = xTarget;
		focusY = yTarget;
	}
	
	public void focusOn(double xTarget, double yTarget) {
	    double yView = myView.getContent().getBoundsInLocal().getHeight();
	    double yBounds = myView.getViewportBounds().getHeight();
	    double xView = myView.getContent().getBoundsInLocal().getWidth();
	    double xBounds = myView.getViewportBounds().getWidth();
	    myView.setHvalue(myView.getHmax() * ((xTarget - 0.5 * xBounds) / (xView - xBounds)));
	    myView.setVvalue(myView.getVmax() * ((yTarget - 0.5 * yBounds) / (yView - yBounds)));
	}
	
	public void focus() {
		focusOn(focusX, focusY);
	}
	
}
