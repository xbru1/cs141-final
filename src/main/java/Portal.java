/*
 * The Portal is an InteractableEntity that triggers a new map to be generated
 */

import java.util.*;
import java.io.*;

public class Portal extends InteractableEntity {

	// Constructor
	public Portal() throws FileNotFoundException {
		super();
		this.initialize();
	}

	// Code to run when stepped over by the player
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

	// Set the current sprite
	public void initialize() throws FileNotFoundException {
		this.setSprite("portal.png", Crawler.tiles);
	}
}
