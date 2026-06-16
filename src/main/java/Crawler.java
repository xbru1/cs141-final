/*
 * The "main" class
 * This class handles most of the rendering, and also initializes the InputHandler
 */

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

	// The InputHandler does need to be instantiated later
	public InputHandler IH;

	// Tiles and other elements are kept in separate groups
	public static Group root = new Group();
	public static Group tiles = new Group();
	public static Text UI; 

	// Main
	public static void main(String[] args) {
		launch(args);
	}

	// Start
	public void start(Stage stage) throws FileNotFoundException {

		// The InputHandler needs to be initialized first
		IH = new InputHandler();

		// Prepare the UI
		UI = Utils.text("if this string is empty it won't render for some reason", 50, 50);
		UI.setFont(Font.font("Helvetica", FontWeight.BOLD, 36));
		UI.setFill(Color.WHITE);

		// Preparations
		startGame();
		stage.setTitle("Dungeon Crawler");

		Scene scene = new Scene(root, Globals.resolutionX, Globals.resolutionY, Color.BLACK);
		root.getChildren().add(tiles);
		renderMap();

		// Show the scene
		stage.setScene(scene);
		stage.show();

		// Pass any inputs received to the InputHandler
		scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			try {
				IH.handleKey(event);
			} 
			catch (Exception e) {
				IO.println(e);	
			}
		});
	}

	// Render the UI by updating the text
	public static void renderUI() {
		if (!World.player.shouldRemove) {
			UI.setText(String.format("HP: %d/%d\nAttack: %d\nDefense: %d\nLevel: %d\nDungeon B%dF", World.player.hp, World.player.maxHp, World.player.attack, World.player.defense, World.player.getLevel(), World.player.floors));


		} else {
			UI.setText("Game over, press ENTER to restart");
		}

		// If debug is on, note it in the UI
		if (Globals.debug) {
			UI.setText(UI.getText() + "\nDEBUG IS ON, TOGGLE WITH F12");
		}
	}

	// Render the TileMap from World
	public static void renderMap() throws FileNotFoundException {
		for (int x = 0; x < Globals.mapSizeX; x++) {
			for (int y = 0; y < Globals.mapSizeY; y++) {
				renderTile(World.tileIndex[World.map[x][y]].texturePath, x, y);
			}
		}
		root.toFront();
		World.player.sprite.toFront();
		tiles.toBack();
	}

	// Start the game by initializing the World
	public static void startGame() throws FileNotFoundException {
		World.initialize();
		World.generateMap();
	}

	// Render a single tile
	private static void renderTile(String path, int i, int o) throws FileNotFoundException {
		ImageView r = Utils.imageView(path, Globals.tileSize * i + Globals.resolutionX / 2 - Globals.tileSize / 2, Globals.tileSize * o + Globals.resolutionY / 2 - Globals.tileSize / 2, Globals.tileSize, Globals.tileSize);
		r.toBack();
	}
}

