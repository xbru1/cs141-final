import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import java.io.*;
import java.util.*;

public class World {
	public static int turn = 0;

	// The map is stored as a 2D array of shorts that each correspond to the index of this array
	public static Tile[] tileIndex = new Tile[] { 
		new Tile(false, "./assets/void.png"), 
		new Tile(true, "./assets/stone.png") 
	};

	// 2D array of shorts that each serve as tile IDs
	public static short[][] map = new short[Globals.mapSize.x][Globals.mapSize.y];

	// List that tracks all entities
	public static ArrayList<Entity> entities = new ArrayList<>();

	// We do keep track of the player in Entities, however having a pointer to it here as well makes it easier to access
	public static Player player;

	public static void initialize() throws FileNotFoundException {
		player = new Player();
		entities.add(player);
		IO.println("initializing world");
		update();
	}

	// Updates performed on each turn
	public static void update() {
		if (player.shouldRemove) {
			
		}

		// Handle entities
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) != null) {
				entities.get(i).update();

				// Remove entity it should be removed
				if (entities.get(i).shouldRemove) {
					entities.set(i, null);
				}
			}
		}

		// Clean up any null pointers in Entities
		entities.removeIf(Objects::isNull);

		//IO.println("updating");
		turn++;
		player.update();
		Crawler.tiles.setTranslateX(-player.position.x * Globals.tileSize);
		Crawler.tiles.setTranslateY(-player.position.y * Globals.tileSize);
	}


	// Generate the map using a Random
	public static void generateMap(Random r) {
		//Random r = new Random(seed);
		// Minimum of 4 rooms per floor, up to 11 total rooms
		int rooms = r.nextInt(6) + 6;
		for (int i = 0; i < map.length; i++) {
			for (int o = 0; o < map[i].length; o++) {
				if (i % 2 == 0 && o % 2 == 0) {
					map[i][o] = 0;
				} else {
					map[i][o] = 1;
				}
			}
		} 
	}

	public static void generateMap(int seed) {
		Random r = new Random(seed);
		generateMap(r);
	}

	public static void generateMap() {
		//generateMap(Math.random());
		Random r = new Random();
		generateMap(r);
	}

	public static void spawnEnemies() {
		
	}

	// Check whether or not a given tile can be walked on to
	// A tile can be walked onto if it is walkable, not outside the bounds of the map, and does not contain an entity
	public static boolean isWalkable(Vector2 position) {
		return !(entityAt(position) || position.x < 0 || position.x > Globals.mapSize.x - 1 || position.y < 0 || position.y > Globals.mapSize.y - 1 || !tileIndex[map[position.x][position.y]].walkable);
		/*if (entityAt(position) || position.x < 0 || position.x > Globals.mapSize.x - 1 || position.y < 0 || position.y > Globals.mapSize.y - 1 || !tileIndex[map[position.x][position.y]].walkable) {
			return false;
		} else {
			return true;
		}*/
	}
	
	// Returns whether or not there is an entity at the given x, y coordinate
	//public static boolean entityAt(int x, int y) {
	public static boolean entityAt(Vector2 position) {

		return !(getEntityAt(position) == null);
	}

	// Returns the entity at a given position
	public static Entity getEntityAt(Vector2 position) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).position.x == position.x && entities.get(i).position.y == position.y) {
				return entities.get(i);
			}
		}
		return null;
	}

	/*public Entity[] scanArea() {

	}*/
}
