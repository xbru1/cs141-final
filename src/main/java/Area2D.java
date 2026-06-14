public class Area2D {
	public Vector2 position;
	public Vector2 size;

	public Area2D(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
	}

	public Area2D() {
		this(new Vector2(), new Vector2());
	}
}
