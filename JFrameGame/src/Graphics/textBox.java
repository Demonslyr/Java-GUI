package Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class textBox {

	// Inits an array list. It's a dynamic array in java! THough it's not really
	// utilized to its full here. The dynamic nature could be used for scrolling
	// text and easily handling strings that are too long for the window to
	// display
	ArrayList<String> textHist = new ArrayList<String>();

	// Default stuff for the text box size and location
	private int height = 75;
	private int width = 400;
	private int xPos = 100;
	private int yPos = 300;

	// Text box constructor. This requires that you tell it how many lines you
	// want it to start with.
	public textBox(int lines) {

		for (int i = 0; i < lines; i++) {
			textHist.add("");
		}
	}

	// This is used to populate the text "Queue" to display a list. it gets rid
	// of the oldest one. Adding more logic here could compensate for dialogue
	// that is too long for the window and must take either multiple lines or be
	// scrolled
	public void addNewLine(String input) {
		for (int i = 0; i < textHist.size() - 1; i++) {
			textHist.set(i, textHist.get(i + 1));
		}
		textHist.set(textHist.size() - 1, input);
	}

	//This Method simply draws whats in the array
	public void drawLines(Graphics g) {

		g.fillRect(xPos - 10, yPos - 20, width, height);
		g.setColor(Color.WHITE);
		g.setFont(new Font("default", Font.BOLD, 16));
		for (int i = 0; i < textHist.size(); i++) {
			g.drawString(textHist.get(i), 100, (i * 15) + 300);
		}
	}
}
