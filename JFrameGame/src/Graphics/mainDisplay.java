package Graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class mainDisplay {

	//General init things
	public String text = "no_name";
	private int height = 100;
	private int width = 100;
	private int left = 100;
	private int top = 100;
	private boolean active = true;
	private BufferedImage img = null;

	//main display constructor
	public mainDisplay() {

	}
	
	//set the image of the main display
	public void setImage(BufferedImage imgIn) {
		img = imgIn;
	}

	//set the dimensions and position of the main display
	public void setDimensions(int top, int left, int height, int width) {
		this.top = top; //pixels from top of JFrame
		this.left = left; //pixels from left of JFrame
		this.height = height; //# of pixels tall
		this.width = width; //# of pixels wide
	}

	//Draw the display
	public void drawDisplay(Graphics g) {
		if (img != null) {
			g.drawImage(img, left, top, width, height, 0, 0, img.getWidth(),
					img.getHeight(), null);
		}
	}

	//A bunch of getters!
	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	//This clears the display image
	public void clearDisp(){
		img=null;
	}
}
