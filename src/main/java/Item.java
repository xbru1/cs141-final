/*
 * Items give experience and fully heal when picked up by a LivingEntity
 * For now, only Players are able to interact with Items
 * I was planning on having multiple types of items initially, but that concept needed to be cut due to time constraints
 */

import java.io.*;
import java.util.*;

public class Item extends InteractableEntity {

	// Constructor
	public Item() throws FileNotFoundException {
		super();
		initialize();
	}


	// Items fully heal and give some experience based on the current floor when picked up
	public void onEnter(LivingEntity entity) {

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
