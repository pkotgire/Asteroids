package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import game.Game;
import game.HUD;
import game.Handler;
import game.Sound;

public class Asteroid extends GameObject {

	// Physical Properties
	private int radius; // Radius of a Large Asteroid
	private int scoreValue; // Score for destroying
	private String explosionSound; // Sound of a Large Asteroid

	// Handler to detect collisions
	private Handler handler;

	// Asteroid points
	private int nPoints = 12;
	private int[] xPoints = new int[nPoints];
	private int[] yPoints = new int[nPoints];

	// Constructor
	private Asteroid(int posX, int posY, double theta, Handler handler) {
		super();
		this.handler = handler;
		setPosX(posX);
		setPosY(posY);
		setTheta(theta);
		updateXYPoints();
	}

	// Update kinematics for Asteroid
	@Override
	public void tick() {

		if (velX == 0 || velY == 0){
			setTheta(Game.randDouble(0, Math.PI));
			updateVelocities(theta);
		}
		
		// Move Asteroid based on velocities
		posX += velX;
		posY += velY;
		updateXYPoints();

		// Wrapping for x-axis
		if (posX + radius < 0) {
			posX += Game.WIDTH;
		} else if (posX > Game.WIDTH) {
			posX -= Game.WIDTH;
		}

		// Wrapping for y-axis
		if (posY > Game.HEIGHT) {
			posY -= Game.HEIGHT;
		} else if (posY + radius < 0) {
			posY += Game.HEIGHT;
		}

		detectCollision();

	}

	// Update graphics for Asteroid
	@Override
	public void render(Graphics g) {
		g.setColor(color);

		// g.drawOval(posX, posY, radius, radius);

		g.drawPolygon(xPoints, yPoints, nPoints);

	}

	// Detects if asteroid has collided with missile
	public void detectCollision() {
		for (int x = 0; x < handler.numMissiles(); x++) {
			try {
				Missile temp = handler.getMissile(x);
				if (getIntersects(getPolygon(), temp.getPolygon())) {
					explode(temp);
				}
			} catch (Exception e) {

			}
		}
	}

	// Explodes Asteroid
	public void explode(Missile missile) {

		// Remove missile
		handler.removeMissile(missile);

		// Play sound and increment score
		Sound.play(explosionSound);
		HUD.SCORE += scoreValue;

		// Create two new asteroids
		double theta = Game.randDouble(0, Math.PI);

		// Create new Asteroids
		if (id == ID.LARGEASTEROID) {
			handler.addObject(mediumAsteroid(posX, posY, theta, handler));
			handler.addObject(mediumAsteroid(posX, posY, theta + Math.PI, handler));
		}

		if (id == ID.MEDIUMASTEROID) {
			handler.addObject(smallAsteroid(posX, posY, theta, handler));
			handler.addObject(smallAsteroid(posX, posY, theta + Math.PI, handler));
		}

		// Remove original asteroid
		handler.removeObject(this);
	}

	public static Asteroid largeAsteroid(int posX, int posY, double theta, Handler handler) {
		Asteroid asteroid = new Asteroid(posX, posY, theta, handler);
		asteroid.id = ID.LARGEASTEROID;
		asteroid.color = Color.ORANGE;
		asteroid.explosionSound = "bangLarge.wav";
		asteroid.scoreValue = 20;
		asteroid.radius = 80;
		asteroid.setSpeed(3);
		return asteroid;
	}

	public static Asteroid mediumAsteroid(int posX, int posY, double theta, Handler handler) {
		Asteroid asteroid = new Asteroid(posX, posY, theta, handler);
		asteroid.id = ID.MEDIUMASTEROID;
		asteroid.color = Color.GREEN;
		asteroid.explosionSound = "bangMedium.wav";
		asteroid.scoreValue = 50;
		asteroid.radius = 60;
		asteroid.setSpeed(4);
		return asteroid;
	}

	public static Asteroid smallAsteroid(int posX, int posY, double theta, Handler handler) {
		Asteroid asteroid = new Asteroid(posX, posY, theta, handler);
		asteroid.id = ID.SMALLASTEROID;
		asteroid.color = Color.PINK;
		asteroid.explosionSound = "bangSmall.wav";
		asteroid.scoreValue = 100;
		asteroid.radius = 40;
		asteroid.setSpeed(5);
		return asteroid;
	}

	// Moves the asteroid based on xPos and yPos
	public void updateXYPoints() {

		if (id == ID.SMALLASTEROID) {
			xPoints = new int[] { posX, posX + 18, posX + 31, posX + 31, posX + 21, posX + 31, posX + 21, posX + 15,
					posX + 2, posX - 11, posX - 11, posX + 3 };

			yPoints = new int[] { posY, posY, posY + 8, posY + 14, posY + 19, posY + 26, posY + 37, posY + 33,
					posY + 37, posY + 24, posY + 9, posY + 8 };

		} else if (id == ID.MEDIUMASTEROID) {
			xPoints = new int[] { posX, posX + 36, posX + 62, posX + 62, posX + 42, posX + 62, posX + 42, posX + 30,
					posX + 4, posX - 22, posX - 22, posX + 6 };

			yPoints = new int[] { posY, posY, posY + 16, posY + 28, posY + 38, posY + 52, posY + 74, posY + 66,
					posY + 74, posY + 48, posY + 18, posY + 16 };
		} else {
			xPoints = new int[] { posX, posX + 72, posX + 124, posX + 124, posX + 84, posX + 124, posX + 84, posX + 60,
					posX + 8, posX - 44, posX - 44, posX + 12 };

			yPoints = new int[] { posY, posY, posY + 32, posY + 56, posY + 76, posY + 104, posY + 148, posY + 132,
					posY + 148, posY + 96, posY + 36, posY + 32 };
		}
	}

	// Returns Asteroid polygon
	@Override
	public Polygon getPolygon() {
		updateXYPoints();
		return new Polygon(xPoints, yPoints, nPoints);
	}
}
