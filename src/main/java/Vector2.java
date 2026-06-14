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

	// Distance formula
	public static double distance (Vector2 a, Vector2 b) {
		return Math.abs(Math.sqrt(Math.pow(b.x - a.x, 2) - Math.pow(b.y - a.y, 2)));
	}

	// Direction constants
	public static final Vector2 UP = new Vector2(0, 1);
	public static final Vector2 LEFT = new Vector2(1, 0);
	public static final Vector2 DOWN = new Vector2(0, -1);
	public static final Vector2 RIGHT = new Vector2(-1, 0);
}
