/*
 * Class that stores an (x, y) value
 * While we could use the built-in Point class, we utilize this class for more than just simply storing a position
 * This is also used for directional movement in addition to positions, so it is better to have a class that is more suited to that use case
 */

import java.io.*;

public class Vector2 implements Serializable {

	// The main fields
	public int x = 0;
	public int y = 0;

	// Constructor from (x,y) values
	public Vector2(int x, int y) {
		set(x, y);
	}

	// Default Constructor
	public Vector2() {
		this(0, 0);
	}

	// Set the current value of the Vector to 2 ints corresponding to (x,y) 
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Set the position of this vector to that of another Vector
	public void set(Vector2 vector) {
		this.x = vector.x;
		this.y = vector.y;
	}

	// Add another Vector to this
	public void add(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
	}

	// Subtract another Vector from this
	public void subtract(Vector2 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
	}

	// Conver this Vector to the form of "(x,y)"
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}

	// Return a direction vector corresponding the direction that will reduce the distance from this to a given coordinate if moved towards
	public Vector2 nearestDirectionTo(Vector2 point) {
		int lowestIndex = 0;
		// This can be any really high number
		double lowest = Double.MAX_VALUE;

		// Find the direction that has the lowest distance to the desired point
		for (int i = 0; i < DIRECTIONS.length; i++) {
			double distance = distance(difference(this, DIRECTIONS[i]), point);
			if (distance < lowest) {
				lowest = distance;
				lowestIndex = i;
			}
		}
		return DIRECTIONS[lowestIndex];	
	}

	// Check for equality
	public static boolean equals(Vector2 v1, Vector2 v2) {
		return (v1.x == v2.x) && (v1.y == v2.y);
	}

	// Return a new Vector2 that is the sum of two vectors
	public static Vector2 sum(Vector2 a, Vector2 b) {
		return new Vector2(a.x + b.x, a.y + b.y);
	}

	// Return a new Vector2 that is the difference of two vectors
	public static Vector2 difference(Vector2 a, Vector2 b) {
		return new Vector2(a.x - b.x, a.y - b.y);
	}

	// Distance formula
	public static double distance (Vector2 a, Vector2 b) {
		return Math.sqrt(Math.abs(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2)));
	}

	// Cardinal direction constants
	public static final Vector2 UP = new Vector2(0, 1);
	public static final Vector2 LEFT = new Vector2(1, 0);
	public static final Vector2 DOWN = new Vector2(0, -1);
	public static final Vector2 RIGHT = new Vector2(-1, 0);

	// Array with all 4 directions
	public static final Vector2[] DIRECTIONS = {
		Vector2.UP,
		Vector2.LEFT,
		Vector2.DOWN,
		Vector2.RIGHT
	};
}
