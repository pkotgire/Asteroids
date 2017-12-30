package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import GameObjects.Ship;

public class Game extends Canvas implements Runnable {

	// Boolean to print fps
	final boolean FPS = true;

	// Width and Height
	public static final int WIDTH = 1500;
	public static final int HEIGHT = 900;

	// Background Color
	private final Color bgColor = Color.BLACK;

	// Thread to store state of game and boolean to check if thread is running
	private Thread thread;
	private boolean running = false;

	// Enum for State of Game
	public static enum STATE {
		MENU, PAUSED, GAME;
	}

	public static STATE gameState = STATE.MENU;

	// Menus
	private MainMenu mainMenu = new MainMenu();
	private PauseMenu pauseMenu = new PauseMenu();

	// Handler
	private Handler handler = new Handler();

	// Ship (Player)
	Ship ship = new Ship(handler);

	// Key Listener
	KeyInput input = new KeyInput(handler, ship);

	// Heads Up Display
	HUD hud = new HUD();

	// Spawner (Increases level)
	Spawner spawner = new Spawner(handler, ship);

	// Main method call constructor to start game
	public static void main(String[] args) {
		new Game();
	}

	// Constructor
	public Game() {

		// Put Game inside a Window
		new Window(WIDTH, HEIGHT, "Asteroids", this);

		// Attach the Key Listener
		addKeyListener(input);
		requestFocus();

		// Mouse Listener
		addMouseListener(mainMenu);
		addMouseListener(pauseMenu);
	}

	// Run method starting game loop
	@Override
	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				if (FPS) {
					System.out.println("FPS: " + frames);
				}
				frames = 0;
			}

		}
		stop();
	}

	// Updates game logistics
	private void tick() {

		if (gameState == STATE.GAME) {
			handler.tick();
			hud.tick();
			spawner.tick();
		} else if (gameState == STATE.MENU) {
			mainMenu.tick();
		} else if (gameState == STATE.PAUSED) {
			pauseMenu.tick();
		}
	}

	// Update game graphics
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(bgColor);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		if (gameState == STATE.GAME) {
			handler.render(g);
			hud.render(g);
		} else if (gameState == STATE.MENU) {
			mainMenu.render(g);
		} else if (gameState == STATE.PAUSED) {
			pauseMenu.render(g);
		}

		g.dispose();
		bs.show();
	}

	// Method to start game
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	// Method to stop game
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to clamp a variable to specific bounds
	public static int clamp(int var, int min, int max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}

	// Method to generate random integer in a given range
	public static int randInt(int min, int max) {
		Random generator = new Random();
		int randomNum = generator.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	// Method to generate random double in a given range
	public static double randDouble(double min, double max) {
		Random generator = new Random();
		double randomNum = min + (max - min) * generator.nextDouble();
		return randomNum;
	}

}
