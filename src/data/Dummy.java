package data;

import javafx.scene.shape.Circle;

public class Dummy {
	
	private String myName;
	private Circle myCircle;
	
	public Dummy(String str, Circle cir) {
		myName = str;
		myCircle = cir;
	}
	
	public void something() {
		System.out.println(myName + myCircle.toString());
	}

}