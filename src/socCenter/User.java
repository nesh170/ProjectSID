package socCenter;

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
	private Integer myID;
	private String myName;
	private String myImagePath;
	private Sprite myDefaultSprite;
	private List<User> myFriends;
	
	public User(Integer id, String name){
		setMyID(id);
		setMyName(name);
	}
	
	//getters, setters
	public Integer getMyID() {
		return myID;
	}
	public void setMyID(Integer myID) {
		this.myID = myID;
	}
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	public String getImagePath() {
		return myImagePath;
	}
	public void setImagePath(String path) {
		this.myImagePath = path;
	}
	public Sprite getMyDefaultSprite() {
		return myDefaultSprite;
	}
	public void setMyDefaultSprite(Sprite myDefaultSprite) {
		this.myDefaultSprite = myDefaultSprite;
	}
	
	public void setAvatar(String path, int width, int height){
		Image img = new Image(path);
		myDefaultSprite.spriteImage().addImage(ImageToInt2DArray.convertImageTo2DIntArray(img, width, height));
		myImagePath = myDefaultSprite.getImagePath();
	}
	
	
	//social
	public void addFriend(User newFriend){
		myFriends.add(newFriend);
		newFriend.addFriend(this);
	}

	public void removeFriend(User friendToRemove){
		myFriends.remove(friendToRemove);
		friendToRemove.removeFriend(this);
	}

	
}
