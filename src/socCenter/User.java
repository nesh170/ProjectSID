package socCenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import sprite.Sprite;
import sprite.SpriteImage;
import util.ImageToInt2DArray;

/**Single user in SID social center */
public class User {
	
	//instance vars
	private String myID;
	private String myName;
	private String myImagePath;
	private String myPassword; //TODO: make secure (like it even matters)
	private Sprite myDefaultSprite;
	private List<User> myFriends;
	private List<String> myComments;
	private File myImage;
	
	public User(String id, String name, String password){
		setMyID(id);
		setName(name);
		setPassword(password);
		setImagePath("");
	}
	
	public User(String id, String name, String password, String imagePath){
		this(id, name, password);
		setImagePath(imagePath);
		myImage = new File(imagePath);
		myComments = new ArrayList<String>();
	}
	//getters, setters
	public File getImage(){
		return myImage;
	}
	
	public String getMyID() {
		return myID;
	}
	public void setMyID(String myID) {
		this.myID = myID;
	}
	
	public void alterPassword(String oldPass, String newPass){
		
		if(this.validate(oldPass)) setPassword(newPass);
		
	}
	
	public String getName() {
		return myName;
	}
	public void setName(String myName) {
		this.myName = myName;
	}
	public String getImagePath() {
		return myImagePath;
	}
	public void setImagePath(String path) {
		this.myImagePath = path;
	}
	public Sprite getDefaultSprite() {
		return myDefaultSprite;
	}
	public void setDefaultSprite(Sprite defaultSprite) {
		this.myDefaultSprite = defaultSprite;
	}
	
	/*public void setAvatar(String path, int width, int height){
		Image img = new Image(path);
		myDefaultSprite.spriteImage().addImage(ImageToInt2DArray.convertImageTo2DIntArray(img, width, height));
		myImagePath = myDefaultSprite.getImagePath();
	}*/
	
	public String getPass(){
		//TODO:
		//figure out security
		return myPassword;
	}
	//social
	public void addFriend(User newFriend){
		myFriends.add(newFriend);
		newFriend.addFriend(this);
	}

	public void addComment(String comment) {
		if(!(comment == null)) myComments.add(comment);
	}
	public void removeFriend(User friendToRemove){
		myFriends.remove(friendToRemove);
		friendToRemove.removeFriend(this);
	}
	
	private void setPassword(String pass){
		myPassword = pass;
	}
	
	public boolean validate(String tryPass){
		return tryPass.equals(myPassword);
	}

	public boolean equals(User other){
		return other.getName().equals(this.getName());
	}
	
}
