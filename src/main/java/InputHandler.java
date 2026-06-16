import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import java.io.*;

public class InputHandler {

	// Interface that provides a single method that can be run
	// While we could use the Updateable interface, we may end up adding another method to it later, so it is best we use a separate interface here
	private interface Runnable {
		void run() throws FileNotFoundException;
	}

	// Map of keybindings, each KeyCode corresponds to an Updateable with a function
	public static HashMap<KeyCode, Runnable> keyMap = new HashMap<>();

	// Constructor that initializes with default keybindings
	// We use anonymous classes and lambda functions here to make handling keybindings simple and concise
	public InputHandler() throws FileNotFoundException {
		keyMap.put(KeyCode.F12, () -> Globals.toggleDebug());
		keyMap.put(KeyCode.W, () -> World.player.move(Vector2.UP));
		keyMap.put(KeyCode.A, () -> World.player.move(Vector2.LEFT));
		keyMap.put(KeyCode.S, () -> World.player.move(Vector2.DOWN));
		keyMap.put(KeyCode.D, () -> World.player.move(Vector2.RIGHT));
		keyMap.put(KeyCode.UP, () -> World.player.move(Vector2.UP));
		keyMap.put(KeyCode.LEFT, () -> World.player.move(Vector2.LEFT));
		keyMap.put(KeyCode.DOWN, () -> World.player.move(Vector2.DOWN));
		keyMap.put(KeyCode.RIGHT, () -> World.player.move(Vector2.RIGHT));
		keyMap.put(KeyCode.L, () -> World.update());
		keyMap.put(KeyCode.ENTER, () -> World.generateMap());
	}

	// Handle a single key press
	public static void handleKey(KeyEvent input) throws FileNotFoundException {

		KeyCode key = input.getCode();
		Group tiles = Crawler.tiles;

		if (Globals.status != Globals.GameStatus.LOADING) {
			try {
				keyMap.get(key).run();
			} catch (Exception e) {
				// Ignore any NullPointerExceptions, as they result from pressing a key that is not in the keyMap
				if (e.getClass() != NullPointerException.class) {
					IO.println(e); 
				}
			}
		}
		
		// Output the key pressed if debug is enabled
		if (Globals.debug) {
			IO.println("Key pressed: " + input.getCode().toString());
		}
	}
}
