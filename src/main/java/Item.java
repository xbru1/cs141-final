/*
 * Items give experience and fully heal when picked up by a LivingEntity
 * For now, only Players are able to interact with Items
 */

import java.io.*;
import java.util.*;

public class Item extends InteractableEntity {

	public Item() throws FileNotFoundException {
		super();
		initialize();
	}

	// Items permanently boost stats and fully heal when picked up
	public void onEnter(LivingEntity entity) {

		Random r = new Random();
		/*entity.maxHpBase += r.nextInt(10);
		entity.defenseBase += r.nextInt(8);
		entity.attackBase += r.nextInt(8);*/
		if (entity instanceof Player) {
			entity.experience += 250 * World.player.floors;
		}
		entity.calculateStats();
		if (entity.hp < entity.maxHp) {
			entity.hp = entity.maxHp;
		}
		this.remove();
	}

	public void initialize() throws FileNotFoundException {
		this.setSprite("item.png", Crawler.tiles);
	}
}
