import java.io.*;
import java.util.*;

public class Item extends Entity {

	public Item() throws FileNotFoundException {
		super();
		this.setSprite("./assets/item.png", Crawler.tiles);
	}

	// Items permanently boost stats and fully heal when picked up
	public void pickup(LivingEntity e) {

		Random r = new Random();
		e.maxHpBase += r.nextInt(10);
		e.defenseBase += r.nextInt(8);
		e.attackBase += r.nextInt(8);
		e.calculateStats();
		e.hp = e.maxHp;
		this.shouldRemove = true;
	}
}
