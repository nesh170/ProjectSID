package resources;


/**
 * Descriptive description here. NB the spacing between large categories (two lines), minor categories (one line)
 * 
 * @author Ruslan
 */
public class SampleCodingConventions {

	// Static Variables


	// Instance Variables
	private Object object;		// named a lowercase version of the class if possible

	// Getters & Setters
	public Object object() {
		return this.object;		// named after the variable if possible
	}

	// Static Methods


	// Constructor & Helpers
	public SampleCodingConventions() {
		
		this(null);			// constructors ALWAYS get extra spacing. even one-liners!
		
	}
	
	public SampleCodingConventions(Object object) {
		
		// even though we can do this.object = object, we will write a configureMethod for self-reading code
		configureObject(object);
		
	}
	
	private void configureObject(Object object) {			// private constructor helpers! woohoo!
		
		this.object = object;
		// Other logic associated with setup here.
		
	}
	

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