import java.util.*;
import java.io.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Crawler extends Application {


	// The InputHandler needs to be initialized first
	public InputHandler IH = new InputHandler();

	public static void main(String[] args) {
		launch(args);
	}

	// Tiles and other elements are kept in separate groups
	public static Group root = new Group();
	public static Group tiles = new Group();

	// Start 
	public void start(Stage stage) throws FileNotFoundException {
		// IO.println(System.getProperty("user.dir"));
		//IO.println(Screen.getPrimary().getVisualBounds().getHeight());

		// Testing enemy
		World.entities.add(new Enemy() {
			public void update() {
				this.experience = 100000;
				//this.hp -= 12;
				//IO.println(this.canAttack(World.player));
				IO.println(this.x);
				if (World.turn % 2 == 0) {
					this.move(new Vector2(0, -1));
				}
				super.update();
			}
		});

		//World.entities.get(0).setSprite("./assets/enemy.png", tiles);
		World.entities.get(0).x = 5;

		Text hello = Utils.text("Hello World!", 50, 50);
		hello.setFont(Font.font("Helvetica", FontWeight.BOLD, 36));
		hello.setFill(Color.WHITE);

		// Preparations
		World.initialize();
		World.generateMap();
		stage.setTitle("Dungeon Crawler");

		Scene scene = new Scene(root, Globals.resolutionX, Globals.resolutionY, Color.BLACK);

		root.getChildren().add(tiles);
		renderMap();

		stage.setScene(scene);
		stage.show();

		// Pass any inputs to the InputHandler
		scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			IH.handleKey(event);
		});
	}

	// Render the UI
	public void renderUI() {
		
	}

	// Render the TileMap from World
	public void renderMap() throws FileNotFoundException {
		for (int i = 0; i < Globals.mapSizeX; i++) {
			for (int o = 0; o < Globals.mapSizeY; o++) {
				renderTile(World.tileIndex[World.map[i][o]].texturePath, i, o);
			}
		}
		root.toFront();
		World.player.sprite.toFront();
		tiles.toBack();

					//ImageView r = Utils.imageView("./assets/stone.png", Globals.tileSize * i + Globals.resolutionX / 2 - Globals.tileSize / 2, Globals.tileSize * o + Globals.resolutionY / 2 - Globals.tileSize / 2, Globals.tileSize, Globals.tileSize);
	}

	private void renderTile(String path, int i, int o) throws FileNotFoundException {
		
		ImageView r = Utils.imageView(path, Globals.tileSize * i + Globals.resolutionX / 2 - Globals.tileSize / 2, Globals.tileSize * o + Globals.resolutionY / 2 - Globals.tileSize / 2, Globals.tileSize, Globals.tileSize);
		r.toBack();

	}
}

