// TODO: Refactor all code to use this class instead of x/y ints

// Class that stores an (x, y) position
// While we could use the built-in Point class, Point simply acts weird
// For example, even though it stores positions as an int, getX() and getY() each return a double for some reason, which would result in an annoying amount of casting elsewhere
public class Vector2 {

	public int x = 0;
	public int y = 0;

	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2() {
		this(0, 0);
	}

	public String toString() {
		return String.format("(%d,%d)", x, y);
	}

	// Direction constants
	public static final Vector2 UP = new Vector2(0, 1);
	public static final Vector2 LEFT = new Vector2(1, 0);
	public static final Vector2 DOWN = new Vector2(0, -1);
	public static final Vector2 RIGHT = new Vector2(-1, 0);
}
