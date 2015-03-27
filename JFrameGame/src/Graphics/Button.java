package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Button {

	// General init stuff for a button
	// The buttons all start 100x100 at (100,100)
	// This should be changed by an init loop in Game.java
	public String text = "no_name";
	private int height = 100;
	private int width = 100;
	private int xPos = 100;
	private int yPos = 100;
	private boolean active = true;
	private BufferedImage img = null;

	// a default constructor
	public Button() {
		this.text = "";
	}

	// a nicer constructor
	public Button(String string, BufferedImage imgIn) {
		this.text = string;
		this.img = imgIn;
	}

	// The drawcall for the button, checks to see if there is an image and then
	// draws it if it exists within the confines of the button. IT AUTOMATICALLY
	// SCALES THE IMAGE! But does not retain proportions...yet
	public void drawButton(Graphics g) {
		if (active) {
			g.setColor(Color.BLACK);
			g.fillRect(xPos, yPos, height, width);
			if (this.img != null) {
				g.drawImage(img, xPos, yPos, xPos + width, yPos + height, 0, 0,
						img.getWidth(), img.getHeight(), null);
			}
		}
	}

	//A bunch of setters!
	public void setImg(BufferedImage imgIn) {
		this.img = imgIn;
	}

	public void updateSize(int x, int y) {
		height = x;
		width = y;
	}

	public void updatePos(int x, int y) {
		xPos = x;
		yPos = y;
	}

	public void setName(String string) {
		this.text = string;
	}

	//A bunch of getters!
	public int getXpos() {
		return xPos;
	}

	public int getYpos() {
		return yPos;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public BufferedImage getImage() {
		return img;
	}

	public String getString() {
		return text;
	}
}
