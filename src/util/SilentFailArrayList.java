package util;

import java.util.ArrayList;

/**
 * Don't freak out and throw a NULL pointer exception on .get(int index)
 * 
 * @author Ruslan
 * @param <E>
 */
public class SilentFailArrayList<E> extends ArrayList<E> {

	/**
	 * @author Ruslan
	 * 
	 * @param int index
	 * @return Object at specified index, null if no Object
	 */
	@Override
	public E get(int index) {
		
		try {
			return super.get(index);
		} 
		
		catch(Exception E) {
			return null;
		}
		
	}
	
}
