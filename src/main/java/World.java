import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import java.io.*;
import java.util.*;

public class World {
	public static int turn = 0;

	// The map is stored as a 2D array of bytes that each correspond to the index of this array
	public static Tile[] tileIndex = new Tile[] { 
		new Tile(false, "./assets/void.png"), 
		new Tile(true, "./assets/stone.png") 
	};

	// 2D array of bytes that each serve as tile IDs
	// We're most likely not going to need more than 256 tile IDs, so using byte saves on memory
	public static byte[][] map = new byte[Globals.mapSize.x][Globals.mapSize.y];

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

		turn++;
		player.update();
		Crawler.tiles.setTranslateX(-player.position.x * Globals.tileSize);
		Crawler.tiles.setTranslateY(-player.position.y * Globals.tileSize);
	}


	// Generate the map using a custom algorithm
	public static void generateMap(Random r) {
		// TODO: Split this into different modules
		//Random r = new Random(seed);
		// Minimum of 8 rooms per map, up to 15 total rooms
		int rooms = r.nextInt(9) + 8;
		//int rooms = 2;

		// The first room always needs to be at (0, 0), as that is where the player spawns
		//Vector2 roomPosition = new Vector2(0, 0);
		Vector2[] roomSizes = new Vector2[rooms];
		Vector2[] roomPositions = new Vector2[rooms];
		roomPositions[0] = new Vector2(0, 0);
		roomSizes[0] = new Vector2(7, 7);

		for (int i = 1; i < rooms; i++) {
			roomSizes[i] = new Vector2(r.nextInt(7) + 3, r.nextInt(7) + 3);
			roomPositions[i] = new Vector2(r.nextInt(Globals.mapSize.x  - roomSizes[i].x), r.nextInt(Globals.mapSize.y  - roomSizes[i].y));
		}

		// For testing purposes
		roomPositions[1] = new Vector2(57, 25);
		roomSizes[1] = new Vector2(7, 7);

		// Generate rooms
		for (int i = 0; i < rooms; i++) {
			// Make corridors				
			if (i != 0) {

				// IO.println("Making room with size " + roomSizes[i].toString() + " and position " + roomPositions[i].toString());

				Vector2 pos = new Vector2(Math.min(roomPositions[i].x, roomPositions[i - 1].x), Math.min(roomPositions[i].y, roomPositions[i - 1].y));
				Vector2 offset = new Vector2(r.nextInt(Math.min(roomSizes[i].x, roomSizes[i - 1].x)), r.nextInt(Math.min(roomSizes[i].y, roomSizes[i - 1].y)));
				pos.add(offset);

				// Connect the positions of the rooms together
				// These four for loops go right/left and up/down as necessary to ensure there is a corridor between this room and the next
				// We don't bother with any other corridors since they naturally overlap with each other to allow multiple routes between rooms

				// Right
				for(; pos.x - offset.x < Math.max(roomPositions[i].x, roomPositions[i - 1].x); pos.x++) {
					map[pos.x][pos.y] = 1;
				}

				// Down
				for (; pos.y - offset.y < Math.max(roomPositions[i].y, roomPositions[i - 1].y); pos.y++) {
					map[pos.x][pos.y] = 1;
				}

				// Left
				for(; pos.x - offset.x > Math.min(roomPositions[i].x, roomPositions[i - 1].x); pos.x--) {
					map[pos.x][pos.y] = 1;
				}

				// Up
				for (; pos.y - offset.y > Math.min(roomPositions[i].y, roomPositions[i - 1].y); pos.y--) {
					map[pos.x][pos.y] = 1;
				}	
			}
						


			for (int x = 0; x < roomSizes[i].x; x++) {
				for (int y = 0; y < roomSizes[i].y; y++) {
					map[x + roomPositions[i].x][y + roomPositions[i].y] = 1;
				}
			}
			//lastRoomPosition.set(roomPosition);
		}
	}

	public static void generateMap(int seed) {
		Random r = new Random(seed);
		generateMap(r);
	}

	public static void generateMap() {
		Random r = new Random();
		generateMap(r);
	}

	// Find suitable spawn locations
	public static Vector2 findSpawn(Vector2[] roomSizes, Vector2[] roomPositions, Random r) {
		if (roomSizes.length != roomPositions.length) {
			throw new IllegalArgumentException();
		}
		int i = r.nextInt(roomSizes.length);
		Vector2 offset = new Vector2();
		return new Vector2();
	}

	public static void spawnEnemies() {
		
	}

	// Check whether or not a given tile can be walked on to
	// A tile can be walked onto if it is walkable, not outside the bounds of the map, and does not contain an entity
	public static boolean isWalkable(Vector2 position) {
		return !(entityAt(position) || position.x < 0 || position.x > Globals.mapSize.x - 1 || position.y < 0 || position.y > Globals.mapSize.y - 1 || !tileIndex[map[position.x][position.y]].walkable);
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
