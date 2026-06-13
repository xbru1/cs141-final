import javafx.scene.*;
import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import java.io.*;


public class Player extends LivingEntity {

	//public ArrayList<Item> inventory = new ArrayList<>();

	public Player() throws FileNotFoundException {
		super(0, 100, 5, 20, 1);
		setSprite("./assets/player.png", Crawler.root);
	}

	// Moving the player involves moving the world around the player since the player is always in the center
	public boolean move(Vector2 vector) {
		// Update the world (pass a turn) if the player successfully moved
		if (super.move(vector) && !shouldRemove) {
			World.update();
			if (Globals.debug) {
				IO.println("X: " + this.position.x + ", Y: " + this.position.y);
			}
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
			IO.println("Game over");
		}
		sprite.toFront();
	}
}
