import java.io.*;
import java.util.*;

public class Item extends InteractableEntity {

	public Item() throws FileNotFoundException {
		super();
		this.setSprite("./assets/item.png", Crawler.tiles);
	}

	// Items permanently boost stats and fully heal when picked up
	public void onEnter(LivingEntity entity) {

		Random r = new Random();
		entity.maxHpBase += r.nextInt(10);
		entity.defenseBase += r.nextInt(8);
		entity.attackBase += r.nextInt(8);
		entity.calculateStats();
		if (entity.hp < entity.maxHp) {
			entity.hp = entity.maxHp;
		}
		this.shouldRemove = true;
	}
}
