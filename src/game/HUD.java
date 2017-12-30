package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {

	public static int MAXHEALTH = 300;
	public static int HEALTH = 300;
	public static int SCORE = 0;
	public static int LEVEL = 0;
	public static boolean GAMEOVER = false;

	public void tick() {
		
	}

	public void render(Graphics g) {

		// Score and level
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.PLAIN, 20));
		g.drawString("Score: " + SCORE, 15, 70);
		g.drawString("Level : " + LEVEL, 15, 92);

		// Health Bar
		g.setColor(Color.gray);
		g.fillRect(15, 15, 300, 32);
		if (HEALTH > 4 * MAXHEALTH / 5) {
			g.setColor(new Color(0,255,0));
		} else if (HEALTH > 3 * MAXHEALTH / 5) {
			g.setColor(new Color(0,128,0));
		} 
		else if (HEALTH > 2 * MAXHEALTH / 5) {
			g.setColor(new Color(128,128,0));
		}
		else if (HEALTH > 1 * MAXHEALTH / 5) {
			g.setColor(new Color(128,0,0));
		}
		else {
			g.setColor(new Color(255,0,0));
		}

		g.fillRect(15, 15, HEALTH, 32);
		g.setColor(Color.white);
		g.drawRect(15, 15, 300, 32);
	}
}
