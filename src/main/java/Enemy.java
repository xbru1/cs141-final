import java.io.*;

public class Enemy extends LivingEntity implements Updateable {

	public Enemy() throws FileNotFoundException {
		super();
		setSprite("./assets/enemy.png", Crawler.tiles);
	}

	public void update() {
		AI();
		super.update();
	}

	public void AI() {
		// This is gonna get cursed
		Vector2[] directions = {
			Vector2.UP,
			Vector2.LEFT,
			Vector2.DOWN,
			Vector2.RIGHT,
		};

		int lowestIndex = 0;
		double lowest = 0;
		for (int i = 0; i < directions.length; i++) {
			double distance = Math.abs((double) Vector2.distance(new Vector2(this.position.x - directions[i].x, this.position.y - directions[i].y), World.player.position));
			//IO.println(distance);
			if (distance < lowest || lowest == 0) {
				lowestIndex = i;
				lowest = distance;
			}

		}

		this.move(directions[lowestIndex]);
	}
}
