package screen;

/**
 * 
 * @author Ruslan
 * 
 */

public interface ScreenControllerInterface {

	// Overall width, height
	public double width();
	public double height();
	
	// For instantiating new Screen instances
	public double newScreenWidth();
	public double newScreenHeight();
	
}
