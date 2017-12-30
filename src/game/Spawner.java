package game;

import javax.swing.JOptionPane;

import GameObjects.Asteroid;
import GameObjects.Ship;

public class Spawner {

	private Handler handler;
	private Ship ship;

	public Spawner(Handler handler, Ship ship) {
		this.handler = handler;
		this.ship = ship;
	}

	public void tick() {
		if (HUD.HEALTH <= 0 && !HUD.GAMEOVER) {
			resetGame();
		} else if (!HUD.GAMEOVER) {
			if (handler.numObjects() == 0) {
				handler.addObject(ship);
				ship.setSpeed(0);
			}
			if (handler.numObjects() == 1) {
				HUD.LEVEL++;
				if (HUD.LEVEL > 1) {
					HUD.SCORE += HUD.HEALTH / 2;
					HUD.HEALTH = Game.clamp(HUD.HEALTH + 100, 0, 300);
					ship.reset();
					handler.clearMissiles();
				}
				Sound.play("extraShip.wav");
				generateAsteroids(HUD.LEVEL);
				ship.setInvincible(1500);
			}
		}
	}

	// Adds specified number of asteroids onto the field
	public void generateAsteroids(int amt) {
		for (int i = 0; i < amt; i++) {
			int posX = Game.randInt(80, Game.WIDTH - 80);
			int posY = Game.randInt(80, Game.HEIGHT - 80);
			double theta = Game.randDouble(0, 2 * Math.PI);
			handler.addObject(Asteroid.largeAsteroid(posX, posY, theta, handler));
		}
	}

	// Resets the Game
	public void resetGame() {
		HUD.GAMEOVER = true;
		Sound.stop();
		handler.removeObject(ship);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, "Game Over!");
				handler.clearAll();
				HUD.HEALTH = HUD.MAXHEALTH;
				HUD.SCORE = 0;
				HUD.LEVEL = 0;
				Game.gameState = Game.STATE.MENU;
				HUD.GAMEOVER = false;
				ship.reset();
			}
		}, 10);

	}
}
