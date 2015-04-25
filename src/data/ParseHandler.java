package data;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import socCenter.User;

public class ParseHandler {
	
	public static String APP_ID = "M82RHsJGWvuQQAjCgUms444ujpCWPuJISu5FzZO0";
	public static String REST_KEY = "Q9ryEswqaCWXBGWMz6DaLwHJtZj49gg5lUU8sTxu";
	
	
	private static void setupParse() {
		System.out.println("setupParse(): initializing...");
		Parse.initialize(APP_ID, REST_KEY);
		
	}
	
	public static void saveUser(User toSave) {
		setupParse();
		ParseObject user = new ParseObject("User");
		user.add("userName", toSave.getName());
		user.add("password", toSave.getPass());
		user.add("imagePath", toSave.getImagePath());
		//toSave.setMyID(user.getObjectId());
		try {
			user.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		setupParse();
		ParseObject user = new ParseObject("User");
		user.add("userName", "df");
		user.add("password", "of");
		System.out.println("heo");
	}
	/*public User loadUser(String name){
		setupParse();
		
	}*/
}
