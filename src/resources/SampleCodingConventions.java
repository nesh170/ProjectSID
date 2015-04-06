package resources;


/**
 * Descriptive description here. NB the spacing between large categories (two lines), minor categories (one line)
 * 
 * @author Ruslan
 */
public class SampleCodingConventions {

	// Static Variables


	// Instance Variables


	// Getters & Setters


	// Static Methods


	// Constructor & Helpers


	// All other instance methods
	// Public
	public void doNothing() {
		
		if (true) {
			
			// Do nothing with spacing! Yay!
			// I'm a two-liner. I get a new line on top and bottom!
			
		}
		
		else {
			// I'm a one-liner. I get no spacing.
		}
		
		bobWithBooleanWithString(false, "hullo");
		
	}
	
	// Private
	private Object bobWithBooleanWithString(boolean noWay, String yeahWay) {

		Object returnObject = null;
		
		if (noWay) {
			System.out.println(yeahWay);
		}
		
		else {
			returnObject = new Object();
		}
		
		return returnObject;
		
	}
	
}