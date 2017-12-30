package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PauseMenu extends MouseAdapter {

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		// Play Button Actions
		if (x >= 500 && x <= 1000 && y >= 250 && y <= 300) {
			Game.gameState = Game.STATE.GAME;
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void tick() {

	}

	public void render(Graphics g) {

		// Color
		g.setColor(Color.white);

		// Menu Title
		g.setFont(new Font("SansSerif", Font.PLAIN, 80));
		g.drawString("Game Paused", 490, 130);

		// Resume Button
		g.drawRect(500, 250, 500, 50);
		g.setFont(new Font("SansSerif", Font.PLAIN, 40));
		g.drawString("Resume", 670, 290);

	}
}
