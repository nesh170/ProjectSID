package util;

import java.util.List;
import java.util.Optional;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import socCenter.User;

public class ParseHandler {
	
	public static final String APP_ID = "M82RHsJGWvuQQAjCgUms444ujpCWPuJISu5FzZO0";
	public static final String REST_KEY = "Q9ryEswqaCWXBGWMz6DaLwHJtZj49gg5lUU8sTxu";
	
	public ParseHandler(){
		
	}
	
	public void saveUser(User toSave) {
		ParseObject user;
		if(isPresent(toSave.getName())) {
			user = userParseObject(toSave.getName(), toSave.getPass());
		} else {
			user = new ParseObject("User");
		}
		//.setObjectId(toSave.getName());
		user.put("userName", toSave.getName());
		user.put("password", toSave.getPass());
		user.put("imagePath", toSave.getImagePath());
		
		try {
			user.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			 DialogUtil.displayMessage("Error in Saving User", "Saving User");
		}
		
		toSave.setMyID(user.getObjectId());
		
	}
	
	public Optional<User> loadUserOptional(String name, String pass) {
		Optional<User> toReturn = Optional.ofNullable(loadUser(name, pass));
		if(!toReturn.isPresent()) return toReturn;
		return Optional.empty();
	}
	
	public User loadUser(String name, String pass){
		ParseObject userParse = userParseObject(name, pass);
		String passServer = userParse.getString("password");
		String image = userParse.getString("imagePath");
		String objectId = userParse.getObjectId();
		User toReturn = new User(objectId, name, passServer, image);
		if(toReturn.validate(pass)){
			System.out.println("validated!");
			return toReturn;
		} else {
			return null;
		}
		
		
	}
	
	private ParseObject userParseObject(String name, String pass) {
		ParseQuery<ParseObject> query  = ParseQuery.getQuery("User");
		query.whereEqualTo("userName", name);
		List<ParseObject> users = null;
		try {
			users = query.find();

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
		}
		if(users != null) {
			return users.get(0);
		} 
		else {
			return null;
		}
	}
	
	private boolean isPresent(String name) {
		ParseQuery<ParseObject> query  = ParseQuery.getQuery("User");
		query.whereEqualTo("userName", name);
		List<ParseObject> users = null;
		try {
			users = query.find();

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
		}
		return (users == null) ? false : true;
		
	}
	

}
