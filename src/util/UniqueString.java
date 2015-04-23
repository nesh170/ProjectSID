package util;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * @author Leo
 * This util class has methods that allow a programmer to make a unique string given some
 * collection.
 *
 */

public class UniqueString {

	public static String makeUniqueKey(Collection<String> collection, String string) {
		if(collection.contains(string)) {
			String stringToMatch = string.substring(string.length()-1);
			if(stringToMatch.matches("[0-9]")) {
				return makeUniqueKey(collection,increaseString(string));
			}
			else {
				return makeUniqueKey(collection,string+"0");
			}
		}
		else {
			return string;
		}
	}
	
	private static String increaseString(String string) {
		int matchIndex = 0;
		for(int i=string.length()-1;i>=0;i--) {
			if(!(string.charAt(i)+"").matches("[0-9]")) {
				matchIndex = i + 1;
				break;
			}
		}
		String returnString = string.substring(0, matchIndex) + (Integer.parseInt(string.substring(matchIndex)) + 1);
		return returnString;
	}
}
