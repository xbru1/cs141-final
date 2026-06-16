import java.util.*;
import java.io.*;

// The portal is just an Item that also generates a new map
public class Portal extends InteractableEntity {
	public Portal() throws FileNotFoundException {
		super();
		this.setSprite("portal.png", Crawler.tiles);
	}
	public void onEnter(LivingEntity entity) {
		super.onEnter(entity);
		try {
			World.player.floors++;
			World.generateMap();

		} 
		catch(Exception e) {
			IO.println(e);
		}
	}
}
