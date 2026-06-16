/*
 * Class for a Tile that renders the map
 * Its only attribute other than texture path is whether or not it is considered walkable
 */


import javafx.scene.paint.*;

public class Tile {

	public final boolean walkable;
	public final String texturePath;

	// Constructor from walkability and texture path
	public Tile(boolean walkable, String texturePath) {
		this.walkable = walkable;
		this.texturePath = texturePath;
		//this.color = Color.BLACK;
	}

	// Constructor from walkability alone, will use a placeholder texture
	public Tile(boolean walkable) {
		this(walkable, "missing.png");
	}

	// Constructor from texture path alone
	public Tile(String texturePath) {
		this(true, texturePath);
	}

	// Constructor from nothing
	public Tile() {
		this(false);
	}

	// Convert to a string
	public String toString() {
		return String.format("%b, %s", this.walkable, this.texturePath);
	}
}
