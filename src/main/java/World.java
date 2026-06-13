import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import java.io.*;
import java.util.*;

public class World {
	public static int turn = 0;
	public static Tile[] tileIndex = new Tile[] { 
		new Tile(false, "./assets/void.png"), 
		new Tile(true, "./assets/stone.png") 
	};

	//public static HashMap<String, Tile> tileIndex = new HashMap
	public static short[][] map = new short[Globals.mapSizeX][Globals.mapSizeY];

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
					//IO.println("REMOVING");
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
	public static boolean isWalkable(int x, int y) {
		//IO.println(entityAt(x, y));
		if (entityAt(x, y) || x < 0 || x > Globals.mapSizeX - 1 || y < 0 || y > Globals.mapSizeY - 1 || !tileIndex[map[x][y]].walkable ) {
			return false;
		} else {
			return true;
		}
	}
	
	// Returns whether or not there is an entity at the given x, y coordinate
	public static boolean entityAt(int x, int y) {
		return !(getEntityAt(x, y) == null);
	}

	// Returns the entity at a given position
	public static Entity getEntityAt(int x, int y) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).x == x && entities.get(i).y == y) {
				return entities.get(i);
			}
		}
		return null;
	}

	/*public Entity[] scanArea() {

	}*/
}
