package data;

import java.util.List;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.GetCallback;

import socCenter.User;

public class ParseHandler {
	
	public static String APP_ID = "M82RHsJGWvuQQAjCgUms444ujpCWPuJISu5FzZO0";
	public static String REST_KEY = "Q9ryEswqaCWXBGWMz6DaLwHJtZj49gg5lUU8sTxu";
	
	//private ParseObject returnObj;
	
	private static void setupParse() {
		System.out.println("setupParse(): initializing...");
		Parse.initialize(APP_ID, REST_KEY);
		
	}
	
	public static void saveUser(User toSave) {
		setupParse();
		ParseObject user = new ParseObject("User");
		//.setObjectId(toSave.getName());
		user.put("userName", toSave.getName());
		user.put("password", toSave.getPass());
		user.put("imagePath", toSave.getImagePath());
		try {
			user.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		toSave.setMyID(user.getObjectId());
		
	}
	
	public static User loadUser(String name){
		ParseQuery<ParseObject> query  = ParseQuery.getQuery("User");
		query.whereEqualTo("userName", name);
		List<ParseObject> users = null;
		try {
			users = query.find();

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String pass = users.get(0).getString("password");
		String image = users.get(0).getString("imagePath");
		String objectId = users.get(0).getObjectId();
		return new User(objectId, name, pass, image);
		
		
		
	}
	
	public static void main(String[] args){
		User dan = new User("", "jim", "soshy");
		saveUser(dan);
		System.out.println(dan.getMyID());
		
		User newU = loadUser("dan");
		System.out.println(newU.getPass());
	}
	/*public User loadUser(String name){
		setupParse();
		
	}*/
}
