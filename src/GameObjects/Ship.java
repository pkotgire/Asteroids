package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import game.Game;
import game.HUD;
import game.Handler;
import game.Sound;

public class Ship extends GameObject {

	// Handler to detect collision with asteroids
	Handler handler;

	// Physical Properties
	private final int SHIPBASE = 20;
	private final int SHIPHEIGHT = 36;
	public boolean invincible = false;

	// Starting position coordinates
	private final int[] startPoint1 = { (Game.WIDTH / 2 - SHIPBASE / 2), (Game.HEIGHT / 2) };
	private final int[] startPoint2 = { (Game.WIDTH / 2), (Game.HEIGHT / 2 - SHIPHEIGHT) };
	private final int[] startPoint3 = { (Game.WIDTH / 2 + SHIPBASE / 2), (Game.HEIGHT / 2) };

	// Current position coordinates
	private int[] currentPoint1 = startPoint1;
	private int[] currentPoint2 = startPoint2;
	private int[] currentPoint3 = startPoint3;

	// Starting angles (radians)
	private double startAngle1 = Math.PI;
	private double startAngle2 = 3 * Math.PI / 2;
	private double startAngle3 = 0;

	// Current angles (radians)
	private double currentAngle1 = startAngle1;
	private double currentAngle2 = startAngle2;
	private double currentAngle3 = startAngle3;

	// Center Point
	private int[] centerPoint = { Game.WIDTH / 2, Game.HEIGHT / 2 };

	// Constructor
	public Ship(Handler handler) {
		super();
		this.handler = handler;
		setPosX(currentPoint2[0]);
		setPosY(currentPoint2[1]);
		setTheta(3 * Math.PI / 2);
		setSpeed(0);
		setId(ID.SHIP);
		setColor(Color.CYAN);
		setInvincible(1500);
	}

	// Updates kinematics for ship
	@Override
	public void tick() {
		if (!HUD.GAMEOVER) {
			rotate();
			setPosX(currentPoint2[0]);
			setPosY(currentPoint2[1]);

			if (speed != 0) {
				move();
			}

			detectCollision();
			if (Sound.thrustSound) {
				if (!Sound.soundLoop) {
					Sound.soundLoop = true;
					Sound.playLoop("thrust.wav");
				}
			} else {
				if (Sound.soundLoop) {
					Sound.stop();
					Sound.soundLoop = false;
				}
			}
		}
	}

	// Updates graphics for ship
	@Override
	public void render(Graphics g) {
		g.setColor(color);
		if (invincible) {
			g.setColor(new Color(Game.randInt(0, 255), Game.randInt(0, 255), Game.randInt(0, 255)));
		}
		g.drawPolygon(getPolygon());
	}

	// Method to move the ship
	public void move() {

		// Move ship based on velocities
		currentPoint1[0] += velX;
		currentPoint2[0] += velX;
		currentPoint3[0] += velX;

		currentPoint1[1] += velY;
		currentPoint2[1] += velY;
		currentPoint3[1] += velY;

		// Wrapping for ship
		if (currentPoint1[0] < 0 && currentPoint2[0] < 0 && currentPoint3[0] < 0) {
			currentPoint1[0] += Game.WIDTH;
			currentPoint2[0] += Game.WIDTH;
			currentPoint3[0] += Game.WIDTH;

		}

		if (currentPoint1[0] > Game.WIDTH && currentPoint2[0] > Game.WIDTH && currentPoint3[0] > Game.WIDTH) {
			currentPoint1[0] -= Game.WIDTH;
			currentPoint2[0] -= Game.WIDTH;
			currentPoint3[0] -= Game.WIDTH;

		}

		if (currentPoint1[1] < 0 && currentPoint2[1] < 0 && currentPoint3[1] < 0) {
			currentPoint1[1] += Game.HEIGHT;
			currentPoint2[1] += Game.HEIGHT;
			currentPoint3[1] += Game.HEIGHT;

		}

		if (currentPoint1[1] > Game.HEIGHT && currentPoint2[1] > Game.HEIGHT && currentPoint3[1] > Game.HEIGHT) {
			currentPoint1[1] -= Game.HEIGHT;
			currentPoint2[1] -= Game.HEIGHT;
			currentPoint3[1] -= Game.HEIGHT;

		}

		// Update Center Point of ship
		updateCenter();
	}

