import javafx.scene.image.*;
import javafx.scene.*;
import java.io.*;
import java.awt.*;

public abstract class Entity implements Updateable, Renderable, Serializable {

	protected Vector2 position;

	// Transient makes this not get serialized
	public transient ImageView sprite;
	
	// Whether this entity should be removed on the next update (whether this Entity is considered dead?)
	public boolean shouldRemove = false;

	public Entity(Vector2 position){
		this.position = position;
	}

	public Entity() {
		this(new Vector2(0, 0));
	}

	// Moving the entity
	//public boolean move(int x, int y) {
	public boolean move(Vector2 vector) {
		if (!World.isWalkable(new Vector2(this.position.x - vector.x, this.position.y - vector.y))) {
			return false;
		} else {
			this.position.x -= vector.x;
			this.position.y -= vector.y;
			return true;
		}
	}

	// Code to run on each turn
	// If overriding, always call super.update() AFTER any changes to hp, otherwise the sprite will not properly unrender when the entity is deleted from World.entities
	public void update() {

		render();
		// Call the code to run on removal if this entity should be removed
		if (shouldRemove) {
			remove();
		}
	}

	// Code to render the Entity at the appropriate location on screen
	public void render() {
		this.sprite.setTranslateX(this.position.x * Globals.tileSize);
		this.sprite.setTranslateY(this.position.y * Globals.tileSize);
	}

	// Code to run before the Entity is removed
	public void remove() {
		this.shouldRemove = true;
		if (sprite != null) {
			sprite.setVisible(false);
		}
	}

	public void setSprite(String path, Group g) throws FileNotFoundException {

		this.sprite = Utils.imageView(path, (Globals.resolutionX / 2 - Globals.tileSize / 2 - this.position.x * Globals.tileSize), (Globals.resolutionY / 2 - Globals.tileSize / 2 - this.position.y * Globals.tileSize), Globals.tileSize, Globals.tileSize, g);
		sprite.toFront();
	}

	public String toString() {
		return String.format("%s", position.toString());
	}
}
