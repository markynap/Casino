package gameSetUps;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class ImageManager extends Applet{

	
/**
	 * 
	 */
	private static final long serialVersionUID = 12L;

public void paint(Graphics g) {
		
	}
	
	public Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = ImageManager.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch(Exception e) {
			System.out.println("Getting the image failed");
		}
		return tempImage;
	}
	
}
