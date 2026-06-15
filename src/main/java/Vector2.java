// TODO: Refactor all code to use this class instead of x/y ints

// Class that stores an (x, y) position
// While we could use the built-in Point class, Point does not work in the way I would like
// For example, even though it stores positions as an int, getX() and getY() each return a double for some reason, which would result in an annoying amount of casting elsewhere
public class Vector2 {

	// The main fields
	public int x = 0;
	public int y = 0;

	// Constructor
	public Vector2(int x, int y) {
		set(x, y);
	}

	// Default Constructor
	public Vector2() {
		this(0, 0);
	}



	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
	}

	public void subtract(Vector2 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
	}

	public void set(Vector2 vector) {
		this.x = vector.x;
		this.y = vector.y;
	}

	public String toString() {
		return String.format("(%d,%d)", x, y);
	}

	// Return a direction vector corresponding the direction that will reduce the distance from this to a point if moved towards
	public Vector2 nearestDirectionTo(Vector2 point) {
		int lowestIndex = 0;
		// This can be any negative number
		double lowest = 99999;

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

	public static final Vector2[] DIRECTIONS = {
		Vector2.UP,
		Vector2.LEFT,
		Vector2.DOWN,
		Vector2.RIGHT
	};
}
