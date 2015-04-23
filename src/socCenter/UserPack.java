package socCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import data.DataHandler;

public class UserPack {
	
	private static String APP_ID = "M82RHsJGWvuQQAjCgUms444ujpCWPuJISu5FzZO0";
	private static String REST_KEY = "M0N668zWJ3n4PE8nPT6FfgYN5RkZ9y9YEs3eahst";
	
	private Map<String, User> allUsers;
	
	public UserPack(){
		
		allUsers = new HashMap<String, User>();
		
	}
	
	public static void setupParse() {
		System.out.println("setupParse(): initializing...");
		Parse.initialize(APP_ID, REST_KEY);
	}
	
	public void addUser(User newUser){
		allUsers.put(newUser.getName(), newUser);
	}
	
	public void removeUser(User toRemove){
		allUsers.remove(toRemove.getName());
	}
	
	public User logIn(String userName, String password) {
		User toTry = allUsers.get(userName);
		if(toTry.validate(password)){
			return toTry;
		} else {
			return null;
			
		}
	}
	
	public static void main(String[] args){
		//Parse.initialize("APP_ID", "REST_KEY");
		setupParse();
		ParseObject g = new ParseObject("User");
		g.put("userName", "Emm");
		g.put("password", "dona");
		try {
			g.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
