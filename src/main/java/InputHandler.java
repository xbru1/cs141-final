import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;

public class InputHandler {

	private interface Runnable {
		void run();
	}

	// Map of keybindings, each KeyCode corresponds to an Updateable with a function
	public static HashMap<KeyCode, Runnable> keyMap = new HashMap<>();

	// Constructor that initializes with default keybindings
	// We use anonymous classes and lambda functions here to make handling keybindings simple and concise
	public InputHandler() {
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
	}

	// Handle a single key press
	public static void handleKey(KeyEvent input) {

		KeyCode key = input.getCode();
		Group tiles = Crawler.tiles;

		try {
			keyMap.get(key).run();
		} catch (Exception e) {
			if (e.getClass() == NullPointerException.class) {
				IO.println(e); 
			}
		}

		// Output the key pressed if debug is enabled
		if (Globals.debug) {
			IO.println("Key pressed: " + input.getCode().toString());
		}
	}
}
