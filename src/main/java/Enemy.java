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

	public void attack(LivingEntity entity) {
		// Prevent friendly fire
		if (entity instanceof Player) {
			super.attack(entity);
		}
	}

	// Heal the player a little when this is destroyed
	public void remove() {
		World.player.hp += (int) (0.25 * World.player.maxHp);
		World.player.experience += this.getLevel() * 2;
		super.remove();
	}
}
