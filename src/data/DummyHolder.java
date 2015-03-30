package data;

import java.util.List;

public class DummyHolder {
	
	private String myString;
	private List<Dummy> myDummies;
	
	public DummyHolder(String str, List<Dummy> list) {
		myString = str;
		myDummies = list;
	}
	
	public void something() {
		System.out.println(myString + myDummies.toString());
	}

}
