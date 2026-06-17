/*
 * Class for Enemy LivingEntities that move towards and attack the player
 * They also give the player experience when destroyed
 */

import java.io.*;

public class Enemy extends LivingEntity {

	// Constructor
	public Enemy(int experience) throws FileNotFoundException {
		super(experience);
		setSprite("enemy.png", Crawler.tiles);
	}

	public Enemy() throws FileNotFoundException {
		this(0);
	}

	// Code to run on each turn
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

	// Give the player experience when this is destroyed
	public void remove() {
		World.player.experience += (this.getLevel() + 5) * 4;
		super.remove();
	}
}
