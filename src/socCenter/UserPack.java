package socCenter;

import java.util.List;
import data.DataHandler;

public class UserPack {
	
	private List<User> allUsers;
	
	public void addUser(User newUser){
		allUsers.add(newUser);
	}
	
	public void removeUser(User toRemove){
		allUsers.remove(toRemove);
	}
}
