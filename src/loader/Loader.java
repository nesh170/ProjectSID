package loader;

public interface Loader {

	/**
	 * return file path for selected file
	 */
	String getPath();
	
	/**
	 * Converting object to xml file
	 * @param o
	 * @return
	 */
	 String toXML(Object o);

	/**
	 * Converting string xml name to object
	 * @param xml
	 * @return
	 */
	Object fromXML(String xml);
}

