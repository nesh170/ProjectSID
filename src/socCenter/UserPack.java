package socCenter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import data.DataHandler;

public class UserPack {
	
	private Map<String, User> allUsers;
	
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
