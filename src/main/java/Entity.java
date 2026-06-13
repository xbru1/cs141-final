import javafx.scene.image.*;
import javafx.scene.*;
import java.io.*;
import java.awt.*;

public abstract class Entity implements Updateable, Renderable {

	protected Vector2 position;
	public int x;
	public int y;
	public ImageView sprite;
	public Group group;
	
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
		// Update the location of the sprite
		//this.sprite.setTranslateX(this.x * Globals.tileSize);
		//this.sprite.setTranslateY(this.y * Globals.tileSize);

		//position.setX(x);
		//position.x = x;
		//position.y = y;
		this.x = position.x;
		this.y = position.y;
		//position.setY(y);
		render();

		if (shouldRemove) {
			remove();
		}
		//this.sprite.toFront();
	}

	public void render() {
		this.sprite.setTranslateX(this.position.x * Globals.tileSize);
		this.sprite.setTranslateY(this.position.y * Globals.tileSize);
	}

	public void remove() {
		this.shouldRemove = true;
		if (sprite != null) {
			sprite.setVisible(false);
		}

		if (group != null) {
			group.setVisible(false);
			Crawler.root.getChildren().remove(group);
		}
	}

	//Globals.resolutionX / 2 - Globals.tileSize / 2, Globals.resolutionY / 2 - Globals.tileSize / 2, Globals.tileSize, Globals.tileSize
	public void setSprite(String path, Group g) throws FileNotFoundException {

		this.sprite = Utils.imageView(path, (Globals.resolutionX / 2 - Globals.tileSize / 2 - this.position.x * Globals.tileSize), (Globals.resolutionY / 2 - Globals.tileSize / 2 - this.position.y * Globals.tileSize), Globals.tileSize, Globals.tileSize, g);
		sprite.toFront();
	}

	public String toString() {
		return String.format("%s", position.toString());
	}
}
