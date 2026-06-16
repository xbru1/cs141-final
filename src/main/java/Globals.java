/*
 * Global constants and variables
 * Methods for JavaFX objects are under Utils
 */

import java.io.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class Globals {

	public static String playerFilePath = "./save_data.txt";
	public enum GameStatus {
		NORMAL,
		LOADING,
		DEAD
	}
	// Current state of the game
	public static GameStatus status = GameStatus.NORMAL;
	
	// Automatically determine the screen resolution
	public static final int resolutionX = (int) Screen.getPrimary().getVisualBounds().getWidth();
	public static final int resolutionY = (int) Screen.getPrimary().getVisualBounds().getHeight();

	// How many vertical tiles should be on the screen
	private static final int viewportTileHeight = 15;
	public static final int tileSize = resolutionY / viewportTileHeight;

	// Size of the map in tiles
	public static int mapSizeX = 64;
	public static int mapSizeY = 32;
	public static Vector2 mapSize = new Vector2(64, 32);

	// Whether or not debug mode is on
	public static boolean debug = false;

	// Toggle debug mode
	public static void toggleDebug() {
		debug = !debug;
		IO.println((debug) ? "Debug is now on" : "Debug is now off");
	}
}
