package jframegame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import Graphics.Button;
import Graphics.mainDisplay;
import Graphics.textBox;

public class Game extends Canvas implements Runnable {
	// /not important at the moment
	private static final long serialVersionUID = 1L;

	// Static variables for the JFrame
	public static final int WIDTH = 200;
	public static final int HEIGHT = WIDTH / 4 * 3;
	public static final int SCALE = 3;
	public static final String NAME = "Game";
	public static final int LINES = 4;
	public int mouse_x;
	public int mouse_y;
	public boolean mouse_pressed;
	public boolean mouse_released;
	//
	// Below declares the classes as objects
	public Button[] button = new Button[4];
	public textBox text = new textBox(LINES);
	public mainDisplay disp = new mainDisplay();

	// Below, a buffered image...
	public BufferedImage img;

	// stuff for the frame rate limitation
	public boolean running = false;
	public int updateCount = 0;
	// decalres the JFrame
	public JFrame frame;

	// Below is the game constructor
	public Game() {

		// Setting up the mouse listeners. These are needed for click events!
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouse_x = e.getX();
				mouse_y = e.getY();
				for (int i = 0; i < button.length; i++) {
					if (isMouseIn(i)) {
						switchDisp(i);
						break;
					}
				}
			}
		});

		// motion limiters update the mouse position when it is moved or dragged
		// They also execute whenever the mouse is moved or dragged
		//
		// Mover catcher
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouse_x = e.getX();
				mouse_y = e.getY();
			}
		});

		// Dragger catcher
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mouse_x = e.getX();
				mouse_y = e.getY();
			}
		});

		// Info about dimensions of the JFrame
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		// This below is JFrame initialization
		frame = new JFrame(NAME);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		// This sets the dimensions of the "screen" in the middle
		disp.setDimensions((getHeight() / 13), getWidth() / 4,
				(getHeight() / 2), (getWidth() / 4) * 3);

		// initializing buttons
		for (int i = 0; i < 4; i++) {
			button[i] = new Button();
		}

		// These blocks load in the images for the buttons
		try {
			button[0].setImg(ImageIO.read(new File("res/player_ship.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			button[1].setImg(ImageIO.read(new File("res/enemy_ship.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			button[2].setImg(ImageIO.read(new File("res/enemy_ship_mb0.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			button[3].setImg(ImageIO.read(new File("res/JFrameGame.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Here is where the text for the button is loaded
		button[0].setName("Here is an image of the Player ship presented!");
		button[1].setName("This is an image of the Enemy ship Presented!");
		button[2].setName("Before you is the Mini Boss ship!");
		button[3].setName("This is a spritesheet!");

		// makes the frame visible, modify this to hide it...idk why...
		frame.setVisible(true);
	}

	// starts the game in its own thread so the processor barely notices it
	// (unless you unlock the frame rate)
	public synchronized void start() {
		running = true;
		new Thread(this).start();

	}

	// something to do with stopping idr
	public synchronized void stop() {
		running = false;
	}

	public void run() {
		/*
		 * The mess below is for time-keeping purposes, it limits how fast the
		 * program can execute.~60 Updates per second and ~500 Frames per second
		 * *
		 */

		long lastTime = System.nanoTime();
		double nsPerUpdate = 1000000000D / 60D;

		int updates = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerUpdate;
			lastTime = now;
			boolean shouldRender = true;// set this to false to force 60 FPS

			while (delta >= 1) {
				updates++;
				update();// this is when an update cycle happens
				delta -= 1;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
				// Use this as a knob to adjust frame speed. Bigger number
				// means longer sleep and less frames per second
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
				frames++;
				draw();// this is when the draw cycle happens
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				//System.out.println("Updates " + updates + ", frames " + frames);
				frames = 0;
				updates = 0;
			}
		}

	}

	// update things in this function so that everything is updated before the
	// screen is drawn
	// TBH the mouse event stuff should probably be moved here and be set off by
	// flags, set true by the mouse events, if the program becomes "large"
	public void update() {
		updateCount++;
		// TODO: move the for loop below to the initialization section or make a
		// method to adjust button placement in the event that the number of
		// buttons changes

		// The purpose of this for loop is to place n buttons "evenly" along the
		// bottom of the screen
		for (int i = 0; i < button.length; i++) {
			button[i].updatePos(
					((i + 1) * (getWidth() / ((button.length) + 1)))
							- (button[i].getWidth() / 2),
					((getHeight() / 16) * 13));
		}
	}

	// This is the draw method! put all your calls to draw between the
	// "Graphics g" line and the "g.dispose" line
	public void draw() {

		// This sets up triple buffering for frames and reduces tearing if
		// things get weird
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		// bellow sets the buffer strat to the graphics canvas, everything
		// "drawn" is added to the buffer, then bs.show() draws the buffer to
		// the screen
		Graphics g = bs.getDrawGraphics();
		// Below sets the paint color to white then paints a white rectangle in
		// the shape of the window
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Below draws all the buttons that have been made
		for (int i = 0; i < button.length; i++) {
			button[i].drawButton(g);
		}

		// Below is the drawcall for the text box Queue
		text.drawLines(g);

		// Below is the call to draw the "display" (the big picture)
		disp.drawDisplay(g);

		// cleans up the canvas
		g.dispose();

		// draws the buffer
		bs.show();

	}

	//Function to determine if the mouse is over a button
	public boolean isMouseIn(int i) {
		if (button[i].getXpos() < this.mouse_x
				&& this.mouse_x < (button[i].getXpos() + button[i].getWidth())) {
			if (button[i].getYpos() < this.mouse_y
					&& this.mouse_y < (button[i].getYpos() + button[i]
							.getHeight())) {
				return true;
			}
		}
		return false;
	}

	//This function switched the display image and text to the one of the button index provided
	public void switchDisp(int i) {
		disp.setImage(button[i].getImage());
		text.addNewLine(button[i].getString());
		// TODO: Change Display to the button picture and text
	}

	//This is the main entry point to the program
	public static void main(String[] args) {
		new Game().start();
	}

}
