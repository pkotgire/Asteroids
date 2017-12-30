package GameObjects;

import java.awt.Graphics;
import java.awt.Polygon;

import game.Handler;

public class Saucer extends GameObject {

	// private Handler handler;

	// Constructor
	private Saucer(int posX, int posY, double theta, Handler handler) {
		super();
		// this.handler = handler;
		setPosX(posX);
		setPosY(posY);
		setTheta(theta);
		// updateXYPoints();
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public Polygon getPolygon() {
		// TODO Auto-generated method stub
		return null;
	}

}
