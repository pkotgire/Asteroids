package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import game.Game;
import game.Handler;

public class Missile extends GameObject {

	// Physical Properties
	private final int LENGTH = 10; // Length of Missile

	// Kinematics
	int posX2, posY2;

	// Handler to remove missiles
	Handler handler;

	public Missile(int posX, int posY, double theta, Handler handler) {
		super();
		this.handler = handler;
		setPosX(posX);
		setPosY(posY);
		setTheta(theta);
		setSpeed(15);
		setId(ID.MISSILE);
		setColor(Color.RED);

		posX2 = (int) ((posX + (LENGTH * Math.cos(theta))));
		posY2 = (int) ((posY + (LENGTH * Math.sin(theta))));

		lifeTime(1000);
	}

	@Override
	public void tick() {

		// Update location based on velocities
		posX += velX;
		posX2 += velX;
		posY += velY;
		posY2 += velY;

		// Wrapping for missile
		if (posX < 0 && posX2 < 0) {
			posX += Game.WIDTH;
			posX2 += Game.WIDTH;

		}
		if (posX > Game.WIDTH && posX2 > Game.WIDTH) {
			posX -= Game.WIDTH;
			posX2 -= Game.WIDTH;
		}
		if (posY < 0 && posY2 < 0) {
			posY += Game.HEIGHT;
			posY2 += Game.HEIGHT;
		}
		if (posY > Game.HEIGHT && posY2 > Game.HEIGHT) {
			posY -= Game.HEIGHT;
			posY2 -= Game.HEIGHT;
		}

	}

	// Updates graphics for missile
	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.drawPolygon(getPolygon());
	}

	// Removes missile after specific amount of time
	public void lifeTime(int milliseconds) {
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				removeMissle();
			}
		}, milliseconds);
	}

	private void removeMissle() {
		handler.removeMissile(this);
	}

	@Override
	public Polygon getPolygon() {
		return new Polygon(new int[] {posX, posX + 1, posX2 + 1, posX2}, new int[] {posY,posY + 1, posY2 + 1, posY2}, 4);
	}
}