	// Method to rotate the ship
	public void rotate() {

		// Change angles
		currentAngle1 += omega;
		currentAngle2 += omega;
		currentAngle3 += omega;

		// Set theta to currentAngle2
		setTheta(currentAngle2);

		// Rotate Ship
		currentPoint1[0] = (int) (centerPoint[0] + (SHIPBASE / 2) * Math.cos(currentAngle1));
		currentPoint1[1] = (int) (centerPoint[1] + (SHIPBASE / 2) * Math.sin(currentAngle1));

		currentPoint2[0] = (int) (centerPoint[0] + (SHIPHEIGHT) * Math.cos(currentAngle2));
		currentPoint2[1] = (int) (centerPoint[1] + (SHIPHEIGHT) * Math.sin(currentAngle2));

		currentPoint3[0] = (int) (centerPoint[0] + (SHIPBASE / 2) * Math.cos(currentAngle3));
		currentPoint3[1] = (int) (centerPoint[1] + (SHIPBASE / 2) * Math.sin(currentAngle3));

	}

	// Updates the center position of the ship
	public void updateCenter() {
		centerPoint[0] = (currentPoint1[0] + currentPoint3[0]) / 2;
		centerPoint[1] = (currentPoint1[1] + currentPoint3[1]) / 2;
	}

	// Detects if ship has collided with an asteroid
	public void detectCollision() {
		try {
			for (int x = 0; x < handler.numObjects(); x++) {
				GameObject temp = handler.getObject(x);

				if (temp != null && temp.getId() != ID.SHIP) {
					if (getIntersects(getPolygon(), temp.getPolygon()) && !invincible) {
						Sound.play("Crash.wav");
						if (temp.id == ID.LARGEASTEROID) {
							HUD.HEALTH -= 100;
						} else if (temp.id == ID.MEDIUMASTEROID) {
							HUD.HEALTH -= 50;
						} else {
							HUD.HEALTH -= 25;
						}

						setInvincible(500);
					}
				}
			}
		} catch (Exception e) {

		}
	}

	// Makes ship invincible for specified amount of time
	public void setInvincible(int milliseconds) {
		invincible = true;
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				invincible = false;
			}
		}, milliseconds);
	}

	// Resets ship to starting location
	public void reset() {

		// Restore angles
		currentAngle1 = startAngle1;
		currentAngle2 = startAngle2;
		currentAngle3 = startAngle3;

		// Restore center
		centerPoint[0] = Game.WIDTH / 2;
		centerPoint[1] = Game.HEIGHT / 2;

		// Redo Constructor initializations
		setPosX(currentPoint2[0]);
		setPosY(currentPoint2[1]);
		setTheta(3 * Math.PI / 2);
		setSpeed(0);
		setOmega(0);

		decelerator.stop();
		accelerator.stop();

		// Invincibility
		setInvincible(1500);
	}

	@Override
	public Polygon getPolygon() {
		return new Polygon(new int[] { currentPoint1[0], currentPoint2[0], currentPoint3[0] },
				new int[] { currentPoint1[1], currentPoint2[1], currentPoint3[1] }, 3);
	}

	// Decelerates and accelerates ship
	public Timer decelerator = new Timer(300, new Decelerator());
	public Timer accelerator = new Timer(100, new Accelerator());
	public double driftAngle;

	public class Decelerator implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (getSpeed() > 5) {
				setSpeed(getSpeed() - 1);
				updateVelocities(driftAngle);
				move();
			} else {
				setSpeed(0);
			}
		}
	}

	public class Accelerator implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (getSpeed() < 10) {
				setSpeed(getSpeed() + 1);
			}
			rotate();
			updateVelocities(getTheta());
			move();
		}
	}
}
