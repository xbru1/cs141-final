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
		//Random r = new Random(seed);
		// Minimum of 6 rooms per map, up to 11 total rooms
		//int rooms = r.nextInt(6) + 6;
		int rooms = 11;

		// The first room always needs to be at (0, 0), as that is where the player spawns
		//Vector2 roomPosition = new Vector2(0, 0);
		Vector2 roomSize = new Vector2();
		Vector2[] roomSizes = new Vector2[rooms];
		Vector2[] roomPositions = new Vector2[rooms];
		Vector2 roomPosition = new Vector2();
		Vector2 lastRoomPosition = new Vector2();

		roomPositions[0] = new Vector2(0, 0);
		roomSizes[0] = new Vector2(5, 5);

		for (int i = 1; i < rooms; i++) {
			roomSizes[i] = new Vector2(r.nextInt(5) + 5, r.nextInt(5) + 5);
			roomPositions[i] = new Vector2(r.nextInt(Globals.mapSize.x  - roomSizes[i].x), r.nextInt(Globals.mapSize.y  - roomSizes[i].y));
		}

		// Generate rooms
		for (int i = 0; i < rooms; i++) {
			// Make corridors				
			if (i != 0) {
				int x = Math.min(roomPositions[i].x, roomPositions[i - 1].x);
				int y = Math.min(roomPositions[i].y, roomPositions[i - 1].y);

				// Connect the positions of the rooms together
				// These four for loops go right/left and up/down as necessary to create corridors between rooms

				// Right
				for(; x < Math.max(roomPositions[i].x, roomPositions[i - 1].x); x++) {
					map[x][y] = 1;
				}

				// Down
				for (; y < Math.max(roomPositions[i].y, roomPositions[i - 1].y); y++) {
					map[x][y] = 1;
				}

				// Left
				for(; x > Math.min(roomPositions[i].x, roomPositions[i - 1].x); x--) {
					map[x][y] = 1;
				}

				// Up
				for (; y > Math.min(roomPositions[i].y, roomPositions[i - 1].y); y--) {
					map[x][y] = 1;
				}



				/*x = Math.max(roomPositions[i].x, roomPositions[i - 1].x);
				for(; x > Math.max(roomPositions[i].x, roomPositions[i - 1].x) - Math.min(roomPositions[i].x, roomPositions[i - 1].x); x--) {
					map[x][y] = 1;
				}

				y = Math.max(roomPositions[i].y, roomPositions[i - 1].y);
				for (; y > Math.max(roomPositions[i].y, roomPositions[i - 1].y) - Math.min(roomPositions[i].y, roomPositions[i - 1].y); y++) {
					map[roomPositions[i - 1].x][y] = 1;
				}*/
	
			}
						
			IO.println("Making room with size " + roomSizes[i].toString() + " and position " + roomPositions[i].toString());

			for (int x = 0; x < roomSizes[i].x; x++) {
				for (int y = 0; y < roomSizes[i].y; y++) {
					map[x + roomPositions[i].x][y + roomPositions[i].y] = 1;
				}
			}
			//lastRoomPosition.set(roomPosition);
		}
		/*
		for (int i = 0; i < map.length; i++) {
			for (int o = 0; o < map[i].length; o++) {
				if (i % 2 == 0 && o % 2 == 0) {
					map[i][o] = 0;
				} else {
					map[i][o] = 1;
				}
			}
		} */
	}

	public static void generateMap(int seed) {
		Random r = new Random(seed);
		generateMap(r);
	}

	public static void generateMap() {
		Random r = new Random();
		generateMap(r);
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
