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

	// AI to run on every turn, this will simply attempt to move to the player's position
	// The default AI is meant to be dumb and exploitable
	public void AI() {
		this.move(this.position.nearestDirectionTo(World.player.position));
	}
}
