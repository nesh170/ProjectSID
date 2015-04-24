package sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

import data.DataHandler;
import javafx.scene.image.*;

public class ImageManager {

	private Map<String, Image> imageMap = new HashMap<>();

	public Image getImageForString(String path) throws MalformedURLException, IOException{
		path = System.getProperty("user.dir") + "/" + path;
		if(path == null){
			return null;
		}
		if(imageMap.containsKey(path)){
			return imageMap.get(path);
		}
		if(path.charAt(0) == ':'){ //filename cannot contain : in any system
			return makeTextFieldImage(path); 
		}
		Image img = DataHandler.fileToImage(new File(path));
		imageMap.put(path, img);
		return img; 
	}

	private Image makeTextFieldImage(String path) throws IOException {
		String text = path.substring(1, path.length());
		BufferedImage buffImg;
		buffImg = ImageIO.read(new File(System.getProperty("user.dir") + "/src/whiteSquare.png"));
		Graphics2D g2d = buffImg.createGraphics();
		g2d.setPaint(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.PLAIN, 20));
		FontMetrics fm = g2d.getFontMetrics();
		int x = 0;
		int y = fm.getHeight() + 10;
		g2d.drawString(text, x, y);
		g2d.dispose();
		Image img = createImage(buffImg);
		imageMap.put(path, img);
		return img;
	}

	public static javafx.scene.image.Image createImage(BufferedImage image) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write((RenderedImage) image, "png", out);
		out.flush();
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		return new javafx.scene.image.Image(in);
	}

}
