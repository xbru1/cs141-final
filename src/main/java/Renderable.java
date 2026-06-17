/*
 * Interface for objects that will move around on screen beyond just following the Player's movements
 */

import javafx.scene.*;
import javafx.scene.image.*;
import java.io.*;

public interface Renderable {
	public void setSprite(String path, Group g) throws FileNotFoundException;
	public void render();
	public void initialize() throws FileNotFoundException;
}
