package util;

import java.util.List;
import java.util.Optional;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.GetCallback;

import socCenter.User;

public class ParseHandler {
	
	public static final String APP_ID = "M82RHsJGWvuQQAjCgUms444ujpCWPuJISu5FzZO0";
	public static final String REST_KEY = "Q9ryEswqaCWXBGWMz6DaLwHJtZj49gg5lUU8sTxu";
	
	public ParseHandler(){
		
	}
	
	private void setupParse() {
		System.out.println("setupParse(): initializing...");
		Parse.initialize(APP_ID, REST_KEY);
		
	}
	
	public void saveUser(User toSave) {
		
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
	
	public Optional<User> loadUserOptional(String name, String pass) {
		Optional<User> toReturn = Optional.ofNullable(loadUser(name, pass));
		if(!toReturn.isPresent()) return toReturn;
		return Optional.empty();
	}
	
	public User loadUser(String name, String pass){
		ParseQuery<ParseObject> query  = ParseQuery.getQuery("User");
		query.whereEqualTo("userName", name);
		List<ParseObject> users = null;
		try {
			users = query.find();

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String passServer = users.get(0).getString("password");
		String image = users.get(0).getString("imagePath");
		String objectId = users.get(0).getObjectId();
		User toReturn = new User(objectId, name, passServer, image);
		
		if(toReturn.validate(pass)) return toReturn;
		return null;
		
		
	}
	
	/*public static void main(String[] args){
		User dan = new User("", "jim", "soshy");
		saveUser(dan);
		System.out.println(dan.getMyID());
		
		User newU = loadUser("Dan");
		System.out.println(newU.getPass());
	}*/

}
