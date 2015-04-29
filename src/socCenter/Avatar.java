package socCenter;

public class Avatar {
	
	private String myURL;
	private String myName;
	
	public Avatar(String name, String URL) {
		this.myURL = URL;
		this.myName = name;
	}
	
	public Avatar(User user){
		this.myURL = user.getImagePath();
		this.myName = user.getName();
	}
	public String getURL() {
		return myURL;
	}
	public void setURL(String uRL) {
		myURL = uRL;
	}
	public String getName() {
		return myName;
	}
	public void setName(String name) {
		myName = name;
	}
}
