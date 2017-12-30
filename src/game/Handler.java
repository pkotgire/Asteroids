package game;

import java.awt.Graphics;
import java.util.LinkedList;

import GameObjects.GameObject;
import GameObjects.Missile;

public class Handler {

	// LinkedList store all current game objects
	LinkedList<GameObject> objectList = new LinkedList<GameObject>();
	LinkedList<Missile> missileList = new LinkedList<Missile>();

	// Calls tick method for each object
	public void tick() {

		// objectList
		for (int i = 0; i < objectList.size(); i++) {
			try {
				objectList.get(i).tick();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// missleList
		for (int i = 0; i < missileList.size(); i++) {
			try {
				missileList.get(i).tick();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Calls render method for each object
	public void render(Graphics g) {
		for (int i = 0; i < objectList.size(); i++) {

			// objectList
			try {
				objectList.get(i).render(g);
			} catch (Exception e) {

			}

		}
		for (int i = 0; i < missileList.size(); i++) {
			// missileList
			try {
				missileList.get(i).render(g);
			} catch (Exception e) {

			}
		}
	}

	// Adds object to linked list
	public void addObject(GameObject object) {
		objectList.add(object);
	}

	// Removes object from linked list
	public void removeObject(GameObject object) {
		objectList.remove(object);
	}

	// Adds missile to missile list
	public void addMissile(Missile missile) {
		missileList.add(missile);
	}

	// Removes missile from missile list
	public void removeMissile(Missile missile) {
		missileList.remove(missile);
	}

	public int numObjects() {
		return objectList.size();
	}

	public int numMissiles() {
		return missileList.size();
	}

	public Missile getMissile(int index) {
		return missileList.get(index);
	}
	
	public GameObject getObject(int index) {
		return objectList.get(index);
	}
	
	public void clearAll(){
		objectList.clear();
		missileList.clear();
	}
	
	public void clearMissiles(){
		missileList.clear();
	}
}
