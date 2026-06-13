import javafx.scene.paint.*;

public class Tile {

	public final boolean walkable;
	public final String texturePath;
	//public final Color color;

	public Tile(boolean walkable, String texturePath) {
		this.walkable = walkable;
		this.texturePath = texturePath;
		//this.color = Color.BLACK;
	}

	/*public Tile(boolean walkable, Color color) {
		this.walkable = walkable;
		this.color = color;
		this.texturePath = "";
	}*/


	public Tile(boolean walkable) {
		this(walkable, "./assets/void.png");
	}

	public Tile(String texturePath) {
		this(true, texturePath);
	}

	public Tile() {
		this(false);
	}

	public String toString() {
		return String.format("%b, %s", this.walkable, this.texturePath);
	}
}
