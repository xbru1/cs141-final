/*
 * Items give experience and fully heal when picked up by a LivingEntity
 * For now, only Players are able to interact with Items
 */

import java.io.*;
import java.util.*;

public class Item extends InteractableEntity {

	// Constructor
	public Item() throws FileNotFoundException {
		super();
		initialize();
	}

	// Items permanently boost stats and fully heal when picked up
	public void onEnter(LivingEntity entity) {

		Random r = new Random();

		if (entity instanceof Player) {
			entity.experience += (int) Math.pow(10, (double) ((World.player.floors + 4) / 4));
		}
		entity.calculateStats();
		if (entity.hp < entity.maxHp) {
			entity.hp = entity.maxHp;
		}
		this.remove();
	}

	// Set the current sprite
	public void initialize() throws FileNotFoundException {
		this.setSprite("item.png", Crawler.tiles);
	}
}
