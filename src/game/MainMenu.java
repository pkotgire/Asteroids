package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends MouseAdapter {

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
		g.drawString("Asteroids", 580, 130);

		// Play Button
		g.drawRect(500, 250, 500, 50);
		g.setFont(new Font("SansSerif", Font.PLAIN, 40));
		g.drawString("Play Game", 650, 290);

		// Asteroid test
		int[] xPoints = new int[] {280,318,351,351,329,351,329,313,283,256,256,286};
		int[] yPoints = new int[] {216,216,234,246,260,277,299,287,298,271,235,235};
		g.drawPolygon(xPoints, yPoints, 12);
		
		

	}
}
