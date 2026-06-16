import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import java.io.*;
import java.util.*;

public class World {
	public static int turn = 0;

	// The map is stored as a 2D array of bytes that each correspond to the index of this array
	public static Tile[] tileIndex = new Tile[] { 
		new Tile(false, "void.png"), 
		new Tile(true, "stone.png") 
	};

	// 2D array of bytes that each serve as tile IDs
	// We're most likely not going to need more than 256 tile IDs, so using byte saves on memory
	public static byte[][] map = new byte[Globals.mapSize.x][Globals.mapSize.y];

	// List that tracks all entities
	public static ArrayList<Entity> entities = new ArrayList<>();

	// We do keep track of the player in Entities, however having a pointer to it here as well makes it easier to access
	public static Player player;

	public static void initialize() throws FileNotFoundException {

		// Learned about serialization from https://www.baeldung.com/java-serialization
		//File playerFile = new File(Globals.playerFilePath);
		// Attempt to read the Player from a file
		try {
			FileInputStream FIS = new FileInputStream(Globals.playerFilePath);
			ObjectInputStream OIS = new ObjectInputStream(FIS);
			player = (Player) OIS.readObject();
			player.initialize();
			FIS.close();
		}
		// If it fails, we catch and create the file
		catch (Exception e) {
			IO.println("No player file present, creating one");
			player = new Player();
			savePlayer();
		}

		entities.add(player);
		IO.println("Initializing world...");
		update();
	}

	// Save the player to the save file location
	private static void savePlayer() throws FileNotFoundException {
		try {
			FileOutputStream FOS = new FileOutputStream(Globals.playerFilePath);
			ObjectOutputStream OOS = new ObjectOutputStream(FOS);
			OOS.writeObject(player);
			OOS.flush();
			OOS.close();
		} catch (Exception e) {
			IO.println(e);
		}

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
		Crawler.tiles.setTranslateX(-player.position.x * Globals.tileSize);
		Crawler.tiles.setTranslateY(-player.position.y * Globals.tileSize);
		Crawler.renderUI();
	}


	// Generate the map using a custom algorithm
	public static void generateMap(Random r) throws FileNotFoundException {

		clearMap();
		for (int i = 0; i < entities.size(); i++) {
			entities.set(i, null);
		}
		if (player != null) {
			player.position.set(0, 0);
			entities.add(player);
		}

		// Minimum of 8 rooms per map, up to 16 total rooms
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

		generateRooms(roomPositions, roomSizes);
		generateCorridors(roomPositions, roomSizes, r);
		generateEnemies(roomPositions, roomSizes, r);
		generateItems(roomPositions, roomSizes, r);
		generatePortal(roomPositions, roomSizes, r);

		Crawler.renderMap();
		savePlayer();
		update();

		// Call garbage collection
		System.gc();
	}

	// Overload that takes a seed to make a Random
	public static void generateMap(int seed) throws FileNotFoundException {
		Random r = new Random(seed);
		generateMap(r);
	}

	// Overload with a random seed
	public static void generateMap() throws FileNotFoundException {
		Random r = new Random();
		generateMap(r);
	}

