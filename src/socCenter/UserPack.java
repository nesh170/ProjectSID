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
	private static String REST_KEY = "Q9ryEswqaCWXBGWMz6DaLwHJtZj49gg5lUU8sTxu";
	
	private Map<String, User> allUsers;
	
	public UserPack(){
		
		allUsers = new HashMap<String, User>();
		
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
	
	
	
}
