package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;

public abstract class GameObject {

	// Kinematics
	protected int posX, posY; // Location on coordinate plane
	protected double theta; // Angle of movement (radians)
	protected double speed; // Speed in one direction
	protected int velX, velY; // Speed in respective direction
	protected double omega; // Rotational velocity (radians)

	// Attributes
	protected ID id; // Object Identification tag
	protected Color color; // Color of object

	// Constructor
	public GameObject(/*int posX, int posY, double theta, double speed, ID id, Color color*/) {
		/*setPosX(posX);
		setPosY(posY);
		setTheta(theta);
		setSpeed(speed);
		setId(id);
		setColor(color);*/
	}
	
	// Abstract methods to be overridden
	public abstract void tick(); // Updates kinematics for object
	public abstract void render(Graphics g); // Updates graphics for object
	public abstract Polygon getPolygon(); // Returns object polygon
	
	public void updateVelocities(double theta){
		velX = (int) (speed * Math.cos(theta));
		velY = (int) (speed * Math.sin(theta));
	}
	
	// Returns whether or not two polygons intersect
	protected boolean getIntersects(Polygon poly1, Polygon poly2){
		Area area = new Area(poly1);
		area.intersect(new Area(poly2));
		return !area.isEmpty();
	}
	
	// Getters and Setters
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
		updateVelocities(theta);
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public double getOmega() {
		return omega;
	}

	public void setOmega(double omega) {
		this.omega = Math.toRadians(omega);
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