	// Set all tiles on the map to ID 0
	private static void clearMap() {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				map[x][y] = 0;
			}
		}
		Crawler.tiles.getChildren().clear();
	}

	// Fill tiles in for rooms
	private static void generateRooms(Vector2[] roomPositions, Vector2[] roomSizes) {
		if (roomPositions.length != roomSizes.length) {
			throw new IllegalArgumentException();
		}
		// Fill in the rooms
		for (int i = 0; i < roomPositions.length; i++) {
			for (int x = 0; x < roomSizes[i].x; x++) {
				for (int y = 0; y < roomSizes[i].y; y++) {
					map[x + roomPositions[i].x][y + roomPositions[i].y] = 1;
				}
			}
		}

	}

	// Fill tiles in for corridors to connect the rooms
	private static void generateCorridors(Vector2[] roomPositions, Vector2[] roomSizes, Random r) {
		if (roomPositions.length != roomSizes.length) {
			throw new IllegalArgumentException();
		}	

		// Make the corridors
		for (int i = 0; i < roomPositions.length; i++) {				
			if (i != 0) {

				// Get where the corridor should start
				Vector2 pos = new Vector2(Math.min(roomPositions[i].x, roomPositions[i - 1].x), Math.min(roomPositions[i].y, roomPositions[i - 1].y));

				// Generate a random offset to add more variety to where corridors begin/end
				Vector2 offset = new Vector2(r.nextInt(Math.min(roomSizes[i].x, roomSizes[i - 1].x)), r.nextInt(Math.min(roomSizes[i].y, roomSizes[i - 1].y)));
				pos.add(offset);

				// Connect the positions of the rooms together
				// These four for loops go right/left and up/down as necessary to ensure there is a corridor of tiles between this room and the next
				// Corridors will naturally overlap with each other and other rooms to create an interesting map

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
		}
	}

	// Spawn enemies
	private static void generateEnemies(Vector2[] roomPositions, Vector2[] roomSizes, Random r) throws FileNotFoundException {

		if (roomPositions.length != roomSizes.length) {
			throw new IllegalArgumentException();
		}

		// Get a random number of Enemies
		int enemies = r.nextInt(10) + 5;

		// Spawn the enemies
		for (int i = 0; i < enemies; i++) {
			int room = r.nextInt(roomPositions.length);
			Enemy e = new Enemy((player.floors - 1) * 250);
			e.position.set(r.nextInt(roomSizes[room].x) + roomPositions[room].x, r.nextInt(roomSizes[room].y) + roomPositions[room].y);
			entities.add(e);
			e.render();
		}
	}

	// Spawn items
	private static void generateItems(Vector2[] roomPositions, Vector2[] roomSizes, Random r) throws FileNotFoundException {

		if (roomPositions.length != roomSizes.length) {
			throw new IllegalArgumentException();
		}

		int enemies = r.nextInt(10) + 5;

		for (int i = 0; i < enemies; i++) {
			int room = r.nextInt(roomPositions.length);
			Item item = new Item();
			item.position.set(findSpawnLocation(roomPositions, roomSizes, r));
			entities.add(item);
			item.render();
		}
	}

	// Spawn the portal to the next floor
	private static void generatePortal(Vector2[] roomPositions, Vector2[] roomSizes, Random r) throws FileNotFoundException {
		if (roomPositions.length != roomSizes.length) {
			throw new IllegalArgumentException();
		}

		Portal p = new Portal();
		p.position.set(findSpawnLocation(roomPositions, roomSizes, r));

		entities.add(p);
	}

	// Find suitable spawn locations
	private static Vector2 findSpawnLocation(Vector2[] roomPositions, Vector2[] roomSizes, Random r) {
		if (roomPositions.length != roomSizes.length) {
			throw new IllegalArgumentException();
		}

		int room = r.nextInt(roomSizes.length);
		Vector2 offset = new Vector2(r.nextInt(roomSizes[room].x), r.nextInt(roomSizes[room].y));
		return Vector2.sum(roomPositions[room], offset);
	}

	// Check whether or not a given tile can be walked on to
	// A tile can be walked onto if it is walkable, not outside the bounds of the map, and does not contain an Entity that is not an InteractableEntity
	public static boolean isWalkable(Vector2 position) {

		return !((entityAt(position) && !(getEntityAt(position) instanceof InteractableEntity)) || position.x < 0 || position.x > Globals.mapSize.x - 1 || position.y < 0 || position.y > Globals.mapSize.y - 1 || !tileIndex[map[position.x][position.y]].walkable);
	}
	
	// Returns whether or not there is an entity at the given x, y coordinate
	//public static boolean entityAt(int x, int y) {
	public static boolean entityAt(Vector2 position) {

		return !(getEntityAt(position) == null);
	}

	// Returns an Entity located at a given position, if there is one
	public static Entity getEntityAt(Vector2 position) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) != null && Vector2.equals(entities.get(i).position, position)) {
				return entities.get(i);
			}
		}
		return null;
	}

	// Returns the LivingEntity at a given position, if there is one
	public static LivingEntity getLivingEntityAt(Vector2 position) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) != null && (entities.get(i) instanceof LivingEntity) && Vector2.equals(entities.get(i).position, position)) {
				return (LivingEntity) entities.get(i);
			}
		}
		return null;
	}

	// Returns an InteractableEntity located at a given position, if there is one
	public static InteractableEntity getInteractableAt(Vector2 position) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) != null && (entities.get(i) instanceof InteractableEntity) && Vector2.equals(entities.get(i).position, position)) {
				return (InteractableEntity) entities.get(i);
			}
		}
		return null;
	}
}
