import javafx.scene.*;
import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import java.io.*;


public class Player extends LivingEntity {

	public int floors;
	//public ArrayList<Item> inventory = new ArrayList<>();

	public Player() throws FileNotFoundException {
		super(0, 50, 20, 10);
		floors = 0;
		this.initialize();
		//setSprite("player.png", Crawler.root);
	}

	// Moving the player involves moving the world around the player since the player is always in the center
	public boolean move(Vector2 vector) {
		// Update the world (pass a turn) if the player successfully moved
		if (super.move(vector) && !shouldRemove) {

			if (Globals.debug) {
				IO.println("Player Pos: " + this.position.toString());
			}

			if (World.getInteractableAt(this.position) != null) {
				World.getInteractableAt(this.position).onEnter(this);
			}

			World.update();

			return true;
		} else {
			return false;
		}
	}

	public void update() {
		super.update();
		if (this.sprite != null) {
			sprite.toFront();
		}
	}

	// Yes, this is empty. The player sprite is always at the center of the screen, so we don't need to update its position.
	public void render() {}

	public void remove() {
		IO.println("Game over");
		super.remove();
	}
	
	// Code to run when being read in from a file
	public void initialize() throws FileNotFoundException {
		super.initialize();
		setSprite("player.png", Crawler.root);
		this.calculateStats();
	}
}
