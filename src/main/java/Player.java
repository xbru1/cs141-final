import javafx.scene.*;
import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import java.io.*;


public class Player extends LivingEntity implements Serializable {

	//public ArrayList<Item> inventory = new ArrayList<>();

	public Player() throws FileNotFoundException {
		super(0, 50, 20, 30);
		setSprite("./assets/player.png", Crawler.root);
	}

	// Moving the player involves moving the world around the player since the player is always in the center
	public boolean move(Vector2 vector) {
		// Update the world (pass a turn) if the player successfully moved
		if (super.move(vector) && !shouldRemove) {

			if (Globals.debug) {
				IO.println("Player Pos: " + this.position.toString());
			}

			if (World.getItemAt(this.position) != null) {
				World.getItemAt(this.position).pickup(this);
			}

			World.update();

			return true;
		} else {
			return false;
		}
	}

	public void update() {
		if (this.hp <= 0) {
			sprite.setVisible(false);
			Crawler.tiles.getChildren().remove(sprite);
			shouldRemove = true;
		}

		if (Globals.debug) {
			IO.println("Player HP: " + this.hp);
		}
		sprite.toFront();
	}

	public void remove() {
		IO.println("Game over");
		super.remove();
	}
}
