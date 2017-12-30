package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import GameObjects.ID;
import GameObjects.Missile;
import GameObjects.Ship;

public class KeyInput extends KeyAdapter {

	// Handler to add missiles
	Handler handler;

	// Ship variable for key events
	Ship ship;

	// Booleans to track which keys are pressed
	boolean up, left, right;

	// Constructor
	public KeyInput(Handler handler, Ship ship) {
		this.handler = handler;
		this.ship = ship;
	}

	// Key Pressed Events
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ESCAPE) {
			if (Game.gameState == Game.STATE.GAME) {
				Game.gameState = Game.STATE.PAUSED;
			} else {
				System.exit(0);
			}
		}

		if (key == KeyEvent.VK_ENTER && (Game.gameState == Game.STATE.MENU || Game.gameState == Game.STATE.PAUSED)) {
			Game.gameState = Game.STATE.GAME;
		}

		if (Game.gameState == Game.STATE.GAME) {

			if (key == KeyEvent.VK_UP) {
				up = true;
				Sound.thrustSound = true;
				ship.setSpeed(5);
				if (ship.decelerator.isRunning()) {
					ship.decelerator.stop();
				}
			}
			if (up) {
				if (!ship.accelerator.isRunning()) {
					ship.accelerator.start();
					ship.driftAngle = ship.getTheta();
				}
			}

			if (key == KeyEvent.VK_LEFT) {
				left = true;
				ship.setOmega(-3);
			}

			if (key == KeyEvent.VK_RIGHT) {
				right = true;
				ship.setOmega(3);
			}

			if (key == KeyEvent.VK_L) {
				while (handler.numObjects() > 1) {
					for (int i = 0; i < handler.numObjects(); i++) {
						if (handler.getObject(i).getId() != ID.SHIP) {
							handler.removeObject(handler.getObject(i));
						}
					}
				}
			}

			if (key == KeyEvent.VK_I) {
				if (!ship.invincible) {
					ship.setInvincible(Integer.MAX_VALUE);
				} else {
					ship.invincible = false;
				}
			}
		}

	}

	// Key Released Events
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (Game.gameState == Game.STATE.GAME) {
			if (key == KeyEvent.VK_UP) {
				if (ship.accelerator.isRunning()) {
					ship.accelerator.stop();
				}
				up = false;
				Sound.thrustSound = false;

			}
			if (key == KeyEvent.VK_LEFT) {
				left = false;
			}
			if (key == KeyEvent.VK_RIGHT) {
				right = false;
			}
			if (key == KeyEvent.VK_SPACE && !ship.invincible) {
				if (handler.numMissiles() < 4) {
					handler.addMissile(new Missile(ship.getPosX(), ship.getPosY(), ship.getTheta(), handler));
					Sound.play("fire.wav");
				}
			}
			if (!up) {
				if (!ship.decelerator.isRunning()) {
					ship.decelerator.start();
					ship.driftAngle = ship.getTheta();
				}
			}
			if (!left && !right) {
				ship.setOmega(0);
			}
		}

	}

}
